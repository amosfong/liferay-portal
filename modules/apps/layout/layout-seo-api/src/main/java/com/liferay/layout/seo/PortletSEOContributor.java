/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.contributor;

import com.liferay.portal.kernel.model.Layout;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

public interface PortletSEOContributor {

	public String contributeRobotsDisallow(LayoutSet layoutSet);

    public String getCanonicalURLParameterNames(HttpServletRequest httpServletRequest, Layout layout);

    public String getPageMetaRobots(Portlet portlet, ThemeDisplay themeDisplay,
		RenderRequest renderRequest);

}
