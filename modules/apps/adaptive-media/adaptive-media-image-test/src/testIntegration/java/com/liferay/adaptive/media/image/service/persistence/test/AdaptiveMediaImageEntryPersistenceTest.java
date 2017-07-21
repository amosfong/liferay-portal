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

package com.liferay.adaptive.media.image.service.persistence.test;

import com.liferay.adaptive.media.image.exception.NoSuchAdaptiveMediaImageEntryException;
import com.liferay.adaptive.media.image.model.AdaptiveMediaImageEntry;
import com.liferay.adaptive.media.image.service.AdaptiveMediaImageEntryLocalServiceUtil;
import com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImageEntryPersistence;
import com.liferay.adaptive.media.image.service.persistence.AdaptiveMediaImageEntryUtil;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class AdaptiveMediaImageEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.adaptive.media.image.service"));

	@Before
	public void setUp() {
		_persistence = AdaptiveMediaImageEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AdaptiveMediaImageEntry> iterator = _adaptiveMediaImageEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AdaptiveMediaImageEntry adaptiveMediaImageEntry = _persistence.create(pk);

		Assert.assertNotNull(adaptiveMediaImageEntry);

		Assert.assertEquals(adaptiveMediaImageEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry = addAdaptiveMediaImageEntry();

		_persistence.remove(newAdaptiveMediaImageEntry);

		AdaptiveMediaImageEntry existingAdaptiveMediaImageEntry = _persistence.fetchByPrimaryKey(newAdaptiveMediaImageEntry.getPrimaryKey());

		Assert.assertNull(existingAdaptiveMediaImageEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAdaptiveMediaImageEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry = _persistence.create(pk);

		newAdaptiveMediaImageEntry.setUuid(RandomTestUtil.randomString());

		newAdaptiveMediaImageEntry.setGroupId(RandomTestUtil.nextLong());

		newAdaptiveMediaImageEntry.setCompanyId(RandomTestUtil.nextLong());

		newAdaptiveMediaImageEntry.setCreateDate(RandomTestUtil.nextDate());

		newAdaptiveMediaImageEntry.setConfigurationUuid(RandomTestUtil.randomString());

		newAdaptiveMediaImageEntry.setFileVersionId(RandomTestUtil.nextLong());

		newAdaptiveMediaImageEntry.setMimeType(RandomTestUtil.randomString());

		newAdaptiveMediaImageEntry.setHeight(RandomTestUtil.nextInt());

		newAdaptiveMediaImageEntry.setWidth(RandomTestUtil.nextInt());

		newAdaptiveMediaImageEntry.setSize(RandomTestUtil.nextLong());

		_adaptiveMediaImageEntries.add(_persistence.update(
				newAdaptiveMediaImageEntry));

		AdaptiveMediaImageEntry existingAdaptiveMediaImageEntry = _persistence.findByPrimaryKey(newAdaptiveMediaImageEntry.getPrimaryKey());

		Assert.assertEquals(existingAdaptiveMediaImageEntry.getUuid(),
			newAdaptiveMediaImageEntry.getUuid());
		Assert.assertEquals(existingAdaptiveMediaImageEntry.getAdaptiveMediaImageEntryId(),
			newAdaptiveMediaImageEntry.getAdaptiveMediaImageEntryId());
		Assert.assertEquals(existingAdaptiveMediaImageEntry.getGroupId(),
			newAdaptiveMediaImageEntry.getGroupId());
		Assert.assertEquals(existingAdaptiveMediaImageEntry.getCompanyId(),
			newAdaptiveMediaImageEntry.getCompanyId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAdaptiveMediaImageEntry.getCreateDate()),
			Time.getShortTimestamp(newAdaptiveMediaImageEntry.getCreateDate()));
		Assert.assertEquals(existingAdaptiveMediaImageEntry.getConfigurationUuid(),
			newAdaptiveMediaImageEntry.getConfigurationUuid());
		Assert.assertEquals(existingAdaptiveMediaImageEntry.getFileVersionId(),
			newAdaptiveMediaImageEntry.getFileVersionId());
		Assert.assertEquals(existingAdaptiveMediaImageEntry.getMimeType(),
			newAdaptiveMediaImageEntry.getMimeType());
		Assert.assertEquals(existingAdaptiveMediaImageEntry.getHeight(),
			newAdaptiveMediaImageEntry.getHeight());
		Assert.assertEquals(existingAdaptiveMediaImageEntry.getWidth(),
			newAdaptiveMediaImageEntry.getWidth());
		Assert.assertEquals(existingAdaptiveMediaImageEntry.getSize(),
			newAdaptiveMediaImageEntry.getSize());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid(StringPool.BLANK);

		_persistence.countByUuid(StringPool.NULL);

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByUUID_G() throws Exception {
		_persistence.countByUUID_G(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUUID_G(StringPool.NULL, 0L);

		_persistence.countByUUID_G((String)null, 0L);
	}

	@Test
	public void testCountByUuid_C() throws Exception {
		_persistence.countByUuid_C(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByUuid_C(StringPool.NULL, 0L);

		_persistence.countByUuid_C((String)null, 0L);
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByCompanyId() throws Exception {
		_persistence.countByCompanyId(RandomTestUtil.nextLong());

		_persistence.countByCompanyId(0L);
	}

	@Test
	public void testCountByConfigurationUuid() throws Exception {
		_persistence.countByConfigurationUuid(StringPool.BLANK);

		_persistence.countByConfigurationUuid(StringPool.NULL);

		_persistence.countByConfigurationUuid((String)null);
	}

	@Test
	public void testCountByFileVersionId() throws Exception {
		_persistence.countByFileVersionId(RandomTestUtil.nextLong());

		_persistence.countByFileVersionId(0L);
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(), StringPool.BLANK);

		_persistence.countByC_C(0L, StringPool.NULL);

		_persistence.countByC_C(0L, (String)null);
	}

	@Test
	public void testCountByC_F() throws Exception {
		_persistence.countByC_F(StringPool.BLANK, RandomTestUtil.nextLong());

		_persistence.countByC_F(StringPool.NULL, 0L);

		_persistence.countByC_F((String)null, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry = addAdaptiveMediaImageEntry();

		AdaptiveMediaImageEntry existingAdaptiveMediaImageEntry = _persistence.findByPrimaryKey(newAdaptiveMediaImageEntry.getPrimaryKey());

		Assert.assertEquals(existingAdaptiveMediaImageEntry,
			newAdaptiveMediaImageEntry);
	}

	@Test(expected = NoSuchAdaptiveMediaImageEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AdaptiveMediaImageEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AdaptiveMediaImageEntry",
			"uuid", true, "adaptiveMediaImageEntryId", true, "groupId", true,
			"companyId", true, "createDate", true, "configurationUuid", true,
			"fileVersionId", true, "mimeType", true, "height", true, "width",
			true, "size", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry = addAdaptiveMediaImageEntry();

		AdaptiveMediaImageEntry existingAdaptiveMediaImageEntry = _persistence.fetchByPrimaryKey(newAdaptiveMediaImageEntry.getPrimaryKey());

		Assert.assertEquals(existingAdaptiveMediaImageEntry,
			newAdaptiveMediaImageEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AdaptiveMediaImageEntry missingAdaptiveMediaImageEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAdaptiveMediaImageEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry1 = addAdaptiveMediaImageEntry();
		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry2 = addAdaptiveMediaImageEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAdaptiveMediaImageEntry1.getPrimaryKey());
		primaryKeys.add(newAdaptiveMediaImageEntry2.getPrimaryKey());

		Map<Serializable, AdaptiveMediaImageEntry> adaptiveMediaImageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, adaptiveMediaImageEntries.size());
		Assert.assertEquals(newAdaptiveMediaImageEntry1,
			adaptiveMediaImageEntries.get(
				newAdaptiveMediaImageEntry1.getPrimaryKey()));
		Assert.assertEquals(newAdaptiveMediaImageEntry2,
			adaptiveMediaImageEntries.get(
				newAdaptiveMediaImageEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AdaptiveMediaImageEntry> adaptiveMediaImageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(adaptiveMediaImageEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry = addAdaptiveMediaImageEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAdaptiveMediaImageEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AdaptiveMediaImageEntry> adaptiveMediaImageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, adaptiveMediaImageEntries.size());
		Assert.assertEquals(newAdaptiveMediaImageEntry,
			adaptiveMediaImageEntries.get(
				newAdaptiveMediaImageEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AdaptiveMediaImageEntry> adaptiveMediaImageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(adaptiveMediaImageEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry = addAdaptiveMediaImageEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAdaptiveMediaImageEntry.getPrimaryKey());

		Map<Serializable, AdaptiveMediaImageEntry> adaptiveMediaImageEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, adaptiveMediaImageEntries.size());
		Assert.assertEquals(newAdaptiveMediaImageEntry,
			adaptiveMediaImageEntries.get(
				newAdaptiveMediaImageEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AdaptiveMediaImageEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AdaptiveMediaImageEntry>() {
				@Override
				public void performAction(
					AdaptiveMediaImageEntry adaptiveMediaImageEntry) {
					Assert.assertNotNull(adaptiveMediaImageEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry = addAdaptiveMediaImageEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AdaptiveMediaImageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"adaptiveMediaImageEntryId",
				newAdaptiveMediaImageEntry.getAdaptiveMediaImageEntryId()));

		List<AdaptiveMediaImageEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AdaptiveMediaImageEntry existingAdaptiveMediaImageEntry = result.get(0);

		Assert.assertEquals(existingAdaptiveMediaImageEntry,
			newAdaptiveMediaImageEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AdaptiveMediaImageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq(
				"adaptiveMediaImageEntryId", RandomTestUtil.nextLong()));

		List<AdaptiveMediaImageEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry = addAdaptiveMediaImageEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AdaptiveMediaImageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"adaptiveMediaImageEntryId"));

		Object newAdaptiveMediaImageEntryId = newAdaptiveMediaImageEntry.getAdaptiveMediaImageEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"adaptiveMediaImageEntryId",
				new Object[] { newAdaptiveMediaImageEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingAdaptiveMediaImageEntryId = result.get(0);

		Assert.assertEquals(existingAdaptiveMediaImageEntryId,
			newAdaptiveMediaImageEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AdaptiveMediaImageEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"adaptiveMediaImageEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in(
				"adaptiveMediaImageEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testResetOriginalValues() throws Exception {
		AdaptiveMediaImageEntry newAdaptiveMediaImageEntry = addAdaptiveMediaImageEntry();

		_persistence.clearCache();

		AdaptiveMediaImageEntry existingAdaptiveMediaImageEntry = _persistence.findByPrimaryKey(newAdaptiveMediaImageEntry.getPrimaryKey());

		Assert.assertTrue(Objects.equals(
				existingAdaptiveMediaImageEntry.getUuid(),
				ReflectionTestUtil.invoke(existingAdaptiveMediaImageEntry,
					"getOriginalUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingAdaptiveMediaImageEntry.getGroupId()),
			ReflectionTestUtil.<Long>invoke(existingAdaptiveMediaImageEntry,
				"getOriginalGroupId", new Class<?>[0]));

		Assert.assertTrue(Objects.equals(
				existingAdaptiveMediaImageEntry.getConfigurationUuid(),
				ReflectionTestUtil.invoke(existingAdaptiveMediaImageEntry,
					"getOriginalConfigurationUuid", new Class<?>[0])));
		Assert.assertEquals(Long.valueOf(
				existingAdaptiveMediaImageEntry.getFileVersionId()),
			ReflectionTestUtil.<Long>invoke(existingAdaptiveMediaImageEntry,
				"getOriginalFileVersionId", new Class<?>[0]));
	}

	protected AdaptiveMediaImageEntry addAdaptiveMediaImageEntry()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		AdaptiveMediaImageEntry adaptiveMediaImageEntry = _persistence.create(pk);

		adaptiveMediaImageEntry.setUuid(RandomTestUtil.randomString());

		adaptiveMediaImageEntry.setGroupId(RandomTestUtil.nextLong());

		adaptiveMediaImageEntry.setCompanyId(RandomTestUtil.nextLong());

		adaptiveMediaImageEntry.setCreateDate(RandomTestUtil.nextDate());

		adaptiveMediaImageEntry.setConfigurationUuid(RandomTestUtil.randomString());

		adaptiveMediaImageEntry.setFileVersionId(RandomTestUtil.nextLong());

		adaptiveMediaImageEntry.setMimeType(RandomTestUtil.randomString());

		adaptiveMediaImageEntry.setHeight(RandomTestUtil.nextInt());

		adaptiveMediaImageEntry.setWidth(RandomTestUtil.nextInt());

		adaptiveMediaImageEntry.setSize(RandomTestUtil.nextLong());

		_adaptiveMediaImageEntries.add(_persistence.update(
				adaptiveMediaImageEntry));

		return adaptiveMediaImageEntry;
	}

	private List<AdaptiveMediaImageEntry> _adaptiveMediaImageEntries = new ArrayList<AdaptiveMediaImageEntry>();
	private AdaptiveMediaImageEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}