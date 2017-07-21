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

package com.liferay.adaptive.media.image.internal.finder;

import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.finder.AdaptiveMediaImageQueryBuilder;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveMediaImageConfigurationEntryImpl;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaImageQueryBuilderImplTest {

	@Test
	public void testFileEntryQueryReturnsLatestFileVersion() throws Exception {
		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		_queryBuilder.allForFileEntry(fileEntry);

		Assert.assertEquals(
			fileEntry.getFileVersion(), _queryBuilder.getFileVersion());
	}

	@Test
	public void testFileEntryWithAttributesQueryReturnsLatestFileVersion()
		throws Exception {

		FileEntry fileEntry = Mockito.mock(FileEntry.class);

		_queryBuilder.forFileEntry(
			fileEntry
		).done();

		Assert.assertEquals(
			fileEntry.getFileVersion(), _queryBuilder.getFileVersion());
	}

	@Test
	public void testMatchingConfigurationAttributeQuery() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.forVersion(
			fileVersion
		).forConfiguration(
			"small"
		);

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(), "small",
				Collections.emptyMap(), true);

		Predicate<AdaptiveMediaImageConfigurationEntry> filter =
			_queryBuilder.getConfigurationEntryFilter();

		Assert.assertTrue(filter.test(configurationEntry));
	}

	@Test
	public void testNonMatchingConfigurationAttributeQuery() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.forVersion(
			fileVersion
		).forConfiguration(
			"small"
		);

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			new AdaptiveMediaImageConfigurationEntryImpl(
				StringUtil.randomString(), StringUtil.randomString(),
				StringUtil.randomString(), Collections.emptyMap(), true);

		Predicate<AdaptiveMediaImageConfigurationEntry> filter =
			_queryBuilder.getConfigurationEntryFilter();

		Assert.assertFalse(filter.test(configurationEntry));
	}

	@Test
	public void testNonNullOptionalAttributeQuery() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.forVersion(
			fileVersion
		).with(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT, Optional.of(100)
		);

		Map<AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, ?>, Object>
			attributes = _queryBuilder.getAttributes();

		Assert.assertEquals(
			100, attributes.get(AdaptiveMediaImageAttribute.IMAGE_HEIGHT));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullAttributeFailsWhenOrderingByIt() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.forVersion(
			fileVersion
		).orderBy(
			null, AdaptiveMediaImageQueryBuilder.SortOrder.DESC
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullAttributeValueFailsWhenQueryingAttributes() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.forVersion(
			fileVersion
		).with(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT, (Integer)null
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullConfigurationStatusFails() {
		_queryBuilder.withConfigurationStatus(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullConfigurationUUIDFailsWhenQueryingAttributes() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.forVersion(
			fileVersion
		).forConfiguration(
			null
		);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileEntryFailsWhenQueryingAll() {
		_queryBuilder.allForFileEntry(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileEntryFailsWhenQueryingAttributes() {
		_queryBuilder.forFileEntry(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileVersionFailsWhenQueryingAll() {
		_queryBuilder.allForVersion(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullFileVersionFailsWhenQueryingAttributes() {
		_queryBuilder.forVersion(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullOptionalAttributeValueFailsWhenQueryingAttributes() {
		FileVersion fileVersion = Mockito.mock(FileVersion.class);

		_queryBuilder.forVersion(
			fileVersion
		).with(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT, (Optional<Integer>)null
		);
	}

	private final AdaptiveMediaImageQueryBuilderImpl _queryBuilder =
		new AdaptiveMediaImageQueryBuilderImpl();

}