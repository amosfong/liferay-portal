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

package com.liferay.portal.upgrade.v7_4_x;

import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Amos Fong
 */
public class UpgradeExternalReferenceCode extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeExternalReferenceCodes("Address", "addressId");
		upgradeExternalReferenceCodes("AssetCategory", "categoryId");
		upgradeExternalReferenceCodes("AssetVocabulary", "vocabularyId");
		upgradeExternalReferenceCodes("DLFileEntry", "fileEntryId");
		upgradeExternalReferenceCodes("Organization_", "organizationId");
		upgradeExternalReferenceCodes("ERCCompanyEntry", "ercCompanyEntryId");
		upgradeExternalReferenceCodes("ERCGroupEntry", "ercGroupEntryId");
		upgradeExternalReferenceCodes("RemoteAppEntry", "remoteAppEntryId");
		upgradeExternalReferenceCodes("ObjectEntry", "objectEntryId");
		upgradeExternalReferenceCodes("MBMessage", "messageId");
		upgradeExternalReferenceCodes("KBArticle", "kbArticleId");
		upgradeExternalReferenceCodes("KBFolder", "kbFolderId");
		upgradeExternalReferenceCodes(
			"CommerceTermEntry", "commerceTermEntryId");
		upgradeExternalReferenceCodes("CommerceOrder", "commerceOrderId");
		upgradeExternalReferenceCodes(
			"CommerceOrderItem", "commerceOrderItemId");
		upgradeExternalReferenceCodes(
			"CommerceOrderNote", "commerceOrderNoteId");
		upgradeExternalReferenceCodes(
			"CommerceOrderType", "commerceOrderTypeId");
		upgradeExternalReferenceCodes(
			"CommerceOrderTypeRel", "commerceOrderTypeRelId");
		upgradeExternalReferenceCodes(
			"CPAttachmentFileEntry", "CPAttachmentFileEntryId");
		upgradeExternalReferenceCodes("CPInstance", "CPInstanceId");
		upgradeExternalReferenceCodes("CPOption", "CPOptionId");
		upgradeExternalReferenceCodes("CPOptionValue", "CPOptionValueId");
		upgradeExternalReferenceCodes("CPTaxCategory", "CPTaxCategoryId");
		upgradeExternalReferenceCodes("CProduct", "CProductId");
		upgradeExternalReferenceCodes("CommerceCatalog", "commerceCatalogId");
		upgradeExternalReferenceCodes("CommerceChannel", "commerceChannelId");
		upgradeExternalReferenceCodes(
			"CommercePriceModifier", "commercePriceModifierId");
		upgradeExternalReferenceCodes(
			"CommercePricingClass", "commercePricingClassId");
		upgradeExternalReferenceCodes(
			"CommercePriceEntry", "commercePriceEntryId");
		upgradeExternalReferenceCodes(
			"CommercePriceList", "commercePriceListId");
		upgradeExternalReferenceCodes(
			"CommerceTierPriceEntry", "commerceTierPriceEntryId");
		upgradeExternalReferenceCodes("COREntry", "COREntryId");
		upgradeExternalReferenceCodes("CIWarehouse", "CIWarehouseId");
		upgradeExternalReferenceCodes("CIWarehouseItem", "CIWarehouseItemId");
		upgradeExternalReferenceCodes("CommerceDiscount", "commerceDiscountId");
		upgradeExternalReferenceCodes("BlogsEntry", "entryId");
		upgradeExternalReferenceCodes("AccountEntry", "accountEntryId");
		upgradeExternalReferenceCodes("AccountGroup", "accountGroupId");
		upgradeExternalReferenceCodes("User_", "userId");
		upgradeExternalReferenceCodes("UserGroup", "userGroupId");
		upgradeExternalReferenceCodes("WikiNode", "nodeId");
		upgradeExternalReferenceCodes("WikiPage", "pageId");
	}

	protected void upgradeExternalReferenceCodes(
			String tableName, String primKeyColumnName)
		throws Exception {

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select " + primKeyColumnName + " from " + tableName +
					" where externalReferenceCode is null or " +
						"externalReferenceCode = ''");
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						"update " + tableName +
							" set externalReferenceCode = ? where " +
								primKeyColumnName + " = ?"))) {

			while (resultSet.next()) {
				long primKey = resultSet.getLong(1);

				preparedStatement2.setString(1, String.valueOf(primKey));
				preparedStatement2.setLong(2, primKey);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();
		}
	}

}