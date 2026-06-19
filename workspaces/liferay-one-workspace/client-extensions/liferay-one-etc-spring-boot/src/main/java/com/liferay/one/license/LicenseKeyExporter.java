/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.license;

import com.liferay.one.constants.ProductVersion;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.ee.license.shared.KeyGenerator;
import com.liferay.portal.ee.license.shared.LicenseConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class LicenseKeyExporter {

	public String aggregateXMLs(String[] xmls) throws Exception {
		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("licenses");

		for (String xml : xmls) {
			Document curDocument = SAXReaderUtil.read(xml);

			rootElement.add(curDocument.getRootElement());
		}

		return document.formattedString();
	}

	public String getFileName(
		String licenseKeyName, String productName, String productVersion) {

		StringBundler sb = new StringBundler(6);

		sb.append("activation-key-");
		sb.append(StringUtil.extractChars(productName));
		sb.append(StringPool.DASH);
		sb.append(productVersion);
		sb.append(StringPool.DASH);
		sb.append(licenseKeyName);

		return _formatFileName(sb.toString());
	}

	public String getFileName(String[] licenseKeyNames, String[] productNames) {
		StringBundler sb = new StringBundler(
			1 + (2 * licenseKeyNames.length) + (2 * productNames.length));

		sb.append("activation-key");

		for (String productName : productNames) {
			sb.append(StringPool.DASH);
			sb.append(StringUtil.extractChars(productName));
		}

		for (String licenseKeyName : licenseKeyNames) {
			sb.append(StringPool.DASH);
			sb.append(licenseKeyName);
		}

		return _formatFileName(sb.toString());
	}

	public String toXML(
			String accountName, Date createDate, String description,
			String domains, Date expirationDate, String hostNames,
			String ipAddresses, String key, String licenseEntryName,
			String licenseType, int licenseVersion, String macAddresses,
			int maxClusterNodes, long maxConcurrentUsers, int maxHttpSessions,
			int maxServers, long maxUsers, String owner, String productId,
			String productName, String productVersion, String serverIds,
			String sizing, Date startDate)
		throws Exception {

		Document document = null;

		Map<String, String> properties = _getProperties(
			accountName, createDate, description, domains, expirationDate,
			hostNames, ipAddresses, licenseEntryName, licenseType,
			licenseVersion, macAddresses, maxClusterNodes, maxConcurrentUsers,
			maxHttpSessions, maxServers, maxUsers, owner, productId,
			productName, productVersion, serverIds, sizing, startDate);

		if (licenseVersion >= 3) {
			document = _toXMLVersion3_4(false, key, properties);
		}
		else {
			document = _toXMLVersion2(key, properties);
		}

		return document.formattedString();
	}

	public String toXML(
			String accountName, Date createDate, String description,
			String domains, Date expirationDate, String[] hostNames,
			String[] ipAddresses, String licenseEntryName, String licenseType,
			int licenseVersion, String[] macAddresses, int maxClusterNodes,
			long maxConcurrentUsers, int maxHttpSessions, int maxServers,
			long maxUsers, String owner, String productId, String productName,
			String productVersion, String[] serverIds, String sizing,
			Date startDate)
		throws Exception {

		Map<String, String> properties = _getProperties(
			accountName, createDate, description, domains, expirationDate,
			hostNames[0], ipAddresses[0], licenseEntryName, licenseType,
			licenseVersion, macAddresses[0], maxClusterNodes,
			maxConcurrentUsers, maxHttpSessions, maxServers, maxUsers, owner,
			productId, productName, productVersion, serverIds[0], sizing,
			startDate);

		if ((licenseVersion >= 4) &&
			licenseType.equals(LicenseConstants.TYPE_PRODUCTION)) {

			properties.put("maxServers", String.valueOf(serverIds.length));
		}

		Document document = _toXMLVersion3_4(
			true, StringPool.BLANK, properties);

		Element rootElement = document.getRootElement();

		List<String> allHostNames = new ArrayList<>();
		List<String> allIpAddresses = new ArrayList<>();
		List<String> allMacAddresses = new ArrayList<>();

		Element serversElement = rootElement.addElement("servers");

		for (int i = 0; i < serverIds.length; i++) {
			Map<String, String> curProperties = _getProperties(
				accountName, createDate, description, domains, expirationDate,
				hostNames[i], ipAddresses[i], licenseEntryName, licenseType,
				licenseVersion, macAddresses[i], maxClusterNodes,
				maxConcurrentUsers, maxHttpSessions, maxServers, maxUsers,
				owner, productId, productName, productVersion, serverIds[i],
				sizing, startDate);

			Element serverElement = serversElement.addElement("server");

			_exportServerToXML(serverElement, curProperties);

			String curHostName = curProperties.get("hostNames");

			if (Validator.isNotNull(curHostName)) {
				allHostNames.add(curHostName);
			}

			List<String> curIpAddresses = ListUtil.fromArray(
				StringUtil.split(curProperties.get("ipAddresses")));

			allIpAddresses.addAll(curIpAddresses);

			List<String> curMacAddresses = ListUtil.fromArray(
				StringUtil.split(curProperties.get("macAddresses")));

			allMacAddresses.addAll(curMacAddresses);
		}

		properties.put("hostNames", StringUtil.merge(allHostNames));
		properties.put("ipAddresses", StringUtil.merge(allIpAddresses));
		properties.put("macAddresses", StringUtil.merge(allMacAddresses));

		_addElement("key", rootElement, KeyGenerator.encrypt(properties));

		return document.formattedString();
	}

	private void _addElement(String name, Element parentElement, String value) {
		Element childElement = parentElement.addElement(name);

		if (value != null) {
			childElement.addText(value);
		}
	}

	private void _exportServerToXML(
		Element element, Map<String, String> properties) {

		Element hostNamesElement = element.addElement("host-names");

		String[] hostNames = StringUtil.split(properties.get("hostNames"));

		for (String hostName : hostNames) {
			_addElement("host-name", hostNamesElement, hostName);
		}

		Element ipAddressesElement = element.addElement("ip-addresses");

		String[] ipAddresses = StringUtil.split(properties.get("ipAddresses"));

		for (String ipAddress : ipAddresses) {
			_addElement("ip-address", ipAddressesElement, ipAddress);
		}

		Element macAddressesElement = element.addElement("mac-addresses");

		String[] macAddresses = StringUtil.split(
			properties.get("macAddresses"));

		for (String macAddress : macAddresses) {
			_addElement("mac-address", macAddressesElement, macAddress);
		}

		String[] serverIds = StringUtil.split(properties.get("serverIds"));

		if (serverIds.length > 0) {
			Element serverIdsElement = element.addElement("server-ids");

			for (String serverId : serverIds) {
				_addElement("server-id", serverIdsElement, serverId);
			}
		}
	}

	private String _formatFileName(String fileName) {
		fileName = StringUtil.replace(
			fileName, CharPool.SPACE, StringPool.BLANK);
		fileName = StringUtil.toLowerCase(fileName);
		fileName = fileName.substring(0, Math.min(fileName.length(), 251));

		return fileName.concat(".xml");
	}

	private Map<String, String> _getProperties(
		String accountName, Date createDate, String description, String domains,
		Date expirationDate, String hostNames, String ipAddresses,
		String licenseEntryName, String licenseType, int licenseVersion,
		String macAddresses, int maxClusterNodes, long maxConcurrentUsers,
		int maxHttpSessions, int maxServers, long maxUsers, String owner,
		String productId, String productName, String productVersion,
		String serverIds, String sizing, Date startDate) {

		Map<String, String> properties = KeyGenerator.getProperties(
			accountName, description, StringUtil.split(domains), expirationDate,
			StringUtil.split(hostNames), sizing, StringUtil.split(ipAddresses),
			licenseEntryName, licenseType, String.valueOf(licenseVersion),
			StringUtil.split(macAddresses), maxClusterNodes, maxConcurrentUsers,
			maxHttpSessions, maxServers, maxUsers, owner, productName,
			productId, productVersion, new String[] {serverIds}, startDate);

		// See LRDCOM-2568

		if (productVersion.equals(ProductVersion.PORTAL_VERSION_6_1_10) ||
			productVersion.equals("6.1 GA 1")) {

			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.DAY_OF_MONTH, 31);
			cal.set(Calendar.MONTH, 6);
			cal.set(Calendar.YEAR, 2012);

			if (createDate.before(cal.getTime())) {
				properties.put("productVersion", "6.1");
			}
		}

		return properties;
	}

	private Document _toXMLVersion2(String key, Map<String, String> properties)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("license");

		_addElement(
			"account-name", rootElement, properties.get("accountEntryName"));

		_addElement("owner", rootElement, properties.get("owner"));

		_addElement("description", rootElement, properties.get("description"));

		_addElement(
			"product-name", rootElement, properties.get("productEntryName"));

		_addElement(
			"product-version", rootElement, properties.get("productVersion"));

		_addElement(
			"license-name", rootElement, properties.get("licenseEntryName"));

		String licenseEntryType = properties.get("type");

		_addElement("license-type", rootElement, licenseEntryType);

		_addElement("license-version", rootElement, properties.get("version"));

		DateFormat dateFormat = DateFormat.getDateTimeInstance(
			DateFormat.FULL, DateFormat.FULL, LocaleUtil.US);

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date startDate = new Date(
			GetterUtil.getLong(properties.get("startDate")));

		_addElement("start-date", rootElement, dateFormat.format(startDate));

		Date expirationDate = new Date(
			GetterUtil.getLong(properties.get("expirationDate")));

		_addElement(
			"expiration-date", rootElement, dateFormat.format(expirationDate));

		if (licenseEntryType.equals(LicenseConstants.TYPE_CLUSTER) ||
			licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER_CLUSTER)) {

			_addElement(
				"max-servers", rootElement, properties.get("maxServers"));
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER) ||
			licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER_CLUSTER)) {

			_addElement(
				"max-http-sessions", rootElement,
				properties.get("maxHttpSessions"));
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_PRODUCTION)) {
			Element serverIdsElement = rootElement.addElement("server-ids");

			String[] serverIds = StringUtil.split(properties.get("serverIds"));

			for (String serverId : serverIds) {
				_addElement("server-id", serverIdsElement, serverId);
			}
		}

		_addElement("key", rootElement, key);

		return document;
	}

	private Document _toXMLVersion3_4(
			boolean aggregate, String key, Map<String, String> properties)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("license");

		String productId = properties.get("productId");

		if (Validator.isNull(productId)) {
			_addElement(
				"account-name", rootElement,
				properties.get("accountEntryName"));
		}

		_addElement("owner", rootElement, properties.get("owner"));

		_addElement("description", rootElement, properties.get("description"));

		_addElement(
			"product-name", rootElement, properties.get("productEntryName"));

		if (Validator.isNotNull(productId)) {
			_addElement("product-id", rootElement, productId);
		}

		_addElement(
			"product-version", rootElement, properties.get("productVersion"));

		if (Validator.isNull(productId)) {
			_addElement(
				"license-name", rootElement,
				properties.get("licenseEntryName"));
		}

		String licenseEntryType = properties.get("type");

		_addElement("license-type", rootElement, licenseEntryType);

		long licenseVersion = GetterUtil.getLong(properties.get("version"));

		_addElement(
			"license-version", rootElement, String.valueOf(licenseVersion));

		DateFormat dateFormat = DateFormat.getDateTimeInstance(
			DateFormat.FULL, DateFormat.FULL, LocaleUtil.US);

		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		Date startDate = new Date(
			GetterUtil.getLong(properties.get("startDate")));

		_addElement("start-date", rootElement, dateFormat.format(startDate));

		Date expirationDate = new Date(
			GetterUtil.getLong(properties.get("expirationDate")));

		_addElement(
			"expiration-date", rootElement, dateFormat.format(expirationDate));

		if (licenseEntryType.equals(LicenseConstants.TYPE_FREE) ||
			licenseEntryType.equals(LicenseConstants.TYPE_VIRTUAL_CLUSTER)) {

			_addElement(
				"max-cluster-nodes", rootElement,
				properties.get("max-cluster-nodes"));
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_CLUSTER) ||
			((licenseVersion >= 4) &&
			 (licenseEntryType.equals(LicenseConstants.TYPE_LIMITED) ||
			  licenseEntryType.equals(LicenseConstants.TYPE_PRODUCTION)))) {

			_addElement(
				"max-servers", rootElement, properties.get("maxServers"));
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER) ||
			licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER_CLUSTER)) {

			_addElement(
				"max-http-sessions", rootElement,
				properties.get("maxHttpSessions"));
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_FREE)) {
			Element domainsElement = rootElement.addElement("domains");

			String[] domains = StringUtil.split(properties.get("domains"));

			for (String domain : domains) {
				_addElement("domain", domainsElement, domain);
			}
		}

		if (licenseEntryType.equals(LicenseConstants.TYPE_PER_USER)) {
			String maxConcurrentUsers = properties.get("maxConcurrentUsers");

			if (Validator.isNotNull(maxConcurrentUsers)) {
				_addElement(
					"max-concurrent-users", rootElement, maxConcurrentUsers);
			}

			String maxUsers = properties.get("maxUsers");

			if (Validator.isNotNull(maxUsers)) {
				_addElement("max-users", rootElement, maxUsers);
			}
		}

		String instanceSize = properties.get("instanceSize");

		if (Validator.isNotNull(instanceSize)) {
			_addElement("instance-size", rootElement, instanceSize);
		}

		if (!aggregate) {
			if (licenseEntryType.equals(LicenseConstants.TYPE_CLUSTER) ||
				licenseEntryType.equals(LicenseConstants.TYPE_LIMITED) ||
				licenseEntryType.equals(LicenseConstants.TYPE_PER_USER) ||
				licenseEntryType.equals(LicenseConstants.TYPE_PRODUCTION)) {

				_exportServerToXML(rootElement, properties);
			}

			_addElement("key", rootElement, key);
		}

		return document;
	}

}