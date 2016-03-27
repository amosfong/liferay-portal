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

package com.liferay.portal.app.license.deployer;

import com.liferay.portal.app.license.deployer.handler.LicenseStreamHandlerService;
import com.liferay.portal.app.license.deployer.transformer.LicenseTransformer;

import java.util.Dictionary;
import java.util.Hashtable;

import org.apache.felix.fileinstall.ArtifactUrlTransformer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;

/**
 * @author Miguel Pastor
 */
@Component(immediate = true)
public class LicenseDeployerActivator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_urlStreamHandlerServiceServiceRegistration = registerHandlerService(
			bundleContext);

		_artifactUrlTransformerServiceRegistration =
			registerArtifactUrlTransformer(bundleContext);
	}

	@Deactivate
	protected void deactivate() {
		_artifactUrlTransformerServiceRegistration.unregister();

		_urlStreamHandlerServiceServiceRegistration.unregister();
	}

	protected ServiceRegistration<?> registerArtifactUrlTransformer(
		BundleContext bundleContext) {

		return bundleContext.registerService(
			ArtifactUrlTransformer.class, new LicenseTransformer(), null);
	}

	protected ServiceRegistration<?> registerHandlerService(
		BundleContext bundleContext) {

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			URLConstants.URL_HANDLER_PROTOCOL, new String[] {"license"});

		return bundleContext.registerService(
			URLStreamHandlerService.class.getName(),
			new LicenseStreamHandlerService(), properties);
	}

	private ServiceRegistration<?> _artifactUrlTransformerServiceRegistration;
	private ServiceRegistration<?> _urlStreamHandlerServiceServiceRegistration;

}