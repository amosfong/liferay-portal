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

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaImageAttributeMappingTest {

	@Test
	public void testCreateFromEmptyMap() {
		AdaptiveMediaImageAttributeMapping attributeMapping =
			AdaptiveMediaImageAttributeMapping.fromProperties(
				Collections.emptyMap());

		Optional<Integer> heightOptional = attributeMapping.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT);

		Assert.assertFalse(heightOptional.isPresent());

		Optional<Integer> widthOptional = attributeMapping.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH);

		Assert.assertFalse(widthOptional.isPresent());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailWhenCreatingFromNullMap() {
		AdaptiveMediaImageAttributeMapping.fromProperties(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailWhenGettingValueOfNullAttribute() {
		AdaptiveMediaImageAttributeMapping attributeMapping =
			AdaptiveMediaImageAttributeMapping.fromProperties(
				MapUtil.fromArray(
					AdaptiveMediaImageAttribute.IMAGE_HEIGHT.getName(), "100",
					AdaptiveMediaImageAttribute.IMAGE_WIDTH.getName(), "200"));

		attributeMapping.getValueOptional(null);
	}

	@Test
	public void testIgnoreUnknownAttributes() {
		AdaptiveMediaImageAttributeMapping attributeMapping =
			AdaptiveMediaImageAttributeMapping.fromProperties(
				MapUtil.fromArray("foo", StringUtil.randomString()));

		Optional<Integer> heightOptional = attributeMapping.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT);

		Assert.assertFalse(heightOptional.isPresent());

		Optional<Integer> widthOptional = attributeMapping.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH);

		Assert.assertFalse(widthOptional.isPresent());
	}

	@Test
	public void testValidAttributes() {
		AdaptiveMediaImageAttributeMapping attributeMapping =
			AdaptiveMediaImageAttributeMapping.fromProperties(
				MapUtil.fromArray(
					AdaptiveMediaImageAttribute.IMAGE_HEIGHT.getName(), "100",
					AdaptiveMediaImageAttribute.IMAGE_WIDTH.getName(), "200"));

		Optional<Integer> heightOptional = attributeMapping.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(Integer.valueOf(100), heightOptional.get());

		Optional<Integer> widthOptional = attributeMapping.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH);

		Assert.assertEquals(Integer.valueOf(200), widthOptional.get());
	}

	@Test
	public void testValidSingleAttribute() {
		AdaptiveMediaImageAttributeMapping attributeMapping =
			AdaptiveMediaImageAttributeMapping.fromProperties(
				MapUtil.fromArray(
					AdaptiveMediaImageAttribute.IMAGE_HEIGHT.getName(), "100"));

		Optional<Integer> heightOptional = attributeMapping.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT);

		Assert.assertEquals(Integer.valueOf(100), heightOptional.get());

		Optional<Integer> widthOptional = attributeMapping.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH);

		Assert.assertFalse(widthOptional.isPresent());
	}

}