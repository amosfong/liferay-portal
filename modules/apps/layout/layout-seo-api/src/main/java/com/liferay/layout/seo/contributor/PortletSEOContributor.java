/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.contributor;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;

import jakarta.portlet.RenderRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

/**
 * @author Amos Fong
 */
public interface PortletSEOContributor {

	public Set<String> contributeRobotsDisallowEntries(LayoutSet layoutSet);

	public Set<String> getCanonicalURLParameterNames(
		HttpServletRequest httpServletRequest, Layout layout, String portletId);

	public String getPageMetaRobots(RenderRequest renderRequest);

}