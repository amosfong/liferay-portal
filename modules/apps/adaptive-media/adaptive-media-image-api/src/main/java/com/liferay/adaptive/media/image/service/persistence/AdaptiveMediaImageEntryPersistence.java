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

import com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the adaptive media image entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.adaptive.media.image.service.persistence.impl.AdaptiveMediaImageEntryPersistenceImpl
 * @see AdaptiveMediaImageEntryUtil
 * @generated
 */
@ProviderType
public interface AdaptiveMediaImageEntryPersistence extends BasePersistence<AdaptiveMediaImageEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AdaptiveMediaImageEntryUtil} to access the adaptive media image entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the adaptive media image entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching adaptive media image entries
	*/
	public java.util.List<AdaptiveMediaImageEntry> findByUuid(
		java.lang.String uuid);

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
	public java.util.List<AdaptiveMediaImageEntry> findByUuid(
		java.lang.String uuid, int start, int end);

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
	public java.util.List<AdaptiveMediaImageEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

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
	public java.util.List<AdaptiveMediaImageEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first adaptive media image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the first adaptive media image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the last adaptive media image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the last adaptive media image entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where uuid = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public AdaptiveMediaImageEntry[] findByUuid_PrevAndNext(
		long adaptiveMediaImageEntryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Removes all the adaptive media image entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of adaptive media image entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching adaptive media image entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the adaptive media image entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAdaptiveMediaImageEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the adaptive media image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByUUID_G(java.lang.String uuid,
		long groupId);

	/**
	* Returns the adaptive media image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the adaptive media image entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the adaptive media image entry that was removed
	*/
	public AdaptiveMediaImageEntry removeByUUID_G(java.lang.String uuid,
		long groupId) throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the number of adaptive media image entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching adaptive media image entries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the adaptive media image entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching adaptive media image entries
	*/
	public java.util.List<AdaptiveMediaImageEntry> findByUuid_C(
		java.lang.String uuid, long companyId);

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
	public java.util.List<AdaptiveMediaImageEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

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
	public java.util.List<AdaptiveMediaImageEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

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
	public java.util.List<AdaptiveMediaImageEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the first adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the last adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the last adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

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
	public AdaptiveMediaImageEntry[] findByUuid_C_PrevAndNext(
		long adaptiveMediaImageEntryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Removes all the adaptive media image entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of adaptive media image entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching adaptive media image entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the adaptive media image entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching adaptive media image entries
	*/
	public java.util.List<AdaptiveMediaImageEntry> findByGroupId(long groupId);

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
	public java.util.List<AdaptiveMediaImageEntry> findByGroupId(long groupId,
		int start, int end);

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
	public java.util.List<AdaptiveMediaImageEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

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
	public java.util.List<AdaptiveMediaImageEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first adaptive media image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the first adaptive media image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the last adaptive media image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the last adaptive media image entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where groupId = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public AdaptiveMediaImageEntry[] findByGroupId_PrevAndNext(
		long adaptiveMediaImageEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Removes all the adaptive media image entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of adaptive media image entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching adaptive media image entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the adaptive media image entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching adaptive media image entries
	*/
	public java.util.List<AdaptiveMediaImageEntry> findByCompanyId(
		long companyId);

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
	public java.util.List<AdaptiveMediaImageEntry> findByCompanyId(
		long companyId, int start, int end);

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
	public java.util.List<AdaptiveMediaImageEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

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
	public java.util.List<AdaptiveMediaImageEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first adaptive media image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the first adaptive media image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the last adaptive media image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the last adaptive media image entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where companyId = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public AdaptiveMediaImageEntry[] findByCompanyId_PrevAndNext(
		long adaptiveMediaImageEntryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Removes all the adaptive media image entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of adaptive media image entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching adaptive media image entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the adaptive media image entries where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @return the matching adaptive media image entries
	*/
	public java.util.List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		java.lang.String configurationUuid);

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
	public java.util.List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		java.lang.String configurationUuid, int start, int end);

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
	public java.util.List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		java.lang.String configurationUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

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
	public java.util.List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		java.lang.String configurationUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first adaptive media image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByConfigurationUuid_First(
		java.lang.String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the first adaptive media image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByConfigurationUuid_First(
		java.lang.String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the last adaptive media image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByConfigurationUuid_Last(
		java.lang.String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the last adaptive media image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByConfigurationUuid_Last(
		java.lang.String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where configurationUuid = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public AdaptiveMediaImageEntry[] findByConfigurationUuid_PrevAndNext(
		long adaptiveMediaImageEntryId, java.lang.String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Removes all the adaptive media image entries where configurationUuid = &#63; from the database.
	*
	* @param configurationUuid the configuration uuid
	*/
	public void removeByConfigurationUuid(java.lang.String configurationUuid);

	/**
	* Returns the number of adaptive media image entries where configurationUuid = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @return the number of matching adaptive media image entries
	*/
	public int countByConfigurationUuid(java.lang.String configurationUuid);

	/**
	* Returns all the adaptive media image entries where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the matching adaptive media image entries
	*/
	public java.util.List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId);

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
	public java.util.List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end);

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
	public java.util.List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

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
	public java.util.List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first adaptive media image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByFileVersionId_First(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the first adaptive media image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByFileVersionId_First(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the last adaptive media image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByFileVersionId_Last(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the last adaptive media image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByFileVersionId_Last(
		long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the adaptive media image entries before and after the current adaptive media image entry in the ordered set where fileVersionId = &#63;.
	*
	* @param adaptiveMediaImageEntryId the primary key of the current adaptive media image entry
	* @param fileVersionId the file version ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public AdaptiveMediaImageEntry[] findByFileVersionId_PrevAndNext(
		long adaptiveMediaImageEntryId, long fileVersionId,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Removes all the adaptive media image entries where fileVersionId = &#63; from the database.
	*
	* @param fileVersionId the file version ID
	*/
	public void removeByFileVersionId(long fileVersionId);

	/**
	* Returns the number of adaptive media image entries where fileVersionId = &#63;.
	*
	* @param fileVersionId the file version ID
	* @return the number of matching adaptive media image entries
	*/
	public int countByFileVersionId(long fileVersionId);

	/**
	* Returns all the adaptive media image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @return the matching adaptive media image entries
	*/
	public java.util.List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		java.lang.String configurationUuid);

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
	public java.util.List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		java.lang.String configurationUuid, int start, int end);

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
	public java.util.List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		java.lang.String configurationUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

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
	public java.util.List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		java.lang.String configurationUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByC_C_First(long companyId,
		java.lang.String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the first adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByC_C_First(long companyId,
		java.lang.String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

	/**
	* Returns the last adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByC_C_Last(long companyId,
		java.lang.String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the last adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByC_C_Last(long companyId,
		java.lang.String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

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
	public AdaptiveMediaImageEntry[] findByC_C_PrevAndNext(
		long adaptiveMediaImageEntryId, long companyId,
		java.lang.String configurationUuid,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Removes all the adaptive media image entries where companyId = &#63; and configurationUuid = &#63; from the database.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	*/
	public void removeByC_C(long companyId, java.lang.String configurationUuid);

	/**
	* Returns the number of adaptive media image entries where companyId = &#63; and configurationUuid = &#63;.
	*
	* @param companyId the company ID
	* @param configurationUuid the configuration uuid
	* @return the number of matching adaptive media image entries
	*/
	public int countByC_C(long companyId, java.lang.String configurationUuid);

	/**
	* Returns the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; or throws a {@link NoSuchAdaptiveMediaImageEntryException} if it could not be found.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the matching adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry findByC_F(
		java.lang.String configurationUuid, long fileVersionId)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByC_F(
		java.lang.String configurationUuid, long fileVersionId);

	/**
	* Returns the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	*/
	public AdaptiveMediaImageEntry fetchByC_F(
		java.lang.String configurationUuid, long fileVersionId,
		boolean retrieveFromCache);

	/**
	* Removes the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; from the database.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the adaptive media image entry that was removed
	*/
	public AdaptiveMediaImageEntry removeByC_F(
		java.lang.String configurationUuid, long fileVersionId)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the number of adaptive media image entries where configurationUuid = &#63; and fileVersionId = &#63;.
	*
	* @param configurationUuid the configuration uuid
	* @param fileVersionId the file version ID
	* @return the number of matching adaptive media image entries
	*/
	public int countByC_F(java.lang.String configurationUuid, long fileVersionId);

	/**
	* Caches the adaptive media image entry in the entity cache if it is enabled.
	*
	* @param adaptiveMediaImageEntry the adaptive media image entry
	*/
	public void cacheResult(AdaptiveMediaImageEntry adaptiveMediaImageEntry);

	/**
	* Caches the adaptive media image entries in the entity cache if it is enabled.
	*
	* @param adaptiveMediaImageEntries the adaptive media image entries
	*/
	public void cacheResult(
		java.util.List<AdaptiveMediaImageEntry> adaptiveMediaImageEntries);

	/**
	* Creates a new adaptive media image entry with the primary key. Does not add the adaptive media image entry to the database.
	*
	* @param adaptiveMediaImageEntryId the primary key for the new adaptive media image entry
	* @return the new adaptive media image entry
	*/
	public AdaptiveMediaImageEntry create(long adaptiveMediaImageEntryId);

	/**
	* Removes the adaptive media image entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry that was removed
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public AdaptiveMediaImageEntry remove(long adaptiveMediaImageEntryId)
		throws NoSuchAdaptiveMediaImageEntryException;

	public AdaptiveMediaImageEntry updateImpl(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry);

	/**
	* Returns the adaptive media image entry with the primary key or throws a {@link NoSuchAdaptiveMediaImageEntryException} if it could not be found.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry
	* @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	*/
	public AdaptiveMediaImageEntry findByPrimaryKey(
		long adaptiveMediaImageEntryId)
		throws NoSuchAdaptiveMediaImageEntryException;

	/**
	* Returns the adaptive media image entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	* @return the adaptive media image entry, or <code>null</code> if a adaptive media image entry with the primary key could not be found
	*/
	public AdaptiveMediaImageEntry fetchByPrimaryKey(
		long adaptiveMediaImageEntryId);

	@Override
	public java.util.Map<java.io.Serializable, AdaptiveMediaImageEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the adaptive media image entries.
	*
	* @return the adaptive media image entries
	*/
	public java.util.List<AdaptiveMediaImageEntry> findAll();

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
	public java.util.List<AdaptiveMediaImageEntry> findAll(int start, int end);

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
	public java.util.List<AdaptiveMediaImageEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator);

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
	public java.util.List<AdaptiveMediaImageEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the adaptive media image entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of adaptive media image entries.
	*
	* @return the number of adaptive media image entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}