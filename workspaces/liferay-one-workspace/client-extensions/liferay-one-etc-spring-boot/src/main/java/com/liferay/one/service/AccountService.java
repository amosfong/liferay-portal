/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.problem.Problem;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountRoleResource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class AccountService extends OneBaseService {

	public void assignUserAccountRole(
			String externalReferenceCode, long accountRoleId,
			String emailAddress, Jwt jwt)
		throws Exception {

		AccountRoleResource accountRoleResource = AccountRoleResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization(jwt)
		).build();

		accountRoleResource.
			postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
				externalReferenceCode, accountRoleId, emailAddress);
	}

	public Account fetchAccount(long accountId) throws Exception {
		AccountResource accountResource = AccountResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization()
		).build();

		try {
			return accountResource.getAccount(accountId);
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			if ((problem != null) && isNotFound(problem.getStatus())) {
				return null;
			}

			throw problemException;
		}
	}

	public Account getAccount(long accountEntryId, Jwt jwt) throws Exception {
		AccountResource accountResource = AccountResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization(jwt)
		).build();

		return accountResource.getAccount(accountEntryId);
	}

	public Account getAccount(String externalReferenceCode, Jwt jwt)
		throws Exception {

		AccountResource accountResource = AccountResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).build();

		return accountResource.getAccountByExternalReferenceCode(
			externalReferenceCode);
	}

	public List<AccountRole> getUserAccountRoles(
			String externalReferenceCode, String emailAddress, Jwt jwt)
		throws Exception {

		AccountRoleResource accountRoleResource = AccountRoleResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization(jwt)
		).build();

		Page<AccountRole> accountRolePage =
			accountRoleResource.
				getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage(
					externalReferenceCode, emailAddress);

		return new ArrayList<>(accountRolePage.getItems());
	}

	public void unassignUserAccountRole(
			String externalReferenceCode, long accountRoleId,
			String emailAddress, Jwt jwt)
		throws Exception {

		AccountRoleResource accountRoleResource = AccountRoleResource.builder(
		).endpoint(
			lxcDXPMainDomain, lxcDXPServerProtocol
		).header(
			HttpHeaders.AUTHORIZATION, getAuthorization(jwt)
		).build();

		accountRoleResource.
			deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
				externalReferenceCode, accountRoleId, emailAddress);
	}

}