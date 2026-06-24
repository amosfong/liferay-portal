/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.List;

import org.json.JSONObject;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Amos Fong
 */
@Component
public class ProjectMembershipService extends OneBaseService {

	public void addProjectMembership(
			long accountEntryId, long projectId, long userId,
			String roleExternalReferenceCode, Jwt jwt)
		throws Exception {

		JSONObject projectMembershipJSONObject = new JSONObject(
		).put(
			"r_accountEntryToProjectMembership_accountEntryId", accountEntryId
		).put(
			"r_projectToProjectMembership_c_projectId", projectId
		).put(
			"r_userToProjectMembership_userId", userId
		).put(
			"roleExternalReferenceCode", roleExternalReferenceCode
		);

		post(
			getAuthorization(jwt), projectMembershipJSONObject.toString(),
			UriComponentsBuilder.fromPath(
				"/o/c/projectmemberships"
			).build(
			).toUri());
	}

	public void deleteProjectMembership(
			long projectId, long userId, String roleExternalReferenceCode,
			Jwt jwt)
		throws Exception {

		List<Long> projectMembershipIds = getAllItems(
			"/o/c/projectmemberships",
			StringBundler.concat(
				"r_projectToProjectMembership_c_projectId eq ", projectId,
				" and r_userToProjectMembership_userId eq ", userId,
				" and roleExternalReferenceCode eq '",
				roleExternalReferenceCode, "'"),
			jsonObject -> jsonObject.getLong("id"));

		for (long projectMembershipId : projectMembershipIds) {
			delete(
				getAuthorization(jwt), StringPool.BLANK,
				UriComponentsBuilder.fromPath(
					"/o/c/projectmemberships/" + projectMembershipId
				).build(
				).toUri());
		}
	}

}