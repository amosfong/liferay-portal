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

package com.liferay.adaptive.media.image.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the AdaptiveMediaImageEntry service. Represents a row in the &quot;AdaptiveMediaImageEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImageEntryModel
 * @see com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageEntryImpl
 * @see com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageEntryModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageEntryImpl")
@ProviderType
public interface AdaptiveMediaImageEntry extends AdaptiveMediaImageEntryModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageEntryImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AdaptiveMediaImageEntry, Long> ADAPTIVE_MEDIA_IMAGE_ENTRY_ID_ACCESSOR =
		new Accessor<AdaptiveMediaImageEntry, Long>() {
			@Override
			public Long get(AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
				return adaptiveMediaImageEntry.getAdaptiveMediaImageEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AdaptiveMediaImageEntry> getTypeClass() {
				return AdaptiveMediaImageEntry.class;
			}
		};
}