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

import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageEntryLocalService;
import com.liferay.adaptive.media.web.constants.AdaptiveMediaPortletKeys;
import com.liferay.adaptive.media.web.internal.constants.AdaptiveMediaWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
	service = MVCRenderCommand.class
)
public class EditImageConfigurationEntryMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String entryUuid = ParamUtil.getString(renderRequest, "entryUuid");

		Optional<AdaptiveMediaImageConfigurationEntry>
			configurationEntryOptional =
				_adaptiveMediaImageConfigurationHelper.
					getAdaptiveMediaImageConfigurationEntry(
						themeDisplay.getCompanyId(), entryUuid);

		boolean configurationEntryEditable = true;

		if (configurationEntryOptional.isPresent()) {
			AdaptiveMediaImageConfigurationEntry configurationEntry =
				configurationEntryOptional.get();

			int entriesCount =
				_adaptiveMediaImageEntryLocalService.
					getAdaptiveMediaImageEntriesCount(
						themeDisplay.getCompanyId(),
						configurationEntry.getUUID());

			if (entriesCount != 0) {
				configurationEntryEditable = false;
			}
		}

		renderRequest.setAttribute(
			AdaptiveMediaWebKeys.CONFIGURATION_ENTRY,
			configurationEntryOptional.orElse(null));

		renderRequest.setAttribute(
			AdaptiveMediaWebKeys.CONFIGURATION_ENTRY_EDITABLE,
			configurationEntryEditable);

		return "/adaptive_media/edit_image_configuration_entry.jsp";
	}

	@Reference
	private AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper;

	@Reference
	private AdaptiveMediaImageEntryLocalService
		_adaptiveMediaImageEntryLocalService;

}