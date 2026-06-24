/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.one.model.Project;

import org.json.JSONObject;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Amos Fong
 */
@Component
public class ProjectService extends OneBaseService {

	public Project getProject(String externalReferenceCode, Jwt jwt)
		throws Exception {

		String response = get(
			getAuthorization(jwt),
			UriComponentsBuilder.fromPath(
				"/o/c/projects/by-external-reference-code/" +
					externalReferenceCode
			).build(
			).toUri());

		return new Project(new JSONObject(response));
	}

}