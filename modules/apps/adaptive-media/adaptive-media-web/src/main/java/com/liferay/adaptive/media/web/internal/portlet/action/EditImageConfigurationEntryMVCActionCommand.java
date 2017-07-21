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

package com.liferay.adaptive.media.web.internal.portlet.action;

import com.liferay.adaptive.media.exception.AdaptiveMediaImageConfigurationException;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageEntryLocalService;
import com.liferay.adaptive.media.web.constants.AdaptiveMediaPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AdaptiveMediaPortletKeys.ADAPTIVE_MEDIA,
		"mvc.command.name=/adaptive_media/edit_image_configuration_entry"
	},
	service = MVCActionCommand.class
)
public class EditImageConfigurationEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doPermissionCheckedProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		hideDefaultErrorMessage(actionRequest);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String uuid = ParamUtil.getString(actionRequest, "uuid");
		String maxHeight = ParamUtil.getString(actionRequest, "maxHeight");
		String maxWidth = ParamUtil.getString(actionRequest, "maxWidth");

		Map<String, String> properties = new HashMap<>();

		properties.put("max-height", maxHeight);
		properties.put("max-width", maxWidth);

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				_adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						themeDisplay.getCompanyId(), uuid);

		boolean automaticUuid = ParamUtil.getBoolean(
			actionRequest, "automaticUuid");

		String newUuid = null;

		boolean autoModifiedUuid = false;

		if (automaticUuid) {
			String normalizedName = FriendlyURLNormalizerUtil.normalize(name);

			newUuid = _getAutomaticUuid(
				themeDisplay.getCompanyId(), normalizedName, uuid);

			if (!newUuid.equals(normalizedName)) {
				autoModifiedUuid = true;
			}
		}
		else {
			newUuid = ParamUtil.getString(actionRequest, "newUuid");
		}

		try {
			if (configurationEntryOptional.isPresent()) {
				AdaptiveMediaImageConfigurationEntry configurationEntry =
					configurationEntryOptional.get();

				if (!_isConfigurationEntryEditable(
						themeDisplay.getCompanyId(),
						configurationEntryOptional.get())) {

					newUuid = configurationEntry.getUUID();

					properties = configurationEntry.getProperties();

					autoModifiedUuid = false;
				}

				configurationEntry =
					_adaptiveMediaImageConfigurationHelper.
						updateAdaptiveMediaImageConfigurationEntry(
							themeDisplay.getCompanyId(), uuid, name,
							description, newUuid, properties);

				if (autoModifiedUuid) {
					SessionMessages.add(
						actionRequest, "configurationEntryUpdatedAndIDRenamed",
						configurationEntry);
				}
				else {
					SessionMessages.add(
						actionRequest, "configurationEntryUpdated",
						configurationEntry);
				}
			}
			else {
				AdaptiveMediaImageConfigurationEntry configurationEntry =
					_adaptiveMediaImageConfigurationHelper.
						addAdaptiveMediaImageConfigurationEntry(
							themeDisplay.getCompanyId(), name, description,
							newUuid, properties);

				boolean addHighResolution = ParamUtil.getBoolean(
					actionRequest, "addHighResolution");

				AdaptiveMediaImageConfigurationEntry
					highResolutionConfigurationEntry = null;

				if (addHighResolution) {
					highResolutionConfigurationEntry =
						_addHighResolutionConfigurationEntry(
							themeDisplay.getCompanyId(), configurationEntry);

					SessionMessages.add(
						actionRequest, "highResolutionConfigurationEntryAdded",
						new AdaptiveMediaImageConfigurationEntry[] {
							configurationEntry, highResolutionConfigurationEntry
						});
				}
				else {
					if (autoModifiedUuid) {
						SessionMessages.add(
							actionRequest,
							"configurationEntryAddedAndIDRenamed",
							configurationEntry);
					}
					else {
						SessionMessages.add(
							actionRequest, "configurationEntryAdded",
							configurationEntry);
					}
				}
			}
		}
		catch (AdaptiveMediaImageConfigurationException amice) {
			SessionErrors.add(actionRequest, amice.getClass());
		}
	}

	private AdaptiveMediaImageConfigurationEntry
			_addHighResolutionConfigurationEntry(
				long companyId,
				AdaptiveMediaImageConfigurationEntry configurationEntry)
		throws AdaptiveMediaImageConfigurationException, IOException {

		Map<String, String> properties = configurationEntry.getProperties();

		int doubleMaxHeight =
			GetterUtil.getInteger(properties.get("max-height")) * 2;
		int doubleMaxWidth =
			GetterUtil.getInteger(properties.get("max-width")) * 2;

		properties.put("max-height", String.valueOf(doubleMaxHeight));
		properties.put("max-width", String.valueOf(doubleMaxWidth));

		String name = configurationEntry.getName();
		String description = configurationEntry.getDescription();
		String uuid = configurationEntry.getUUID();

		return _adaptiveMediaImageConfigurationHelper.
			addAdaptiveMediaImageConfigurationEntry(
				companyId, name.concat("-2x"), "2x " + description,
				uuid.concat("-2x"), properties);
	}

	private String _getAutomaticUuid(
		long companyId, String normalizedName, String oldUuid) {

		String curUuid = normalizedName;

		for (int i = 1;; i++) {
			if (curUuid.equals(oldUuid)) {
				break;
			}

			Optional<AdaptiveMediaImageConfigurationEntry>
				adaptiveMediaImageConfigurationEntryOptional =
					_adaptiveMediaImageConfigurationHelper.
						getAdaptiveMediaImageConfigurationEntry(
							companyId, curUuid);

			if (!adaptiveMediaImageConfigurationEntryOptional.isPresent()) {
				break;
			}

			String suffix = StringPool.DASH + i;

			curUuid = FriendlyURLNormalizerUtil.normalize(
				normalizedName + suffix);
		}

		return curUuid;
	}

	private boolean _isConfigurationEntryEditable(
		long companyId,
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		int entriesCount =
			_adaptiveMediaImageEntryLocalService.
				getAdaptiveMediaImageEntriesCount(
					companyId, configurationEntry.getUUID());

		if (entriesCount == 0) {
			return true;
		}

		return false;
	}

	@Reference
	private AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper;

	@Reference
	private AdaptiveMediaImageEntryLocalService
		_adaptiveMediaImageEntryLocalService;

}