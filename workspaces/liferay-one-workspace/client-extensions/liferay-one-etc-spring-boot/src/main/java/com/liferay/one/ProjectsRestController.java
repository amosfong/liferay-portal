/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one;

import com.liferay.headless.admin.user.client.dto.v1_0.AccountRole;
import com.liferay.one.model.Project;
import com.liferay.one.service.AccountRoleService;
import com.liferay.one.service.ProjectMembershipService;
import com.liferay.one.service.ProjectService;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felipe Veloso
 * @author Kyle Bischof
 */
@RequestMapping("/projects")
@RestController
public class ProjectsRestController extends OneBaseRestController {

	@DeleteMapping("/{externalReferenceCode}/users/{userId}/roles")
	public void deleteUserByEmailAddressRoles(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@PathVariable long userId, @RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		Project project = _projectService.getProject(
			externalReferenceCode, jwt);

		AccountRole accountRole = _accountRoleService.getAccountRole(
			jsonObject.getLong("accountRoleId"), jwt);

		_projectMembershipService.deleteProjectMembership(
			project.getProjectId(), userId,
			accountRole.getExternalReferenceCode(), jwt);
	}

	@PutMapping("/{externalReferenceCode}/users/{userId}/roles")
	public void putUserByEmailAddressRoles(
			@AuthenticationPrincipal Jwt jwt,
			@PathVariable("externalReferenceCode") String externalReferenceCode,
			@PathVariable long userId, @RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		Project project = _projectService.getProject(
			externalReferenceCode, jwt);

		AccountRole accountRole = _accountRoleService.getAccountRole(
			jsonObject.getLong("accountRoleId"), jwt);

		_projectMembershipService.addProjectMembership(
			project.getAccountEntryId(), project.getProjectId(), userId,
			accountRole.getExternalReferenceCode(), jwt);
	}

	@Autowired
	private AccountRoleService _accountRoleService;

	@Autowired
	private ProjectMembershipService _projectMembershipService;

	@Autowired
	private ProjectService _projectService;

}