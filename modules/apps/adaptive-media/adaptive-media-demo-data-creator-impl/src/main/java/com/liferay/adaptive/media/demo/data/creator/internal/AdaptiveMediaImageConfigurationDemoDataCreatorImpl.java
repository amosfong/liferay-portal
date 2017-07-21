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

package com.liferay.adaptive.media.demo.data.creator.internal;

import com.liferay.adaptive.media.demo.data.creator.AdaptiveMediaImageConfigurationDemoDataCreator;
import com.liferay.adaptive.media.demo.data.creator.DemoAdaptiveMediaImageConfigurationVariant;
import com.liferay.adaptive.media.exception.AdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = AdaptiveMediaImageConfigurationDemoDataCreator.class)
public class AdaptiveMediaImageConfigurationDemoDataCreatorImpl
	implements AdaptiveMediaImageConfigurationDemoDataCreator {

	@Override
	public Collection<AdaptiveMediaImageConfigurationEntry> create(
			long companyId)
		throws IOException {

		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			new ArrayList<>();

		for (DemoAdaptiveMediaImageConfigurationVariant
				demoConfigurationVariant :
					DemoAdaptiveMediaImageConfigurationVariant.values()) {

			AdaptiveMediaImageConfigurationEntry configurationEntry = create(
				companyId, demoConfigurationVariant);

			configurationEntries.add(configurationEntry);
		}

		return configurationEntries;
	}

	@Override
	public AdaptiveMediaImageConfigurationEntry create(
			long companyId,
			DemoAdaptiveMediaImageConfigurationVariant
				demoAdaptiveMediaImageConfigurationVariant)
		throws IOException {

		AdaptiveMediaImageConfigurationEntry configurationEntry = null;

		try {
			configurationEntry =
				_adaptiveMediaImageConfigurationHelper.
					addAdaptiveMediaImageConfigurationEntry(
						companyId,
						demoAdaptiveMediaImageConfigurationVariant.getName(),
						demoAdaptiveMediaImageConfigurationVariant.
							getDescription(),
						demoAdaptiveMediaImageConfigurationVariant.getUuid(),
						demoAdaptiveMediaImageConfigurationVariant.
							getProperties());

			_addConfigurationUuid(companyId, configurationEntry.getUUID());
		}
		catch (AdaptiveMediaImageConfigurationException amice) {
			_log.error(
				"Unable to add image adaptive media configuration", amice);
		}

		return configurationEntry;
	}

	@Override
	public void delete() throws IOException {
		for (Long companyId : _configurationIds.keySet()) {
			List<String> uuids = _configurationIds.get(companyId);

			for (String uuid : uuids) {
				_adaptiveMediaImageConfigurationHelper.
					forceDeleteAdaptiveMediaImageConfigurationEntry(
						companyId, uuid);

				uuids.remove(uuid);
			}
		}
	}

	private void _addConfigurationUuid(long companyId, String uuid) {
		_configurationIds.computeIfAbsent(
			companyId, k -> new CopyOnWriteArrayList<>());

		List<String> uuids = _configurationIds.get(companyId);

		uuids.add(uuid);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AdaptiveMediaImageConfigurationDemoDataCreatorImpl.class);

	@Reference
	private AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper;

	private final Map<Long, List<String>> _configurationIds = new HashMap<>();

}