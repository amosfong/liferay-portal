/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.web.internal.helper;

import com.liferay.layout.seo.contributor.LayoutSetRobotsContributor;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.configuration.kernel.util.PortletConfigurationUtil;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Amos Fong
 */
@Component(service = LayoutSEORobotsHelper.class)
public class LayoutSEORobotsHelper {

	public Map<String, String> getCrawlerDisabledPortletTitles(
		Layout layout, Locale locale) {

		Map<String, String> portletTitles = new LinkedHashMap<>();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		for (Portlet portlet : layoutTypePortlet.getAllPortlets(false)) {
			LayoutSetRobotsContributor layoutSetRobotsContributor =
				_serviceTrackerMap.getService(portlet.getRootPortletId());

			if (layoutSetRobotsContributor == null) {
				continue;
			}

			jakarta.portlet.PortletPreferences portletPreferences =
				_portletPreferencesLocalService.fetchPreferences(
					layout.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
					portlet.getPortletId());

			if (portletPreferences == null) {
				continue;
			}

			if (layoutSetRobotsContributor.isWebCrawlerIndexingEnabled(
					portletPreferences)) {

				continue;
			}

			String portletTitle = PortletConfigurationUtil.getPortletTitle(
				portlet.getPortletId(), portletPreferences,
				LocaleUtil.toLanguageId(locale));

			if (Validator.isNull(portletTitle)) {
				portletTitle = _portal.getPortletTitle(portlet, locale);
			}

			portletTitles.put(portlet.getPortletId(), portletTitle);
		}

		return portletTitles;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, LayoutSetRobotsContributor.class,
			"jakarta.portlet.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private ServiceTrackerMap<String, LayoutSetRobotsContributor> _serviceTrackerMap;

}