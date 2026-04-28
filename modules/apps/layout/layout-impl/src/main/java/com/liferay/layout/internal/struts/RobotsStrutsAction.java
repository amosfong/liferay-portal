/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.struts;

import com.liferay.layout.seo.contributor.PortletSEOContributor;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.RobotsUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(property = "path=/portal/robots", service = StrutsAction.class)
public class RobotsStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		try {
			String host = GetterUtil.getString(
				_portal.getForwardedHost(httpServletRequest));

			LayoutSet layoutSet = null;

			VirtualHost virtualHost = _virtualHostLocalService.fetchVirtualHost(
				host);

			if ((virtualHost != null) && (virtualHost.getLayoutSetId() > 0)) {
				layoutSet = _layoutSetLocalService.fetchLayoutSet(host);
			}
			else {
				Company company = _portal.getCompany(httpServletRequest);

				if (host.equals(company.getVirtualHostname()) &&
					Validator.isNotNull(
						PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME)) {

					Group defaultGroup = _groupLocalService.getGroup(
						company.getCompanyId(),
						PropsValues.VIRTUAL_HOSTS_DEFAULT_SITE_NAME);

					layoutSet = defaultGroup.getPublicLayoutSet();
				}
			}

			StringBundler sb = new StringBundler();

			sb.append(
				RobotsUtil.getRobots(layoutSet, httpServletRequest.isSecure()));

			if (layoutSet != null) {
				Set<String> disallowURLEntries = new TreeSet<>();

				for (PortletSEOContributor portletSEOContributor :
						_serviceTrackerList.toList()) {

					Set<String> contributedDisallowURLEntries =
						portletSEOContributor.contributeRobotsDisallowURLEntries(
							layoutSet);

					if (contributedDisallowURLEntries != null) {
						disallowURLEntries.addAll(contributedDisallowURLEntries);
					}
				}

				if (!disallowURLEntries.isEmpty()) {
					sb.append("\n\nUser-Agent: *\n");

					for (String disallowURLEntry : disallowURLEntries) {
						sb.append("Disallow: ");
						sb.append(disallowURLEntry);
						sb.append(StringPool.NEW_LINE);
					}
				}
			}

			ServletResponseUtil.sendFile(
				httpServletRequest, httpServletResponse, null,
				sb.toString(
				).getBytes(
					StringPool.UTF8
				),
				ContentTypes.TEXT_PLAIN_UTF8);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			_portal.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception,
				httpServletRequest, httpServletResponse);
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, PortletSEOContributor.class);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RobotsStrutsAction.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	private ServiceTrackerList<PortletSEOContributor> _serviceTrackerList;

	@Reference
	private VirtualHostLocalService _virtualHostLocalService;

}