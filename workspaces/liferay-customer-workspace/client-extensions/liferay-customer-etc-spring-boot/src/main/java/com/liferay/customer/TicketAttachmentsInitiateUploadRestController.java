/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.customer.model.TicketAttachment;
import com.liferay.customer.service.GoogleCloudStorageService;
import com.liferay.customer.service.JiraService;
import com.liferay.customer.service.TicketAttachmentService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Amos Fong
 */
@ComponentScan(basePackages = "com.liferay.osb")
@RequestMapping("/ticket-attachments/initiate-upload")
@RestController
public class TicketAttachmentsInitiateUploadRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json,
			@RequestHeader(name = HttpHeaders.ORIGIN) String origin)
		throws Exception {

		try {
			JSONObject jsonObject = new JSONObject(json);

			String fileName = jsonObject.getString("fileName");
			String fileSize = jsonObject.getString("fileSize");
			String gcsSessionURL = jsonObject.optString("gcsSessionURL");
			String md5Checksum = jsonObject.optString("md5Checksum");

			String ticketId = jsonObject.getString("ticketId");

			String accountKey = _getAccountKey(ticketId);

			TicketAttachment ticketAttachment =
				_ticketAttachmentService.fetchTicketAttachment(
					"Bearer " + jwt.getTokenValue(), fileName, ticketId,
					md5Checksum);

			if (ticketAttachment != null) {
				if (ticketAttachment.isApproved()) {
					return new ResponseEntity(
						"Ticket attachment " +
							ticketAttachment.getTicketAttachmentId() +
								" already exists",
						HttpStatus.CONFLICT);
				}
			}
			else {
				String externalReferenceCode = jsonObject.optString(
					"externalReferenceCode");
				String type = jsonObject.optString("type");

				ticketAttachment = _ticketAttachmentService.addTicketAttachment(
					"Bearer " + jwt.getTokenValue(), accountKey,
					externalReferenceCode, fileName, fileSize, ticketId,
					md5Checksum, TicketAttachment.STATUS_DRAFT, type);
			}

			JSONObject responseJSONObject = new JSONObject();

			responseJSONObject.put(
				"accountKey", accountKey
			).put(
				"ticketAttachmentId", ticketAttachment.getTicketAttachmentId()
			);

			if (!gcsSessionURL.equals("")) {
				responseJSONObject.put("gcsSessionURL", gcsSessionURL);
			}
			else {
				responseJSONObject.put(
					"gcsSessionURL",
					_googleCloudStorageService.getUploadSessionURL(
						origin, ticketAttachment.getGCSBucketName(),
						ticketAttachment.getGCSObjectName(), fileSize));
			}

			return new ResponseEntity<>(
				responseJSONObject.toString(), HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String _getAccountKey(String jiraIssueKey) throws Exception {
		JSONObject jsonObject = _jiraService.getIssueJSONObject(jiraIssueKey);

		JSONObject fieldsJSONObject = jsonObject.getJSONObject("fields");

		JSONObject statusJSONObject = fieldsJSONObject.getJSONObject("status");

		String name = statusJSONObject.getString("name");

		if (name.equals("Closed")) {
			throw new Exception("Jira ticket " + jiraIssueKey + " is closed");
		}

		JSONObject projectJSONObject = fieldsJSONObject.getJSONObject(
			"project");

		return projectJSONObject.getString("CUSTOM_FIELD_ASDASFASFSFASFASFASF");
	}

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentsInitiateUploadRestController.class);

	@Autowired
	private GoogleCloudStorageService _googleCloudStorageService;

	@Autowired
	private JiraService _jiraService;

	@Autowired
	private TicketAttachmentService _ticketAttachmentService;

}