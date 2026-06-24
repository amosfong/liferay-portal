/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.model;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class Project {

	public Project(JSONObject jsonObject) {
		_accountEntryId = jsonObject.optLong(
			"r_accountEntryToProject_accountEntryId");
		_externalReferenceCode = jsonObject.optString("externalReferenceCode");
		_name = jsonObject.optString("name");
		_projectId = jsonObject.getLong("id");
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getName() {
		return _name;
	}

	public long getProjectId() {
		return _projectId;
	}

	private final long _accountEntryId;
	private final String _externalReferenceCode;
	private final String _name;
	private final long _projectId;

}