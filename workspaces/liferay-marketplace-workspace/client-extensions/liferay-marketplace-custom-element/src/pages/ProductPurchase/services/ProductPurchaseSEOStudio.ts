/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {z} from 'zod';

import {OrderCustomFields, OrderTypes} from '../../../enums/Order';
import zodSchema from '../../../schema/zod';
import {getSiteURL} from '../../../utils/site';
import ProductPurchase from './ProductPurchase';

type SEOStudioForm = z.infer<typeof zodSchema.seoStudioForm> & {
	salesforceProjectId: string;
};

export class ProductPurchaseSEOStudio extends ProductPurchase {
	private form?: SEOStudioForm;
	protected orderTypeExternalReferenceCode = OrderTypes.SEO_STUDIO;

	setForm(form: SEOStudioForm) {
		this.form = form;
	}

	protected getCart() {
		return {
			...super.getCart(),
			customFields: {
				[OrderCustomFields.ORDER_METADATA]: JSON.stringify({
					salesforceProjectId: this.form?.salesforceProjectId,
					seoStudioForm: this.form,
				}),
			},
		} as Cart;
	}

	public async createOrder(cart: Cart, cartOptions: any) {
		if (!this.form) {
			throw new Error('Form is missing.');
		}

		return super.createOrder(
			{
				...cart,
				...this.getCart(),
			},
			cartOptions
		);
	}

	public async getNextStepsLink() {
		return `${window.location.origin}${getSiteURL()}/customer-dashboard/#/products`;
	}
}
