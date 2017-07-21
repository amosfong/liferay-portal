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

package com.liferay.adaptive.media.image.internal.processor;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveMediaImageAttributeMapping;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;

import java.io.InputStream;

import java.net.URI;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @author Adolfo Pérez
 */
public final class AdaptiveMediaImage
	implements AdaptiveMedia<AdaptiveMediaImageProcessor> {

	public AdaptiveMediaImage(
		Supplier<InputStream> supplier,
		AdaptiveMediaImageAttributeMapping attributeMapping, URI uri) {

		_supplier = supplier;
		_attributeMapping = attributeMapping;
		_uri = uri;
	}

	@Override
	public InputStream getInputStream() {
		return _supplier.get();
	}

	@Override
	public URI getURI() {
		return _uri;
	}

	@Override
	public <V> Optional<V> getValueOptional(
		AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, V>
			adaptiveMediaAttribute) {

		return _attributeMapping.getValueOptional(adaptiveMediaAttribute);
	}

	private final AdaptiveMediaImageAttributeMapping _attributeMapping;
	private final Supplier<InputStream> _supplier;
	private final URI _uri;

}