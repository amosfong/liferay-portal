/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.util;

import com.liferay.headless.commerce.admin.catalog.client.custom.field.CustomField;
import com.liferay.headless.commerce.admin.catalog.client.custom.field.CustomValue;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.SkuOption;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Keven Leone
 * @author Eduardo Diniz
 */
public class SkuUtil {

	public static String getSalesforceProductId(Sku sku) {
		if ((sku == null) || (sku.getCustomFields() == null)) {
			return null;
		}

		for (CustomField customField : sku.getCustomFields()) {
			if (Objects.equals(
					customField.getName(), "salesforce-product-id")) {

				CustomValue customValue = customField.getCustomValue();

				if (customValue != null) {
					Object data = customValue.getData();

					if (data != null) {
						return data.toString();
					}
				}

				break;
			}
		}

		return null;
	}

	public static String getSkuOptionValue(String key, SkuOption[] skuOptions) {
		if (skuOptions == null) {
			return null;
		}

		for (SkuOption skuOption : skuOptions) {
			String skuOptionKey = skuOption.getKey();

			if ((skuOptionKey == null) || !skuOptionKey.endsWith(key)) {
				continue;
			}

			return skuOption.getValue();
		}

		return null;
	}

	public static String getSkuOptionValue(String key, String options) {
		if (Validator.isNull(options)) {
			return null;
		}

		JSONArray optionsJSONArray = new JSONArray(options);

		for (int i = 0; i < optionsJSONArray.length(); i++) {
			JSONObject jsonObject = optionsJSONArray.getJSONObject(i);

			String skuOptionKey = jsonObject.optString("key");

			if (!skuOptionKey.endsWith(key)) {
				continue;
			}

			JSONArray jsonArray = jsonObject.getJSONArray("value");

			return jsonArray.getString(0);
		}

		return null;
	}

}