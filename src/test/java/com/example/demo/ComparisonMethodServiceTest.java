package com.example.demo;

import com.pepit.compareTout.entity.ComparisonMethod;
import com.pepit.compareTout.repository.ComparisonMethodRepository;
import com.pepit.compareTout.service.ComparisonMethodService;
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
public class ComparisonMethodServiceTest {

	@InjectMocks
	private ComparisonMethodService service;

	@Mock
	private ComparisonMethodRepository repository;

	private static ComparisonMethod object;

	private static Collection<ComparisonMethod> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our ComparisonMethod
		object = new ComparisonMethod();
		object.setIdComparisonMethod(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<ComparisonMethod>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<ComparisonMethod>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindComparisonMethodByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndComparisonMethodWithThisIdExist(){
		ComparisonMethod object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(ComparisonMethodServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonMethodByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonMethodByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonMethodByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindComparisonMethodByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllComparisonMethodIsOkWhenComparisonMethodCollectionContainsElements(){
		Collection<ComparisonMethod> objects = service.findAll();
		assertEquals(ComparisonMethodServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllComparisonMethodIsOkWhenComparisonMethodCollectionIsEmpty(){
		ComparisonMethodServiceTest.objects.clear();
		Collection<ComparisonMethod> empty = new ArrayList<ComparisonMethod>();
		Collection<ComparisonMethod> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenComparisonMethodIsNotNull(){
		ComparisonMethod object = service.create(ComparisonMethodServiceTest.object);
		assertEquals(ComparisonMethodServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenComparisonMethodIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenComparisonMethodIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		ComparisonMethod object = new ComparisonMethod();
		object.setIdComparisonMethod(1L);
		ComparisonMethod updated = service.update(object, 1L);
		assertEquals(updated.getIdComparisonMethod(),object.getIdComparisonMethod());
		assertTrue(updated.getIdComparisonMethod()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenComparisonMethodIsNull(){
		service.update(null, 1L);
	}

}
