/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.custom.facet.portlet.seo;

import com.liferay.layout.seo.contributor.LayoutCanonicalURLContributor;
import com.liferay.layout.seo.contributor.LayoutMetaRobotsProvider;
import com.liferay.layout.seo.contributor.LayoutSetRobotsContributor;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.seo.BasePortletSEOContributor;
import com.liferay.portal.search.web.internal.seo.SEOPortletPreferences;

import jakarta.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Amos Fong
 */
@Component(
	property = "jakarta.portlet.name=" + CustomFacetPortletKeys.CUSTOM_FACET,
	service = {
		LayoutCanonicalURLContributor.class, LayoutMetaRobotsProvider.class,
		LayoutSetRobotsContributor.class
	}
)
public class CustomFacetPortletSEOContributor
	extends BasePortletSEOContributor {

	@Override
	protected String getPortletId() {
		return CustomFacetPortletKeys.CUSTOM_FACET;
	}

	@Override
	protected SEOPortletPreferences getSEOPortletPreferences(
		PortletPreferences portletPreferences) {

		return new CustomFacetPortletPreferencesImpl(portletPreferences);
	}

}