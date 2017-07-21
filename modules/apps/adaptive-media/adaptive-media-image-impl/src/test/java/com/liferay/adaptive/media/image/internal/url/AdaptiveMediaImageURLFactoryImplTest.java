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

package com.liferay.adaptive.media.image.internal.url;

import com.liferay.adaptive.media.AdaptiveMediaURIResolver;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveMediaImageConfigurationEntryImpl;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.net.URI;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Alejandro Tardín
 */
@RunWith(MockitoJUnitRunner.class)
public class AdaptiveMediaImageURLFactoryImplTest {

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_fileVersion.getFileEntryId()
		).thenReturn(
			1L
		);

		Mockito.when(
			_fileVersion.getFileVersionId()
		).thenReturn(
			2L
		);

		Mockito.when(
			_fileVersion.getFileName()
		).thenReturn(
			"fileName"
		);

		Mockito.when(
			_adaptiveMediaURIResolver.resolveURI(Mockito.any(URI.class))
		).thenAnswer(
			invocation -> URI.create("prefix/" + invocation.getArguments()[0])
		);

		_adaptiveMediaImageURLFactory.setAdaptiveMediaURIResolver(
			_adaptiveMediaURIResolver);
	}

	@Test
	public void testCreatesURLForFileEntry() throws Exception {
		URI uri = _adaptiveMediaImageURLFactory.createFileEntryURL(
			_fileVersion, _configurationEntry);

		Assert.assertEquals("prefix/image/1/theUuid/fileName", uri.toString());
	}

	@Test
	public void testCreatesURLForFileVersion() throws Exception {
		URI uri = _adaptiveMediaImageURLFactory.createFileVersionURL(
			_fileVersion, _configurationEntry);

		Assert.assertEquals(
			"prefix/image/1/2/theUuid/fileName", uri.toString());
	}

	private static final String _UUID = "theUuid";

	private final AdaptiveMediaImageURLFactoryImpl
		_adaptiveMediaImageURLFactory = new AdaptiveMediaImageURLFactoryImpl();

	@Mock
	private AdaptiveMediaURIResolver _adaptiveMediaURIResolver;

	private final AdaptiveMediaImageConfigurationEntry _configurationEntry =
		new AdaptiveMediaImageConfigurationEntryImpl(
			"small", _UUID, new HashMap<>());

	@Mock
	private FileVersion _fileVersion;

}