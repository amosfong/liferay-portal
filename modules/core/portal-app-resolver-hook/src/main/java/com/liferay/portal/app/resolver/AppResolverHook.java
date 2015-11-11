/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.app.resolver;

import aQute.bnd.header.Attrs;
import aQute.bnd.header.OSGiHeader;

import com.liferay.portal.app.license.AppLicenseVerifier;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.SortedMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.hooks.resolver.ResolverHook;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.framework.wiring.BundleRequirement;
import org.osgi.framework.wiring.BundleRevision;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Amos Fong
 */
public class AppResolverHook implements ResolverHook {

	public AppResolverHook(
		final Collection<BundleRevision> triggers,
		final ServiceTracker<AppLicenseVerifier, AppLicenseVerifier>
			serviceTracker) {

		_triggers = triggers;
		_serviceTracker = serviceTracker;
	}

	@Override
	public void filterSingletonCollisions(
		BundleCapability singleton,
		Collection<BundleCapability> collisionCandidates) {
	}

	@Override
	public void filterResolvable(Collection<BundleRevision> candidates) {
		Iterator<BundleRevision> iterator = candidates.iterator();

		while (iterator.hasNext()) {
			BundleRevision bundleRevision = iterator.next();

			try {
				filterResolvable(bundleRevision);
			}
			catch (Exception e) {
				iterator.remove();
			}
		}
	}

	private void filterResolvable(BundleRevision bundleRevision)
		throws Exception {

		SortedMap<ServiceReference<AppLicenseVerifier>, AppLicenseVerifier>
			verifiers = _serviceTracker.getTracked();

		if (verifiers.isEmpty()) {
			System.out.println("# NO LICENSE VERIFIERS FOUND");

			return;
		}

		Bundle bundle = bundleRevision.getBundle();

		System.out.println(
			"####HOOK RESOLVER FILTERING: " + bundle.getBundleId());

		Dictionary<String, String> headers = bundle.getHeaders();

		String marketplaceProperties = headers.get("X-Liferay-Marketplace");

		if (marketplaceProperties == null) {
			return;
		}

		Attrs parameters = OSGiHeader.parseProperties(marketplaceProperties);

		String productId = parameters.get("productId");
		String productType = parameters.get("productType");
		String productVersion = parameters.get("productVersion");
		String licenseVersion = parameters.get("licenseVersion");

		System.out.println("productId: " + productId);
		System.out.println("productType: " + productType);
		System.out.println("productVersion: " + productVersion);
		System.out.println("licenseVersion: " + licenseVersion);

		if (productId == null) {
			return;
		}

		Filter filter = FrameworkUtil.createFilter(
			"(version=" + licenseVersion + ")");

		for (ServiceReference<AppLicenseVerifier> serviceReference :
				verifiers.keySet()) {

			if (!filter.match(serviceReference)) {
				continue;
			}

			AppLicenseVerifier appLicenseVerifier = verifiers.get(
				serviceReference);

			if (!appLicenseVerifier.verify(
					productId, productType, productVersion)) {

				System.out.println("####HOOK RESOLVER NOT VERIFIED");
			}

			break;
		}
	}

	@Override
	public void filterMatches(
		BundleRequirement requirement,
		Collection<BundleCapability> candidates) {
	}

	@Override
	public void end() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AppResolverHook.class);

	private final ServiceTracker<AppLicenseVerifier, AppLicenseVerifier>
		_serviceTracker;
	private final Collection<BundleRevision> _triggers;

}