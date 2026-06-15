/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.publisher;

import com.liferay.osb.spring.boot.client.pubsub.Message;

import org.apache.commons.lang3.RandomStringUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author Kyle Bischof
 */
@RunWith(MockitoJUnitRunner.class)
public class BasePubsubPublisherTest {

	@Test
	public void testErrorHandling() throws Exception {
		BasePubsubPublisher basePubsubPublisher = Mockito.mock(
			BasePubsubPublisher.class, Mockito.CALLS_REAL_METHODS);

		Exception exception = new Exception(
			RandomStringUtils.randomAlphanumeric(16));
		Message message = new Message(
			null, RandomStringUtils.randomAlphanumeric(10),
			RandomStringUtils.randomAlphabetic(6));

		Mockito.doNothing(
		).when(
			basePubsubPublisher
		).handleError(
			exception, message
		);

		Mockito.doThrow(
			exception
		).when(
			basePubsubPublisher
		).doPublish(
			message
		);

		basePubsubPublisher.publish(message);

		Mockito.verify(
			basePubsubPublisher
		).handleError(
			exception, message
		);
	}

}