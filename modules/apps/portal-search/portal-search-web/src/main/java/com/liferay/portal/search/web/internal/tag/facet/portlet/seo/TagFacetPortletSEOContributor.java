/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.tag.facet.portlet.seo;

import com.liferay.layout.seo.contributor.LayoutCanonicalURLContributor;
import com.liferay.layout.seo.contributor.LayoutMetaRobotsProvider;
import com.liferay.layout.seo.contributor.LayoutSetRobotsContributor;
import com.liferay.portal.search.web.internal.seo.BasePortletSEOContributor;
import com.liferay.portal.search.web.internal.seo.SEOPortletPreferences;
import com.liferay.portal.search.web.internal.tag.facet.constants.TagFacetPortletKeys;
import com.liferay.portal.search.web.internal.tag.facet.portlet.TagFacetPortletPreferencesImpl;

import jakarta.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Amos Fong
 */
@Component(
	property = "jakarta.portlet.name=" + TagFacetPortletKeys.TAG_FACET,
	service = {
		LayoutCanonicalURLContributor.class, LayoutMetaRobotsProvider.class,
		LayoutSetRobotsContributor.class
	}
)
public class TagFacetPortletSEOContributor extends BasePortletSEOContributor {

	@Override
	protected String getPortletId() {
		return TagFacetPortletKeys.TAG_FACET;
	}

	@Override
	protected SEOPortletPreferences getSEOPortletPreferences(
		PortletPreferences portletPreferences) {

		return new TagFacetPortletPreferencesImpl(portletPreferences);
	}

}