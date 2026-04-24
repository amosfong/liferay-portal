/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.robots;

import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.robots.RobotsContributor;
import com.liferay.portal.search.web.internal.seo.SearchSEOSettingsUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(service = RobotsContributor.class)
public class SearchFacetRobotsContributor implements RobotsContributor {

	@Override
	public String contribute(LayoutSet layoutSet) {
		return _searchSEOSettingsUtil.getRobotsDisallowSection(layoutSet);
	}

	@Reference
	private SearchSEOSettingsUtil _searchSEOSettingsUtil;

}
