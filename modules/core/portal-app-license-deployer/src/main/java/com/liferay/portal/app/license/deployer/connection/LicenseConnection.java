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

package com.liferay.portal.app.license.deployer.connection;

import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Miguel Pastor
 */
public class LicenseConnection extends URLConnection {

	public LicenseConnection(URL url) {
		super(url);

		try {
			_innerURL = new URL(url.getPath());
		}
		catch (MalformedURLException murle) {
			throw new RuntimeException(
				"Unable to build URL from " + url.getPath());
		}
	}

	@Override
	public void connect() {
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}

	private final URL _innerURL;

}