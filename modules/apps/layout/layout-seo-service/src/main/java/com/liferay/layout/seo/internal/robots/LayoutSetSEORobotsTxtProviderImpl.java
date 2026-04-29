/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.robots;

import com.liferay.layout.seo.contributor.LayoutSetRobotsContributor;
import com.liferay.layout.seo.robots.LayoutSetSEORobotsTxtProvider;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.LayoutSet;

import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Amos Fong
 */
@Component(service = LayoutSetSEORobotsTxtProvider.class)
public class LayoutSetSEORobotsTxtProviderImpl
	implements LayoutSetSEORobotsTxtProvider {

	@Override
	public String getRobotsTxtContribution(LayoutSet layoutSet) {
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

	private ServiceTrackerList<LayoutSetRobotsContributor> _serviceTrackerList;

}