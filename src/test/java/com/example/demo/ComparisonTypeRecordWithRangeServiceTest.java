package com.example.demo;

import com.pepit.compareTout.entity.ComparisonTypeRecordWithRange;
import com.pepit.compareTout.repository.ComparisonTypeRecordWithRangeRepository;
import com.pepit.compareTout.service.ComparisonTypeRecordWithRangeService;
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
public class ComparisonTypeRecordWithRangeServiceTest {

	@InjectMocks
	private ComparisonTypeRecordWithRangeService service;

	@Mock
	private ComparisonTypeRecordWithRangeRepository repository;

	private static ComparisonTypeRecordWithRange object;

	private static Collection<ComparisonTypeRecordWithRange> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our ComparisonTypeRecordWithRange
		object = new ComparisonTypeRecordWithRange(1f,10f);
		object.setRightOperande(1);
		object.setLeftOperande(2);
		object.setIdComparisonTypeRecord(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<ComparisonTypeRecordWithRange>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<ComparisonTypeRecordWithRange>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindComparisonTypeRecordWithRangeByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndComparisonTypeRecordWithRangeWithThisIdExist(){
		ComparisonTypeRecordWithRange object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(ComparisonTypeRecordWithRangeServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordWithRangeByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordWithRangeByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordWithRangeByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindComparisonTypeRecordWithRangeByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllComparisonTypeRecordWithRangeIsOkWhenComparisonTypeRecordWithRangeCollectionContainsElements(){
		Collection<ComparisonTypeRecordWithRange> objects = service.findAll();
		assertEquals(ComparisonTypeRecordWithRangeServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllComparisonTypeRecordWithRangeIsOkWhenComparisonTypeRecordWithRangeCollectionIsEmpty(){
		ComparisonTypeRecordWithRangeServiceTest.objects.clear();
		Collection<ComparisonTypeRecordWithRange> empty = new ArrayList<ComparisonTypeRecordWithRange>();
		Collection<ComparisonTypeRecordWithRange> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenComparisonTypeRecordWithRangeIsNotNull(){
		ComparisonTypeRecordWithRange object = service.create(ComparisonTypeRecordWithRangeServiceTest.object);
		assertEquals(ComparisonTypeRecordWithRangeServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenComparisonTypeRecordWithRangeIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenComparisonTypeRecordWithRangeIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		ComparisonTypeRecordWithRange object = new ComparisonTypeRecordWithRange(1f,10f);
		object.setRightOperande(2);
		object.setLeftOperande(4);
		ComparisonTypeRecordWithRange updated = service.update(object, 1L);
		assertTrue(updated.getLeftOperande()==object.getLeftOperande());
		assertTrue(updated.getRightOperande()==object.getRightOperande());
		assertTrue(updated.getIdComparisonTypeRecord()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenComparisonTypeRecordWithRangeIsNull(){
		service.update(null, 1L);
	}

}
