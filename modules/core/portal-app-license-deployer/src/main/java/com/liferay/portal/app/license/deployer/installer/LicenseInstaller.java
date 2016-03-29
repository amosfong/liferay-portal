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

package com.liferay.portal.app.license.deployer.installer;

import java.io.File;

import org.apache.felix.fileinstall.ArtifactInstaller;

/**
 * @author Miguel Pastor
 */
public class LicenseInstaller implements ArtifactInstaller {

	@Override
	public boolean canHandle(File artifact) {
		String name = artifact.getName();

		if (name.endsWith(".xml")) {
			return true;
		}

		return false;
	}

	@Override
	public void install(File file) throws Exception {
		System.out.println("Installing file " + file.getName());
	}

	@Override
	public void uninstall(File file) throws Exception {
		System.out.println("Updating file " + file.getName());
	}

	@Override
	public void update(File file) throws Exception {
		System.out.println("Updating file " + file.getName());
	}

}