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

import com.liferay.adaptive.media.exception.AdaptiveMediaRuntimeException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.internal.util.ImageProcessor;
import com.liferay.adaptive.media.image.internal.util.RenderedImageUtil;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageEntryLocalService;
import com.liferay.adaptive.media.processor.AdaptiveMediaProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.awt.image.RenderedImage;

import java.io.IOException;

import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.repository.model.FileVersion",
	service = {AdaptiveMediaImageProcessor.class, AdaptiveMediaProcessor.class}
)
public final class AdaptiveMediaImageProcessorImpl
	implements AdaptiveMediaImageProcessor {

	@Override
	public void cleanUp(FileVersion fileVersion) {
		try {
			if (!_imageProcessor.isMimeTypeSupported(
					fileVersion.getMimeType())) {

				return;
			}

			_imageEntryLocalService.deleteAdaptiveMediaImageEntryFileVersion(
				fileVersion);
		}
		catch (PortalException pe) {
			throw new AdaptiveMediaRuntimeException.IOException(pe);
		}
	}

	@Override
	public void process(FileVersion fileVersion) {
		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return;
		}

		Iterable<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			_configurationHelper.getAdaptiveMediaImageConfigurationEntries(
				fileVersion.getCompanyId());

		configurationEntries.forEach(
			configurationEntry -> process(
				fileVersion, configurationEntry.getUUID()));
	}

	@Override
	public void process(
		FileVersion fileVersion, String configurationEntryUuid) {

		if (!_imageProcessor.isMimeTypeSupported(fileVersion.getMimeType())) {
			return;
		}

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				_configurationHelper.getAdaptiveMediaImageConfigurationEntry(
					fileVersion.getCompanyId(), configurationEntryUuid);

		if (!configurationEntryOptional.isPresent()) {
			return;
		}

		AdaptiveMediaImageConfigurationEntry configurationEntry =
			configurationEntryOptional.get();

		AdaptiveMediaImageEntry imageEntry =
			_imageEntryLocalService.fetchAdaptiveMediaImageEntry(
				configurationEntry.getUUID(), fileVersion.getFileVersionId());

		if (imageEntry != null) {
			return;
		}

		RenderedImage renderedImage = _imageProcessor.scaleImage(
			fileVersion, configurationEntry);

		try {
			byte[] bytes = RenderedImageUtil.getRenderedImageContentStream(
				renderedImage, fileVersion.getMimeType());

			_imageEntryLocalService.addAdaptiveMediaImageEntry(
				configurationEntry, fileVersion, renderedImage.getWidth(),
				renderedImage.getHeight(),
				new UnsyncByteArrayInputStream(bytes), bytes.length);
		}
		catch (IOException | PortalException e) {
			throw new AdaptiveMediaRuntimeException.IOException(e);
		}
	}

	@Reference(unbind = "-")
	public void setAdaptiveMediaImageConfigurationHelper(
		AdaptiveMediaImageConfigurationHelper configurationHelper) {

		_configurationHelper = configurationHelper;
	}

	@Reference(unbind = "-")
	public void setImageEntryLocalService(
		AdaptiveMediaImageEntryLocalService imageEntryLocalService) {

		_imageEntryLocalService = imageEntryLocalService;
	}

	@Reference(unbind = "-")
	public void setImageProcessor(ImageProcessor imageProcessor) {
		_imageProcessor = imageProcessor;
	}

	private AdaptiveMediaImageConfigurationHelper _configurationHelper;
	private AdaptiveMediaImageEntryLocalService _imageEntryLocalService;
	private ImageProcessor _imageProcessor;

}