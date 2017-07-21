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

package com.liferay.adaptive.media.exception;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaRuntimeException extends SystemException {

	public AdaptiveMediaRuntimeException() {
	}

	public AdaptiveMediaRuntimeException(String s) {
		super(s);
	}

	public AdaptiveMediaRuntimeException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public AdaptiveMediaRuntimeException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * This exception is thrown when a value serialized as a
	 * <code>String</code> cannot be converted by an
	 * {@link AdaptiveMediaAttribute}.
	 */
	public static final class AdaptiveMediaAttributeFormatException
		extends AdaptiveMediaRuntimeException {

		public AdaptiveMediaAttributeFormatException() {
		}

		public AdaptiveMediaAttributeFormatException(String s) {
			super(s);
		}

		public AdaptiveMediaAttributeFormatException(
			String s, Throwable throwable) {

			super(s, throwable);
		}

		public AdaptiveMediaAttributeFormatException(Throwable throwable) {
			super(throwable);
		}

	}

	/**
	 * This exception is thrown when there is a processor configuration error.
	 */
	public static final class InvalidConfiguration
		extends AdaptiveMediaRuntimeException {

		public InvalidConfiguration() {
		}

		public InvalidConfiguration(String s) {
			super(s);
		}

		public InvalidConfiguration(String s, Throwable throwable) {
			super(s, throwable);
		}

		public InvalidConfiguration(Throwable throwable) {
			super(throwable);
		}

	}

	/**
	 * This exception wraps {@link java.io.IOException} instances. Since it
	 * is a system error, it is reasonable to wrap it inside a runtime
	 * exception.
	 */
	public static final class IOException
		extends AdaptiveMediaRuntimeException {

		public IOException() {
		}

		public IOException(String s) {
			super(s);
		}

		public IOException(String s, Throwable throwable) {
			super(s, throwable);
		}

		public IOException(Throwable throwable) {
			super(throwable);
		}

	}

	/**
	 * This exception wraps {@link java.io.UnsupportedEncodingException}
	 * instances. Since it is a system error, it is reasonable to wrap it
	 * inside a runtime exception.
	 */
	public static final class UnsupportedEncodingException
		extends AdaptiveMediaRuntimeException {

		public UnsupportedEncodingException() {
		}

		public UnsupportedEncodingException(String s) {
			super(s);
		}

		public UnsupportedEncodingException(String s, Throwable throwable) {
			super(s, throwable);
		}

		public UnsupportedEncodingException(Throwable throwable) {
			super(throwable);
		}

	}

}