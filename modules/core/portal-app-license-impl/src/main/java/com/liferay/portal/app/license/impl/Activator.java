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

package com.liferay.portal.app.license.impl;

import com.liferay.portal.app.license.AppLicenseVerifier;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Raymond Augé
 */
public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("version", bundleContext.getBundle().getVersion());

		_serviceRegistration = bundleContext.registerService(
			AppLicenseVerifier.class, new LiferayPackageAppLicenseVerifier(),
			properties);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_serviceRegistration.unregister();
	}

	private ServiceRegistration<AppLicenseVerifier> _serviceRegistration;

}