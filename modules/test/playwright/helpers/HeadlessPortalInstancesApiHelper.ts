/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

type TPortalInstance = {
	companyId?: string;
	domain: string;
	portalInstanceId: number;
	virtualHost: string;
};

export class HeadlessPortalInstancesApiHelper {
	apiHelpers: ApiHelpers;
	basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-portal-instances/v1.0/portal-instances';
	}

	async createPortalInstance(portalInstance: TPortalInstance): Promise<TPortalInstance> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}`,
			{data: portalInstance}
		);
	}

	async deletePortalInstance(portalInstanceId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/${portalInstanceId}`
		);
	}
}
