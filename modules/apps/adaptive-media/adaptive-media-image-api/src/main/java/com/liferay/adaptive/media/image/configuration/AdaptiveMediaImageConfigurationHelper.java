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

package com.liferay.adaptive.media.image.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.exception.AdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.exception.AdaptiveMediaImageConfigurationException.InvalidStateAdaptiveMediaImageConfigurationException;

import java.io.IOException;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Alejandro Hernández
 */
@ProviderType
public interface AdaptiveMediaImageConfigurationHelper {

	public AdaptiveMediaImageConfigurationEntry
			addAdaptiveMediaImageConfigurationEntry(
				long companyId, String name, String description, String uuid,
				Map<String, String> properties)
		throws AdaptiveMediaImageConfigurationException, IOException;

	public void deleteAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws InvalidStateAdaptiveMediaImageConfigurationException,
			IOException;

	public void disableAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException;

	public void enableAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException;

	public void forceDeleteAdaptiveMediaImageConfigurationEntry(
			long companyId, String uuid)
		throws IOException;

	public Collection<AdaptiveMediaImageConfigurationEntry>
		getAdaptiveMediaImageConfigurationEntries(long companyId);

	public Collection<AdaptiveMediaImageConfigurationEntry>
		getAdaptiveMediaImageConfigurationEntries(
			long companyId,
			Predicate<? super AdaptiveMediaImageConfigurationEntry> predicate);

	public Optional<AdaptiveMediaImageConfigurationEntry>
		getAdaptiveMediaImageConfigurationEntry(
			long companyId, String configurationEntryUUID);

	public AdaptiveMediaImageConfigurationEntry
			updateAdaptiveMediaImageConfigurationEntry(
				long companyId, String oldUuid, String name, String description,
				String newUuid, Map<String, String> properties)
		throws AdaptiveMediaImageConfigurationException, IOException;

}