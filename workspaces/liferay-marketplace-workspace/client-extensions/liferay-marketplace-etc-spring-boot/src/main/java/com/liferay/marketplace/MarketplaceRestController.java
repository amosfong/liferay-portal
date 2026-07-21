/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.headless.admin.user.client.dto.v1_0.Account;
import com.liferay.headless.admin.user.client.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.client.dto.v1_0.PostalAddress;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountResource;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountRoleResource;
import com.liferay.headless.admin.user.client.resource.v1_0.PostalAddressResource;
import com.liferay.headless.admin.user.client.resource.v1_0.UserAccountResource;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Currency;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductVirtualSettingsFileEntry;
import com.liferay.headless.commerce.admin.catalog.client.resource.v1_0.CurrencyResource;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.BillingAddress;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderItemResource;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.marketplace.model.PublisherAssetLink;
import com.liferay.marketplace.permission.DefaultServiceAccountPermission;
import com.liferay.marketplace.service.MarketplaceService;
import com.liferay.marketplace.service.ProvisioningService;
import com.liferay.marketplace.util.MarketplaceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import java.math.BigDecimal;

import java.net.http.HttpResponse;

import java.nio.file.Files;
import java.nio.file.Path;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author Keven Leone
 */
@RequestMapping("/marketplace")
@RestController
public class MarketplaceRestController extends BaseRestController {

	@GetMapping("orders/export")
	public ResponseEntity<StreamingResponseBody> getOrdersExport(
			@RequestParam(defaultValue = "", name = "filters", required = false)
				String filterString)
		throws Exception {

		StreamingResponseBody streamingResponseBody = outputStream -> {
			try (CSVPrinter csvPrinter = new CSVPrinter(
					new BufferedWriter(new OutputStreamWriter(outputStream)),
					CSVFormat.DEFAULT.builder(
					).setHeader(
						"Account ERC", "Account Name", "Create Date",
						"Creator Email", "Order ID", "Order Type",
						"Product Name", "Total"
					).build())) {

				OrderResource orderResource =
					_marketplaceService.getOrderResource();

				for (int i = 1;; i++) {
					Page<Order> page = orderResource.getOrdersPage(
						"", filterString, Pagination.of(i, 200), "");

					for (Order order : page.getItems()) {
						String orderItemName = "";

						for (OrderItem orderItem : order.getOrderItems()) {
							orderItemName = orderItem.getName(
							).get(
								"en_US"
							);

							break;
						}

						com.liferay.headless.commerce.admin.order.client.dto.
							v1_0.Account account = order.getAccount();

						csvPrinter.printRecord(
							account.getExternalReferenceCode(),
							account.getName(), order.getCreateDate(),
							order.getCreatorEmailAddress(), order.getId(),
							order.getOrderTypeExternalReferenceCode(),
							orderItemName, order.getTotalFormatted());
					}

					csvPrinter.flush();

					if (i >= page.getLastPage()) {
						break;
					}
				}
			}
			catch (Exception exception) {
				throw new IOException(exception);
			}
		};

		return ResponseEntity.ok(
		).header(
			HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.csv"
		).contentType(
			MediaType.parseMediaType("text/csv")
		).body(
			streamingResponseBody
		);
	}

	@GetMapping("/product/{productExternalReferenceCode}/version")
	public ResponseEntity<Map<String, Object>> getProductVersion(
			@RequestParam String dxpVersion,
			@PathVariable String productExternalReferenceCode,
			@AuthenticationPrincipal Jwt jwt)
		throws Exception {

		Product product = _marketplaceService.getProductByExternalReferenceCode(
			productExternalReferenceCode, jwt);

		ProductVirtualSettingsFileEntry productVirtualSettingsFileEntry =
			_getProductVirtualSettingsFileEntry(
				dxpVersion,
				_marketplaceService.getProductVirtualSettingsFileEntries(
					product.getProductId(), jwt));

		if (productVirtualSettingsFileEntry == null) {
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Product virtual settings file entry was not found");
		}

		String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath(
		).path(
			"/marketplace/products/{productExternalReferenceCode}" +
				"/virtual-entry/{virtualEntryId}/download"
		).buildAndExpand(
			productExternalReferenceCode,
			productVirtualSettingsFileEntry.getId()
		).toUriString();

		return ResponseEntity.ok(
			HashMapBuilder.<String, Object>put(
				"downloadURL", downloadURL
			).put(
				"productId", _getProductId(product)
			).put(
				"productName",
				product.getName(
				).get(
					"en_US"
				)
			).put(
				"sha256Checksum",
				_getSHA256Checksum(productVirtualSettingsFileEntry)
			).put(
				"version", productVirtualSettingsFileEntry.getVersion()
			).put(
				"virtualEntryId", productVirtualSettingsFileEntry.getId()
			).build());
	}

	@PostMapping(
		"/products/{productExternalReferenceCode}/virtual-entry/{virtualEntryId}/download"
	)
	public ResponseEntity<StreamingResponseBody> getVirtualEntryDownload(
			@PathVariable String productExternalReferenceCode,
			@PathVariable long virtualEntryId, @RequestBody String jwt)
		throws Exception {

		Product product = _marketplaceService.getProductByExternalReferenceCode(
			productExternalReferenceCode);

		ProductVirtualSettingsFileEntry productVirtualSettingsFileEntry =
			_getProductVirtualSettingsFileEntry(
				virtualEntryId,
				_marketplaceService.getProductVirtualSettingsFileEntries(
					product.getProductId()));

		if (productVirtualSettingsFileEntry == null) {
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND,
				"Product virtual settings file entry was not found");
		}

		if (!_hasAddOn(
				_getProductId(product),
				_provisioningService.getEnvironmentAddOns(jwt))) {

			throw new ResponseStatusException(
				HttpStatus.FORBIDDEN,
				"Environment is not entitled to the product");
		}

		HttpResponse<InputStream> httpResponse =
			_marketplaceService.getAssetHttpResponse(
				productVirtualSettingsFileEntry.getSrc());

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setAccessControlExposeHeaders(
			Collections.singletonList(HttpHeaders.CONTENT_DISPOSITION));

		List<String> contentTypes = httpResponse.headers(
		).allValues(
			HttpHeaders.CONTENT_TYPE
		);

		if (contentTypes.isEmpty()) {
			httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		}
		else {
			httpHeaders.setContentType(
				MediaType.parseMediaType(contentTypes.get(0)));
		}

		List<String> contentDispositions = httpResponse.headers(
		).allValues(
			HttpHeaders.CONTENT_DISPOSITION
		);

		if (contentDispositions.isEmpty()) {
			httpHeaders.setContentDispositionFormData(
				"attachment", "product-virtual-entry-" + virtualEntryId);
		}
		else {
			httpHeaders.set(
				HttpHeaders.CONTENT_DISPOSITION, contentDispositions.get(0));
		}

		StreamingResponseBody streamingResponseBody = outputStream -> {
			try (InputStream inputStream = httpResponse.body()) {
				inputStream.transferTo(outputStream);
			}
		};

		return new ResponseEntity<>(
			streamingResponseBody, httpHeaders, HttpStatus.OK);
	}

	@PostMapping("/account")
	public ResponseEntity<Account> postAccount(
			@RequestPart("account") String accountJSON,
			@RequestPart(name = "file", required = false) MultipartFile file,
			@AuthenticationPrincipal Jwt jwt)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("POST account " + accountJSON);
		}

		Account account = Account.toDTO(accountJSON);

		if (file != null) {
			Base64.Encoder encoder = Base64.getEncoder();

			account.setLogoBase64(
				() -> encoder.encodeToString(file.getBytes()));
		}

		AccountResource accountResource =
			_marketplaceService.getAccountResource();

		com.liferay.headless.admin.user.client.pagination.Page<Account>
			accountsPage = accountResource.getAccountsPage(
				"", "name eq '" + account.getName() + "'",
				com.liferay.headless.admin.user.client.pagination.Pagination.of(
					1, 1),
				"");

		if (accountsPage.getTotalCount() > 0) {
			throw new ResponseStatusException(
				HttpStatus.CONFLICT, "Account already exists");
		}

		account = accountResource.postAccount(account);

		PostalAddressResource postalAddressesResource =
			_marketplaceService.getPostalAddressResource();

		PostalAddress postalAddress =
			postalAddressesResource.getAccountPostalAddressesPage(
				account.getId()
			).fetchFirstItem();

		if (postalAddress != null) {
			accountResource.patchAccount(
				account.getId(),
				new Account() {
					{
						setDefaultBillingAddressId(postalAddress::getId);
					}
				});
		}

		UserAccountResource userAccountResource =
			_marketplaceService.getUserAccountResource();

		UserAccount userAccount = userAccountResource.getUserAccount(
			GetterUtil.getLong(jwt.getClaimAsString("sub")));

		String emailAddress = userAccount.getEmailAddress();

		userAccountResource.postAccountUserAccountByEmailAddress(
			account.getId(), emailAddress);

		Long accountRoleId = _getAccountAdministratorRoleId(account.getId());

		if (accountRoleId != null) {
			AccountRoleResource accountRoleResource =
				_marketplaceService.getAccountRoleResource();

			accountRoleResource.
				postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
					account.getExternalReferenceCode(), accountRoleId,
					emailAddress);
		}

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"User ", emailAddress, " was associated with account ",
					account.getName()));
		}

		return ResponseEntity.ok(account);
	}

	@PostMapping("/tax-calculate/{orderId}")
	public void postTaxCalculate(@PathVariable long orderId) throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info("POST tax calculate for order " + orderId);
		}

		Order order = _marketplaceService.getOrder(orderId);

		BillingAddress billingAddress = _marketplaceService.getBillingAddress(
			orderId);

		if (billingAddress == null) {
			return;
		}

		com.liferay.headless.commerce.admin.order.client.dto.v1_0.Account
			account = order.getAccount();

		BigDecimal subtotalAmount = BigDecimal.valueOf(
			order.getSubtotalAmount());

		BigDecimal taxAmount = BigDecimal.ZERO;

		BigDecimal total = subtotalAmount.add(taxAmount);

		if ((Objects.equals(account.getType(), _ACCOUNT_TYPE_BUSINESS) &&
			 Objects.equals(billingAddress.getCountryISOCode(), "IE")) ||
			(Objects.equals(account.getType(), _ACCOUNT_TYPE_PERSON) &&
			 _europeanCountriesISOCode.contains(
				 billingAddress.getCountryISOCode()))) {

			OrderResource orderResource =
				_marketplaceService.getOrderResource();

			OrderItemResource orderItemResource =
				_marketplaceService.getOrderItemResource();

			taxAmount = subtotalAmount.multiply(
				BigDecimal.valueOf(_MARKETPLACE_TAX_PERCENTAGE));

			total = subtotalAmount.add(taxAmount);
			BigDecimal finalTaxAmount = taxAmount;

			BigDecimal finalTotal = total;

			for (OrderItem orderItem : order.getOrderItems()) {
				orderItemResource.patchOrderItem(
					orderItem.getId(),
					new OrderItem() {
						{
							setFinalPrice(orderItem::getFinalPrice);
							setFinalPriceWithTaxAmount(
								() -> orderItem.getFinalPrice(
								).add(
									orderItem.getFinalPrice(
									).multiply(
										BigDecimal.valueOf(
											_MARKETPLACE_TAX_PERCENTAGE)
									)
								));
							setPriceManuallyAdjusted(() -> true);
						}
					});
			}

			_setExchangeRate(order);

			orderResource.patchOrder(
				orderId,
				new Order() {
					{
						setCustomFields(order::getCustomFields);
						setTaxAmount(() -> finalTaxAmount);
						setTotal(() -> finalTotal);
					}
				});
		}
	}

	@PostMapping("/process-publisher-asset-links/{productId}")
	public void processPublisherAssetLinks(@PathVariable long productId)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info(
				"POST process publisher asset links for product " + productId);
		}

		try {
			Product product = _marketplaceService.getProduct(productId);

			Map<String, String> productSpecificationsMap =
				_marketplaceService.getProductSpecificationsMap(productId);

			if (Objects.equals(productSpecificationsMap.get("type"), "dxp")) {
				return;
			}

			List<PublisherAssetLink> publisherAssetLinks =
				_getPublisherAssetLinks(
					_marketplaceService.getPublisherAssetsJSONObject(
						productId));

			if (publisherAssetLinks.isEmpty()) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"No publisher asset links were found to process for " +
							"product " + productId);
				}

				return;
			}

			for (PublisherAssetLink publisherAssetLink : publisherAssetLinks) {
				_processPublisherAssetLink(
					product, productSpecificationsMap, publisherAssetLink);
			}
		}
		catch (WebClientResponseException webClientResponseException) {
			_log.error(
				StringBundler.concat(
					"Unable to process publisher asset links for product ",
					productId, ":\n",
					webClientResponseException.getResponseBodyAsString()));
		}
	}

	private boolean _compatibleVersion(String dxpVersion, String fileVersion) {
		if (Validator.isNull(dxpVersion) || Validator.isNull(fileVersion)) {
			return false;
		}

		dxpVersion = StringUtil.replace(
			StringUtil.toLowerCase(dxpVersion.trim()), '.', ' ');

		fileVersion = StringUtil.replace(
			StringUtil.toLowerCase(fileVersion.trim()), '.', ' ');

		if (dxpVersion.contains(fileVersion) ||
			fileVersion.contains(dxpVersion)) {

			return true;
		}

		String[] dxpVersionParts = dxpVersion.split(" ");

		if ((dxpVersionParts.length >= 2) &&
			dxpVersionParts[0].matches("\\d{4}") &&
			dxpVersionParts[1].matches("q[1-4]")) {

			return fileVersion.contains(
				dxpVersionParts[0] + " " + dxpVersionParts[1]);
		}

		return false;
	}

	private Long _getAccountAdministratorRoleId(long accountId)
		throws Exception {

		AccountRoleResource accountRoleResource =
			_marketplaceService.getAccountRoleResource();

		com.liferay.headless.admin.user.client.pagination.Page<AccountRole>
			accountRolesPage = accountRoleResource.getAccountAccountRolesPage(
				accountId, null, "name eq 'Account Administrator'",
				com.liferay.headless.admin.user.client.pagination.Pagination.of(
					1, 1),
				null);

		AccountRole accountRole = accountRolesPage.fetchFirstItem();

		if (accountRole == null) {
			return null;
		}

		return accountRole.getId();
	}

	private String _getOrderTypeName(Order order) {
		if (Objects.equals(
				order.getOrderTypeExternalReferenceCode(), "AI_HUB")) {

			return "AI Hub Beta";
		}

		if (Objects.equals(
				order.getOrderTypeExternalReferenceCode(), "CMP_BETA")) {

			return "CMP Beta";
		}

		return null;
	}

	private String _getProductId(Product product) throws Exception {
		Map<String, String> productSpecificationsMap =
			_marketplaceService.getProductSpecificationsMap(
				product.getProductId());

		String productId = productSpecificationsMap.get("app-entry-uuid");

		if (Validator.isNull(productId)) {
			productId = product.getExternalReferenceCode();
		}

		return productId;
	}

	private ProductVirtualSettingsFileEntry _getProductVirtualSettingsFileEntry(
		long virtualEntryId,
		ProductVirtualSettingsFileEntry[] productVirtualSettingsFileEntries) {

		if (productVirtualSettingsFileEntries == null) {
			return null;
		}

		for (ProductVirtualSettingsFileEntry productVirtualSettingsFileEntry :
				productVirtualSettingsFileEntries) {

			if (Objects.equals(
					productVirtualSettingsFileEntry.getId(), virtualEntryId)) {

				return productVirtualSettingsFileEntry;
			}
		}

		return null;
	}

	private ProductVirtualSettingsFileEntry _getProductVirtualSettingsFileEntry(
		String dxpVersion,
		ProductVirtualSettingsFileEntry[] productVirtualSettingsFileEntries) {

		if (productVirtualSettingsFileEntries == null) {
			return null;
		}

		for (ProductVirtualSettingsFileEntry productVirtualSettingsFileEntry :
				productVirtualSettingsFileEntries) {

			if (_compatibleVersion(
					dxpVersion, productVirtualSettingsFileEntry.getVersion())) {

				return productVirtualSettingsFileEntry;
			}
		}

		return productVirtualSettingsFileEntries[0];
	}

	private File _getPublisherAssetFile(String publisherAssetURL)
		throws Exception {

		Path path = Files.createTempFile("publisher_asset_", ".zip");

		File file = path.toFile();

		try (InputStream inputStream =
				_marketplaceService.getPublisherAssetInputStream(
					publisherAssetURL);
			FileOutputStream fileOutputStream = new FileOutputStream(file)) {

			inputStream.transferTo(fileOutputStream);
		}

		return file;
	}

	private List<PublisherAssetLink> _getPublisherAssetLinks(
		JSONObject jsonObject) {

		List<PublisherAssetLink> publisherAssetLinks = new ArrayList<>();

		JSONArray itemsJSONArray = jsonObject.optJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			JSONArray attachmentsJSONArray = itemJSONObject.getJSONArray(
				"publisherAssetsToAttachment");

			for (int j = 0; j < attachmentsJSONArray.length(); j++) {
				JSONObject attachmentJSONObject =
					attachmentsJSONArray.getJSONObject(j);

				if (attachmentJSONObject.getBoolean("processed")) {
					continue;
				}

				JSONObject sourceCodeJSONObject =
					attachmentJSONObject.getJSONObject("sourceCode");

				JSONObject linkJSONObject = sourceCodeJSONObject.getJSONObject(
					"link");

				publisherAssetLinks.add(
					new PublisherAssetLink(
						attachmentJSONObject.getLong("id"),
						sourceCodeJSONObject.getString("name"),
						linkJSONObject.getString("href"),
						itemJSONObject.optString("version", "")));
			}
		}

		return publisherAssetLinks;
	}

	private String _getSHA256Checksum(
			ProductVirtualSettingsFileEntry productVirtualSettingsFileEntry)
		throws Exception {

		String key =
			productVirtualSettingsFileEntry.getId() + "@" +
				productVirtualSettingsFileEntry.getVersion();

		String sha256Checksum = _sha256Checksums.get(key);

		if (sha256Checksum != null) {
			return sha256Checksum;
		}

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

		HttpResponse<InputStream> httpResponse =
			_marketplaceService.getAssetHttpResponse(
				productVirtualSettingsFileEntry.getSrc());

		try (InputStream inputStream = httpResponse.body()) {
			byte[] bytes = new byte[8192];
			int bytesRead = -1;

			while ((bytesRead = inputStream.read(bytes)) != -1) {
				messageDigest.update(bytes, 0, bytesRead);
			}
		}

		HexFormat hexFormat = HexFormat.of();

		sha256Checksum = hexFormat.formatHex(messageDigest.digest());

		_sha256Checksums.put(key, sha256Checksum);

		return sha256Checksum;
	}

	private boolean _hasAddOn(String productId, JSONObject addOnsJSONObject) {
		JSONArray addOnsJSONArray = addOnsJSONObject.optJSONArray("add-ons");

		if (addOnsJSONArray == null) {
			return false;
		}

		for (int i = 0; i < addOnsJSONArray.length(); i++) {
			JSONObject addOnJSONObject = addOnsJSONArray.getJSONObject(i);

			if (Objects.equals(
					addOnJSONObject.optString("productId"), productId)) {

				return true;
			}
		}

		return false;
	}

	@PostMapping("request-product-feedback/{orderId}")
	private void _postRequestProductFeedback(
			@AuthenticationPrincipal Jwt jwt, @PathVariable long orderId)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("POST request product feedback " + orderId);
		}

		_defaultServiceAccountPermission.check(jwt);

		Order order = _marketplaceService.getOrder(orderId);

		OrderItem[] orderItems = order.getOrderItems();

		OrderItem orderItem = orderItems[0];

		if (orderItem == null) {
			return;
		}

		Product product = _marketplaceService.getProductBySkuId(
			orderItem.getSkuId());

		Map<String, String> productSpecificationsMap =
			_marketplaceService.getProductSpecificationsMap(
				product.getProductId());

		_marketplaceService.postNotificationQueueEntry(
			order.getCreatorEmailAddress(),
			"MARKETPLACE-REQUEST-PRODUCT-FEEDBACK",
			HashMapBuilder.put(
				"[%CATALOG_NAME%]",
				product.getCatalog(
				).getName()
			).put(
				"[%EMAIL_BODY%]",
				StringBundler.concat(
					"<p>It has been a few weeks since you started using <b>",
					_getOrderTypeName(order),
					"</b> via the Marketplace. We hope it’s helping you ",
					"streamline your Liferay operations. Could you spare <b>5 ",
					"minutes</b> to let us know how we’re doing?</p>")
			).put(
				"[%MARKETPLACE_HOST%]",
				lxcDXPServerProtocol + "://" + lxcDXPMainDomain
			).put(
				"[%ORDER_ID%]", String.valueOf(orderId)
			).put(
				"[%PRODUCT_NAME%]",
				product.getName(
				).get(
					"en_US"
				)
			).put(
				"[%PRODUCT_THUMBNAIL%]",
				_marketplaceService.getProductThumbnail(product)
			).put(
				"[%PRODUCT_TYPE%]", productSpecificationsMap.get("app-beta")
			).build());
	}

	private void _processPublisherAssetLink(
			Product product, Map<String, String> productSpecificationsMap,
			PublisherAssetLink publisherAssetLink)
		throws Exception {

		File publisherAssetArtifactFile = null;
		File publisherAssetFile = null;

		try {
			publisherAssetFile = _getPublisherAssetFile(
				publisherAssetLink.getHREF());

			publisherAssetArtifactFile = MarketplaceUtil.addArtifactMetadata(
				publisherAssetFile, publisherAssetLink.getFileName(),
				MarketplaceUtil.getArtifactPropertiesMap(
					product, productSpecificationsMap, publisherAssetLink));

			_marketplaceService.postVirtualFileEntry(
				publisherAssetArtifactFile, product.getProductId(),
				publisherAssetLink.getVersion());

			if (Objects.equals(productSpecificationsMap.get("type"), "cloud")) {
				_marketplaceService.postProductAttachment(
					publisherAssetArtifactFile,
					publisherAssetLink.getFileName(), product.getProductId());
			}

			_marketplaceService.patchPublisherAssetAttachment(
				new JSONObject(
				).put(
					"processed", true
				).toString(),
				publisherAssetLink.getAttachmentId());
		}
		finally {
			MarketplaceUtil.deleteTempFile(publisherAssetArtifactFile, true);
			MarketplaceUtil.deleteTempFile(publisherAssetFile, false);
		}
	}

	private void _setExchangeRate(Order order) throws Exception {
		JSONObject orderMetadataJSONObject =
			MarketplaceUtil.getOrderMetadataJSONObject(order);

		if (orderMetadataJSONObject.has("exchangeRate")) {
			return;
		}

		CurrencyResource currencyResource =
			_marketplaceService.getCurrencyResource();

		com.liferay.headless.commerce.admin.catalog.client.pagination.Page
			<Currency> currenciesPage = currencyResource.getCurrenciesPage(
				null, "code eq 'EUR'",
				com.liferay.headless.commerce.admin.catalog.client.pagination.
					Pagination.of(1, 1),
				null);

		Currency currency = currenciesPage.fetchFirstItem();

		if (currency == null) {
			return;
		}

		Map<String, String> customFields =
			(Map<String, String>)order.getCustomFields();

		customFields.put(
			"order-metadata",
			orderMetadataJSONObject.put(
				"exchangeRate", currency.getRate()
			).toString());
	}

	private static final int _ACCOUNT_TYPE_BUSINESS = 2;

	private static final int _ACCOUNT_TYPE_PERSON = 1;

	private static final double _MARKETPLACE_TAX_PERCENTAGE = 0.20;

	private static final Log _log = LogFactory.getLog(
		MarketplaceRestController.class);

	@Autowired
	private DefaultServiceAccountPermission _defaultServiceAccountPermission;

	private final Set<String> _europeanCountriesISOCode = Set.of(
		"AT", "BE", "BG", "CY", "CZ", "DE", "DK", "EE", "ES", "FI", "FR", "GR",
		"HR", "HU", "IE", "IT", "LT", "LU", "LV", "MT", "NL", "PL", "PT", "RO",
		"SE", "SI", "SK");

	@Autowired
	private MarketplaceService _marketplaceService;

	@Autowired
	private ProvisioningService _provisioningService;

	private final Map<String, String> _sha256Checksums =
		new ConcurrentHashMap<>();

}