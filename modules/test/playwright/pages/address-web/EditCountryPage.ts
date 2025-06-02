/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {waitForAlert} from '../../utils/waitForAlert';

export class EditCountryPage {
	readonly activeButton: Locator;
	readonly backButton: Locator;
	readonly billingAllowedInput: Locator;
	readonly keyInput: Locator;
	readonly notTranslatedMessage: (language: string) => Locator;
	readonly numberInput: Locator;
	readonly page: Page;
	readonly priorityInput: Locator;
	readonly saveButton: Locator;
	readonly shippingAllowedInput: Locator;
	readonly subjectToVATInput: Locator;
	readonly threeLetterIsocodeInput: Locator;
	readonly titleInput: Locator;
	readonly titleTranslationButton: Locator;
	readonly translatedMessage: (language: string) => Locator;
	readonly twoLetterIsocodeInput: Locator;

	constructor(page: Page) {
		this.activeButton = page.getByLabel('Active');
		this.backButton = page.getByRole('link', {exact: true, name: 'Back'});
		this.billingAllowedInput = page.getByLabel('Billing Allowed');
		this.keyInput = page.getByLabel('Key');
		this.notTranslatedMessage = (language) =>
			page.getByLabel(
				`Not translated into ${language}. Press enter to edit ${language} translation.`,
				{exact: true}
			);
		this.numberInput = page.getByLabel('Number');
		this.page = page;
		this.priorityInput = page.getByLabel('Priority');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.shippingAllowedInput = page.getByLabel('Shipping Allowed');
		this.subjectToVATInput = page.getByLabel('Subject to VAT');
		this.threeLetterIsocodeInput = page.getByLabel('Three-Letter ISO Code');
		this.titleInput = page.locator(
			'[id="_com_liferay_address_web_internal_portlet_CountriesManagementAdminPortlet_title"]'
		);
		this.titleTranslationButton = page.getByRole('button', {
			name: 'Current translation is',
		});
		this.translatedMessage = (language) =>
			page.getByLabel(
				`Translated into ${language}. Press enter to edit ${language} translation.`,
				{exact: true}
			);
		this.twoLetterIsocodeInput = page.getByLabel('Two-Letter ISO Code');
	}

	async editCountry(country: {
		key: string;
		number: string;
		priority: string;
		threeLetterIsocode: string;
		title: string;
		twoLetterIsocode: string;
	}) {
		await this.titleInput.fill(country.title);
		await this.keyInput.fill(country.key);
		await this.twoLetterIsocodeInput.fill(country.twoLetterIsocode);
		await this.threeLetterIsocodeInput.fill(country.threeLetterIsocode);
		await this.numberInput.fill(country.number);
		await this.priorityInput.fill(country.priority);
		await this.saveButton.click();

		await waitForAlert(this.page);
	}
}
