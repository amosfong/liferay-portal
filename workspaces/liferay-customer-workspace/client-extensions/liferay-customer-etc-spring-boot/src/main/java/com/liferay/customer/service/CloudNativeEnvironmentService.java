/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Amos Fong
 */
@Component
public class CloudNativeEnvironmentService extends BaseService {

	public JSONObject fetchCloudNativeEnvironment(
			String authorization, String activationCode)
		throws Exception {

		return _fetchCloudNativeEnvironment(
			authorization, "activationCode eq '" + activationCode + "'");
	}

	public JSONObject fetchCloudNativeEnvironmentByEnvironmentId(
			String authorization, String environmentId)
		throws Exception {

		return _fetchCloudNativeEnvironment(
			authorization, "environmentId eq '" + environmentId + "'");
	}

	public JSONObject updateCloudNativeEnvironment(
			String authorization, JSONObject cloudNativeEnvironmentJSONObject,
			String activationMethod, String environmentId,
			String environmentName)
		throws Exception {

		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put(
			"activationMethod", activationMethod
		).put(
			"environmentId", environmentId
		).put(
			"environmentName", environmentName
		);

		String externalReferenceCode =
			cloudNativeEnvironmentJSONObject.getString("externalReferenceCode");

		return new JSONObject(
			patch(
				authorization, requestJSONObject.toString(),
				UriComponentsBuilder.fromPath(
					_PATH + "/by-external-reference-code/" +
						externalReferenceCode
				).build(
				).toUri()));
	}

	private JSONObject _fetchCloudNativeEnvironment(
			String authorization, String filterString)
		throws Exception {

		JSONObject jsonObject = new JSONObject(
			get(
				authorization,
				UriComponentsBuilder.fromPath(
					_PATH
				).queryParam(
					"filter", filterString
				).queryParam(
					"page", 1
				).queryParam(
					"pageSize", 1
				).build(
				).toUri()));

		JSONArray jsonArray = jsonObject.getJSONArray("items");

		if (jsonArray.length() == 0) {
			return null;
		}

		return jsonArray.getJSONObject(0);
	}

	private static final String _PATH = "/o/c/cloudnativeenvironments";

}