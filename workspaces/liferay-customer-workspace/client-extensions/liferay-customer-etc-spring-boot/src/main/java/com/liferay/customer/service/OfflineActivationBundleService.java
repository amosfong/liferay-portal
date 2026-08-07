/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.customer.exception.AddOnsUnavailableException;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Path;

import java.security.MessageDigest;

import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class OfflineActivationBundleService {

	public Path createBundle(String environmentId, String dxpVersion)
		throws Exception {

		JSONObject manifestJSONObject = _getManifestJSONObject(
			environmentId, dxpVersion);

		Path path = Files.createTempFile("offline-activation-bundle-", ".zip");

		try {
			try (ZipOutputStream zipOutputStream = new ZipOutputStream(
					Files.newOutputStream(path))) {

				zipOutputStream.putNextEntry(new ZipEntry("manifest.json"));

				zipOutputStream.write(
					manifestJSONObject.toString(
						2
					).getBytes(
						"UTF-8"
					));

				zipOutputStream.closeEntry();

				JSONArray addOnsJSONArray = manifestJSONObject.optJSONArray(
					"add-ons");

				if (addOnsJSONArray != null) {
					for (int i = 0; i < addOnsJSONArray.length(); i++) {
						_writeAddOn(
							zipOutputStream, addOnsJSONArray.getJSONObject(i),
							environmentId);
					}
				}
			}

			return path;
		}
		catch (Exception exception) {
			Files.deleteIfExists(path);

			throw exception;
		}
	}

	private String _download(
			String downloadURL, String environmentId, Path path)
		throws Exception {

		HttpRequest httpRequest = HttpRequest.newBuilder(
		).uri(
			URI.create(downloadURL)
		).header(
			"Content-Type", "text/plain"
		).POST(
			HttpRequest.BodyPublishers.ofString(
				_masterKeyService.createSignedJWT(
					HashMapBuilder.<String, Object>put(
						"environmentID", environmentId
					).build()))
		).build();

		HttpClient httpClient = HttpClient.newBuilder(
		).followRedirects(
			HttpClient.Redirect.NORMAL
		).build();

		HttpResponse<InputStream> httpResponse = httpClient.send(
			httpRequest, HttpResponse.BodyHandlers.ofInputStream());

		int statusCode = httpResponse.statusCode();

		if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
			throw new Exception(
				StringBundler.concat(
					"Unable to download add-on from ", downloadURL, ": ",
					statusCode));
		}

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

		try (InputStream inputStream = httpResponse.body();
			OutputStream outputStream = Files.newOutputStream(path)) {

			byte[] buffer = new byte[_BUFFER_SIZE];

			while (true) {
				int count = inputStream.read(buffer);

				if (count == -1) {
					break;
				}

				messageDigest.update(buffer, 0, count);

				outputStream.write(buffer, 0, count);
			}
		}

		return _toHexString(messageDigest.digest());
	}

	private void _downloadAddOn(
			String downloadURL, String environmentId, String sha256Checksum,
			Path path)
		throws Exception {

		String actualChecksum = null;

		for (int i = 1; i <= _MAX_ATTEMPTS; i++) {
			actualChecksum = _download(downloadURL, environmentId, path);

			if (Validator.isNull(sha256Checksum)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Skipping checksum verification, no checksum was " +
							"published for " + downloadURL);
				}

				return;
			}

			if (StringUtil.equalsIgnoreCase(actualChecksum, sha256Checksum)) {
				return;
			}

			_log.error(
				StringBundler.concat(
					"Checksum mismatch for ", downloadURL, " on attempt ", i,
					" of ", _MAX_ATTEMPTS, ", expected ", sha256Checksum,
					" but got ", actualChecksum));
		}

		throw new Exception(
			StringBundler.concat(
				"Checksum mismatch for ", downloadURL, " after ", _MAX_ATTEMPTS,
				" attempts, expected ", sha256Checksum, " but got ",
				actualChecksum));
	}

	private String _getAddOnFileName(
		JSONObject addOnJSONObject, String downloadURL) {

		String fileName = downloadURL.substring(
			downloadURL.lastIndexOf('/') + 1);

		int index = fileName.indexOf('?');

		if (index != -1) {
			fileName = fileName.substring(0, index);
		}

		if (fileName.endsWith(".lpkg")) {
			return fileName;
		}

		return addOnJSONObject.optString("productId") + ".lpkg";
	}

	private JSONObject _getManifestJSONObject(
			String environmentId, String dxpVersion)
		throws Exception {

		Map<String, Object> claims = HashMapBuilder.<String, Object>put(
			"dxpVersion", dxpVersion
		).put(
			"environmentID", environmentId
		).build();

		HttpResponse<String> httpResponse =
			_provisioningService.getCloudEnvironmentManifest(
				environmentId, _masterKeyService.createSignedJWT(claims));

		int statusCode = httpResponse.statusCode();

		if (statusCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
			throw new Exception(
				StringBundler.concat(
					"Unable to get the manifest for environment ",
					environmentId, ": ", statusCode, " ", httpResponse.body()));
		}

		return new JSONObject(httpResponse.body());
	}

	private String _toHexString(byte[] bytes) {
		StringBundler sb = new StringBundler(bytes.length);

		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	private void _writeAddOn(
			ZipOutputStream zipOutputStream, JSONObject addOnJSONObject,
			String environmentId)
		throws Exception {

		String downloadURL = addOnJSONObject.optString("downloadURL");

		if (Validator.isNull(downloadURL)) {
			throw new AddOnsUnavailableException(
				"Add-on has no download URL: " + addOnJSONObject.toString());
		}

		Path path = Files.createTempFile("offline-activation-add-on-", ".lpkg");

		try {
			_downloadAddOn(
				downloadURL, environmentId,
				addOnJSONObject.optString("sha256Checksum"), path);

			zipOutputStream.putNextEntry(
				new ZipEntry(
					"add-ons/" +
						_getAddOnFileName(addOnJSONObject, downloadURL)));

			Files.copy(path, zipOutputStream);

			zipOutputStream.closeEntry();
		}
		finally {
			Files.deleteIfExists(path);
		}
	}

	private static final int _BUFFER_SIZE = 8192;

	private static final int _MAX_ATTEMPTS = 3;

	private static final Log _log = LogFactory.getLog(
		OfflineActivationBundleService.class);

	@Autowired
	private MasterKeyService _masterKeyService;

	@Autowired
	private ProvisioningService _provisioningService;

}