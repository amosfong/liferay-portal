/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.portal.kernel.util.Validator;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class MasterKeyService {

	public String createSignedJWT(Map<String, Object> claims) throws Exception {
		if (Validator.isNull(_masterPrivateKey)) {
			throw new IllegalStateException(
				"The property \"liferay.customer.master.private.key\" is not " +
					"set");
		}

		JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();

		for (Map.Entry<String, Object> entry : claims.entrySet()) {
			builder.claim(entry.getKey(), entry.getValue());
		}

		builder.expirationTime(
			Date.from(
				Instant.now(
				).plus(
					_EXPIRATION_MINUTES, ChronoUnit.MINUTES
				)));
		builder.issueTime(Date.from(Instant.now()));

		SignedJWT signedJWT = new SignedJWT(
			new JWSHeader(JWSAlgorithm.RS256), builder.build());

		signedJWT.sign(new RSASSASigner(_toRSAPrivateKey(_masterPrivateKey)));

		return signedJWT.serialize();
	}

	private RSAPrivateKey _toRSAPrivateKey(String encodedPrivateKey)
		throws Exception {

		String base64 = encodedPrivateKey.replaceAll(
			"-----(BEGIN|END) PRIVATE KEY-----", "");

		base64 = base64.replaceAll("\\s", "");

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		return (RSAPrivateKey)keyFactory.generatePrivate(
			new PKCS8EncodedKeySpec(
				Base64.getDecoder(
				).decode(
					base64
				)));
	}

	private static final long _EXPIRATION_MINUTES = 10;

	@Value("${liferay.customer.master.private.key}")
	private String _masterPrivateKey;

}