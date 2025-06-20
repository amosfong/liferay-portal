/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.customer.model.TicketAttachment;
import com.liferay.customer.service.JiraService;
import com.liferay.customer.service.NotificationQueueEntryService;
import com.liferay.customer.service.TicketAttachmentService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Amos Fong
 */
@ComponentScan(basePackages = "com.liferay.osb")
@RequestMapping("/ticket-attachments/{ticketAttachmentId}/complete-upload")
@RestController
public class TicketAttachmentsCompleteUploadRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
			@AuthenticationPrincipal Jwt jwt, @RequestBody String json,
			@PathVariable("ticketAttachmentId") long ticketAttachmentId)
		throws Exception {

		try {
			TicketAttachment ticketAttachment =
				_ticketAttachmentService.approveTicketAttachment(
					"Bearer " + jwt.getTokenValue(), ticketAttachmentId);
			JSONObject jsonObject = new JSONObject(json);

			String jiraIssueCommentBody = _buildJiraIssueCommentBody(
				ticketAttachment, jsonObject.optString("commentBody"));

			try {
				_postJiraComment(
					ticketAttachment.getJiraIssueKey(), jiraIssueCommentBody);
			}
			catch (Exception exception) {
				_log.error(exception, exception);

				_ticketAttachmentService.updateTicketAttachmentDraftCommentBody(
					"Bearer " + jwt.getTokenValue(), ticketAttachmentId,
					jiraIssueCommentBody);

				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}

			return new ResponseEntity<>(HttpStatus.OK);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			return new ResponseEntity(
				exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Scheduled(cron = "0 0 */1 * * ?")
	public void scheduledUpdateTicketAttachmentDraftCommentBody()
		throws Exception {

		List<TicketAttachment> ticketAttachments =
			_ticketAttachmentService.search(
				_getAuthorization(),
				"draftCommentBody ne null and draftCommentBody ne '' and " +
					"(state eq 0 or state eq null) and status/any(s:s eq 0)",
				1, 500);

		for (TicketAttachment ticketAttachment : ticketAttachments) {
			try {
				_postJiraComment(
					ticketAttachment.getJiraIssueKey(),
					ticketAttachment.getDraftCommentBody());

				_ticketAttachmentService.updateTicketAttachmentDraftCommentBody(
					_getAuthorization(),
					ticketAttachment.getTicketAttachmentId(), "");
			}
			catch (Exception exception) {
				_log.error(exception, exception);

				_notificationQueueEntryService.addNotificationQueueEntry(
					"solutions@liferay.com", "Customer Portal",
					"is-support@liferay.com",
					"Customer Portal Error Notification",
					StringBundler.concat(
						"<p>There was an error posting a large file uploader ",
						"comment to Zendesk.</p>",
						StackTraceUtil.getStackTrace(exception)));
			}
		}
	}

	private String _buildJiraIssueCommentBody(
			TicketAttachment ticketAttachment, String commentBody)
		throws Exception {

		StringBundler sb = new StringBundler(13);

		sb.append(_getCommentAuthorInfo(ticketAttachment.getUserId()));

		if (Validator.isNotNull(commentBody)) {
			sb.append("{quote}");
			sb.append(
				StringUtil.replace(commentBody, CharPool.NEW_LINE, "<br />"));
			sb.append("{quote}");
		}
		else {
			sb.append("(empty line)");
		}

		sb.append("[");
		sb.append(ticketAttachment.getFileName());
		sb.append("|");
		sb.append(lxcDXPServerProtocol);
		sb.append("://");
		sb.append(lxcDXPMainDomain);
		sb.append("/placeholder/");
		sb.append(ticketAttachment.getTicketAttachmentId());
		sb.append("]");

		return sb.toString();
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	private String _getCommentAuthorInfo(long userId) {
		StringBundler sb = new StringBundler(6);

		JSONObject jsonObject = new JSONObject(
			get(
				_getAuthorization(),
				UriComponentsBuilder.fromPath(
					"/o/headless-admin-user/v1.0/user-accounts/" + userId
				).build(
				).toUri()));

		sb.append(jsonObject.getString("name"));

		sb.append(" (");
		sb.append(jsonObject.getString("emailAddress"));
		sb.append(") ");

		String languageId = jsonObject.optString("languageId");

		if (languageId.equals("ja_JP")) {
		}
		else if (languageId.equals("zh_CN")) {
		}
		else if (languageId.equals("zh_CN")) {
		}
		else {
			sb.append("wrote");
		}

		sb.append(":");

		return sb.toString();
	}

	private void _postJiraComment(
		String jiraIssueKey, String draftCommentBody) {

		_jiraService.addComment(jiraIssueKey, draftCommentBody);
	}

	private static final Log _log = LogFactory.getLog(
		TicketAttachmentsCompleteUploadRestController.class);

	@Value("${liferay.customer.jira.api.email.address}")
	private String _jiraAPIEmailAddress;

	@Autowired
	private JiraService _jiraService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private NotificationQueueEntryService _notificationQueueEntryService;

	@Autowired
	private TicketAttachmentService _ticketAttachmentService;

}