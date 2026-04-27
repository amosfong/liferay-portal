/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.category.facet.portlet.seo;

import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.layout.seo.contributor.PortletSEOContributor;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortletPreferences;
import com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.portlet.shared.task.helper.PortletSharedRequestHelper;

import jakarta.portlet.RenderRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(
	property = "jakarta.portlet.name=" + CategoryFacetPortletKeys.CATEGORY_FACET,
	service = PortletSEOContributor.class
)
public class CategoryFacetPortletSEOContributor
	implements PortletSEOContributor {

	public Set<String> contributeRobotsDisallowEntries(LayoutSet layoutSet) {
		Set<String> robotDisallowEntries = new HashSet<>();

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				layoutSet.getCompanyId(), layoutSet.getGroupId(),
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				CategoryFacetPortletKeys.CATEGORY_FACET,
				layoutSet.isPrivateLayout());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			jakarta.portlet.PortletPreferences preferences =
				_portletPreferencesLocalService.getPreferences(
					portletPreferences.getCompanyId(),
					portletPreferences.getOwnerId(),
					portletPreferences.getOwnerType(),
					portletPreferences.getPlid(),
					portletPreferences.getPortletId());

			CategoryFacetPortletPreferences categoryFacetPortletPreferences =
				new CategoryFacetPortletPreferencesImpl(
					_assetVocabularyLocalService, _groupLocalService,
					preferences);

			if ((categoryFacetPortletPreferences == null) ||
				categoryFacetPortletPreferences.isIndexingDisabled()) {

				continue;
			}

			String parameterName =
				categoryFacetPortletPreferences.getParameterName();

			if (Validator.isNull(parameterName)) {
				continue;
			}

			Layout layout = _layoutLocalService.fetchLayout(
				portletPreferences.getPlid());

			if (layout == null) {
				continue;
			}

			robotDisallowEntries.add(
				layout.getFriendlyURL() + "*?" + parameterName + "=");
			robotDisallowEntries.add(
				layout.getFriendlyURL() + "*&" + parameterName + "=");
		}

		return robotDisallowEntries;
	}

	public Set<String> getCanonicalURLParameterNames(
		HttpServletRequest httpServletRequest, Layout layout,
		String portletId) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		CategoryFacetPortletPreferences categoryFacetPortletPreferences =
			new CategoryFacetPortletPreferencesImpl(
				_assetVocabularyLocalService, _groupLocalService,
				_portletPreferencesLocalService.fetchPreferences(
					themeDisplay.getCompanyId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
					portletId));

		if ((categoryFacetPortletPreferences == null) ||
			categoryFacetPortletPreferences.isIndexingDisabled()) {

			return Collections.emptySet();
		}

		String parameterName =
			categoryFacetPortletPreferences.getParameterName();

		if (Validator.isNull(parameterName) ||
			(httpServletRequest.getParameter(parameterName) == null)) {

			return Collections.emptySet();
		}

		Set<String> parameterNames = new HashSet<>();

		parameterNames.add(parameterName);

		return parameterNames;
	}

	public String getPageMetaRobots(RenderRequest renderRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CategoryFacetPortletPreferences categoryFacetPortletPreferences =
			new CategoryFacetPortletPreferencesImpl(
				_assetVocabularyLocalService, _groupLocalService,
				renderRequest.getPreferences());

		if ((categoryFacetPortletPreferences == null) ||
			categoryFacetPortletPreferences.isIndexingDisabled()) {

			return "";
		}

		if (_portletSharedRequestHelper.getParameter(
				categoryFacetPortletPreferences.getParameterName(),
				renderRequest) != null) {

			return "noindex,nofollow";
		}

		return "";
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private PortletSharedRequestHelper _portletSharedRequestHelper;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}