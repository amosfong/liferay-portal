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

package com.liferay.adaptive.media.image.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the adaptive media image entry service. This utility wraps {@link com.liferay.adaptive.media.image.service.persistence.impl.AdaptiveMediaImageEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImageEntryPersistence
 * @see com.liferay.adaptive.media.image.service.persistence.impl.AdaptiveMediaImageEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageEntryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		getPersistence().clearCache(adaptiveMediaImageEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AdaptiveMediaImageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AdaptiveMediaImageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AdaptiveMediaImageEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AdaptiveMediaImageEntry update(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		return getPersistence().update(adaptiveMediaImageEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AdaptiveMediaImageEntry update(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(adaptiveMediaImageEntry, serviceContext);
	}

	/**
	* Returns all the adaptive media image entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByUuid(
		java.lang.String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the adaptive media image entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @return the range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByUuid(
		java.lang.String uuid, int start, int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByUuid_First(
		java.lang.String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByUuid_First(
		java.lang.String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByUuid_Last(
		java.lang.String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where uuid = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public static AdaptiveMediaImageEntry[] findByUuid_PrevAndNext(
		long adaptiveMediaImageEntryId, java.lang.String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByUuid_PrevAndNext(adaptiveMediaImageEntryId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the adaptive media image entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(java.lang.String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of adaptive media image entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching adaptive media image entries
	*/
	public static int countByUuid(java.lang.String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the adaptive media image entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAdaptiveMediaImageEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByUUID_G(java.lang.String uuid,
		long groupId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the adaptive media image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByUUID_G(java.lang.String uuid,
		long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the adaptive media image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the adaptive media image entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the adaptive media image entry that was removed
	*/
	public static AdaptiveMediaImageEntry removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of adaptive media image entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching adaptive media image entries
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the adaptive media image entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByUuid_C(
		java.lang.String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the adaptive media image entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @return the range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public static AdaptiveMediaImageEntry[] findByUuid_C_PrevAndNext(
		long adaptiveMediaImageEntryId, java.lang.String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(adaptiveMediaImageEntryId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the adaptive media image entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of adaptive media image entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching adaptive media image entries
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the adaptive media image entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the adaptive media image entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @return the range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByGroupId(long groupId,
		int start, int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByGroupId_First(long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByGroupId_First(long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByGroupId_Last(long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where groupId = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public static AdaptiveMediaImageEntry[] findByGroupId_PrevAndNext(
		long adaptiveMediaImageEntryId, long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(adaptiveMediaImageEntryId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the adaptive media image entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of adaptive media image entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching adaptive media image entries
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns all the adaptive media image entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the adaptive media image entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @return the range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByCompanyId_First(
		long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByCompanyId_Last(long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where companyId = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public static AdaptiveMediaImageEntry[] findByCompanyId_PrevAndNext(
		long adaptiveMediaImageEntryId, long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(adaptiveMediaImageEntryId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the adaptive media image entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of adaptive media image entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching adaptive media image entries
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the adaptive media image entries where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @return the matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		java.lang.String configurationUuid) {
		return getPersistence().findByConfigurationUuid(configurationUuid);
	}

	/**
	* Returns a range of all the adaptive media image entries where configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @return the range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		java.lang.String configurationUuid, int start, int end) {
		return getPersistence()
				   .findByConfigurationUuid(configurationUuid, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		java.lang.String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .findByConfigurationUuid(configurationUuid, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		java.lang.String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByConfigurationUuid(configurationUuid, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByConfigurationUuid_First(
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByConfigurationUuid_First(configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByConfigurationUuid_First(
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByConfigurationUuid_First(configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByConfigurationUuid_Last(
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByConfigurationUuid_Last(configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByConfigurationUuid_Last(
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByConfigurationUuid_Last(configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public static AdaptiveMediaImageEntry[] findByConfigurationUuid_PrevAndNext(
		long adaptiveMediaImageEntryId, java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByConfigurationUuid_PrevAndNext(adaptiveMediaImageEntryId,
			configurationUuid, orderByComparator);
	}

	/**
	* Removes all the adaptive media image entries where configurationUuid = &#63; from the database.
	*
	* @param configurationUuid the configuration uuid
	*/
	public static void removeByConfigurationUuid(
		java.lang.String configurationUuid) {
		getPersistence().removeByConfigurationUuid(configurationUuid);
	}

	/**
	* Returns the number of adaptive media image entries where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @return the number of matching adaptive media image entries
	*/
	public static int countByConfigurationUuid(
		java.lang.String configurationUuid) {
		return getPersistence().countByConfigurationUuid(configurationUuid);
	}

	/**
	* Returns all the adaptive media image entries where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId) {
		return getPersistence().findByFileVersionId(fileVersionId);
	}

	/**
	* Returns a range of all the adaptive media image entries where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @return the range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end) {
		return getPersistence().findByFileVersionId(fileVersionId, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where fileVersionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileVersionId the file version ID
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByFileVersionId(fileVersionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByFileVersionId_First(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByFileVersionId_Last(fileVersionId, orderByComparator);
	}

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public static AdaptiveMediaImageEntry[] findByFileVersionId_PrevAndNext(
		long adaptiveMediaImageEntryId, long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByFileVersionId_PrevAndNext(adaptiveMediaImageEntryId,
			fileVersionId, orderByComparator);
	}

	/**
	* Removes all the adaptive media image entries where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	*/
	public static void removeByFileVersionId(long fileVersionId) {
		getPersistence().removeByFileVersionId(fileVersionId);
	}

	/**
	* Returns the number of adaptive media image entries where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching adaptive media image entries
	*/
	public static int countByFileVersionId(long fileVersionId) {
		return getPersistence().countByFileVersionId(fileVersionId);
	}

	/**
	* Returns all the adaptive media image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @return the matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		java.lang.String configurationUuid) {
		return getPersistence().findByC_C(companyId, configurationUuid);
	}

	/**
	* Returns a range of all the adaptive media image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @return the range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		java.lang.String configurationUuid, int start, int end) {
		return getPersistence()
				   .findByC_C(companyId, configurationUuid, start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		java.lang.String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .findByC_C(companyId, configurationUuid, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		java.lang.String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(companyId, configurationUuid, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByC_C_First(long companyId,
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByC_C_First(companyId, configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the first adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByC_C_First(long companyId,
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(companyId, configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByC_C_Last(long companyId,
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByC_C_Last(companyId, configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the last adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByC_C_Last(long companyId,
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(companyId, configurationUuid,
			orderByComparator);
	}

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public static AdaptiveMediaImageEntry[] findByC_C_PrevAndNext(
		long adaptiveMediaImageEntryId, long companyId,
		java.lang.String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence()
				   .findByC_C_PrevAndNext(adaptiveMediaImageEntryId, companyId,
			configurationUuid, orderByComparator);
	}

	/**
	* Removes all the adaptive media image entries where companyId = &#63; and configurationUuid = &#63; from the database.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	*/
	public static void removeByC_C(long companyId,
		java.lang.String configurationUuid) {
		getPersistence().removeByC_C(companyId, configurationUuid);
	}

	/**
	* Returns the number of adaptive media image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @return the number of matching adaptive media image entries
	*/
	public static int countByC_C(long companyId,
		java.lang.String configurationUuid) {
		return getPersistence().countByC_C(companyId, configurationUuid);
	}

	/**
	* Returns the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; or throws a {@link NoSuchAdaptiveMediaImageEntryException} if it could not be found.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry findByC_F(
		java.lang.String configurationUuid, long fileVersionId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence().findByC_F(configurationUuid, fileVersionId);
	}

	/**
	* Returns the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByC_F(
		java.lang.String configurationUuid, long fileVersionId) {
		return getPersistence().fetchByC_F(configurationUuid, fileVersionId);
	}

	/**
	* Returns the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByC_F(
		java.lang.String configurationUuid, long fileVersionId,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_F(configurationUuid, fileVersionId,
			retrieveFromCache);
	}

	/**
	* Removes the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; from the database.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the adaptive media image entry that was removed
	*/
	public static AdaptiveMediaImageEntry removeByC_F(
		java.lang.String configurationUuid, long fileVersionId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence().removeByC_F(configurationUuid, fileVersionId);
	}

	/**
	* Returns the number of adaptive media image entries where configurationUuid = &#63; and fileVersionId = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the number of matching adaptive media image entries
	*/
	public static int countByC_F(java.lang.String configurationUuid,
		long fileVersionId) {
		return getPersistence().countByC_F(configurationUuid, fileVersionId);
	}

	/**
	* Caches the adaptive media image entry in the entity cache if it is enabled.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	*/
	public static void cacheResult(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		getPersistence().cacheResult(adaptiveMediaImageEntry);
	}

	/**
	* Caches the adaptive media image entries in the entity cache if it is enabled.
	*
	* @param adaptiveMediaImageEntries the adaptive media image entries
	*/
	public static void cacheResult(
		List<AdaptiveMediaImageEntry> adaptiveMediaImageEntries) {
		getPersistence().cacheResult(adaptiveMediaImageEntries);
	}

	/**
	* Creates a new adaptive media image entry with the primary key. Does not add the adaptive media image entry to the database.
	*
	* @param adaptiveMediaImageEntryId the primary key for the new adaptive media image entry
	* @return the new adaptive media image entry
	*/
	public static AdaptiveMediaImageEntry create(long adaptiveMediaImageEntryId) {
		return getPersistence().create(adaptiveMediaImageEntryId);
	}

	/**
	* Removes the adaptive media image entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry that was removed
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public static AdaptiveMediaImageEntry remove(long adaptiveMediaImageEntryId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence().remove(adaptiveMediaImageEntryId);
	}

	public static AdaptiveMediaImageEntry updateImpl(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		return getPersistence().updateImpl(adaptiveMediaImageEntry);
	}

	/**
	* Returns the adaptive media image entry with the primary key or throws a {@link NoSuchAdaptiveMediaImageEntryException} if it could not be found.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public static AdaptiveMediaImageEntry findByPrimaryKey(
		long adaptiveMediaImageEntryId)
		throws com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException {
		return getPersistence().findByPrimaryKey(adaptiveMediaImageEntryId);
	}

	/**
	* Returns the adaptive media image entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry, or <code>null</code> if a adaptive media image entry with the primary key could not be found
	*/
	public static AdaptiveMediaImageEntry fetchByPrimaryKey(
		long adaptiveMediaImageEntryId) {
		return getPersistence().fetchByPrimaryKey(adaptiveMediaImageEntryId);
	}

	public static java.util.Map<java.io.Serializable, AdaptiveMediaImageEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the adaptive media image entries.
	*
	* @return the adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the adaptive media image entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @return the range of adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findAll(int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the adaptive media image entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AdaptiveMediaImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of adaptive media image entries
	* @param end the upper bound of the range of adaptive media image entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of adaptive media image entries
	*/
	public static List<AdaptiveMediaImageEntry> findAll(int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the adaptive media image entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of adaptive media image entries.
	*
	* @return the number of adaptive media image entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static AdaptiveMediaImageEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AdaptiveMediaImageEntryPersistence, AdaptiveMediaImageEntryPersistence> _serviceTracker =
		ServiceTrackerFactory.open(AdaptiveMediaImageEntryPersistence.class);
}