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

package com.liferay.adaptive.media.image.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AdaptiveMediaImageEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImageEntry
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageEntryCacheModel implements CacheModel<AdaptiveMediaImageEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AdaptiveMediaImageEntryCacheModel)) {
			return false;
		}

		AdaptiveMediaImageEntryCacheModel adaptiveMediaImageEntryCacheModel = (AdaptiveMediaImageEntryCacheModel)obj;

		if (adaptiveMediaImageEntryId == adaptiveMediaImageEntryCacheModel.adaptiveMediaImageEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, adaptiveMediaImageEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", adaptiveMediaImageEntryId=");
		sb.append(adaptiveMediaImageEntryId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", configurationUuid=");
		sb.append(configurationUuid);
		sb.append(", fileVersionId=");
		sb.append(fileVersionId);
		sb.append(", mimeType=");
		sb.append(mimeType);
		sb.append(", height=");
		sb.append(height);
		sb.append(", width=");
		sb.append(width);
		sb.append(", size=");
		sb.append(size);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AdaptiveMediaImageEntry toEntityModel() {
		AdaptiveMediaImageEntryImpl adaptiveMediaImageEntryImpl = new AdaptiveMediaImageEntryImpl();

		if (uuid == null) {
			adaptiveMediaImageEntryImpl.setUuid(StringPool.BLANK);
		}
		else {
			adaptiveMediaImageEntryImpl.setUuid(uuid);
		}

		adaptiveMediaImageEntryImpl.setAdaptiveMediaImageEntryId(adaptiveMediaImageEntryId);
		adaptiveMediaImageEntryImpl.setGroupId(groupId);
		adaptiveMediaImageEntryImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			adaptiveMediaImageEntryImpl.setCreateDate(null);
		}
		else {
			adaptiveMediaImageEntryImpl.setCreateDate(new Date(createDate));
		}

		if (configurationUuid == null) {
			adaptiveMediaImageEntryImpl.setConfigurationUuid(StringPool.BLANK);
		}
		else {
			adaptiveMediaImageEntryImpl.setConfigurationUuid(configurationUuid);
		}

		adaptiveMediaImageEntryImpl.setFileVersionId(fileVersionId);

		if (mimeType == null) {
			adaptiveMediaImageEntryImpl.setMimeType(StringPool.BLANK);
		}
		else {
			adaptiveMediaImageEntryImpl.setMimeType(mimeType);
		}

		adaptiveMediaImageEntryImpl.setHeight(height);
		adaptiveMediaImageEntryImpl.setWidth(width);
		adaptiveMediaImageEntryImpl.setSize(size);

		adaptiveMediaImageEntryImpl.resetOriginalValues();

		return adaptiveMediaImageEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		adaptiveMediaImageEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		configurationUuid = objectInput.readUTF();

		fileVersionId = objectInput.readLong();
		mimeType = objectInput.readUTF();

		height = objectInput.readInt();

		width = objectInput.readInt();

		size = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(adaptiveMediaImageEntryId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);

		if (configurationUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(configurationUuid);
		}

		objectOutput.writeLong(fileVersionId);

		if (mimeType == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(mimeType);
		}

		objectOutput.writeInt(height);

		objectOutput.writeInt(width);

		objectOutput.writeLong(size);
	}

	public String uuid;
	public long adaptiveMediaImageEntryId;
	public long groupId;
	public long companyId;
	public long createDate;
	public String configurationUuid;
	public long fileVersionId;
	public String mimeType;
	public int height;
	public int width;
	public long size;
}