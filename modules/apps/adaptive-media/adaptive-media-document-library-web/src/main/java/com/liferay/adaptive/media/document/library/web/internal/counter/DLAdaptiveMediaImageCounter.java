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

package com.liferay.adaptive.media.document.library.web.internal.counter;

import com.liferay.adaptive.media.image.constants.AdaptiveMediaImageConstants;
import com.liferay.adaptive.media.image.counter.AdaptiveMediaImageCounter;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true, property = {"adaptive.media.key=document-library"},
	service = AdaptiveMediaImageCounter.class
)
public class DLAdaptiveMediaImageCounter implements AdaptiveMediaImageCounter {

	@Override
	public int countExpectedAdaptiveMediaImageEntries(long companyId) {
		DynamicQuery dynamicQuery = _dlFileEntryLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");
		Property repositoryIdProperty = PropertyFactoryUtil.forName(
			"repositoryId");

		dynamicQuery.add(groupIdProperty.eqProperty(repositoryIdProperty));

		Property mimeTypeProperty = PropertyFactoryUtil.forName("mimeType");

		dynamicQuery.add(
			mimeTypeProperty.in(
				AdaptiveMediaImageConstants.getSupportedMimeTypes()));

		return (int)_dlFileEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}