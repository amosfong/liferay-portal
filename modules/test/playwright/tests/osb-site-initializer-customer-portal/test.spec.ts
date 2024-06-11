/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	loginTest()
);

const accountERC = 'ERC-001';
const portalInstanceId = getRandomString();
let site;

test.beforeEach(async ({apiHelpers}) => {
	await apiHelpers.headlessPortalInstances.createPortalInstance({
		domain: 'able.com',
		portalInstanceId: portalInstanceId,
		virtualHost: 'www.able.com'
	});

	// update page to now use www.able.com
	// create newApiHelpers var to use www.able.com?
	// login to www.able.com as test@able.com/test

	site = await apiHelpers.headlessSite.createSite({
		externalReferenceCode: getRandomString(),
		name: 'Test Customer Portal Site ' + getRandomString(),
		templateKey: 'com.liferay.osb.site.initializer.customer.portal',
		templateType: 'site-initializer'
	});

	const account = await apiHelpers.headlessAdminUser.getAccountByExternalReferenceCode(accountERC);

	await apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
		account.id,
		['test@liferay.com']
	);

	const rolesResponse = await apiHelpers.headlessAdminUser.getAccountRoles(
		account.id
	);

	const accountAdministratorRole = rolesResponse?.items?.filter((role) => {
		return role.name === 'Account Administrator';
	});

	await apiHelpers.headlessAdminUser.assignAccountRoles(
		accountERC,
		accountAdministratorRole[0].id,
		'test@liferay.com'
	);
});

test('test mock', async ({page}) => {
	 await page.route('https://login-dev.liferay.com/api/v1/sessions/me', async route => {
		const json = {"id":"VALIDSESSIONID"};

		await route.fulfill({ json });
	  });

	await page.route('https://webserver-lrprovisioning-uat.lfr.cloud/o/provisioning-rest/v1.0/accounts/*/contacts/by-email-address/*/roles*', async route => {
		await route.fulfill({
		    status: 204,
		    body: ''
		  });
	  });

	await page.goto('/web' + site.friendlyUrlPath + '/project/#/' + accountERC);

	await expect(page.getByRole('button', {name: 'Start Project Setup'})).toBeVisible();

	await page.goto('/web' + site.friendlyUrlPath + '/project/#/' + accountERC + '/team-members');

	await page.getByRole('button', {name: 'invite'}).click();

	await page.getByLabel('First Name').fill('testfirst');
	await page.getByLabel('Last Name').fill('testlast');

	await page.getByLabel('Email').fill('email@email.com');

	await page.getByLabel('Role').selectOption({ label: 'User' });

	await page.getByRole('button', {name: 'Send Invitations'}).click();

	await page.goto('/web' + site.friendlyUrlPath + '/project/#/' + accountERC + '/team-members');

	await expect(page.getByText('testfirst testlast')).toBeVisible();
});

test.afterEach(async ({apiHelpers}) => {
	//Don't need if deleting portal instance
	//await apiHelpers.headlessSite.deleteSiteByERC(site.externalReferenceCode);

	await apiHelpers.headlessPortalInstances.deletePortalInstance(portalInstanceId);
});