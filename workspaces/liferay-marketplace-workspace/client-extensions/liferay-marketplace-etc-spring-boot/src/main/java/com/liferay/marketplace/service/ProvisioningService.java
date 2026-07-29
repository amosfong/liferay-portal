/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.Product;
import com.liferay.osb.koroneiki.phloem.rest.client.dto.v1_0.ProductPurchase;
import com.liferay.osb.provisioning.marketplace.rest.client.dto.v1_0.AppLicenseKey;
import com.liferay.osb.provisioning.marketplace.rest.client.resource.v1_0.AppLicenseKeyResource;
import com.liferay.osb.provisioning.rest.client.resource.v1_0.LicenseKeyResource;
import com.liferay.petra.string.StringBundler;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.time.ZonedDateTime;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * @author Caleb Hall
 */
@Component
public class ProvisioningService extends BaseService {

	public AppLicenseKeyResource getAppLicenseKeyResource() throws Exception {
		return AppLicenseKeyResource.builder(
		).header(
			"Authorization",
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"external-provisioning")
		).endpoint(
			_externalProvisioningHomePageURL
		).build();
	}

	public JSONObject getEnvironmentAddOns(String jwt) throws Exception {
		JWTClaimsSet jwtClaimsSet = JWTParser.parse(
			jwt
		).getJWTClaimsSet();

		String environmentId = jwtClaimsSet.getStringClaim("environmentID");

		HttpRequest httpRequest = HttpRequest.newBuilder(
		).uri(
			URI.create(
				StringBundler.concat(
					_externalProvisioningHomePageURL,
					"/o/provisioning-rest/v1.0/cloud/environments/",
					environmentId, "/manifest/add-ons"))
		).header(
			"Content-Type", "text/plain"
		).POST(
			HttpRequest.BodyPublishers.ofString(jwt)
		).build();

		HttpClient httpClient = HttpClient.newHttpClient();

		HttpResponse<String> httpResponse = httpClient.send(
			httpRequest, HttpResponse.BodyHandlers.ofString());

		if (httpResponse.statusCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
			throw new IOException(
				StringBundler.concat(
					"Unable to get add-ons for environment ", environmentId,
					": ", httpResponse.statusCode()));
		}

		return new JSONObject(httpResponse.body());
	}

	public LicenseKeyResource getLicenseKeyResource() throws Exception {
		return LicenseKeyResource.builder(
		).header(
			"Authorization",
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"external-provisioning")
		).endpoint(
			_externalProvisioningHomePageURL
		).build();
	}

	public AppLicenseKey postAppLicenseKey(AppLicenseKey appLicenseKey, Jwt jwt)
		throws Exception {

		return postAppLicenseKey(
			appLicenseKey, jwt,
			_koroneikiService.getProductPurchase(
				appLicenseKey.getProductPurchaseKey()));
	}

	public AppLicenseKey postAppLicenseKey(
			AppLicenseKey appLicenseKey, Jwt jwt,
			ProductPurchase productPurchase)
		throws Exception {

		appLicenseKey.setActive(true);
		appLicenseKey.setCreateDate(new Date());

		Date expirationDate = productPurchase.getEndDate();

		if (productPurchase.getPerpetual()) {
			expirationDate = Date.from(
				ZonedDateTime.now(
				).plusYears(
					100
				).toInstant());
		}

		appLicenseKey.setExpirationDate(expirationDate);

		appLicenseKey.setLicenseType(AppLicenseKey.LicenseType.PRODUCTION);

		appLicenseKey.setOwner((String)jwt.getClaim("username"));

		if (appLicenseKey.getProductId() == null) {
			appLicenseKey.setProductId(productPurchase.getProductKey());
		}

		appLicenseKey.setProductName(_getProductName(productPurchase));
		appLicenseKey.setProductPurchaseKey(productPurchase.getKey());
		appLicenseKey.setProductVersion("1");

		Date startDate = productPurchase.getStartDate();

		if (startDate == null) {
			startDate = new Date();
		}

		appLicenseKey.setStartDate(startDate);
		appLicenseKey.setUserName((String)jwt.getClaim("username"));
		appLicenseKey.setUserUuid((String)jwt.getClaim("sub"));

		AppLicenseKeyResource appLicenseKeyResource =
			getAppLicenseKeyResource();

		appLicenseKey = appLicenseKeyResource.postAppLicenseKey(
			jwt.getClaim("username"), jwt.getClaim("sub"), appLicenseKey);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Created app license key for " +
					appLicenseKey.getProductName());
		}

		return appLicenseKey;
	}

	private String _getProductName(ProductPurchase productPurchase) {
		Product product = productPurchase.getProduct();

		Map<String, String> propertiesMap = product.getProperties();

		return propertiesMap.getOrDefault(
			"display-group-name", product.getName());
	}

	private static final Log _log = LogFactory.getLog(
		ProvisioningService.class);

	@Value("${external.provisioning.oauth2.headless.server.home.page.url}")
	private URL _externalProvisioningHomePageURL;

	@Autowired
	private KoroneikiService _koroneikiService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}