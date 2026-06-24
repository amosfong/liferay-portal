/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.headless.admin.user.client.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountRoleResource;

import java.util.Collection;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class AccountRoleService extends OneBaseService {

	public AccountRole getAccountRole(long accountRoleId, Jwt jwt)
		throws Exception {

		AccountRoleResource accountRoleResource = AccountRoleResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).build();

		Page<AccountRole> accountRolePage =
			accountRoleResource.getAccountAccountRolesPage(
				0L, null, null, Pagination.of(1, 1), null);

		Collection<AccountRole> accountRoles = accountRolePage.getItems();

		for (AccountRole accountRole : accountRoles) {
			if (Objects.equals(accountRole.getId(), accountRoleId)) {
				return accountRole;
			}
		}

		throw new Exception("Unable to find account role " + accountRoleId);
	}

}