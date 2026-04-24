/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.canonical.url;

import com.liferay.layout.seo.canonical.url.CanonicalURLParameterContributor;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.search.web.internal.seo.SearchSEOSettingsUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = CanonicalURLParameterContributor.class)
public class SearchFacetCanonicalURLParameterContributor
	implements CanonicalURLParameterContributor {

	@Override
	public Set<String> getParameterNames(
		HttpServletRequest httpServletRequest, Layout layout) {

		return _searchSEOSettingsUtil.getCanonicalURLParameterNames(
			httpServletRequest, layout);
	}

	@Reference
	private SearchSEOSettingsUtil _searchSEOSettingsUtil;

}
