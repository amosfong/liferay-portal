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

package com.liferay.adaptive.media.image.internal.mediaquery;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.finder.AdaptiveMediaImageFinder;
import com.liferay.adaptive.media.image.internal.configuration.AdaptiveMediaImageAttributeMapping;
import com.liferay.adaptive.media.image.internal.processor.AdaptiveMediaImage;
import com.liferay.adaptive.media.image.mediaquery.Condition;
import com.liferay.adaptive.media.image.mediaquery.MediaQuery;
import com.liferay.adaptive.media.image.mediaquery.MediaQueryProvider;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageAttribute;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.adaptive.media.image.url.AdaptiveMediaImageURLFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringBundler;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component
public class MediaQueryProviderImpl implements MediaQueryProvider {

	@Override
	public List<MediaQuery> getMediaQueries(FileEntry fileEntry)
		throws PortalException {

		Collection<AdaptiveMedia<AdaptiveMediaImageProcessor>> adaptiveMedias =
			_getAdaptiveMedias(fileEntry);

		List<MediaQuery> mediaQueries = new ArrayList<>();
		AdaptiveMedia<AdaptiveMediaImageProcessor> previousAdaptiveMedia = null;

		for (AdaptiveMedia<AdaptiveMediaImageProcessor>
				adaptiveMedia : adaptiveMedias) {

			Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
				hdAdaptiveMediaOptional = _getHDAdaptiveMedia(
					adaptiveMedia, adaptiveMedias);

			mediaQueries.add(
				_getMediaQuery(
					adaptiveMedia, previousAdaptiveMedia,
					hdAdaptiveMediaOptional));

			previousAdaptiveMedia = adaptiveMedia;
		}

		return mediaQueries;
	}

	@Reference(unbind = "-")
	protected void setAdaptiveMediaImageConfigurationHelper(
		AdaptiveMediaImageConfigurationHelper
			adaptiveMediaImageConfigurationHelper) {

		_adaptiveMediaImageConfigurationHelper =
			adaptiveMediaImageConfigurationHelper;
	}

	@Reference(unbind = "-")
	protected void setAdaptiveMediaImageFinder(
		AdaptiveMediaImageFinder adaptiveMediaImageFinder) {

		_adaptiveMediaImageFinder = adaptiveMediaImageFinder;
	}

	@Reference(unbind = "-")
	protected void setAdaptiveMediaImageURLFactory(
		AdaptiveMediaImageURLFactory adaptiveMediaImageURLFactory) {

		_adaptiveMediaImageURLFactory = adaptiveMediaImageURLFactory;
	}

	private Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
		_findAdaptiveMedia(
			FileEntry fileEntry,
			AdaptiveMediaImageConfigurationEntry configurationEntry) {

		try {
			return _adaptiveMediaImageFinder.getAdaptiveMediaStream(
				queryBuilder -> queryBuilder.forFileEntry(
					fileEntry
				).forConfiguration(
					configurationEntry.getUUID()
				).done()
			).findFirst();
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe);
			}

			return Optional.empty();
		}
	}

	private AdaptiveMedia<AdaptiveMediaImageProcessor>
		_getAdaptiveMediaFromConfigurationEntry(
			FileEntry fileEntry,
			AdaptiveMediaImageConfigurationEntry configurationEntry) {

		Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
			adaptiveMediaOptional = _findAdaptiveMedia(
				fileEntry, configurationEntry);

		if (adaptiveMediaOptional.isPresent()) {
			return adaptiveMediaOptional.get();
		}

		Optional<Integer> widthOptional = _getWidth(configurationEntry);
		Optional<Integer> heightOptional = _getHeight(configurationEntry);

		Map<String, String> properties = new HashMap<>();

		properties.put(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH.getName(),
			String.valueOf(widthOptional.orElse(0)));

		properties.put(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT.getName(),
			String.valueOf(heightOptional.orElse(0)));

		return new AdaptiveMediaImage(
			() -> null,
			AdaptiveMediaImageAttributeMapping.fromProperties(properties),
			_getFileEntryURL(fileEntry, configurationEntry));
	}

	private Collection<AdaptiveMedia<AdaptiveMediaImageProcessor>>
			_getAdaptiveMedias(FileEntry fileEntry)
		throws PortalException {

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			_adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntries(
					fileEntry.getCompanyId());

		List<AdaptiveMedia<AdaptiveMediaImageProcessor>> adaptiveMedias =
			new ArrayList<>();

		for (AdaptiveMediaImageConfigurationEntry configurationEntry :
				configurationEntries) {

			AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia =
				_getAdaptiveMediaFromConfigurationEntry(
					fileEntry, configurationEntry);

			if (_getWidth(adaptiveMedia) > 0) {
				adaptiveMedias.add(adaptiveMedia);
			}
		}

		Collections.sort(adaptiveMedias, _comparator);

		return adaptiveMedias;
	}

	private Optional<Integer> _getAttribute(
		AdaptiveMediaImageConfigurationEntry adaptiveMedia, String name) {

		try {
			Integer height = Integer.valueOf(
				adaptiveMedia.getProperties().get(name));

			return Optional.of(height);
		}
		catch (NumberFormatException nfe) {
			return Optional.empty();
		}
	}

	private List<Condition> _getConditions(
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia,
		AdaptiveMedia<AdaptiveMediaImageProcessor> previousAdaptiveMedia) {

		List<Condition> conditions = new ArrayList<>();

		conditions.add(
			new Condition("max-width", _getWidth(adaptiveMedia) + "px"));

		if (previousAdaptiveMedia != null) {
			conditions.add(
				new Condition(
					"min-width", _getWidth(previousAdaptiveMedia) + "px"));
		}

		return conditions;
	}

	private URI _getFileEntryURL(
		FileEntry fileEntry,
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		try {
			return _adaptiveMediaImageURLFactory.createFileEntryURL(
				fileEntry.getFileVersion(), configurationEntry);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
		_getHDAdaptiveMedia(
			AdaptiveMedia<AdaptiveMediaImageProcessor> originalAdaptiveMedia,
			Collection<AdaptiveMedia<AdaptiveMediaImageProcessor>>
				adaptiveMedias) {

		for (AdaptiveMedia<AdaptiveMediaImageProcessor>
				adaptiveMedia : adaptiveMedias) {

			int originalWidth = _getWidth(originalAdaptiveMedia) * 2;
			int originalHeight = _getHeight(originalAdaptiveMedia) * 2;

			IntStream widthIntStream = IntStream.range(
				originalWidth - 1, originalWidth + 2);

			boolean widthMatch = widthIntStream.anyMatch(
				value -> value == _getWidth(adaptiveMedia));

			IntStream heightIntStream = IntStream.range(
				originalHeight - 1, originalHeight + 2);

			boolean heightMatch = heightIntStream.anyMatch(
				value -> value == _getHeight(adaptiveMedia));

			if (widthMatch && heightMatch) {
				return Optional.of(adaptiveMedia);
			}
		}

		return Optional.empty();
	}

	private Integer _getHeight(
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia) {

		return adaptiveMedia.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_HEIGHT).orElse(0);
	}

	private Optional<Integer> _getHeight(
		AdaptiveMediaImageConfigurationEntry originalConfigurationEntry) {

		return _getAttribute(originalConfigurationEntry, "max-height");
	}

	private MediaQuery _getMediaQuery(
			AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia,
			AdaptiveMedia<AdaptiveMediaImageProcessor> previousAdaptiveMedia,
			Optional<AdaptiveMedia<AdaptiveMediaImageProcessor>>
				hdAdaptiveMediaOptional)
		throws PortalException {

		StringBundler src = new StringBundler(3);

		List<Condition> conditions = _getConditions(
			adaptiveMedia, previousAdaptiveMedia);

		src.append(adaptiveMedia.getURI());

		hdAdaptiveMediaOptional.ifPresent(
			hdAdaptiveMedia -> {
				src.append(", ");
				src.append(hdAdaptiveMedia.getURI());
				src.append(" 2x");
			});

		return new MediaQuery(conditions, src.toString());
	}

	private Integer _getWidth(
		AdaptiveMedia<AdaptiveMediaImageProcessor> adaptiveMedia) {

		Optional<Integer> attributeValue = adaptiveMedia.getValueOptional(
			AdaptiveMediaImageAttribute.IMAGE_WIDTH);

		return attributeValue.orElse(0);
	}

	private Optional<Integer> _getWidth(
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		return _getAttribute(configurationEntry, "max-width");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MediaQueryProviderImpl.class);

	private AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper;
	private AdaptiveMediaImageFinder _adaptiveMediaImageFinder;
	private AdaptiveMediaImageURLFactory _adaptiveMediaImageURLFactory;
	private final Comparator<AdaptiveMedia<AdaptiveMediaImageProcessor>>
		_comparator = Comparator.comparingInt(this::_getWidth);

}