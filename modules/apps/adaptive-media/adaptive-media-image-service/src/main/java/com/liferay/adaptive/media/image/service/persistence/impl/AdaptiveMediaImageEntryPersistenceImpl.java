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

package com.liferay.adaptive.media.image.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;
import com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageEntryImpl;
import com.liferay.adaptive.media.image.model.impl.AdaptiveMediaImageEntryModelImpl;
import com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImageEntryPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the adaptive media image entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AdaptiveMediaImageEntryPersistence
 * @see com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImageEntryUtil
 * @generated
 */
@ProviderType
public class AdaptiveMediaImageEntryPersistenceImpl extends BasePersistenceImpl<AdaptiveMediaImageEntry>
	implements AdaptiveMediaImageEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AdaptiveMediaImageEntryUtil} to access the adaptive media image entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AdaptiveMediaImageEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			AdaptiveMediaImageEntryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the adaptive media image entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching adaptive media image entries
	 */
	@Override
	public List<AdaptiveMediaImageEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<AdaptiveMediaImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : list) {
					if (!Objects.equals(uuid, adaptiveMediaImageEntry.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByUuid_First(String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByUuid_First(String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		List<AdaptiveMediaImageEntry> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByUuid_Last(String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByUuid_Last(String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImageEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AdaptiveMediaImageEntry[] findByUuid_PrevAndNext(
		long adaptiveMediaImageEntryId, String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = findByPrimaryKey(adaptiveMediaImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImageEntry[] array = new AdaptiveMediaImageEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, adaptiveMediaImageEntry,
					uuid, orderByComparator, true);

			array[1] = adaptiveMediaImageEntry;

			array[2] = getByUuid_PrevAndNext(session, adaptiveMediaImageEntry,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AdaptiveMediaImageEntry getByUuid_PrevAndNext(Session session,
		AdaptiveMediaImageEntry adaptiveMediaImageEntry, String uuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media image entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImageEntry);
		}
	}

	/**
	 * Returns the number of adaptive media image entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching adaptive media image entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "adaptiveMediaImageEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "adaptiveMediaImageEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(adaptiveMediaImageEntry.uuid IS NULL OR adaptiveMediaImageEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			AdaptiveMediaImageEntryModelImpl.UUID_COLUMN_BITMASK |
			AdaptiveMediaImageEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the adaptive media image entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAdaptiveMediaImageEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByUUID_G(uuid,
				groupId);

		if (adaptiveMediaImageEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
		}

		return adaptiveMediaImageEntry;
	}

	/**
	 * Returns the adaptive media image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the adaptive media image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof AdaptiveMediaImageEntry) {
			AdaptiveMediaImageEntry adaptiveMediaImageEntry = (AdaptiveMediaImageEntry)result;

			if (!Objects.equals(uuid, adaptiveMediaImageEntry.getUuid()) ||
					(groupId != adaptiveMediaImageEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<AdaptiveMediaImageEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					AdaptiveMediaImageEntry adaptiveMediaImageEntry = list.get(0);

					result = adaptiveMediaImageEntry;

					cacheResult(adaptiveMediaImageEntry);

					if ((adaptiveMediaImageEntry.getUuid() == null) ||
							!adaptiveMediaImageEntry.getUuid().equals(uuid) ||
							(adaptiveMediaImageEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, adaptiveMediaImageEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (AdaptiveMediaImageEntry)result;
		}
	}

	/**
	 * Removes the adaptive media image entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the adaptive media image entry that was removed
	 */
	@Override
	public AdaptiveMediaImageEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = findByUUID_G(uuid,
				groupId);

		return remove(adaptiveMediaImageEntry);
	}

	/**
	 * Returns the number of adaptive media image entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching adaptive media image entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "adaptiveMediaImageEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "adaptiveMediaImageEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(adaptiveMediaImageEntry.uuid IS NULL OR adaptiveMediaImageEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "adaptiveMediaImageEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			AdaptiveMediaImageEntryModelImpl.UUID_COLUMN_BITMASK |
			AdaptiveMediaImageEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the adaptive media image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching adaptive media image entries
	 */
	@Override
	public List<AdaptiveMediaImageEntry> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<AdaptiveMediaImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : list) {
					if (!Objects.equals(uuid, adaptiveMediaImageEntry.getUuid()) ||
							(companyId != adaptiveMediaImageEntry.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public AdaptiveMediaImageEntry findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		List<AdaptiveMediaImageEntry> list = findByUuid_C(uuid, companyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AdaptiveMediaImageEntry findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImageEntry> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AdaptiveMediaImageEntry[] findByUuid_C_PrevAndNext(
		long adaptiveMediaImageEntryId, String uuid, long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = findByPrimaryKey(adaptiveMediaImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImageEntry[] array = new AdaptiveMediaImageEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					adaptiveMediaImageEntry, uuid, companyId,
					orderByComparator, true);

			array[1] = adaptiveMediaImageEntry;

			array[2] = getByUuid_C_PrevAndNext(session,
					adaptiveMediaImageEntry, uuid, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AdaptiveMediaImageEntry getByUuid_C_PrevAndNext(Session session,
		AdaptiveMediaImageEntry adaptiveMediaImageEntry, String uuid,
		long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media image entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImageEntry);
		}
	}

	/**
	 * Returns the number of adaptive media image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching adaptive media image entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "adaptiveMediaImageEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "adaptiveMediaImageEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(adaptiveMediaImageEntry.uuid IS NULL OR adaptiveMediaImageEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "adaptiveMediaImageEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			AdaptiveMediaImageEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the adaptive media image entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching adaptive media image entries
	 */
	@Override
	public List<AdaptiveMediaImageEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<AdaptiveMediaImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : list) {
					if ((groupId != adaptiveMediaImageEntry.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByGroupId_First(long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByGroupId_First(long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		List<AdaptiveMediaImageEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByGroupId_Last(long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImageEntry> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AdaptiveMediaImageEntry[] findByGroupId_PrevAndNext(
		long adaptiveMediaImageEntryId, long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = findByPrimaryKey(adaptiveMediaImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImageEntry[] array = new AdaptiveMediaImageEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					adaptiveMediaImageEntry, groupId, orderByComparator, true);

			array[1] = adaptiveMediaImageEntry;

			array[2] = getByGroupId_PrevAndNext(session,
					adaptiveMediaImageEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AdaptiveMediaImageEntry getByGroupId_PrevAndNext(
		Session session, AdaptiveMediaImageEntry adaptiveMediaImageEntry,
		long groupId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media image entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImageEntry);
		}
	}

	/**
	 * Returns the number of adaptive media image entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching adaptive media image entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "adaptiveMediaImageEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			AdaptiveMediaImageEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the adaptive media image entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching adaptive media image entries
	 */
	@Override
	public List<AdaptiveMediaImageEntry> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<AdaptiveMediaImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : list) {
					if ((companyId != adaptiveMediaImageEntry.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByCompanyId_First(long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		List<AdaptiveMediaImageEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByCompanyId_Last(long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImageEntry> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AdaptiveMediaImageEntry[] findByCompanyId_PrevAndNext(
		long adaptiveMediaImageEntryId, long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = findByPrimaryKey(adaptiveMediaImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImageEntry[] array = new AdaptiveMediaImageEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					adaptiveMediaImageEntry, companyId, orderByComparator, true);

			array[1] = adaptiveMediaImageEntry;

			array[2] = getByCompanyId_PrevAndNext(session,
					adaptiveMediaImageEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AdaptiveMediaImageEntry getByCompanyId_PrevAndNext(
		Session session, AdaptiveMediaImageEntry adaptiveMediaImageEntry,
		long companyId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media image entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImageEntry);
		}
	}

	/**
	 * Returns the number of adaptive media image entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching adaptive media image entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "adaptiveMediaImageEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CONFIGURATIONUUID =
		new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByConfigurationUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID =
		new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByConfigurationUuid", new String[] { String.class.getName() },
			AdaptiveMediaImageEntryModelImpl.CONFIGURATIONUUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CONFIGURATIONUUID = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByConfigurationUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the adaptive media image entries where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @return the matching adaptive media image entries
	 */
	@Override
	public List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		String configurationUuid) {
		return findByConfigurationUuid(configurationUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end) {
		return findByConfigurationUuid(configurationUuid, start, end, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return findByConfigurationUuid(configurationUuid, start, end,
			orderByComparator, true);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID;
			finderArgs = new Object[] { configurationUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CONFIGURATIONUUID;
			finderArgs = new Object[] {
					configurationUuid,
					
					start, end, orderByComparator
				};
		}

		List<AdaptiveMediaImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : list) {
					if (!Objects.equals(configurationUuid,
								adaptiveMediaImageEntry.getConfigurationUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				if (!pagination) {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByConfigurationUuid_First(
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByConfigurationUuid_First(configurationUuid,
				orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("configurationUuid=");
		msg.append(configurationUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByConfigurationUuid_First(
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		List<AdaptiveMediaImageEntry> list = findByConfigurationUuid(configurationUuid,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByConfigurationUuid_Last(
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByConfigurationUuid_Last(configurationUuid,
				orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("configurationUuid=");
		msg.append(configurationUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByConfigurationUuid_Last(
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		int count = countByConfigurationUuid(configurationUuid);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImageEntry> list = findByConfigurationUuid(configurationUuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AdaptiveMediaImageEntry[] findByConfigurationUuid_PrevAndNext(
		long adaptiveMediaImageEntryId, String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = findByPrimaryKey(adaptiveMediaImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImageEntry[] array = new AdaptiveMediaImageEntryImpl[3];

			array[0] = getByConfigurationUuid_PrevAndNext(session,
					adaptiveMediaImageEntry, configurationUuid,
					orderByComparator, true);

			array[1] = adaptiveMediaImageEntry;

			array[2] = getByConfigurationUuid_PrevAndNext(session,
					adaptiveMediaImageEntry, configurationUuid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AdaptiveMediaImageEntry getByConfigurationUuid_PrevAndNext(
		Session session, AdaptiveMediaImageEntry adaptiveMediaImageEntry,
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

		boolean bindConfigurationUuid = false;

		if (configurationUuid == null) {
			query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1);
		}
		else if (configurationUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3);
		}
		else {
			bindConfigurationUuid = true;

			query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindConfigurationUuid) {
			qPos.add(configurationUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media image entries where configurationUuid = &#63; from the database.
	 *
	 * @param configurationUuid the configuration uuid
	 */
	@Override
	public void removeByConfigurationUuid(String configurationUuid) {
		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : findByConfigurationUuid(
				configurationUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImageEntry);
		}
	}

	/**
	 * Returns the number of adaptive media image entries where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @return the number of matching adaptive media image entries
	 */
	@Override
	public int countByConfigurationUuid(String configurationUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CONFIGURATIONUUID;

		Object[] finderArgs = new Object[] { configurationUuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1 =
		"adaptiveMediaImageEntry.configurationUuid IS NULL";
	private static final String _FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2 =
		"adaptiveMediaImageEntry.configurationUuid = ?";
	private static final String _FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3 =
		"(adaptiveMediaImageEntry.configurationUuid IS NULL OR adaptiveMediaImageEntry.configurationUuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileVersionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileVersionId",
			new String[] { Long.class.getName() },
			AdaptiveMediaImageEntryModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEVERSIONID = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileVersionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the adaptive media image entries where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching adaptive media image entries
	 */
	@Override
	public List<AdaptiveMediaImageEntry> findByFileVersionId(long fileVersionId) {
		return findByFileVersionId(fileVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end) {
		return findByFileVersionId(fileVersionId, start, end, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return findByFileVersionId(fileVersionId, start, end,
			orderByComparator, true);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByFileVersionId(
		long fileVersionId, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID;
			finderArgs = new Object[] { fileVersionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID;
			finderArgs = new Object[] {
					fileVersionId,
					
					start, end, orderByComparator
				};
		}

		List<AdaptiveMediaImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : list) {
					if ((fileVersionId != adaptiveMediaImageEntry.getFileVersionId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

				if (!pagination) {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByFileVersionId_First(fileVersionId,
				orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByFileVersionId_First(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		List<AdaptiveMediaImageEntry> list = findByFileVersionId(fileVersionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByFileVersionId_Last(fileVersionId,
				orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByFileVersionId_Last(
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		int count = countByFileVersionId(fileVersionId);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImageEntry> list = findByFileVersionId(fileVersionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AdaptiveMediaImageEntry[] findByFileVersionId_PrevAndNext(
		long adaptiveMediaImageEntryId, long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = findByPrimaryKey(adaptiveMediaImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImageEntry[] array = new AdaptiveMediaImageEntryImpl[3];

			array[0] = getByFileVersionId_PrevAndNext(session,
					adaptiveMediaImageEntry, fileVersionId, orderByComparator,
					true);

			array[1] = adaptiveMediaImageEntry;

			array[2] = getByFileVersionId_PrevAndNext(session,
					adaptiveMediaImageEntry, fileVersionId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AdaptiveMediaImageEntry getByFileVersionId_PrevAndNext(
		Session session, AdaptiveMediaImageEntry adaptiveMediaImageEntry,
		long fileVersionId,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

		query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileVersionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media image entries where fileVersionId = &#63; from the database.
	 *
	 * @param fileVersionId the file version ID
	 */
	@Override
	public void removeByFileVersionId(long fileVersionId) {
		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : findByFileVersionId(
				fileVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImageEntry);
		}
	}

	/**
	 * Returns the number of adaptive media image entries where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the number of matching adaptive media image entries
	 */
	@Override
	public int countByFileVersionId(long fileVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEVERSIONID;

		Object[] finderArgs = new Object[] { fileVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2 = "adaptiveMediaImageEntry.fileVersionId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), String.class.getName() },
			AdaptiveMediaImageEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			AdaptiveMediaImageEntryModelImpl.CONFIGURATIONUUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the adaptive media image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @return the matching adaptive media image entries
	 */
	@Override
	public List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		String configurationUuid) {
		return findByC_C(companyId, configurationUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		String configurationUuid, int start, int end) {
		return findByC_C(companyId, configurationUuid, start, end, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return findByC_C(companyId, configurationUuid, start, end,
			orderByComparator, true);
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
	@Override
	public List<AdaptiveMediaImageEntry> findByC_C(long companyId,
		String configurationUuid, int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { companyId, configurationUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					companyId, configurationUuid,
					
					start, end, orderByComparator
				};
		}

		List<AdaptiveMediaImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : list) {
					if ((companyId != adaptiveMediaImageEntry.getCompanyId()) ||
							!Objects.equals(configurationUuid,
								adaptiveMediaImageEntry.getConfigurationUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				if (!pagination) {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public AdaptiveMediaImageEntry findByC_C_First(long companyId,
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByC_C_First(companyId,
				configurationUuid, orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", configurationUuid=");
		msg.append(configurationUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the first adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByC_C_First(long companyId,
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		List<AdaptiveMediaImageEntry> list = findByC_C(companyId,
				configurationUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AdaptiveMediaImageEntry findByC_C_Last(long companyId,
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByC_C_Last(companyId,
				configurationUuid, orderByComparator);

		if (adaptiveMediaImageEntry != null) {
			return adaptiveMediaImageEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", configurationUuid=");
		msg.append(configurationUuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
	}

	/**
	 * Returns the last adaptive media image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByC_C_Last(long companyId,
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		int count = countByC_C(companyId, configurationUuid);

		if (count == 0) {
			return null;
		}

		List<AdaptiveMediaImageEntry> list = findByC_C(companyId,
				configurationUuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public AdaptiveMediaImageEntry[] findByC_C_PrevAndNext(
		long adaptiveMediaImageEntryId, long companyId,
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = findByPrimaryKey(adaptiveMediaImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImageEntry[] array = new AdaptiveMediaImageEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(session, adaptiveMediaImageEntry,
					companyId, configurationUuid, orderByComparator, true);

			array[1] = adaptiveMediaImageEntry;

			array[2] = getByC_C_PrevAndNext(session, adaptiveMediaImageEntry,
					companyId, configurationUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AdaptiveMediaImageEntry getByC_C_PrevAndNext(Session session,
		AdaptiveMediaImageEntry adaptiveMediaImageEntry, long companyId,
		String configurationUuid,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		boolean bindConfigurationUuid = false;

		if (configurationUuid == null) {
			query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_1);
		}
		else if (configurationUuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_3);
		}
		else {
			bindConfigurationUuid = true;

			query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindConfigurationUuid) {
			qPos.add(configurationUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(adaptiveMediaImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AdaptiveMediaImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the adaptive media image entries where companyId = &#63; and configurationUuid = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 */
	@Override
	public void removeByC_C(long companyId, String configurationUuid) {
		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : findByC_C(
				companyId, configurationUuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(adaptiveMediaImageEntry);
		}
	}

	/**
	 * Returns the number of adaptive media image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @return the number of matching adaptive media image entries
	 */
	@Override
	public int countByC_C(long companyId, String configurationUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { companyId, configurationUuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 = "adaptiveMediaImageEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CONFIGURATIONUUID_1 = "adaptiveMediaImageEntry.configurationUuid IS NULL";
	private static final String _FINDER_COLUMN_C_C_CONFIGURATIONUUID_2 = "adaptiveMediaImageEntry.configurationUuid = ?";
	private static final String _FINDER_COLUMN_C_C_CONFIGURATIONUUID_3 = "(adaptiveMediaImageEntry.configurationUuid IS NULL OR adaptiveMediaImageEntry.configurationUuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_F = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_F",
			new String[] { String.class.getName(), Long.class.getName() },
			AdaptiveMediaImageEntryModelImpl.CONFIGURATIONUUID_COLUMN_BITMASK |
			AdaptiveMediaImageEntryModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F = new FinderPath(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; or throws a {@link NoSuchAdaptiveMediaImageEntryException} if it could not be found.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the matching adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByC_F(String configurationUuid,
		long fileVersionId) throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByC_F(configurationUuid,
				fileVersionId);

		if (adaptiveMediaImageEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("configurationUuid=");
			msg.append(configurationUuid);

			msg.append(", fileVersionId=");
			msg.append(fileVersionId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAdaptiveMediaImageEntryException(msg.toString());
		}

		return adaptiveMediaImageEntry;
	}

	/**
	 * Returns the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByC_F(String configurationUuid,
		long fileVersionId) {
		return fetchByC_F(configurationUuid, fileVersionId, true);
	}

	/**
	 * Returns the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching adaptive media image entry, or <code>null</code> if a matching adaptive media image entry could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByC_F(String configurationUuid,
		long fileVersionId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { configurationUuid, fileVersionId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_F,
					finderArgs, this);
		}

		if (result instanceof AdaptiveMediaImageEntry) {
			AdaptiveMediaImageEntry adaptiveMediaImageEntry = (AdaptiveMediaImageEntry)result;

			if (!Objects.equals(configurationUuid,
						adaptiveMediaImageEntry.getConfigurationUuid()) ||
					(fileVersionId != adaptiveMediaImageEntry.getFileVersionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_2);
			}

			query.append(_FINDER_COLUMN_C_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				qPos.add(fileVersionId);

				List<AdaptiveMediaImageEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_F, finderArgs,
						list);
				}
				else {
					AdaptiveMediaImageEntry adaptiveMediaImageEntry = list.get(0);

					result = adaptiveMediaImageEntry;

					cacheResult(adaptiveMediaImageEntry);

					if ((adaptiveMediaImageEntry.getConfigurationUuid() == null) ||
							!adaptiveMediaImageEntry.getConfigurationUuid()
														.equals(configurationUuid) ||
							(adaptiveMediaImageEntry.getFileVersionId() != fileVersionId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_F,
							finderArgs, adaptiveMediaImageEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (AdaptiveMediaImageEntry)result;
		}
	}

	/**
	 * Removes the adaptive media image entry where configurationUuid = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the adaptive media image entry that was removed
	 */
	@Override
	public AdaptiveMediaImageEntry removeByC_F(String configurationUuid,
		long fileVersionId) throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = findByC_F(configurationUuid,
				fileVersionId);

		return remove(adaptiveMediaImageEntry);
	}

	/**
	 * Returns the number of adaptive media image entries where configurationUuid = &#63; and fileVersionId = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the number of matching adaptive media image entries
	 */
	@Override
	public int countByC_F(String configurationUuid, long fileVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_F;

		Object[] finderArgs = new Object[] { configurationUuid, fileVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_2);
			}

			query.append(_FINDER_COLUMN_C_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				qPos.add(fileVersionId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_F_CONFIGURATIONUUID_1 = "adaptiveMediaImageEntry.configurationUuid IS NULL AND ";
	private static final String _FINDER_COLUMN_C_F_CONFIGURATIONUUID_2 = "adaptiveMediaImageEntry.configurationUuid = ? AND ";
	private static final String _FINDER_COLUMN_C_F_CONFIGURATIONUUID_3 = "(adaptiveMediaImageEntry.configurationUuid IS NULL OR adaptiveMediaImageEntry.configurationUuid = '') AND ";
	private static final String _FINDER_COLUMN_C_F_FILEVERSIONID_2 = "adaptiveMediaImageEntry.fileVersionId = ?";

	public AdaptiveMediaImageEntryPersistenceImpl() {
		setModelClass(AdaptiveMediaImageEntry.class);
	}

	/**
	 * Caches the adaptive media image entry in the entity cache if it is enabled.
	 *
	 * @param adaptiveMediaImageEntry the adaptive media image entry
	 */
	@Override
	public void cacheResult(AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		entityCache.putResult(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			adaptiveMediaImageEntry.getPrimaryKey(), adaptiveMediaImageEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				adaptiveMediaImageEntry.getUuid(),
				adaptiveMediaImageEntry.getGroupId()
			}, adaptiveMediaImageEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F,
			new Object[] {
				adaptiveMediaImageEntry.getConfigurationUuid(),
				adaptiveMediaImageEntry.getFileVersionId()
			}, adaptiveMediaImageEntry);

		adaptiveMediaImageEntry.resetOriginalValues();
	}

	/**
	 * Caches the adaptive media image entries in the entity cache if it is enabled.
	 *
	 * @param adaptiveMediaImageEntries the adaptive media image entries
	 */
	@Override
	public void cacheResult(
		List<AdaptiveMediaImageEntry> adaptiveMediaImageEntries) {
		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : adaptiveMediaImageEntries) {
			if (entityCache.getResult(
						AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
						AdaptiveMediaImageEntryImpl.class,
						adaptiveMediaImageEntry.getPrimaryKey()) == null) {
				cacheResult(adaptiveMediaImageEntry);
			}
			else {
				adaptiveMediaImageEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all adaptive media image entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AdaptiveMediaImageEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the adaptive media image entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		entityCache.removeResult(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			adaptiveMediaImageEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AdaptiveMediaImageEntryModelImpl)adaptiveMediaImageEntry,
			true);
	}

	@Override
	public void clearCache(
		List<AdaptiveMediaImageEntry> adaptiveMediaImageEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : adaptiveMediaImageEntries) {
			entityCache.removeResult(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
				AdaptiveMediaImageEntryImpl.class,
				adaptiveMediaImageEntry.getPrimaryKey());

			clearUniqueFindersCache((AdaptiveMediaImageEntryModelImpl)adaptiveMediaImageEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		AdaptiveMediaImageEntryModelImpl adaptiveMediaImageEntryModelImpl) {
		Object[] args = new Object[] {
				adaptiveMediaImageEntryModelImpl.getUuid(),
				adaptiveMediaImageEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			adaptiveMediaImageEntryModelImpl, false);

		args = new Object[] {
				adaptiveMediaImageEntryModelImpl.getConfigurationUuid(),
				adaptiveMediaImageEntryModelImpl.getFileVersionId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_F, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F, args,
			adaptiveMediaImageEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AdaptiveMediaImageEntryModelImpl adaptiveMediaImageEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					adaptiveMediaImageEntryModelImpl.getUuid(),
					adaptiveMediaImageEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((adaptiveMediaImageEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					adaptiveMediaImageEntryModelImpl.getOriginalUuid(),
					adaptiveMediaImageEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					adaptiveMediaImageEntryModelImpl.getConfigurationUuid(),
					adaptiveMediaImageEntryModelImpl.getFileVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}

		if ((adaptiveMediaImageEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_F.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					adaptiveMediaImageEntryModelImpl.getOriginalConfigurationUuid(),
					adaptiveMediaImageEntryModelImpl.getOriginalFileVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}
	}

	/**
	 * Creates a new adaptive media image entry with the primary key. Does not add the adaptive media image entry to the database.
	 *
	 * @param adaptiveMediaImageEntryId the primary key for the new adaptive media image entry
	 * @return the new adaptive media image entry
	 */
	@Override
	public AdaptiveMediaImageEntry create(long adaptiveMediaImageEntryId) {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = new AdaptiveMediaImageEntryImpl();

		adaptiveMediaImageEntry.setNew(true);
		adaptiveMediaImageEntry.setPrimaryKey(adaptiveMediaImageEntryId);

		String uuid = PortalUUIDUtil.generate();

		adaptiveMediaImageEntry.setUuid(uuid);

		adaptiveMediaImageEntry.setCompanyId(companyProvider.getCompanyId());

		return adaptiveMediaImageEntry;
	}

	/**
	 * Removes the adaptive media image entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	 * @return the adaptive media image entry that was removed
	 * @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry remove(long adaptiveMediaImageEntryId)
		throws NoSuchAdaptiveMediaImageEntryException {
		return remove((Serializable)adaptiveMediaImageEntryId);
	}

	/**
	 * Removes the adaptive media image entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the adaptive media image entry
	 * @return the adaptive media image entry that was removed
	 * @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry remove(Serializable primaryKey)
		throws NoSuchAdaptiveMediaImageEntryException {
		Session session = null;

		try {
			session = openSession();

			AdaptiveMediaImageEntry adaptiveMediaImageEntry = (AdaptiveMediaImageEntry)session.get(AdaptiveMediaImageEntryImpl.class,
					primaryKey);

			if (adaptiveMediaImageEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAdaptiveMediaImageEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(adaptiveMediaImageEntry);
		}
		catch (NoSuchAdaptiveMediaImageEntryException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected AdaptiveMediaImageEntry removeImpl(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		adaptiveMediaImageEntry = toUnwrappedModel(adaptiveMediaImageEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(adaptiveMediaImageEntry)) {
				adaptiveMediaImageEntry = (AdaptiveMediaImageEntry)session.get(AdaptiveMediaImageEntryImpl.class,
						adaptiveMediaImageEntry.getPrimaryKeyObj());
			}

			if (adaptiveMediaImageEntry != null) {
				session.delete(adaptiveMediaImageEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (adaptiveMediaImageEntry != null) {
			clearCache(adaptiveMediaImageEntry);
		}

		return adaptiveMediaImageEntry;
	}

	@Override
	public AdaptiveMediaImageEntry updateImpl(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		adaptiveMediaImageEntry = toUnwrappedModel(adaptiveMediaImageEntry);

		boolean isNew = adaptiveMediaImageEntry.isNew();

		AdaptiveMediaImageEntryModelImpl adaptiveMediaImageEntryModelImpl = (AdaptiveMediaImageEntryModelImpl)adaptiveMediaImageEntry;

		if (Validator.isNull(adaptiveMediaImageEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			adaptiveMediaImageEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (adaptiveMediaImageEntry.isNew()) {
				session.save(adaptiveMediaImageEntry);

				adaptiveMediaImageEntry.setNew(false);
			}
			else {
				adaptiveMediaImageEntry = (AdaptiveMediaImageEntry)session.merge(adaptiveMediaImageEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AdaptiveMediaImageEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					adaptiveMediaImageEntryModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					adaptiveMediaImageEntryModelImpl.getUuid(),
					adaptiveMediaImageEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { adaptiveMediaImageEntryModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { adaptiveMediaImageEntryModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					adaptiveMediaImageEntryModelImpl.getConfigurationUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CONFIGURATIONUUID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID,
				args);

			args = new Object[] {
					adaptiveMediaImageEntryModelImpl.getFileVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
				args);

			args = new Object[] {
					adaptiveMediaImageEntryModelImpl.getCompanyId(),
					adaptiveMediaImageEntryModelImpl.getConfigurationUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((adaptiveMediaImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { adaptiveMediaImageEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((adaptiveMediaImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getOriginalUuid(),
						adaptiveMediaImageEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getUuid(),
						adaptiveMediaImageEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((adaptiveMediaImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((adaptiveMediaImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((adaptiveMediaImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getOriginalConfigurationUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CONFIGURATIONUUID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID,
					args);

				args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getConfigurationUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CONFIGURATIONUUID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID,
					args);
			}

			if ((adaptiveMediaImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getOriginalFileVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);

				args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getFileVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);
			}

			if ((adaptiveMediaImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getOriginalCompanyId(),
						adaptiveMediaImageEntryModelImpl.getOriginalConfigurationUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						adaptiveMediaImageEntryModelImpl.getCompanyId(),
						adaptiveMediaImageEntryModelImpl.getConfigurationUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}
		}

		entityCache.putResult(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AdaptiveMediaImageEntryImpl.class,
			adaptiveMediaImageEntry.getPrimaryKey(), adaptiveMediaImageEntry,
			false);

		clearUniqueFindersCache(adaptiveMediaImageEntryModelImpl, false);
		cacheUniqueFindersCache(adaptiveMediaImageEntryModelImpl);

		adaptiveMediaImageEntry.resetOriginalValues();

		return adaptiveMediaImageEntry;
	}

	protected AdaptiveMediaImageEntry toUnwrappedModel(
		AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
		if (adaptiveMediaImageEntry instanceof AdaptiveMediaImageEntryImpl) {
			return adaptiveMediaImageEntry;
		}

		AdaptiveMediaImageEntryImpl adaptiveMediaImageEntryImpl = new AdaptiveMediaImageEntryImpl();

		adaptiveMediaImageEntryImpl.setNew(adaptiveMediaImageEntry.isNew());
		adaptiveMediaImageEntryImpl.setPrimaryKey(adaptiveMediaImageEntry.getPrimaryKey());

		adaptiveMediaImageEntryImpl.setUuid(adaptiveMediaImageEntry.getUuid());
		adaptiveMediaImageEntryImpl.setAdaptiveMediaImageEntryId(adaptiveMediaImageEntry.getAdaptiveMediaImageEntryId());
		adaptiveMediaImageEntryImpl.setGroupId(adaptiveMediaImageEntry.getGroupId());
		adaptiveMediaImageEntryImpl.setCompanyId(adaptiveMediaImageEntry.getCompanyId());
		adaptiveMediaImageEntryImpl.setCreateDate(adaptiveMediaImageEntry.getCreateDate());
		adaptiveMediaImageEntryImpl.setConfigurationUuid(adaptiveMediaImageEntry.getConfigurationUuid());
		adaptiveMediaImageEntryImpl.setFileVersionId(adaptiveMediaImageEntry.getFileVersionId());
		adaptiveMediaImageEntryImpl.setMimeType(adaptiveMediaImageEntry.getMimeType());
		adaptiveMediaImageEntryImpl.setHeight(adaptiveMediaImageEntry.getHeight());
		adaptiveMediaImageEntryImpl.setWidth(adaptiveMediaImageEntry.getWidth());
		adaptiveMediaImageEntryImpl.setSize(adaptiveMediaImageEntry.getSize());

		return adaptiveMediaImageEntryImpl;
	}

	/**
	 * Returns the adaptive media image entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the adaptive media image entry
	 * @return the adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAdaptiveMediaImageEntryException {
		AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByPrimaryKey(primaryKey);

		if (adaptiveMediaImageEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAdaptiveMediaImageEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return adaptiveMediaImageEntry;
	}

	/**
	 * Returns the adaptive media image entry with the primary key or throws a {@link NoSuchAdaptiveMediaImageEntryException} if it could not be found.
	 *
	 * @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	 * @return the adaptive media image entry
	 * @throws NoSuchAdaptiveMediaImageEntryException if a adaptive media image entry with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry findByPrimaryKey(
		long adaptiveMediaImageEntryId)
		throws NoSuchAdaptiveMediaImageEntryException {
		return findByPrimaryKey((Serializable)adaptiveMediaImageEntryId);
	}

	/**
	 * Returns the adaptive media image entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the adaptive media image entry
	 * @return the adaptive media image entry, or <code>null</code> if a adaptive media image entry with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
				AdaptiveMediaImageEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AdaptiveMediaImageEntry adaptiveMediaImageEntry = (AdaptiveMediaImageEntry)serializable;

		if (adaptiveMediaImageEntry == null) {
			Session session = null;

			try {
				session = openSession();

				adaptiveMediaImageEntry = (AdaptiveMediaImageEntry)session.get(AdaptiveMediaImageEntryImpl.class,
						primaryKey);

				if (adaptiveMediaImageEntry != null) {
					cacheResult(adaptiveMediaImageEntry);
				}
				else {
					entityCache.putResult(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
						AdaptiveMediaImageEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
					AdaptiveMediaImageEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return adaptiveMediaImageEntry;
	}

	/**
	 * Returns the adaptive media image entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param adaptiveMediaImageEntryId the primary key of the adaptive media image entry
	 * @return the adaptive media image entry, or <code>null</code> if a adaptive media image entry with the primary key could not be found
	 */
	@Override
	public AdaptiveMediaImageEntry fetchByPrimaryKey(
		long adaptiveMediaImageEntryId) {
		return fetchByPrimaryKey((Serializable)adaptiveMediaImageEntryId);
	}

	@Override
	public Map<Serializable, AdaptiveMediaImageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AdaptiveMediaImageEntry> map = new HashMap<Serializable, AdaptiveMediaImageEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AdaptiveMediaImageEntry adaptiveMediaImageEntry = fetchByPrimaryKey(primaryKey);

			if (adaptiveMediaImageEntry != null) {
				map.put(primaryKey, adaptiveMediaImageEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
					AdaptiveMediaImageEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AdaptiveMediaImageEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : (List<AdaptiveMediaImageEntry>)q.list()) {
				map.put(adaptiveMediaImageEntry.getPrimaryKeyObj(),
					adaptiveMediaImageEntry);

				cacheResult(adaptiveMediaImageEntry);

				uncachedPrimaryKeys.remove(adaptiveMediaImageEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AdaptiveMediaImageEntryModelImpl.ENTITY_CACHE_ENABLED,
					AdaptiveMediaImageEntryImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the adaptive media image entries.
	 *
	 * @return the adaptive media image entries
	 */
	@Override
	public List<AdaptiveMediaImageEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<AdaptiveMediaImageEntry> findAll(int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<AdaptiveMediaImageEntry> findAll(int start, int end,
		OrderByComparator<AdaptiveMediaImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<AdaptiveMediaImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AdaptiveMediaImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY;

				if (pagination) {
					sql = sql.concat(AdaptiveMediaImageEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AdaptiveMediaImageEntry>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the adaptive media image entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AdaptiveMediaImageEntry adaptiveMediaImageEntry : findAll()) {
			remove(adaptiveMediaImageEntry);
		}
	}

	/**
	 * Returns the number of adaptive media image entries.
	 *
	 * @return the number of adaptive media image entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AdaptiveMediaImageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the adaptive media image entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AdaptiveMediaImageEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY = "SELECT adaptiveMediaImageEntry FROM AdaptiveMediaImageEntry adaptiveMediaImageEntry";
	private static final String _SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE_PKS_IN =
		"SELECT adaptiveMediaImageEntry FROM AdaptiveMediaImageEntry adaptiveMediaImageEntry WHERE adaptiveMediaImageEntryId IN (";
	private static final String _SQL_SELECT_ADAPTIVEMEDIAIMAGEENTRY_WHERE = "SELECT adaptiveMediaImageEntry FROM AdaptiveMediaImageEntry adaptiveMediaImageEntry WHERE ";
	private static final String _SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY = "SELECT COUNT(adaptiveMediaImageEntry) FROM AdaptiveMediaImageEntry adaptiveMediaImageEntry";
	private static final String _SQL_COUNT_ADAPTIVEMEDIAIMAGEENTRY_WHERE = "SELECT COUNT(adaptiveMediaImageEntry) FROM AdaptiveMediaImageEntry adaptiveMediaImageEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "adaptiveMediaImageEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AdaptiveMediaImageEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AdaptiveMediaImageEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AdaptiveMediaImageEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "size"
			});
}