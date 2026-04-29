/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.robots;

import com.liferay.layout.seo.contributor.LayoutSetRobotsContributor;
import com.liferay.layout.seo.robots.LayoutSetRobotsProvider;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Amos Fong
 * @author David Truong
 * @author Jesse Rao
 */
@Component(service = LayoutSetRobotsProvider.class)
public class LayoutSetRobotsProviderImpl
	implements LayoutSetRobotsProvider {

	@Override
	public String getRobots(LayoutSet layoutSet, boolean secure)
		throws PortalException {

		if (layoutSet == null) {
			try {
				return StringUtil.read(
					PortalClassLoaderUtil.getClassLoader(),
					PropsValues.ROBOTS_TXT_WITHOUT_SITEMAP);
			}
			catch (IOException ioException) {
				_log.error(
					"Unable to read the content for " +
						PropsValues.ROBOTS_TXT_WITHOUT_SITEMAP,
					ioException);
			}
		}

		int portalServerPort = PortalUtil.getPortalServerPort(secure);

		NavigableMap<String, String> virtualHostnames =
			PortalUtil.getVirtualHostnames(layoutSet);

		String virtualHostname = StringPool.BLANK;

		if (!virtualHostnames.isEmpty()) {
			virtualHostname = virtualHostnames.firstKey();
		}

		String robotsTxt = null;

		try {
			robotsTxt = GetterUtil.getString(
				layoutSet.getSettingsProperty(
					layoutSet.isPrivateLayout() + "-robots.txt"),
				StringUtil.read(
					PortalClassLoaderUtil.getClassLoader(),
					PropsValues.ROBOTS_TXT_WITH_SITEMAP));
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to read the content for " +
					PropsValues.ROBOTS_TXT_WITH_SITEMAP,
				ioException);
		}

		robotsTxt = _replaceWildcards(
			robotsTxt, virtualHostname, secure, portalServerPort);

		String robotsContribution = getRobotsContribution(layoutSet);

		if (Validator.isNotNull(robotsContribution)) {
			return StringBundler.concat(robotsTxt, "\n\n", robotsContribution);
		}

		return robotsTxt;
	}

	@Override
	public String getRobotsContribution(LayoutSet layoutSet) {
		Set<String> disallowURLEntries = new TreeSet<>();

		for (LayoutSetRobotsContributor layoutSetRobotsContributor :
				_serviceTrackerList.toList()) {

			Set<String> contributedDisallowURLEntries =
				layoutSetRobotsContributor.contributeDisallowURLEntries(
					layoutSet);

			if (contributedDisallowURLEntries != null) {
				disallowURLEntries.addAll(contributedDisallowURLEntries);
			}
		}

		if (disallowURLEntries.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(
			(disallowURLEntries.size() * 3) + 1);

		sb.append("User-Agent: *\n");

		for (String disallowURLEntry : disallowURLEntries) {
			sb.append("Disallow: ");
			sb.append(disallowURLEntry);
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, LayoutSetRobotsContributor.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private String _replaceWildcards(
		String robotsTxt, String virtualHostname, boolean secure, int port) {

		if (Validator.isNotNull(virtualHostname)) {
			robotsTxt = StringUtil.replace(
				robotsTxt, "[$HOST$]", virtualHostname);
		}
		else if (_log.isWarnEnabled()) {
			_log.warn(
				"Placeholder [$HOST$] could not be replaced with the actual " +
					"host");
		}

		robotsTxt = StringUtil.replace(
			robotsTxt, "[$PORT$]", String.valueOf(port));

		if (secure) {
			return StringUtil.replace(robotsTxt, "[$PROTOCOL$]", "https");
		}

		return StringUtil.replace(robotsTxt, "[$PROTOCOL$]", "http");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetRobotsProviderImpl.class);

	private ServiceTrackerList<LayoutSetRobotsContributor> _serviceTrackerList;

}
