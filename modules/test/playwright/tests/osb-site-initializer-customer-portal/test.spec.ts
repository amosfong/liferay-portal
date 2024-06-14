/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import performLogin from '../../utils/performLogin';

export const test = mergeTests(
	apiHelpersTest,
	loginTest()
);

const accountERC = 'ERC-001';
const portalInstanceId = getRandomString();
let site;

test.beforeEach(async ({apiHelpers, context}) => {

	/*await apiHelpers.headlessPortalInstances.createPortalInstance({
		domain: 'able.com',
		portalInstanceId: portalInstanceId,
		virtualHost: 'www.able.com'
	});


	let newPage = await context.newPage(
		{
			baseUrl: "http://www.able.com:8080"
		});

	await performLogin(newPage, 'test', 'able.com');

	await newPage.goto('/');

	await expect(newPage.getByText('testfirst testlast')).toBeVisible();

	let newApiHelpers = new ApiHelpers(newPage);

	site = await newApiHelpers.headlessSite.createSite({
		externalReferenceCode: getRandomString(),
		name: 'Test Customer Portal Site ' + getRandomString(),
		templateKey: 'com.liferay.osb.site.initializer.customer.portal',
		templateType: 'site-initializer'
	});

	newPage.goto('/');
*/
	// update page to now use www.able.com
	// create new apiHelpers to use www.able.com?
	// login to www.able.com as test@able.com/test

});

test('test mock', async ({page, context}) => {
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

	let newPage = await context.newPage(
		{
			baseURL: "http://www.able.com:8080"
		});

//	await performLogin(newPage, 'test', 'able.com');

	await newPage.bringToFront();

	await newPage.goto('/');

	await expect(newPage.getByText('testfirst testlast')).toBeVisible();

//	await page.goto('http://www.able.com:8080');

//	await page.goto('/home');

//	await expect(page.getByText('testfirst testlast')).toBeVisible();
});

test.afterEach(async ({apiHelpers}) => {
	//Don't need if deleting portal instance
	//await apiHelpers.headlessSite.deleteSiteByERC(site.externalReferenceCode);

	//await apiHelpers.headlessPortalInstances.deletePortalInstance(portalInstanceId);
});