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

package com.liferay.adaptive.media.image.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageEntrySoap implements Serializable {
	public static AdaptiveMediaImageEntrySoap toSoapModel(
		AdaptiveMediaImageEntry model) {
		AdaptiveMediaImageEntrySoap soapModel = new AdaptiveMediaImageEntrySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setAdaptiveMediaImageEntryId(model.getAdaptiveMediaImageEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setConfigurationUuid(model.getConfigurationUuid());
		soapModel.setFileVersionId(model.getFileVersionId());
		soapModel.setMimeType(model.getMimeType());
		soapModel.setHeight(model.getHeight());
		soapModel.setWidth(model.getWidth());
		soapModel.setSize(model.getSize());

		return soapModel;
	}

	public static AdaptiveMediaImageEntrySoap[] toSoapModels(
		AdaptiveMediaImageEntry[] models) {
		AdaptiveMediaImageEntrySoap[] soapModels = new AdaptiveMediaImageEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AdaptiveMediaImageEntrySoap[][] toSoapModels(
		AdaptiveMediaImageEntry[][] models) {
		AdaptiveMediaImageEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AdaptiveMediaImageEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AdaptiveMediaImageEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AdaptiveMediaImageEntrySoap[] toSoapModels(
		List<AdaptiveMediaImageEntry> models) {
		List<AdaptiveMediaImageEntrySoap> soapModels = new ArrayList<AdaptiveMediaImageEntrySoap>(models.size());

		for (AdaptiveMediaImageEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AdaptiveMediaImageEntrySoap[soapModels.size()]);
	}

	public AdaptiveMediaImageEntrySoap() {
	}

	public long getPrimaryKey() {
		return _adaptiveMediaImageEntryId;
	}

	public void setPrimaryKey(long pk) {
		setAdaptiveMediaImageEntryId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getAdaptiveMediaImageEntryId() {
		return _adaptiveMediaImageEntryId;
	}

	public void setAdaptiveMediaImageEntryId(long adaptiveMediaImageEntryId) {
		_adaptiveMediaImageEntryId = adaptiveMediaImageEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public String getConfigurationUuid() {
		return _configurationUuid;
	}

	public void setConfigurationUuid(String configurationUuid) {
		_configurationUuid = configurationUuid;
	}

	public long getFileVersionId() {
		return _fileVersionId;
	}

	public void setFileVersionId(long fileVersionId) {
		_fileVersionId = fileVersionId;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public void setMimeType(String mimeType) {
		_mimeType = mimeType;
	}

	public int getHeight() {
		return _height;
	}

	public void setHeight(int height) {
		_height = height;
	}

	public int getWidth() {
		return _width;
	}

	public void setWidth(int width) {
		_width = width;
	}

	public long getSize() {
		return _size;
	}

	public void setSize(long size) {
		_size = size;
	}

	private String _uuid;
	private long _adaptiveMediaImageEntryId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private String _configurationUuid;
	private long _fileVersionId;
	private String _mimeType;
	private int _height;
	private int _width;
	private long _size;
}