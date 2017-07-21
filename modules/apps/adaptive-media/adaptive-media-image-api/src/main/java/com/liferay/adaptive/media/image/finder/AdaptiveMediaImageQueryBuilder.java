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

package com.liferay.adaptive.media.image.finder;

import com.liferay.adaptive.media.AdaptiveMediaAttribute;
import com.liferay.adaptive.media.finder.AdaptiveMediaQuery;
import com.liferay.adaptive.media.finder.AdaptiveMediaQueryBuilder;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Adolfo Pérez
 */
public interface AdaptiveMediaImageQueryBuilder
	extends AdaptiveMediaQueryBuilder
		<FileVersion, AdaptiveMediaImageProcessor> {

	public InitialStep allForFileEntry(FileEntry fileEntry);

	public InitialStep allForVersion(FileVersion fileVersion);

	public InitialStep forFileEntry(FileEntry fileEntry);

	public InitialStep forVersion(FileVersion fileVersion);

	public enum ConfigurationStatus {

		ANY(configurationEntry -> true),
		ENABLED(AdaptiveMediaImageConfigurationEntry::isEnabled),
		DISABLED(configurationEntry -> !configurationEntry.isEnabled());

		public Predicate<AdaptiveMediaImageConfigurationEntry> getPredicate() {
			return _predicate;
		}

		private ConfigurationStatus(
			Predicate<AdaptiveMediaImageConfigurationEntry> predicate) {

			_predicate = predicate;
		}

		private final Predicate<AdaptiveMediaImageConfigurationEntry>
			_predicate;

	}

	public interface ConfigurationStep {

		public FinalStep forConfiguration(String configurationUuid);

		public InitialStep withConfigurationStatus(
			ConfigurationStatus configurationStatus);

	}

	public interface FinalStep {

		public AdaptiveMediaQuery<FileVersion, AdaptiveMediaImageProcessor>
			done();

	}

	public interface FuzzySortStep extends FinalStep {

		public <V> FuzzySortStep with(
			AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, V>
				adaptiveMediaAttribute,
			Optional<V> valueOptional);

		public <V> FuzzySortStep with(
			AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, V>
				adaptiveMediaAttribute,
			V value);

	}

	public interface InitialStep
		extends ConfigurationStep, FuzzySortStep, StrictSortStep {
	}

	public enum SortOrder {

		ASC {

			@Override
			public int getSortValue(int value) {
				return value;
			}

		},

		DESC {

			@Override
			public int getSortValue(int value) {
				return -value;
			}

		};

		public abstract int getSortValue(int value);

	}

	public interface StrictSortStep extends FinalStep {

		public <V> StrictSortStep orderBy(
			AdaptiveMediaAttribute<AdaptiveMediaImageProcessor, V>
				adaptiveMediaAttribute,
			SortOrder sortOrder);

	}

}