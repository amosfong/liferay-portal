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

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public class AdaptiveMediaException extends PortalException {

	public AdaptiveMediaException() {
	}

	public AdaptiveMediaException(String s) {
		super(s);
	}

	public AdaptiveMediaException(String s, Throwable throwable) {
		super(s, throwable);
	}

	public AdaptiveMediaException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * This exception is raised when a requested media type or instance is not found.
	 */
	public static final class AdaptiveMediaNotFound
		extends AdaptiveMediaException {

		public AdaptiveMediaNotFound() {
		}

		public AdaptiveMediaNotFound(String s) {
			super(s);
		}

		public AdaptiveMediaNotFound(String s, Throwable throwable) {
			super(s, throwable);
		}

		public AdaptiveMediaNotFound(Throwable throwable) {
			super(throwable);
		}

	}

}