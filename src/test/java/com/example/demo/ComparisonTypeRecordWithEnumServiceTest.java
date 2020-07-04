package com.example.demo;

import com.pepit.compareTout.entity.ComparisonTypeRecordWithEnum;
import com.pepit.compareTout.entity.ComparisonTypeRecordWithEnumValue;
import com.pepit.compareTout.repository.ComparisonTypeRecordWithEnumRepository;
import com.pepit.compareTout.service.ComparisonTypeRecordWithEnumService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class ComparisonTypeRecordWithEnumServiceTest {

	@InjectMocks
	private ComparisonTypeRecordWithEnumService service;

	@Mock
	private ComparisonTypeRecordWithEnumRepository repository;

	private static ComparisonTypeRecordWithEnum object;

	private static Collection<ComparisonTypeRecordWithEnum> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our ComparisonTypeRecordWithEnum
		object = new ComparisonTypeRecordWithEnum(new ArrayList<ComparisonTypeRecordWithEnumValue>());
		object.setIdComparisonTypeRecord(1L);
		object.setIdComparisonTypeRecordWithEnum(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<ComparisonTypeRecordWithEnum>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<ComparisonTypeRecordWithEnum>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindComparisonTypeRecordWithEnumByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndComparisonTypeRecordWithEnumWithThisIdExist(){
		ComparisonTypeRecordWithEnum object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(ComparisonTypeRecordWithEnumServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordWithEnumByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordWithEnumByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordWithEnumByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindComparisonTypeRecordWithEnumByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllComparisonTypeRecordWithEnumIsOkWhenComparisonTypeRecordWithEnumCollectionContainsElements(){
		Collection<ComparisonTypeRecordWithEnum> objects = service.findAll();
		assertEquals(ComparisonTypeRecordWithEnumServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllComparisonTypeRecordWithEnumIsOkWhenComparisonTypeRecordWithEnumCollectionIsEmpty(){
		ComparisonTypeRecordWithEnumServiceTest.objects.clear();
		Collection<ComparisonTypeRecordWithEnum> empty = new ArrayList<ComparisonTypeRecordWithEnum>();
		Collection<ComparisonTypeRecordWithEnum> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenComparisonTypeRecordWithEnumIsNotNull(){
		ComparisonTypeRecordWithEnum object = service.create(ComparisonTypeRecordWithEnumServiceTest.object);
		assertEquals(ComparisonTypeRecordWithEnumServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenComparisonTypeRecordWithEnumIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenComparisonTypeRecordWithEnumIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		ComparisonTypeRecordWithEnum object = new ComparisonTypeRecordWithEnum(new ArrayList<ComparisonTypeRecordWithEnumValue>());
		object.setIdComparisonTypeRecordWithEnum(4L);
		object.setIdComparisonTypeRecord(1L);
		ComparisonTypeRecordWithEnum updated = service.update(object, 1L);
		assertTrue(updated.getIdComparisonTypeRecord()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenComparisonTypeRecordWithEnumIsNull(){
		service.update(null, 1L);
	}

}
