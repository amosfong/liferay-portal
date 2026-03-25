/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.customer.constants.EntitlementConstants;
import com.liferay.customer.constants.ProductConstants;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ContactRole;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Entitlement;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Amos Fong
 */
@Component
public class KoroneikiAccountService extends BaseService {

	public JSONObject updateKoroneikiAccount(Jwt jwt, Account account)
		throws Exception {

		ProductPurchase[] productPurchases = account.getProductPurchases();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"accountKey", account.getKey()
		).put(
			"acWorkspaceGroupId",
			_getACWorkspaceGroupId(account.getExternalLinks())
		).put(
			"allowSelfProvisioning",
			_isAllowSelfProvisioning(account.getProperties())
		).put(
			"code", account.getCode()
		).put(
			"dataRegion", account.getDataRegionAsString()
		).put(
			"dxpVersion", _getLiferayVersion(account.getProperties())
		).put(
			"externalReferenceCode", account.getKey()
		).put(
			"liferayContactEmailAddress",
			_getLiferayContactEmailAddress(account.getWorkerContacts())
		).put(
			"liferayContactName",
			_getLiferayContactName(account.getWorkerContacts())
		).put(
			"liferayContactRole",
			_getLiferayContactRole(account.getWorkerContacts())
		).put(
			"maxRequestors", _getMaxRequestors(productPurchases)
		).put(
			"name", account.getName()
		).put(
			"partner", _isPartner(account.getEntitlements())
		).put(
			"partnershipCurrent", _getPartnershipCurrent(productPurchases)
		).put(
			"partnershipCurrentEndDate",
			_getPartnershipCurrentEndDate(productPurchases)
		).put(
			"partnershipCurrentStartDate",
			_getPartnershipCurrentStartDate(productPurchases)
		).put(
			"partnershipExpired", _getPartnershipExpired(productPurchases)
		).put(
			"partnershipExpiredEndDate",
			_getPartnershipExpiredEndDate(productPurchases)
		).put(
			"partnershipExpiredStartDate",
			_getPartnershipExpiredStartDate(productPurchases)
		).put(
			"partnershipFuture", _getPartnershipFuture(productPurchases)
		).put(
			"partnershipFutureEndDate",
			_getPartnershipFutureEndDate(productPurchases)
		).put(
			"partnershipFutureStartDate",
			_getPartnershipFutureStartDate(productPurchases)
		).put(
			"region", account.getRegionAsString()
		).put(
			"salesforceAccountKey",
			_getSalesforceAccountKey(account.getExternalLinks())
		).put(
			"salesforceProjectKey",
			_getSalesforceProjectKey(account.getExternalLinks())
		).put(
			"slaCurrent", _getSLACurrent(productPurchases)
		).put(
			"slaCurrentEndDate", _getSLACurrentEndDate(productPurchases)
		).put(
			"slaCurrentStartDate", _getSLACurrentStartDate(productPurchases)
		).put(
			"slaExpired", _getSLAExpired(productPurchases)
		).put(
			"slaExpiredEndDate", _getSLAExpiredEndDate(productPurchases)
		).put(
			"slaExpiredStartDate", _getSLAExpiredStartDate(productPurchases)
		).put(
			"slaFuture", _getSLAFuture(productPurchases)
		).put(
			"slaFutureEndDate", _getSLAFutureEndDate(productPurchases)
		).put(
			"slaFutureStartDate", _getSLAFutureStartDate(productPurchases)
		);

		return new JSONObject(
			put(
				"Bearer " + jwt.getTokenValue(), jsonObject.toString(),
				UriComponentsBuilder.fromPath(
					"/o/c/koroneikiaccounts"
				).build(
				).toUri()));
	}

	private boolean _contains(String[] array, String value) {
		for (String s : array) {
			if (s.equals(value)) {
				return true;
			}
		}

		return false;
	}

	private String _getACWorkspaceGroupId(ExternalLink[] externalLinks) {
		return _getExternalLinkEntityId(
			externalLinks, ExternalLinkDomain.ANALYTICS_CLOUD,
			ExternalLinkEntityName.ANALYTICS_CLOUD_GROUP);
	}

	private String _getEarliestStartDate(
			ProductPurchase[] productPurchases, String productName)
		throws Exception {

		Date earliestStartDate = null;

		for (ProductPurchase productPurchase : productPurchases) {
			Product product = productPurchase.getProduct();

			if (!productName.equals(product.getName()) ||
				!_isActive(productPurchase.getStatusAsString())) {

				continue;
			}

			Date startDate = productPurchase.getStartDate();

			if ((earliestStartDate == null) ||
				earliestStartDate.before(startDate)) {

				earliestStartDate = startDate;
			}
		}

		return _simpleDateFormat.format(earliestStartDate);
	}

	private String _getExternalLinkEntityId(
		ExternalLink[] externalLinks, String domain, String entityName) {

		for (ExternalLink externalLink : externalLinks) {
			if (domain.equals(externalLink.getDomain()) &&
				entityName.equals(externalLink.getEntityName())) {

				return externalLink.getEntityId();
			}
		}

		return StringPool.BLANK;
	}

	private String _getLatestEndDate(
			ProductPurchase[] productPurchases, String productName)
		throws Exception {

		Date latestEndDate = null;

		for (ProductPurchase productPurchase : productPurchases) {
			Product product = productPurchase.getProduct();

			if (!productName.equals(product.getName()) ||
				!_isActive(productPurchase.getStatusAsString())) {

				continue;
			}

			Date originalEndDate = productPurchase.getOriginalEndDate();

			if ((latestEndDate == null) ||
				latestEndDate.before(originalEndDate)) {

				latestEndDate = originalEndDate;
			}
		}

		return _simpleDateFormat.format(latestEndDate);
	}

	private String _getLiferayContactEmailAddress(Contact[] workerContacts) {
		String primaryContactEmailAddress = null;
		String secondaryContactEmailAddress = null;

		if (ArrayUtil.isNotEmpty(workerContacts)) {
			for (Contact contact : workerContacts) {
				ContactRole[] contactRoles = contact.getContactRoles();

				for (ContactRole contactRole : contactRoles) {
					String contactRoleName = contactRole.getName();

					if (contactRoleName.equals(_CONTACT_ROLE_NAME_PRIMARY)) {
						primaryContactEmailAddress = contact.getEmailAddress();

						break;
					}
					else if (contactRoleName.equals(
								_CONTACT_ROLE_NAME_SECONDARY)) {

						secondaryContactEmailAddress =
							contact.getEmailAddress();

						break;
					}
				}
			}
		}

		if ((primaryContactEmailAddress != null) &&
			!primaryContactEmailAddress.isEmpty()) {

			return primaryContactEmailAddress;
		}

		if ((secondaryContactEmailAddress != null) &&
			!secondaryContactEmailAddress.isEmpty()) {

			return secondaryContactEmailAddress;
		}

		return "customer-service@liferay.com";
	}

	private String _getLiferayContactName(Contact[] workerContacts) {
		String primaryContactName = null;
		String secondaryContactName = null;

		if (ArrayUtil.isNotEmpty(workerContacts)) {
			for (Contact contact : workerContacts) {
				ContactRole[] contactRoles = contact.getContactRoles();

				for (ContactRole contactRole : contactRoles) {
					String contactRoleName = contactRole.getName();

					if (contactRoleName.equals(_CONTACT_ROLE_NAME_PRIMARY)) {
						primaryContactName =
							contact.getFirstName() + " " +
								contact.getLastName();

						break;
					}
					else if (contactRoleName.equals(
								_CONTACT_ROLE_NAME_SECONDARY)) {

						secondaryContactName =
							contact.getFirstName() + " " +
								contact.getLastName();

						break;
					}
				}
			}
		}

		if ((primaryContactName != null) && !primaryContactName.isEmpty()) {
			return primaryContactName;
		}

		if ((secondaryContactName != null) && !secondaryContactName.isEmpty()) {
			return secondaryContactName;
		}

		return "Liferay Support";
	}

	private String _getLiferayContactRole(Contact[] workerContacts) {
		String primaryContactRole = null;
		String secondaryContactRole = null;

		if (ArrayUtil.isNotEmpty(workerContacts)) {
			for (Contact contact : workerContacts) {
				ContactRole[] contactRoles = contact.getContactRoles();

				boolean primaryContact = false;
				boolean secondaryContact = false;
				Set<String> contactRoleNames = new TreeSet<>();

				for (ContactRole contactRole : contactRoles) {
					String contactRoleName = contactRole.getName();

					if (contactRoleName.equals(_CONTACT_ROLE_NAME_PRIMARY)) {
						primaryContact = true;
					}
					else if (contactRoleName.equals(
								_CONTACT_ROLE_NAME_SECONDARY)) {

						secondaryContact = true;
					}
					else {
						contactRoleNames.add(contactRoleName);
					}
				}

				if (primaryContact) {
					primaryContactRole = String.join(", ", contactRoleNames);
				}
				else if (secondaryContact) {
					secondaryContactRole = String.join(", ", contactRoleNames);
				}
			}
		}

		if ((primaryContactRole != null) && !primaryContactRole.isEmpty()) {
			return primaryContactRole;
		}

		if ((secondaryContactRole != null) && !secondaryContactRole.isEmpty()) {
			return secondaryContactRole;
		}

		return StringPool.BLANK;
	}

	private String _getLiferayVersion(Map<String, String> properties)
		throws Exception {

		if (properties.containsKey("liferayVersion")) {
			String liferayVersion = properties.get("liferayVersion");

			if (liferayVersion.startsWith("DXP ")) {
				return liferayVersion.substring(4);
			}
		}

		return StringPool.BLANK;
	}

	private int _getMaxRequestors(ProductPurchase[] productPurchases)
		throws Exception {

		String name = _getSLACurrent(productPurchases);

		if (name.isEmpty()) {
			return 0;
		}

		boolean analyticsCloud = false;
		boolean managedServices = false;
		int supportSeatAddons = 0;
		int productionInstances = 0;
		int maxSupportSeatCount = 0;

		for (ProductPurchase productPurchase : productPurchases) {
			if (!_isActive(
					productPurchase.getStartDate(),
					productPurchase.getEndDate(),
					productPurchase.getStatusAsString())) {

				continue;
			}

			int quantity = productPurchase.getQuantity();

			Product product = productPurchase.getProduct();

			String curName = product.getName();

			if (curName.equals(ProductConstants.NAME_ANALYTICS_CLOUD_BASIC) ||
				curName.equals(
					ProductConstants.NAME_ANALYTICS_CLOUD_BUSINESS) ||
				curName.equals(
					ProductConstants.NAME_ANALYTICS_CLOUD_ENTERPRISE)) {

				analyticsCloud = true;
			}

			if (curName.equals(
					ProductConstants.NAME_DESIGNATED_CONTACT_ADD_ON)) {

				supportSeatAddons += quantity;
			}
			else if (curName.equals(ProductConstants.NAME_MANAGED_SERVICES) ||
					 curName.equals(
						 ProductConstants.NAME_MANAGED_SERVICES_DXP) ||
					 curName.equals(
						 ProductConstants.NAME_MANAGED_SERVICES_LXC_SM)) {

				managedServices = true;
			}
			else if (curName.equals(
						ProductConstants.
							NAME_LIFERAY_PAAS_SUBSCRIPTION_HA_PRODUCTION)) {

				productionInstances += 2 * quantity;
			}
			else if (curName.equals(ProductConstants.NAME_DXP_EWSA) ||
					 curName.equals(ProductConstants.NAME_DXP_FLEX) ||
					 curName.equals(ProductConstants.NAME_DXP_OEM) ||
					 curName.equals(ProductConstants.NAME_DXP_PRODUCTION) ||
					 curName.equals(ProductConstants.NAME_PAAS_EXPERIENCE) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_PAAS_INSTANCE_PRODUCTION) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_PAAS_SUBSCRIPTION_STD_PRODUCTION) ||
					 curName.equals(ProductConstants.NAME_LIFERAY_PLATFORM) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_CSP_CUSTOM_USER_TIER) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_CSP_UP_TO_1K_USERS) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_CSP_UP_TO_5K_USERS) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_CSP_UP_TO_10K_USERS) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_CSP_UP_TO_20K_USERS) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_CSP_UP_TO_100_USERS) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_CSP_UP_TO_500_USERS) ||
					 curName.equals(ProductConstants.NAME_SAAS_EXPERIENCE) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_SUBSCRIPTION_ENGAGE_SITE) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_SUBSCRIPTION_SUPPORT_SITE) ||
					 curName.equals(
						 ProductConstants.
							 NAME_LIFERAY_SAAS_SUBSCRIPTION_TRANSACT_SITE) ||
					 curName.equals(ProductConstants.NAME_PORTAL_EWSA) ||
					 curName.equals(ProductConstants.NAME_PORTAL_OEM) ||
					 curName.equals(ProductConstants.NAME_PORTAL_PRODUCTION)) {

				productionInstances += quantity;
			}
			else if (curName.equals(
						ProductConstants.NAME_LIFERAY_SAAS_PRO_PLAN)) {

				maxSupportSeatCount = 2;
			}
			else if (curName.equals(
						ProductConstants.NAME_LIFERAY_SAAS_BUSINESS_PLAN)) {

				maxSupportSeatCount = 15;
			}
			else if (curName.equals(
						ProductConstants.NAME_LIFERAY_SAAS_ENTERPRISE_PLAN)) {

				maxSupportSeatCount = 18;
			}
		}

		if (managedServices) {
			return 10 + supportSeatAddons;
		}

		maxSupportSeatCount += supportSeatAddons;

		if (productionInstances <= 0) {
			if (analyticsCloud) {
				return -1;
			}

			return maxSupportSeatCount;
		}

		if (name.equals(ProductConstants.NAME_GOLD) ||
			name.equals(ProductConstants.NAME_STANDARD_8_5_SUPPORT)) {

			if (productionInstances <= 4) {
				maxSupportSeatCount += 2;
			}
			else if (productionInstances <= 8) {
				maxSupportSeatCount += 4;
			}
			else if (productionInstances <= 12) {
				maxSupportSeatCount += 6;
			}
			else if (productionInstances <= 16) {
				maxSupportSeatCount += 8;
			}
			else if (productionInstances <= 20) {
				maxSupportSeatCount += 10;
			}
			else {
				maxSupportSeatCount += 12;
			}
		}
		else if (name.equals(ProductConstants.NAME_GLOBAL_24_7_SUPPORT) ||
				 name.equals(ProductConstants.NAME_PLATINUM) ||
				 name.equals(ProductConstants.NAME_PREMIER_24_7_SUPPORT) ||
				 name.equals(ProductConstants.NAME_STRATEGIC_24_7_SUPPORT)) {

			if (productionInstances <= 4) {
				maxSupportSeatCount += 3;
			}
			else if (productionInstances <= 8) {
				maxSupportSeatCount += 6;
			}
			else if (productionInstances <= 12) {
				maxSupportSeatCount += 9;
			}
			else if (productionInstances <= 16) {
				maxSupportSeatCount += 12;
			}
			else if (productionInstances <= 20) {
				maxSupportSeatCount += 15;
			}
			else {
				maxSupportSeatCount += 18;
			}
		}
		else if (name.equals(ProductConstants.NAME_PREMIUM)) {
			if (productionInstances <= 3) {
				maxSupportSeatCount += 3;
			}
			else if (productionInstances <= 6) {
				maxSupportSeatCount += 6;
			}
			else if (productionInstances <= 9) {
				maxSupportSeatCount += 9;
			}
			else if (productionInstances <= 12) {
				maxSupportSeatCount += 12;
			}
			else if (productionInstances <= 15) {
				maxSupportSeatCount += 15;
			}
			else {
				maxSupportSeatCount += 18;
			}
		}

		return maxSupportSeatCount;
	}

	private String _getPartnershipCurrent(ProductPurchase[] productPurchases)
		throws Exception {

		for (ProductPurchase productPurchase : productPurchases) {
			if (!_isPartnershipProduct(productPurchase) ||
				!_isActive(
					productPurchase.getStartDate(),
					productPurchase.getEndDate(),
					productPurchase.getStatusAsString())) {

				continue;
			}

			Product product = productPurchase.getProduct();

			return product.getName();
		}

		return StringPool.BLANK;
	}

	private String _getPartnershipCurrentEndDate(
			ProductPurchase[] productPurchases)
		throws Exception {

		String partnershipCurrent = _getPartnershipCurrent(productPurchases);

		return _getLatestEndDate(productPurchases, partnershipCurrent);
	}

	private String _getPartnershipCurrentStartDate(
			ProductPurchase[] productPurchases)
		throws Exception {

		String partnershipCurrent = _getPartnershipCurrent(productPurchases);

		return _getEarliestStartDate(productPurchases, partnershipCurrent);
	}

	private String _getPartnershipExpired(ProductPurchase[] productPurchases)
		throws Exception {

		String partnershipCurrent = _getPartnershipCurrent(productPurchases);

		if (!partnershipCurrent.isEmpty()) {
			return StringPool.BLANK;
		}

		for (ProductPurchase productPurchase : productPurchases) {
			if (!_isPartnershipProduct(productPurchase) ||
				!_isActive(productPurchase.getStatusAsString()) ||
				_isActiveFuture(
					productPurchase.getStartDate(),
					productPurchase.getStatusAsString())) {

				continue;
			}

			Product product = productPurchase.getProduct();

			return product.getName();
		}

		return StringPool.BLANK;
	}

	private String _getPartnershipExpiredEndDate(
			ProductPurchase[] productPurchases)
		throws Exception {

		String partnershipExpired = _getPartnershipExpired(productPurchases);

		return _getLatestEndDate(productPurchases, partnershipExpired);
	}

	private String _getPartnershipExpiredStartDate(
			ProductPurchase[] productPurchases)
		throws Exception {

		String partnershipExpired = _getPartnershipExpired(productPurchases);

		return _getEarliestStartDate(productPurchases, partnershipExpired);
	}

	private String _getPartnershipFuture(ProductPurchase[] productPurchases)
		throws Exception {

		String partnershipCurrent = _getPartnershipCurrent(productPurchases);

		for (ProductPurchase productPurchase : productPurchases) {
			if (!_isPartnershipProduct(productPurchase)) {
				continue;
			}

			Product product = productPurchase.getProduct();

			if (partnershipCurrent.equals(product.getName()) ||
				!_isActiveFuture(
					productPurchase.getStartDate(),
					productPurchase.getStatusAsString())) {

				continue;
			}

			return product.getName();
		}

		return StringPool.BLANK;
	}

	private String _getPartnershipFutureEndDate(
			ProductPurchase[] productPurchases)
		throws Exception {

		String partnershipFuture = _getPartnershipFuture(productPurchases);

		return _getLatestEndDate(productPurchases, partnershipFuture);
	}

	private String _getPartnershipFutureStartDate(
			ProductPurchase[] productPurchases)
		throws Exception {

		String partnershipFuture = _getPartnershipFuture(productPurchases);

		return _getEarliestStartDate(productPurchases, partnershipFuture);
	}

	private String _getSalesforceAccountKey(ExternalLink[] externalLinks) {
		return _getExternalLinkEntityId(
			externalLinks, ExternalLinkDomain.SALESFORCE,
			ExternalLinkEntityName.SALESFORCE_ACCOUNT);
	}

	private String _getSalesforceProjectKey(ExternalLink[] externalLinks) {
		return _getExternalLinkEntityId(
			externalLinks, ExternalLinkDomain.SALESFORCE,
			ExternalLinkEntityName.SALESFORCE_PROJECT);
	}

	private String _getSLACurrent(ProductPurchase[] productPurchases)
		throws Exception {

		ProductPurchase slaProductPurchase = null;

		for (ProductPurchase productPurchase : productPurchases) {
			if (!_isSLAProduct(productPurchase) ||
				!_isActive(
					productPurchase.getStartDate(),
					productPurchase.getEndDate(),
					productPurchase.getStatusAsString())) {

				continue;
			}

			if (_isHigherSLA(slaProductPurchase, productPurchase)) {
				slaProductPurchase = productPurchase;
			}
		}

		if (slaProductPurchase != null) {
			Product product = slaProductPurchase.getProduct();

			return product.getName();
		}

		return StringPool.BLANK;
	}

	private String _getSLACurrentEndDate(ProductPurchase[] productPurchases)
		throws Exception {

		String slaCurrent = _getSLACurrent(productPurchases);

		return _getLatestEndDate(productPurchases, slaCurrent);
	}

	private String _getSLACurrentStartDate(ProductPurchase[] productPurchases)
		throws Exception {

		String slaCurrent = _getSLACurrent(productPurchases);

		return _getEarliestStartDate(productPurchases, slaCurrent);
	}

	private String _getSLAExpired(ProductPurchase[] productPurchases)
		throws Exception {

		String slaCurrent = _getSLACurrent(productPurchases);

		if (!slaCurrent.isEmpty()) {
			return StringPool.BLANK;
		}

		ProductPurchase slaProductPurchase = null;

		for (ProductPurchase productPurchase : productPurchases) {
			if (!_isSLAProduct(productPurchase) ||
				!_isActive(productPurchase.getStatusAsString()) ||
				_isActiveFuture(
					productPurchase.getStartDate(),
					productPurchase.getStatusAsString())) {

				continue;
			}

			if (_isHigherSLA(slaProductPurchase, productPurchase)) {
				slaProductPurchase = productPurchase;
			}
		}

		if (slaProductPurchase != null) {
			Product product = slaProductPurchase.getProduct();

			return product.getName();
		}

		return StringPool.BLANK;
	}

	private String _getSLAExpiredEndDate(ProductPurchase[] productPurchases)
		throws Exception {

		String slaExpired = _getSLAExpired(productPurchases);

		return _getLatestEndDate(productPurchases, slaExpired);
	}

	private String _getSLAExpiredStartDate(ProductPurchase[] productPurchases)
		throws Exception {

		String slaExpired = _getSLAExpired(productPurchases);

		return _getEarliestStartDate(productPurchases, slaExpired);
	}

	private String _getSLAFuture(ProductPurchase[] productPurchases)
		throws Exception {

		String slaCurrent = _getSLACurrent(productPurchases);

		ProductPurchase slaProductPurchase = null;

		for (ProductPurchase productPurchase : productPurchases) {
			if (!_isSLAProduct(productPurchase)) {
				continue;
			}

			Product product = productPurchase.getProduct();

			if (slaCurrent.equals(product.getName()) ||
				!_isActiveFuture(
					productPurchase.getStartDate(),
					productPurchase.getStatusAsString())) {

				continue;
			}

			if (_isHigherSLA(slaProductPurchase, productPurchase)) {
				slaProductPurchase = productPurchase;
			}
		}

		if (slaProductPurchase != null) {
			Product product = slaProductPurchase.getProduct();

			return product.getName();
		}

		return StringPool.BLANK;
	}

	private String _getSLAFutureEndDate(ProductPurchase[] productPurchases)
		throws Exception {

		String slaFuture = _getSLAFuture(productPurchases);

		return _getLatestEndDate(productPurchases, slaFuture);
	}

	private String _getSLAFutureStartDate(ProductPurchase[] productPurchases)
		throws Exception {

		String slaFuture = _getSLAFuture(productPurchases);

		return _getEarliestStartDate(productPurchases, slaFuture);
	}

	private int _getSLARank(Product product) throws Exception {
		String name = product.getName();

		if (name.equals(ProductConstants.NAME_GLOBAL_24_7_SUPPORT)) {
			return 6;
		}
		else if (name.equals(ProductConstants.NAME_GOLD)) {
			return 3;
		}
		else if (name.equals(ProductConstants.NAME_LIMITED)) {
			return 1;
		}
		else if (name.equals(ProductConstants.NAME_PLATINUM)) {
			return 5;
		}
		else if (name.equals(ProductConstants.NAME_PREMIER_24_7_SUPPORT)) {
			return 7;
		}
		else if (name.equals(ProductConstants.NAME_PREMIUM)) {
			return 9;
		}
		else if (name.equals(ProductConstants.NAME_SILVER)) {
			return 2;
		}
		else if (name.equals(ProductConstants.NAME_STANDARD_8_5_SUPPORT)) {
			return 4;
		}
		else if (name.equals(ProductConstants.NAME_STRATEGIC_24_7_SUPPORT)) {
			return 8;
		}

		return 0;
	}

	private boolean _isActive(Date startDate, Date endDate, String status)
		throws Exception {

		if (!_isActive(status)) {
			return false;
		}

		Date now = new Date();

		if ((startDate != null) && now.before(startDate)) {
			return false;
		}

		if ((endDate != null) && now.after(endDate)) {
			return false;
		}

		return true;
	}

	private boolean _isActive(String status) throws Exception {
		if ((status == null) ||
			status.equals(ProductConstants.PURCHASE_STATUS_CANCELLED)) {

			return false;
		}

		return true;
	}

	private boolean _isActiveFuture(Date startDate, String status)
		throws Exception {

		if (!_isActive(status)) {
			return false;
		}

		Date now = new Date();

		if ((startDate != null) && now.before(startDate)) {
			return true;
		}

		return false;
	}

	private boolean _isAllowSelfProvisioning(Map<String, String> properties) {
		if (properties.containsKey("allowSelfProvisioning")) {
			return GetterUtil.getBoolean(
				properties.get("allowSelfProvisioning"));
		}

		return true;
	}

	private boolean _isHigherSLA(
			ProductPurchase curProductPurchase, ProductPurchase productPurchase)
		throws Exception {

		if (curProductPurchase == null) {
			return true;
		}

		int curSLARank = _getSLARank(curProductPurchase.getProduct());
		int slaRank = _getSLARank(productPurchase.getProduct());

		if (slaRank > curSLARank) {
			return true;
		}

		if (slaRank < curSLARank) {
			return false;
		}

		if (curProductPurchase.getPerpetual() &&
			!productPurchase.getPerpetual()) {

			return true;
		}

		if (curProductPurchase.getPerpetual()) {
			return false;
		}

		Date endDate = curProductPurchase.getStartDate();

		return endDate.after(curProductPurchase.getEndDate());
	}

	private boolean _isPartner(Entitlement[] entitlements) {
		for (Entitlement entitlement : entitlements) {
			String name = entitlement.getName();

			if (name.equals(EntitlementConstants.PARTNER)) {
				return true;
			}
		}

		return false;
	}

	private boolean _isPartnershipProduct(ProductPurchase productPurchase) {
		Product product = productPurchase.getProduct();

		return _contains(ProductConstants.NAMES_PARTNERSHIP, product.getName());
	}

	private boolean _isSLAProduct(ProductPurchase productPurchase) {
		Product product = productPurchase.getProduct();

		return _contains(
			ProductConstants.NAMES_SUBSCRIPTION, product.getName());
	}

	private static final String _CONTACT_ROLE_NAME_PRIMARY = "Primary Contact";

	private static final String _CONTACT_ROLE_NAME_SECONDARY =
		"Secondary Contact";

	private final SimpleDateFormat _simpleDateFormat = new SimpleDateFormat(
		"yyyy-MM-dd'T'HH:mm:ss'Z'");

}