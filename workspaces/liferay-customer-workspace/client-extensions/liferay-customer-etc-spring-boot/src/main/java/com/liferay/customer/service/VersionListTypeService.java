/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Ryan Schuhler
 */
@Component
public class VersionListTypeService extends BaseService {

	@Scheduled(cron = "${liferay.customer.version.list.type.cron}")
	public void scheduled() throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("Updating list type definitions");
		}

		JSONArray releasesJSONArray = new JSONArray(
			get(
				StringPool.BLANK,
				UriComponentsBuilder.fromUriString(
					_liferayCustomerVersionListTypeReleasesURL
				).build(
				).toUri()));

		_updateListTypeDefinition(
			_liferayCustomerVersionListTypeDXPMajorERC, "DXP Major Version",
			_getDXPMajorVersions(releasesJSONArray));

		_updateListTypeDefinition(
			_liferayCustomerVersionListTypeDXPMinorERC, "DXP Minor Version",
			_getDXPMinorVersions(releasesJSONArray));
	}

	private int _compareProductVersions(
		String productVersion1, String productVersion2) {

		int[] quarterlyVersion1 = _getQuarterlyVersion(productVersion1);
		int[] quarterlyVersion2 = _getQuarterlyVersion(productVersion2);

		if (quarterlyVersion1 == null) {
			if (quarterlyVersion2 != null) {
				return 1;
			}

			return productVersion2.compareTo(productVersion1);
		}

		if (quarterlyVersion2 == null) {
			return -1;
		}

		for (int i = 0; i < quarterlyVersion1.length; i++) {
			if (quarterlyVersion1[i] != quarterlyVersion2[i]) {
				return quarterlyVersion2[i] - quarterlyVersion1[i];
			}
		}

		return productVersion1.compareTo(productVersion2);
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	private List<String> _getDXPMajorVersions(JSONArray releasesJSONArray) {
		TreeSet<String> versions = new TreeSet<>();

		for (int i = 0; i < releasesJSONArray.length(); i++) {
			JSONObject releaseJSONObject = releasesJSONArray.getJSONObject(i);

			String product = releaseJSONObject.optString("product");
			String productGroupVersion = releaseJSONObject.optString(
				"productGroupVersion");

			if (Validator.isNull(product) ||
				Validator.isNull(productGroupVersion) ||
				!product.equals("dxp") ||
				!_isSupported(releaseJSONObject.optJSONArray("tags"))) {

				continue;
			}

			String productMajorVersion = releaseJSONObject.optString(
				"productMajorVersion");

			if (Validator.isNull(productMajorVersion)) {
				productMajorVersion =
					StringUtil.toUpperCase(product) + StringPool.SPACE +
						StringUtil.toUpperCase(productGroupVersion);
			}

			versions.add(productMajorVersion);
		}

		return new ArrayList<>(versions);
	}

	private List<String> _getDXPMinorVersions(JSONArray releasesJSONArray) {
		TreeSet<String> versions = new TreeSet<>(this::_compareProductVersions);

		for (int i = 0; i < releasesJSONArray.length(); i++) {
			JSONObject releaseJSONObject = releasesJSONArray.getJSONObject(i);

			String product = releaseJSONObject.optString("product");
			String productVersion = releaseJSONObject.optString(
				"productVersion");

			if (Validator.isNull(product) || Validator.isNull(productVersion) ||
				!product.equals("dxp")) {

				continue;
			}

			versions.add(productVersion);
		}

		return new ArrayList<>(versions);
	}

	private int[] _getQuarterlyVersion(String productVersion) {
		Matcher matcher = _quarterlyVersionPattern.matcher(productVersion);

		if (!matcher.find()) {
			return null;
		}

		return new int[] {
			GetterUtil.getInteger(matcher.group(1)),
			GetterUtil.getInteger(matcher.group(2)),
			GetterUtil.getInteger(matcher.group(3))
		};
	}

	private boolean _isSupported(JSONArray tagsJSONArray) {
		if (tagsJSONArray == null) {
			return false;
		}

		for (int i = 0; i < tagsJSONArray.length(); i++) {
			if (StringUtil.equals(tagsJSONArray.optString(i), "supported")) {
				return true;
			}
		}

		return false;
	}

	private void _updateListTypeDefinition(
			String externalReferenceCode, String name, List<String> values)
		throws Exception {

		JSONObject listTypeDefinitionJSONObject = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/headless-admin-list-type/v1.0/list-type-definitions" +
						"/by-external-reference-code/" + externalReferenceCode
				).build(
				).toUri()));

		JSONArray listTypeEntriesJSONArray = new JSONArray();

		for (String value : values) {
			if (Validator.isNull(value)) {
				continue;
			}

			listTypeEntriesJSONArray.put(
				new JSONObject(
				).put(
					"externalReferenceCode",
					value.toUpperCase(
					).replaceAll(
						"[^A-Z0-9]", "_"
					)
				).put(
					"key",
					value.toLowerCase(
					).replaceAll(
						"[^a-z0-9]", ""
					)
				).put(
					"name", value
				).put(
					"name_i18n",
					new JSONObject(
					).put(
						"en-US", value
					)
				));
		}

		put(
			_getAuthorization(),
			new JSONObject(
			).put(
				"externalReferenceCode", externalReferenceCode
			).put(
				"listTypeEntries", listTypeEntriesJSONArray
			).put(
				"name", name
			).put(
				"name_i18n",
				new JSONObject(
				).put(
					"en-US", name
				)
			).toString(),
			UriComponentsBuilder.fromPath(
				"/o/headless-admin-list-type/v1.0/list-type-definitions/" +
					listTypeDefinitionJSONObject.getInt("id")
			).build(
			).toUri());

		if (_log.isInfoEnabled()) {
			_log.info("Updated list type definition " + externalReferenceCode);
		}
	}

	private static final Log _log = LogFactory.getLog(
		VersionListTypeService.class);

	private static final Pattern _quarterlyVersionPattern = Pattern.compile(
		"^DXP (\\d{4})\\.Q([1-4])\\.(\\d+)");

	@Value("${liferay.customer.version.list.type.dxp.major.erc}")
	private String _liferayCustomerVersionListTypeDXPMajorERC;

	@Value("${liferay.customer.version.list.type.dxp.minor.erc}")
	private String _liferayCustomerVersionListTypeDXPMinorERC;

	@Value("${liferay.customer.version.list.type.releases.url}")
	private String _liferayCustomerVersionListTypeReleasesURL;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}