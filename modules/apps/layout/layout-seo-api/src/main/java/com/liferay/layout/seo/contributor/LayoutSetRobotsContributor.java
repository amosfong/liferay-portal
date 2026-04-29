/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.contributor;

import com.liferay.portal.kernel.model.LayoutSet;

import jakarta.portlet.PortletPreferences;

import java.util.Set;

/**
 * @author Amos Fong
 */
public interface LayoutSetRobotsContributor {

	public Set<String> contributeDisallowURLEntries(LayoutSet layoutSet);

	public boolean isWebCrawlerIndexingEnabled(
		PortletPreferences portletPreferences);

}