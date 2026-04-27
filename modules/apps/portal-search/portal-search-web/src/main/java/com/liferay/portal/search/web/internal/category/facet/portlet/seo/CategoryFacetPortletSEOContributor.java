/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.category.facet.portlet.seo;

import com.liferay.portal.kernel.model.Layout;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

/**
 * @author Amos Fong
 */
@Component(
	property = {
		"jakarta.portlet.name=" + CategoryFacetPortletKeys.CATEGORY_FACET,
	},
	service = {PortletSEOContributor.class}
)
public class CategoryFacetPortletSEOContributor interface PortletSEOContributor {

	public Set<String> contributeRobotsDisallowEntries(LayoutSet layoutSet) {
        Set<String> robotDisallowEntries = new HashSet<>();

        List<PortletPreferences> portletPreferencesList =
            _portletPreferencesLocalService.getPortletPreferences(
                layoutSet.getCompanyId(), layoutSet.getGroupId(),
                PortletKeys.PREFS_OWNER_ID_DEFAULT,
                PortletKeys.PREFS_OWNER_TYPE_LAYOUT, CategoryFacetPortletKeys.CATEGORY_FACET,
                layoutSet.isPrivateLayout());

        for (PortletPreferences portletPreferences :
                portletPreferencesList) {
                
            jakarta.portlet.PortletPreferences preferences =
                _portletPreferencesLocalService.getPreferences(
                    portletPreferences.getCompanyId(),
                    portletPreferences.getOwnerId(),
                    portletPreferences.getOwnerType(),
                    portletPreferences.getPlid(),
                    portletPreferences.getPortletId());

            CategoryFacetPortletPreferences categoryFacetPortletPreferences = new CategoryFacetPortletPreferencesImpl(
                _assetVocabularyLocalService, _groupLocalService,
                preferences);

            if ((categoryFacetPortletPreferences == null) ||
                categoryFacetPortletPreferences.isSEOIndexingEnabled()) {

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

            robotDisallowEntries.add(layout.getFriendlyURL() + "*?" + parameterName + "=");
            robotDisallowEntries.add(layout.getFriendlyURL() + "*&" + parameterName + "=");
        }

        return robotDisallowEntries;
    }

    public String getCanonicalURLParameterNames(Portlet portlet, Layout layout) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);

        CategoryFacetPortletPreferences categoryFacetPortletPreferences = new CategoryFacetPortletPreferencesImpl(
            _assetVocabularyLocalService, _groupLocalService,            
        portletPreferencesLocalService.fetchPreferences(
			themeDisplay.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			portlet.getPortletId()));

			if ((categoryFacetPortletPreferences == null) ||
				!categoryFacetPortletPreferences.isSEOIndexingEnabled()) {

				continue;
			}

			String parameterName = categoryFacetPortletPreferences.getParameterName();

			if (Validator.isNull(parameterName) ||
				(httpServletRequest.getParameter(parameterName) == null)) {

				continue;
			}

            Set<String> parameterNames = new HashSet<>();

			parameterNames.add(parameterName);

            return parameterNames;
    }

    public String getPageMetaRobots(Portlet portlet, ThemeDisplay themeDisplay,
		RenderRequest renderRequest) {

        CategoryFacetPortletPreferences categoryFacetPortletPreferences = new CategoryFacetPortletPreferencesImpl(
            _assetVocabularyLocalService, _groupLocalService,            
        portletPreferencesLocalService.fetchPreferences(
			themeDisplay.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			portlet.getPortletId()));


		if ((categoryFacetPortletPreferences == null) ||
			categoryFacetPortletPreferences.isIndexingEnabled()) {

			return false;
		}

		if (portletSharedRequestHelper.getParameter(
			categoryFacetPortletPreferences.getParameterName(), renderRequest) != null) {
            
            return true;
        }

        return false;    
    }

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}
