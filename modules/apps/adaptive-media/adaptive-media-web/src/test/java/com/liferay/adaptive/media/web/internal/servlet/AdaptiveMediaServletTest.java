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

package com.liferay.adaptive.media.web.internal.servlet;

import com.liferay.adaptive.media.exception.AdaptiveMediaException;
import com.liferay.adaptive.media.handler.AdaptiveMediaRequestHandler;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaServletTest {

	@Before
	public void setUp() {
		_servlet.setAdaptiveMediaRequestHandlerLocator(
			_adaptiveMediaRequestHandlerLocator);
	}

	@Test
	public void testMiscellaneousError() throws Exception {
		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_adaptiveMediaRequestHandlerLocator.locateForPattern(
				Mockito.anyString())
		).thenReturn(
			_adaptiveMediaRequestHandler
		);

		Mockito.when(
			_adaptiveMediaRequestHandler.handleRequest(_request)
		).thenThrow(
			new IllegalArgumentException()
		);

		_servlet.doGet(_request, _response);

		Mockito.verify(
			_response
		).sendError(
			Mockito.eq(HttpServletResponse.SC_BAD_REQUEST), Mockito.anyString()
		);
	}

	@Test
	public void testNoMediaFound() throws Exception {
		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_adaptiveMediaRequestHandlerLocator.locateForPattern(
				Mockito.anyString())
		).thenReturn(
			_adaptiveMediaRequestHandler
		);

		Mockito.when(
			_adaptiveMediaRequestHandler.handleRequest(_request)
		).thenReturn(
			Optional.empty()
		);

		_servlet.doGet(_request, _response);

		Mockito.verify(
			_response
		).sendError(
			Mockito.eq(HttpServletResponse.SC_NOT_FOUND), Mockito.anyString()
		);
	}

	@Test
	public void testNoMediaFoundWithException() throws Exception {
		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_adaptiveMediaRequestHandlerLocator.locateForPattern(
				Mockito.anyString())
		).thenReturn(
			_adaptiveMediaRequestHandler
		);

		Mockito.when(
			_adaptiveMediaRequestHandler.handleRequest(_request)
		).thenThrow(
			AdaptiveMediaException.AdaptiveMediaNotFound.class
		);

		_servlet.doGet(_request, _response);

		Mockito.verify(
			_response
		).sendError(
			Mockito.eq(HttpServletResponse.SC_NOT_FOUND), Mockito.anyString()
		);
	}

	@Test
	public void testNoPermissionError() throws Exception {
		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_adaptiveMediaRequestHandlerLocator.locateForPattern(
				Mockito.anyString())
		).thenReturn(
			_adaptiveMediaRequestHandler
		);

		Mockito.when(
			_adaptiveMediaRequestHandler.handleRequest(_request)
		).thenThrow(
			new ServletException(new PrincipalException())
		);

		_servlet.doGet(_request, _response);

		Mockito.verify(
			_response
		).sendError(
			Mockito.eq(HttpServletResponse.SC_FORBIDDEN), Mockito.anyString()
		);
	}

	@Test
	public void testNoRequestHandlerFound() throws Exception {
		Mockito.when(
			_request.getPathInfo()
		).thenReturn(
			StringUtil.randomString()
		);

		Mockito.when(
			_adaptiveMediaRequestHandlerLocator.locateForPattern(
				Mockito.anyString())
		).thenReturn(
			null
		);

		_servlet.doGet(_request, _response);

		Mockito.verify(
			_response
		).sendError(
			Mockito.eq(HttpServletResponse.SC_NOT_FOUND), Mockito.anyString()
		);
	}

	private final AdaptiveMediaRequestHandler<?> _adaptiveMediaRequestHandler =
		Mockito.mock(AdaptiveMediaRequestHandler.class);
	private final AdaptiveMediaRequestHandlerLocator
		_adaptiveMediaRequestHandlerLocator = Mockito.mock(
			AdaptiveMediaRequestHandlerLocator.class);
	private final HttpServletRequest _request = Mockito.mock(
		HttpServletRequest.class);
	private final HttpServletResponse _response = Mockito.mock(
		HttpServletResponse.class);
	private final AdaptiveMediaServlet _servlet = new AdaptiveMediaServlet();

}