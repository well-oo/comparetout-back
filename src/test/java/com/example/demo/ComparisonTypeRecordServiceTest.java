package com.example.demo;

import com.pepit.compareTout.entity.ComparisonTypeRecord;
import com.pepit.compareTout.repository.ComparisonTypeRecordRepository;
import com.pepit.compareTout.service.ComparisonTypeRecordService;
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
public class ComparisonTypeRecordServiceTest {

	@InjectMocks
	private ComparisonTypeRecordService service;

	@Mock
	private ComparisonTypeRecordRepository repository;

	private static ComparisonTypeRecord object;

	private static Collection<ComparisonTypeRecord> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our ComparisonTypeRecord
		object = new ComparisonTypeRecord();
		object.setIdComparisonTypeRecord(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<ComparisonTypeRecord>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<ComparisonTypeRecord>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindComparisonTypeRecordByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndComparisonTypeRecordWithThisIdExist(){
		ComparisonTypeRecord object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(ComparisonTypeRecordServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindComparisonTypeRecordByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllComparisonTypeRecordIsOkWhenComparisonTypeRecordCollectionContainsElements(){
		Collection<ComparisonTypeRecord> objects = service.findAll();
		assertEquals(ComparisonTypeRecordServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllComparisonTypeRecordIsOkWhenComparisonTypeRecordCollectionIsEmpty(){
		ComparisonTypeRecordServiceTest.objects.clear();
		Collection<ComparisonTypeRecord> empty = new ArrayList<ComparisonTypeRecord>();
		Collection<ComparisonTypeRecord> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenComparisonTypeRecordIsNotNull(){
		ComparisonTypeRecord object = service.create(ComparisonTypeRecordServiceTest.object);
		assertEquals(ComparisonTypeRecordServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenComparisonTypeRecordIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenComparisonTypeRecordIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		ComparisonTypeRecord object = new ComparisonTypeRecord();
		object.setIdComparisonTypeRecord(1L);
		ComparisonTypeRecord updated = service.update(object, 1L);
		assertEquals(updated.getIdComparisonTypeRecord(),object.getIdComparisonTypeRecord());
		assertTrue(updated.getIdComparisonTypeRecord()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenComparisonTypeRecordIsNull(){
		service.update(null, 1L);
	}

}
