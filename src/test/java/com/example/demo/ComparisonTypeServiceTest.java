package com.example.demo;

import com.pepit.compareTout.entity.ComparisonType;
import com.pepit.compareTout.repository.ComparisonTypeRepository;
import com.pepit.compareTout.service.ComparisonTypeService;
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
public class ComparisonTypeServiceTest {

	@InjectMocks
	private ComparisonTypeService service;

	@Mock
	private ComparisonTypeRepository repository;

	private static ComparisonType object;

	private static Collection<ComparisonType> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our ComparisonType
		object = new ComparisonType();
		object.setName("TypeComp");
		object.setIdComparisonType(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<ComparisonType>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<ComparisonType>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindComparisonTypeByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndComparisonTypeWithThisIdExist(){
		ComparisonType object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(ComparisonTypeServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindComparisonTypeByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllComparisonTypeIsOkWhenComparisonTypeCollectionContainsElements(){
		Collection<ComparisonType> objects = service.findAll();
		assertEquals(ComparisonTypeServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllComparisonTypeIsOkWhenComparisonTypeCollectionIsEmpty(){
		ComparisonTypeServiceTest.objects.clear();
		Collection<ComparisonType> empty = new ArrayList<ComparisonType>();
		Collection<ComparisonType> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenComparisonTypeIsNotNull(){
		ComparisonType object = service.create(ComparisonTypeServiceTest.object);
		assertEquals(ComparisonTypeServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenComparisonTypeIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenComparisonTypeIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		ComparisonType object = new ComparisonType();
		object.setName("TypeCompUpdated");
		ComparisonType updated = service.update(object, 1L);
		assertEquals(updated.getName(),object.getName());
		assertTrue(updated.getIdComparisonType()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenComparisonTypeIsNull(){
		service.update(null, 1L);
	}

}
