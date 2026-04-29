/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.seo;

import com.liferay.layout.seo.contributor.LayoutCanonicalURLContributor;
import com.liferay.layout.seo.contributor.LayoutMetaRobotsProvider;
import com.liferay.layout.seo.contributor.LayoutSetRobotsContributor;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.portlet.shared.task.helper.PortletSharedRequestHelper;

import jakarta.portlet.RenderRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
public abstract class BasePortletSEOContributor
	implements LayoutCanonicalURLContributor, LayoutMetaRobotsProvider,
			   LayoutSetRobotsContributor {

	@Override
	public Set<String> contributeCanonicalURLParameters(
		HttpServletRequest httpServletRequest, Layout layout,
		String portletId) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		SEOPortletPreferences seoPortletPreferences = getSEOPortletPreferences(
			portletPreferencesLocalService.fetchPreferences(
				themeDisplay.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId));

		if ((seoPortletPreferences == null) ||
			!seoPortletPreferences.isWebCrawlerIndexingEnabled()) {

			return Collections.emptySet();
		}

		String parameterName = seoPortletPreferences.getSEOParameterName();

		if (Validator.isNull(parameterName) ||
			(httpServletRequest.getParameter(parameterName) == null)) {

			return Collections.emptySet();
		}

		Set<String> parameterNames = new HashSet<>();

		parameterNames.add(parameterName);

		return parameterNames;
	}

	@Override
	public Set<String> contributeDisallowURLEntries(LayoutSet layoutSet) {
		Set<String> disallowURLEntries = new HashSet<>();

		List<PortletPreferences> portletPreferencesList =
			portletPreferencesLocalService.getPortletPreferences(
				layoutSet.getCompanyId(), layoutSet.getGroupId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, getPortletId(),
				layoutSet.isPrivateLayout());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			SEOPortletPreferences seoPortletPreferences =
				getSEOPortletPreferences(
					portletPreferencesLocalService.getPreferences(
						portletPreferences.getCompanyId(),
						portletPreferences.getOwnerId(),
						portletPreferences.getOwnerType(),
						portletPreferences.getPlid(),
						portletPreferences.getPortletId()));

			if ((seoPortletPreferences == null) ||
				seoPortletPreferences.isWebCrawlerIndexingEnabled()) {

				continue;
			}

			String parameterName = seoPortletPreferences.getSEOParameterName();

			if (Validator.isNull(parameterName)) {
				continue;
			}

			Layout layout = layoutLocalService.fetchLayout(
				portletPreferences.getPlid());

			if (layout == null) {
				continue;
			}

			disallowURLEntries.add(
				StringBundler.concat(
					layout.getFriendlyURL(), "*?", parameterName,
					StringPool.EQUAL));
			disallowURLEntries.add(
				StringBundler.concat(
					layout.getFriendlyURL(), "*&", parameterName,
					StringPool.EQUAL));
		}

		return disallowURLEntries;
	}

	@Override
	public String getPageMetaRobots(RenderRequest renderRequest) {
		SEOPortletPreferences seoPortletPreferences = getSEOPortletPreferences(
			renderRequest.getPreferences());

		if ((seoPortletPreferences == null) ||
			seoPortletPreferences.isWebCrawlerIndexingEnabled()) {

			return StringPool.BLANK;
		}

		String parameter = portletSharedRequestHelper.getParameter(
			seoPortletPreferences.getSEOParameterName(), renderRequest);

		if (parameter != null) {
			return "noindex,nofollow";
		}

		return StringPool.BLANK;
	}

	@Override
	public boolean isWebCrawlerIndexingEnabled(
		jakarta.portlet.PortletPreferences portletPreferences) {

		SEOPortletPreferences seoPortletPreferences = getSEOPortletPreferences(
			portletPreferences);

		if (seoPortletPreferences == null) {
			return true;
		}

		return seoPortletPreferences.isWebCrawlerIndexingEnabled();
	}

	protected abstract String getPortletId();

	protected abstract SEOPortletPreferences getSEOPortletPreferences(
		jakarta.portlet.PortletPreferences portletPreferences);

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected PortletPreferencesLocalService portletPreferencesLocalService;

	@Reference
	protected PortletSharedRequestHelper portletSharedRequestHelper;

}