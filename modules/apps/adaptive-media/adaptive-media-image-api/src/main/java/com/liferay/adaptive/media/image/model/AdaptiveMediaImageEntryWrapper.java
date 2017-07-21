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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AdaptiveMediaImageEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImageEntry
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageEntryWrapper implements AdaptiveMediaImageEntry,
	ModelWrapper<AdaptiveMediaImageEntry> {
	public AdaptiveMediaImageEntryWrapper(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		_adaptiveMediaImageEntry = adaptiveMediaImageEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return AdaptiveMediaImageEntry.class;
	}

	@Override
	public String getModelClassName() {
		return AdaptiveMediaImageEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("adaptiveMediaImageEntryId",
			getAdaptiveMediaImageEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("configurationUuid", getConfigurationUuid());
		attributes.put("fileVersionId", getFileVersionId());
		attributes.put("mimeType", getMimeType());
		attributes.put("height", getHeight());
		attributes.put("width", getWidth());
		attributes.put("size", getSize());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long adaptiveMediaImageEntryId = (Long)attributes.get(
				"adaptiveMediaImageEntryId");

		if (adaptiveMediaImageEntryId != null) {
			setAdaptiveMediaImageEntryId(adaptiveMediaImageEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String configurationUuid = (String)attributes.get("configurationUuid");

		if (configurationUuid != null) {
			setConfigurationUuid(configurationUuid);
		}

		Long fileVersionId = (Long)attributes.get("fileVersionId");

		if (fileVersionId != null) {
			setFileVersionId(fileVersionId);
		}

		String mimeType = (String)attributes.get("mimeType");

		if (mimeType != null) {
			setMimeType(mimeType);
		}

		Integer height = (Integer)attributes.get("height");

		if (height != null) {
			setHeight(height);
		}

		Integer width = (Integer)attributes.get("width");

		if (width != null) {
			setWidth(width);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}
	}

	@Override
	public AdaptiveMediaImageEntry toEscapedModel() {
		return new AdaptiveMediaImageEntryWrapper(_adaptiveMediaImageEntry.toEscapedModel());
	}

	@Override
	public AdaptiveMediaImageEntry toUnescapedModel() {
		return new AdaptiveMediaImageEntryWrapper(_adaptiveMediaImageEntry.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _adaptiveMediaImageEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _adaptiveMediaImageEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _adaptiveMediaImageEntry.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _adaptiveMediaImageEntry.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AdaptiveMediaImageEntry> toCacheModel() {
		return _adaptiveMediaImageEntry.toCacheModel();
	}

	@Override
	public int compareTo(AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		return _adaptiveMediaImageEntry.compareTo(adaptiveMediaImageEntry);
	}

	/**
	* Returns the height of this adaptive media image entry.
	*
	* @return the height of this adaptive media image entry
	*/
	@Override
	public int getHeight() {
		return _adaptiveMediaImageEntry.getHeight();
	}

	/**
	* Returns the width of this adaptive media image entry.
	*
	* @return the width of this adaptive media image entry
	*/
	@Override
	public int getWidth() {
		return _adaptiveMediaImageEntry.getWidth();
	}

	@Override
	public int hashCode() {
		return _adaptiveMediaImageEntry.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _adaptiveMediaImageEntry.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new AdaptiveMediaImageEntryWrapper((AdaptiveMediaImageEntry)_adaptiveMediaImageEntry.clone());
	}

	/**
	* Returns the configuration uuid of this adaptive media image entry.
	*
	* @return the configuration uuid of this adaptive media image entry
	*/
	@Override
	public java.lang.String getConfigurationUuid() {
		return _adaptiveMediaImageEntry.getConfigurationUuid();
	}

	/**
	* Returns the mime type of this adaptive media image entry.
	*
	* @return the mime type of this adaptive media image entry
	*/
	@Override
	public java.lang.String getMimeType() {
		return _adaptiveMediaImageEntry.getMimeType();
	}

	/**
	* Returns the uuid of this adaptive media image entry.
	*
	* @return the uuid of this adaptive media image entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _adaptiveMediaImageEntry.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _adaptiveMediaImageEntry.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _adaptiveMediaImageEntry.toXmlString();
	}

	/**
	* Returns the create date of this adaptive media image entry.
	*
	* @return the create date of this adaptive media image entry
	*/
	@Override
	public Date getCreateDate() {
		return _adaptiveMediaImageEntry.getCreateDate();
	}

	/**
	* Returns the adaptive media image entry ID of this adaptive media image entry.
	*
	* @return the adaptive media image entry ID of this adaptive media image entry
	*/
	@Override
	public long getAdaptiveMediaImageEntryId() {
		return _adaptiveMediaImageEntry.getAdaptiveMediaImageEntryId();
	}

	/**
	* Returns the company ID of this adaptive media image entry.
	*
	* @return the company ID of this adaptive media image entry
	*/
	@Override
	public long getCompanyId() {
		return _adaptiveMediaImageEntry.getCompanyId();
	}

	/**
	* Returns the file version ID of this adaptive media image entry.
	*
	* @return the file version ID of this adaptive media image entry
	*/
	@Override
	public long getFileVersionId() {
		return _adaptiveMediaImageEntry.getFileVersionId();
	}

	/**
	* Returns the group ID of this adaptive media image entry.
	*
	* @return the group ID of this adaptive media image entry
	*/
	@Override
	public long getGroupId() {
		return _adaptiveMediaImageEntry.getGroupId();
	}

	/**
	* Returns the primary key of this adaptive media image entry.
	*
	* @return the primary key of this adaptive media image entry
	*/
	@Override
	public long getPrimaryKey() {
		return _adaptiveMediaImageEntry.getPrimaryKey();
	}

	/**
	* Returns the size of this adaptive media image entry.
	*
	* @return the size of this adaptive media image entry
	*/
	@Override
	public long getSize() {
		return _adaptiveMediaImageEntry.getSize();
	}

	@Override
	public void persist() {
		_adaptiveMediaImageEntry.persist();
	}

	/**
	* Sets the adaptive media image entry ID of this adaptive media image entry.
	*
	* @param adaptiveMediaImageEntryId the adaptive media image entry ID of this adaptive media image entry
	*/
	@Override
	public void setAdaptiveMediaImageEntryId(long adaptiveMediaImageEntryId) {
		_adaptiveMediaImageEntry.setAdaptiveMediaImageEntryId(adaptiveMediaImageEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_adaptiveMediaImageEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this adaptive media image entry.
	*
	* @param companyId the company ID of this adaptive media image entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_adaptiveMediaImageEntry.setCompanyId(companyId);
	}

	/**
	* Sets the configuration uuid of this adaptive media image entry.
	*
	* @param configurationUuid the configuration uuid of this adaptive media image entry
	*/
	@Override
	public void setConfigurationUuid(java.lang.String configurationUuid) {
		_adaptiveMediaImageEntry.setConfigurationUuid(configurationUuid);
	}

	/**
	* Sets the create date of this adaptive media image entry.
	*
	* @param createDate the create date of this adaptive media image entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_adaptiveMediaImageEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_adaptiveMediaImageEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_adaptiveMediaImageEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_adaptiveMediaImageEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file version ID of this adaptive media image entry.
	*
	* @param fileVersionId the file version ID of this adaptive media image entry
	*/
	@Override
	public void setFileVersionId(long fileVersionId) {
		_adaptiveMediaImageEntry.setFileVersionId(fileVersionId);
	}

	/**
	* Sets the group ID of this adaptive media image entry.
	*
	* @param groupId the group ID of this adaptive media image entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_adaptiveMediaImageEntry.setGroupId(groupId);
	}

	/**
	* Sets the height of this adaptive media image entry.
	*
	* @param height the height of this adaptive media image entry
	*/
	@Override
	public void setHeight(int height) {
		_adaptiveMediaImageEntry.setHeight(height);
	}

	/**
	* Sets the mime type of this adaptive media image entry.
	*
	* @param mimeType the mime type of this adaptive media image entry
	*/
	@Override
	public void setMimeType(java.lang.String mimeType) {
		_adaptiveMediaImageEntry.setMimeType(mimeType);
	}

	@Override
	public void setNew(boolean n) {
		_adaptiveMediaImageEntry.setNew(n);
	}

	/**
	* Sets the primary key of this adaptive media image entry.
	*
	* @param primaryKey the primary key of this adaptive media image entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_adaptiveMediaImageEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_adaptiveMediaImageEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the size of this adaptive media image entry.
	*
	* @param size the size of this adaptive media image entry
	*/
	@Override
	public void setSize(long size) {
		_adaptiveMediaImageEntry.setSize(size);
	}

	/**
	* Sets the uuid of this adaptive media image entry.
	*
	* @param uuid the uuid of this adaptive media image entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_adaptiveMediaImageEntry.setUuid(uuid);
	}

	/**
	* Sets the width of this adaptive media image entry.
	*
	* @param width the width of this adaptive media image entry
	*/
	@Override
	public void setWidth(int width) {
		_adaptiveMediaImageEntry.setWidth(width);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AdaptiveMediaImageEntryWrapper)) {
			return false;
		}

		AdaptiveMediaImageEntryWrapper adaptiveMediaImageEntryWrapper = (AdaptiveMediaImageEntryWrapper)obj;

		if (Objects.equals(_adaptiveMediaImageEntry,
					adaptiveMediaImageEntryWrapper._adaptiveMediaImageEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public AdaptiveMediaImageEntry getWrappedModel() {
		return _adaptiveMediaImageEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _adaptiveMediaImageEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _adaptiveMediaImageEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_adaptiveMediaImageEntry.resetOriginalValues();
	}

	private final AdaptiveMediaImageEntry _adaptiveMediaImageEntry;
}