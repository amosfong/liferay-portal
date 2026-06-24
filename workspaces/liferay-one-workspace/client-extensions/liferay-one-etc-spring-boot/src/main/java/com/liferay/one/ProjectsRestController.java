/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one;

import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkDomain;
import com.liferay.osb.koroneiki.phloem.rest.client.constants.ExternalLinkEntityName;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Account;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Contact;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ContactRole;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ExternalLink;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.AccountSerDes;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.ContactRoleSerDes;
import com.liferay.osb.koroneiki.phloem.rest.client.serdes.v1_0.ContactSerDes;
import com.liferay.osb.provisioning.distributed.messaging.internal.constants.KoroneikiConstants;
import com.liferay.osb.provisioning.identity.management.provider.ContactIdentityProvider;
import com.liferay.osb.provisioning.koroneiki.constants.ContactRoleConstants;
import com.liferay.osb.provisioning.koroneiki.web.service.ContactRoleWebService;
import com.liferay.osb.provisioning.koroneiki.web.service.ExternalLinkWebService;
import com.liferay.osb.provisioning.license.model.LicenseKey;
import com.liferay.osb.provisioning.license.service.LicenseKeyLocalService;
import com.liferay.osb.provisioning.subscription.model.SubscriptionEntry;
import com.liferay.osb.provisioning.subscription.service.SubscriptionEntryLocalService;
import com.liferay.osb.provisioning.util.CustomerPortalRelease;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.List;

import org.osgi.service.component.annotations.Reference;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felipe Veloso
 * @author Kyle Bischof
 */
@RequestMapping("/projects")
@RestController
public class ProjectsRestController extends OneBaseRestController {

	@PostMapping("/membership/create")
	public void postMembershipCreate(@RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		Account account = AccountSerDes.toDTO(jsonObject.getString("account"));
		Contact contact = ContactSerDes.toDTO(jsonObject.getString("contact"));

		ContactRole contactRole = ContactRoleSerDes.toDTO(
			jsonObject.getString("contactRole"));

		if (ArrayUtil.contains(
				ContactRoleConstants.NAMES_PARTNER_CONTACT_ROLES,
				contactRole.getName())) {

			_customerPortalRelease.sendPartnerContactUpdateEmail(
				account, contact, contactRole,
				KoroneikiConstants.ACTION_CONTACT_ROLE_ASSIGNED);
		}

		String contactRoleName = contactRole.getName();

		if (contactRoleName.equals(ContactRoleConstants.NAME_PAAS_USER)) {
			List<ExternalLink> externalLinks =
				_externalLinkWebService.getExternalLinks(
					account.getKey(), 1, 1000);

			for (ExternalLink externalLink : externalLinks) {
				String domain = externalLink.getDomain();
				String entityName = externalLink.getEntityName();

				if (domain.equals(ExternalLinkDomain.OKTA) &&
					entityName.equals(
						ExternalLinkEntityName.OKTA_APPLICATION)) {

					_contactIdentityProvider.assignUserToApplication(
						externalLink.getEntityId(), contact.getEmailAddress());

					break;
				}
			}
		}
	}

	@PostMapping("/membership/delete")
	public void postMembershipDelete(@RequestBody String json)
		throws Exception {

		JSONObject jsonObject = new JSONObject(json);

		Account account = AccountSerDes.toDTO(jsonObject.getString("account"));
		Contact contact = ContactSerDes.toDTO(jsonObject.getString("contact"));

		ContactRole contactRole = ContactRoleSerDes.toDTO(
			jsonObject.getString("contactRole"));

		if (ArrayUtil.contains(
				ContactRoleConstants.NAMES_PARTNER_CONTACT_ROLES,
				contactRole.getName())) {

			_customerPortalRelease.sendPartnerContactUpdateEmail(
				account, contact, contactRole,
				KoroneikiConstants.ACTION_CONTACT_ROLE_UNASSIGNED);
		}

		String contactRoleName = contactRole.getName();

		if (contactRoleName.equals(ContactRoleConstants.NAME_PAAS_USER)) {
			List<ExternalLink> externalLinks =
				_externalLinkWebService.getExternalLinks(
					account.getKey(), 1, 1000);

			for (ExternalLink externalLink : externalLinks) {
				String domain = externalLink.getDomain();
				String entityName = externalLink.getEntityName();

				if (domain.equals(ExternalLinkDomain.OKTA) &&
					entityName.equals(
						ExternalLinkEntityName.OKTA_APPLICATION)) {

					_contactIdentityProvider.unassignUserFromApplication(
						externalLink.getEntityId(), contact.getEmailAddress());

					break;
				}
			}
		}

		String accountKey = account.getKey();

		List<ContactRole> contactRoles =
			_contactRoleWebService.getAccountCustomerContactRoles(
				accountKey, contact.getEmailAddress(), 1, 1000);

		if (!contactRoles.isEmpty()) {
			return;
		}

		long classNameId = _classNameLocalService.getClassNameId(
			LicenseKey.class);

		List<SubscriptionEntry> subscriptionEntries =
			_subscriptionEntryLocalService.getSubscriptionEntries(
				classNameId, contact.getUuid());

		for (SubscriptionEntry subscriptionEntry : subscriptionEntries) {
			LicenseKey licenseKey = _licenseKeyLocalService.getLicenseKey(
				subscriptionEntry.getClassPK());

			if (accountKey.equals(licenseKey.getAccountKey())) {
				_subscriptionEntryLocalService.deleteSubscriptionEntry(
					subscriptionEntry.getSubscriptionEntryId());
			}
		}
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference(target = "(provider=okta)")
	private ContactIdentityProvider _contactIdentityProvider;

	@Reference
	private ContactRoleWebService _contactRoleWebService;

	@Reference
	private CustomerPortalRelease _customerPortalRelease;

	@Reference
	private ExternalLinkWebService _externalLinkWebService;

	@Reference
	private LicenseKeyLocalService _licenseKeyLocalService;

	@Reference
	private SubscriptionEntryLocalService _subscriptionEntryLocalService;

}