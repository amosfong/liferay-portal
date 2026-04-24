/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.canonical.url;

import com.liferay.portal.kernel.model.Layout;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

public interface CanonicalURLParameterContributor {

	public Set<String> getParameterNames(
		HttpServletRequest httpServletRequest, Layout layout);

}
