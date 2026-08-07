/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.petra.string.StringBundler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class ProvisioningService {

	public HttpResponse<String> activateCloudEnvironment(
			String environmentId, String activationCode, String jwt)
		throws Exception {

		HttpRequest httpRequest = HttpRequest.newBuilder(
		).uri(
			URI.create(
				StringBundler.concat(
					_provisioningURL,
					"/o/provisioning-rest/v1.0/cloud/environments/",
					environmentId, "/activation"))
		).header(
			"Activation-Code", activationCode
		).header(
			"Content-Type", "text/plain"
		).POST(
			HttpRequest.BodyPublishers.ofString(jwt)
		).build();

		HttpClient httpClient = HttpClient.newHttpClient();

		return httpClient.send(
			httpRequest, HttpResponse.BodyHandlers.ofString());
	}

	public HttpResponse<String> getCloudEnvironmentManifest(
			String environmentId, String jwt)
		throws Exception {

		HttpRequest httpRequest = HttpRequest.newBuilder(
		).uri(
			URI.create(
				StringBundler.concat(
					_provisioningURL,
					"/o/provisioning-rest/v1.0/cloud/environments/",
					environmentId, "/manifest"))
		).header(
			"Content-Type", "text/plain"
		).POST(
			HttpRequest.BodyPublishers.ofString(jwt)
		).build();

		HttpClient httpClient = HttpClient.newHttpClient();

		return httpClient.send(
			httpRequest, HttpResponse.BodyHandlers.ofString());
	}

	@Value("${liferay.customer.provisioning.url}")
	private String _provisioningURL;

}