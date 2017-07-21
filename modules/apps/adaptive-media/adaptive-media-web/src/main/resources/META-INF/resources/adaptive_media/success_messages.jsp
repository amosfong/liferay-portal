<%--
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
--%>

<%@ include file="/adaptive_media/init.jsp" %>

<liferay-ui:success key="configurationEntriesDeleted">

	<%
	List<AdaptiveMediaImageConfigurationEntry> configurationEntries = (List<AdaptiveMediaImageConfigurationEntry>)SessionMessages.get(renderRequest, "configurationEntriesDeleted");
	%>

	<c:choose>
		<c:when test="<%= configurationEntries.size() == 1 %>">
			<liferay-ui:message arguments="<%= configurationEntries.get(0).getName() %>" key="x-was-deleted-successfully" translateArguments="<%= false %>" />
		</c:when>
		<c:otherwise>
			<liferay-ui:message arguments='<%= ListUtil.toString(configurationEntries, "name") %>' key="x-were-deleted-successfully" translateArguments="<%= false %>" />
		</c:otherwise>
	</c:choose>
</liferay-ui:success>

<liferay-ui:success key="configurationEntryAdded">

	<%
	AdaptiveMediaImageConfigurationEntry configurationEntry = (AdaptiveMediaImageConfigurationEntry)SessionMessages.get(renderRequest, "configurationEntryAdded");
	%>

	<liferay-ui:message arguments="<%= configurationEntry.getName() %>" key="x-was-saved-successfully" translateArguments="<%= false %>" />
</liferay-ui:success>

<liferay-ui:success key="configurationEntryAddedAndIDRenamed">

	<%
	AdaptiveMediaImageConfigurationEntry configurationEntry = (AdaptiveMediaImageConfigurationEntry)SessionMessages.get(renderRequest, "configurationEntryAddedAndIDRenamed");
	%>

	<liferay-ui:message arguments="<%= new String[] {configurationEntry.getName(), configurationEntry.getUUID()} %>" key="x-was-saved-successfully.-the-id-was-duplicated-and-renamed-to-x" translateArguments="<%= false %>" />
</liferay-ui:success>

<liferay-ui:success key="configurationEntryEnabled">

	<%
	AdaptiveMediaImageConfigurationEntry configurationEntry = (AdaptiveMediaImageConfigurationEntry)SessionMessages.get(renderRequest, "configurationEntryEnabled");
	%>

	<liferay-ui:message arguments="<%= configurationEntry.getName() %>" key="x-was-enabled-successfully" translateArguments="<%= false %>" />
</liferay-ui:success>

<liferay-ui:success key="configurationEntryDisabled">

	<%
	AdaptiveMediaImageConfigurationEntry configurationEntry = (AdaptiveMediaImageConfigurationEntry)SessionMessages.get(renderRequest, "configurationEntryDisabled");
	%>

	<liferay-ui:message arguments="<%= configurationEntry.getName() %>" key="x-was-disabled-successfully" translateArguments="<%= false %>" />
</liferay-ui:success>

<liferay-ui:success key="configurationEntryUpdated">

	<%
	AdaptiveMediaImageConfigurationEntry configurationEntry = (AdaptiveMediaImageConfigurationEntry)SessionMessages.get(renderRequest, "configurationEntryUpdated");
	%>

	<liferay-ui:message arguments="<%= configurationEntry.getName() %>" key="x-was-saved-successfully" translateArguments="<%= false %>" />
</liferay-ui:success>

<liferay-ui:success key="configurationEntryUpdatedAndIDRenamed">

	<%
	AdaptiveMediaImageConfigurationEntry configurationEntry = (AdaptiveMediaImageConfigurationEntry)SessionMessages.get(renderRequest, "configurationEntryUpdatedAndIDRenamed");
	%>

	<liferay-ui:message arguments="<%= new String[] {configurationEntry.getName(), configurationEntry.getUUID()} %>" key="x-was-saved-successfully.-the-id-was-duplicated-and-renamed-to-x" translateArguments="<%= false %>" />
</liferay-ui:success>

<liferay-ui:success key="highResolutionConfigurationEntryAdded">

	<%
	AdaptiveMediaImageConfigurationEntry[] addedConfigurationEntries = (AdaptiveMediaImageConfigurationEntry[])SessionMessages.get(renderRequest, "highResolutionConfigurationEntryAdded");
	%>

	<liferay-ui:message arguments="<%= new String[] {addedConfigurationEntries[0].getName(), addedConfigurationEntries[1].getName()} %>" key="x-and-x-were-saved-successfully" translateArguments="<%= false %>" />
</liferay-ui:success>

<liferay-ui:success key="optimizeImages" message="processing-images.-this-could-take-a-while-depending-on-the-number-of-images" />