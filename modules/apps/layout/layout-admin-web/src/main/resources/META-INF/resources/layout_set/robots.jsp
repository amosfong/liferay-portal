<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LayoutSet layoutSet = layoutsAdminDisplayContext.getSelLayoutSet();

String virtualHostname = layoutsAdminDisplayContext.getVirtualHostname();

String robotsContribution = layoutsAdminDisplayContext.getRobotsContribution();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="robots"
/>

<c:choose>
	<c:when test="<%= Validator.isNotNull(virtualHostname) %>">
		<p class="text-secondary" id="<portlet:namespace />robotsDescription"><liferay-ui:message key="robots-txt-help" /></p>

		<aui:input aria-describedby="<portlet:namespace />robotsDescription" label="robots" name='<%= "TypeSettingsProperties--" + layoutSet.isPrivateLayout() + "-robots.txt--" %>' placeholder="robots" type="textarea" value="<%= layoutsAdminDisplayContext.getRobots() %>" />

		<c:if test="<%= Validator.isNotNull(robotsContribution) %>">
			<clay:alert
				cssClass="mt-2"
				displayType="info"
			>
				<liferay-ui:message key="widgets-on-these-pages-are-contributing-the-following-entries-to-robots-txt-you-can-manage-these-from-the-widget-configurations-on-those-pages" />

				<pre class="mb-0 mt-2"><%= HtmlUtil.escape(robotsContribution) %></pre>
			</clay:alert>
		</c:if>
	</c:when>
	<c:otherwise>
		<clay:alert
			displayType="info"
			message="please-set-the-virtual-host-before-you-set-the-robots-txt"
		/>
	</c:otherwise>
</c:choose>