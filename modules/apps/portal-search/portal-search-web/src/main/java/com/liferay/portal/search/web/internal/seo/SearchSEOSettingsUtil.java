/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.seo;

import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.asset.SearchableAssetClassNamesProvider;
import com.liferay.portal.search.web.internal.category.facet.constants.CategoryFacetPortletKeys;
import com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortletPreferences;
import com.liferay.portal.search.web.internal.category.facet.portlet.CategoryFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.custom.facet.constants.CustomFacetPortletKeys;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferences;
import com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.folder.facet.constants.FolderFacetPortletKeys;
import com.liferay.portal.search.web.internal.folder.facet.portlet.FolderFacetPortletPreferences;
import com.liferay.portal.search.web.internal.folder.facet.portlet.FolderFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.modified.facet.constants.ModifiedFacetPortletKeys;
import com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortletPreferences;
import com.liferay.portal.search.web.internal.modified.facet.portlet.ModifiedFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.portlet.preferences.PortletPreferencesLookup;
import com.liferay.portal.search.web.internal.site.facet.constants.SiteFacetPortletKeys;
import com.liferay.portal.search.web.internal.site.facet.portlet.SiteFacetPortletPreferences;
import com.liferay.portal.search.web.internal.site.facet.portlet.SiteFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.sort.constants.SortPortletKeys;
import com.liferay.portal.search.web.internal.sort.portlet.SortPortletPreferences;
import com.liferay.portal.search.web.internal.sort.portlet.SortPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.tag.facet.constants.TagFacetPortletKeys;
import com.liferay.portal.search.web.internal.tag.facet.portlet.TagFacetPortletPreferences;
import com.liferay.portal.search.web.internal.tag.facet.portlet.TagFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.type.facet.constants.TypeFacetPortletKeys;
import com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortletPreferences;
import com.liferay.portal.search.web.internal.type.facet.portlet.TypeFacetPortletPreferencesImpl;
import com.liferay.portal.search.web.internal.user.facet.constants.UserFacetPortletKeys;
import com.liferay.portal.search.web.internal.user.facet.portlet.UserFacetPortletPreferences;
import com.liferay.portal.search.web.internal.user.facet.portlet.UserFacetPortletPreferencesImpl;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = SearchSEOSettingsUtil.class)
public class SearchSEOSettingsUtil {

	public Set<String> getCanonicalURLParameterNames(
		HttpServletRequest httpServletRequest, Layout layout) {

		if ((layout == null) ||
			!(layout.getLayoutType() instanceof LayoutTypePortlet)) {

			return Collections.emptySet();
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return Collections.emptySet();
		}

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		Set<String> parameterNames = new LinkedHashSet<>();

		for (Portlet portlet : layoutTypePortlet.getAllPortlets(false)) {
			Info info = resolve(portlet, themeDisplay);

			if ((info == null) || info.indexingDisabled ||
				Validator.isNull(info.parameterName)) {

				continue;
			}

			if (httpServletRequest.getParameter(info.parameterName) == null) {
				continue;
			}

			parameterNames.add(info.parameterName);
		}

		return parameterNames;
	}

	public String getRobotsDisallowSection(LayoutSet layoutSet) {
		Map<String, Set<String>> parameterNamesByPath = new TreeMap<>();

		for (String rootPortletId : _FACET_PORTLET_NAMES) {
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

				Info info = _resolve(rootPortletId, preferences);

				if ((info == null) || !info.indexingDisabled ||
					Validator.isNull(info.parameterName)) {

					continue;
				}

				Layout layout = _layoutLocalService.fetchLayout(
					portletPreferences.getPlid());

				if (layout == null) {
					continue;
				}

				Set<String> parameterNames = parameterNamesByPath.
					computeIfAbsent(
						layout.getFriendlyURL(), key -> new TreeSet<>());

				parameterNames.add(info.parameterName);
			}
		}

		if (parameterNamesByPath.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		sb.append("\nUser-agent: *\n");

		for (Map.Entry<String, Set<String>> entry :
				parameterNamesByPath.entrySet()) {

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

	public Info resolve(Portlet portlet, ThemeDisplay themeDisplay) {
		return _resolve(
			portlet.getPortletName(),
			_portletPreferencesLookup.fetchPreferences(portlet, themeDisplay));
	}

	public static final class Info {

		public Info(String parameterName, boolean indexingDisabled) {
			this.parameterName = parameterName;
			this.indexingDisabled = indexingDisabled;
		}

		public final boolean indexingDisabled;
		public final String parameterName;

	}

	private Info _resolve(
		String portletName,
		jakarta.portlet.PortletPreferences portletPreferences) {

		if (portletName.equals(CategoryFacetPortletKeys.CATEGORY_FACET)) {
			CategoryFacetPortletPreferences prefs =
				new CategoryFacetPortletPreferencesImpl(
					_assetVocabularyLocalService, _groupLocalService,
					portletPreferences);

			return new Info(
				prefs.getParameterName(), prefs.isIndexingDisabled());
		}

		if (portletName.equals(CustomFacetPortletKeys.CUSTOM_FACET)) {
			CustomFacetPortletPreferences prefs =
				new CustomFacetPortletPreferencesImpl(portletPreferences);

			String parameterName = prefs.getParameterName();

			if (Validator.isNull(parameterName)) {
				parameterName = prefs.getAggregationField();
			}

			return new Info(parameterName, prefs.isIndexingDisabled());
		}

		if (portletName.equals(FolderFacetPortletKeys.FOLDER_FACET)) {
			FolderFacetPortletPreferences prefs =
				new FolderFacetPortletPreferencesImpl(portletPreferences);

			return new Info(
				prefs.getParameterName(), prefs.isIndexingDisabled());
		}

		if (portletName.equals(ModifiedFacetPortletKeys.MODIFIED_FACET)) {
			ModifiedFacetPortletPreferences prefs =
				new ModifiedFacetPortletPreferencesImpl(portletPreferences);

			return new Info(
				prefs.getParameterName(), prefs.isIndexingDisabled());
		}

		if (portletName.equals(SiteFacetPortletKeys.SITE_FACET)) {
			SiteFacetPortletPreferences prefs =
				new SiteFacetPortletPreferencesImpl(portletPreferences);

			return new Info(
				prefs.getParameterName(), prefs.isIndexingDisabled());
		}

		if (portletName.equals(SortPortletKeys.SORT)) {
			SortPortletPreferences prefs =
				new SortPortletPreferencesImpl(portletPreferences);

			return new Info(
				prefs.getParameterName(), prefs.isIndexingDisabled());
		}

		if (portletName.equals(TagFacetPortletKeys.TAG_FACET)) {
			TagFacetPortletPreferences prefs =
				new TagFacetPortletPreferencesImpl(portletPreferences);

			return new Info(
				prefs.getParameterName(), prefs.isIndexingDisabled());
		}

		if (portletName.equals(TypeFacetPortletKeys.TYPE_FACET)) {
			TypeFacetPortletPreferences prefs =
				new TypeFacetPortletPreferencesImpl(
					_objectDefinitionLocalService, portletPreferences,
					_searchableAssetClassNamesProvider);

			return new Info(
				prefs.getParameterName(), prefs.isIndexingDisabled());
		}

		if (portletName.equals(UserFacetPortletKeys.USER_FACET)) {
			UserFacetPortletPreferences prefs =
				new UserFacetPortletPreferencesImpl(portletPreferences);

			return new Info(
				prefs.getParameterName(), prefs.isIndexingDisabled());
		}

		return null;
	}

	private static final Set<String> _FACET_PORTLET_NAMES = Set.of(
		CategoryFacetPortletKeys.CATEGORY_FACET,
		CustomFacetPortletKeys.CUSTOM_FACET,
		FolderFacetPortletKeys.FOLDER_FACET,
		ModifiedFacetPortletKeys.MODIFIED_FACET,
		SiteFacetPortletKeys.SITE_FACET, SortPortletKeys.SORT,
		TagFacetPortletKeys.TAG_FACET, TypeFacetPortletKeys.TYPE_FACET,
		UserFacetPortletKeys.USER_FACET);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private PortletPreferencesLookup _portletPreferencesLookup;

	@Reference
	private SearchableAssetClassNamesProvider
		_searchableAssetClassNamesProvider;

}
