/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.robots;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.robots.RobotsContributor;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.folder.facet.constants.FolderFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.site.facet.constants.SiteFacetPortletKeys;
import com.liferay.portal.search.web.internal.sort.constants.SortPortletKeys;
import com.liferay.portal.search.web.internal.tag.facet.constants.TagFacetPortletKeys;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetPortletKeys;
import com.liferay.portal.search.web.internal.user.facet.constants.UserFacetPortletKeys;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(service = RobotsContributor.class)
public class SearchFacetRobotsContributor implements RobotsContributor {

	@Override
	public String contribute(LayoutSet layoutSet) {
		Map<String, Set<String>> disallowsByPath = new TreeMap<>();

		for (Map.Entry<String, String> entry : _FACET_PARAM_DEFAULTS.entrySet()) {
			String rootPortletId = entry.getKey();
			String defaultParameterName = entry.getValue();

			List<PortletPreferences> portletPreferencesList =
				_portletPreferencesLocalService.getPortletPreferences(
					layoutSet.getCompanyId(), layoutSet.getGroupId(),
					PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, rootPortletId,
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

				if (!GetterUtil.getBoolean(
						preferences.getValue("indexingDisabled", "true"))) {

					continue;
				}

				Layout layout = _layoutLocalService.fetchLayout(
					portletPreferences.getPlid());

				if (layout == null) {
					continue;
				}

				String parameterName = preferences.getValue(
					"parameterName", defaultParameterName);

				if (Validator.isNull(parameterName)) {
					continue;
				}

				Set<String> parameterNames = disallowsByPath.computeIfAbsent(
					layout.getFriendlyURL(), key -> new TreeSet<>());

				parameterNames.add(parameterName);
			}
		}

		if (disallowsByPath.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append("\nUser-agent: *\n");

		for (Map.Entry<String, Set<String>> entry :
				disallowsByPath.entrySet()) {

			String path = entry.getKey();

			for (String parameterName : entry.getValue()) {
				sb.append("Disallow: ");
				sb.append(path);
				sb.append("?*");
				sb.append(parameterName);
				sb.append("=*\n");
			}
		}

		return sb.toString();
	}

	private static final Map<String, String> _FACET_PARAM_DEFAULTS =
		HashMapBuilder.put(
			CategoryFacetPortletKeys.CATEGORY_FACET, "category"
		).put(
			CustomFacetPortletKeys.CUSTOM_FACET, ""
		).put(
			FolderFacetPortletKeys.FOLDER_FACET, "folder"
		).put(
			ModifiedFacetPortletKeys.MODIFIED_FACET, "modified"
		).put(
			SiteFacetPortletKeys.SITE_FACET, "site"
		).put(
			SortPortletKeys.SORT, "sort"
		).put(
			TagFacetPortletKeys.TAG_FACET, "tag"
		).put(
			TypeFacetPortletKeys.TYPE_FACET, "type"
		).put(
			UserFacetPortletKeys.USER_FACET, "user"
		).build();

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}
