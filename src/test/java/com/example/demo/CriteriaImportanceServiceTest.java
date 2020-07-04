package com.example.demo;

import com.pepit.compareTout.entity.CriteriaImportance;
import com.pepit.compareTout.repository.CriteriaImportanceRepository;
import com.pepit.compareTout.service.CriteriaImportanceService;
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
public class CriteriaImportanceServiceTest {

	@InjectMocks
	private CriteriaImportanceService service;

	@Mock
	private CriteriaImportanceRepository repository;

	private static CriteriaImportance object;

	private static Collection<CriteriaImportance> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our CriteriaImportance
		object = new CriteriaImportance();
		object.setWeight(1);
		object.setOrderOfImportance(1);
		object.setIdCriteriaImportance(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<CriteriaImportance>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<CriteriaImportance>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindCriteriaImportanceByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndCriteriaImportanceWithThisIdExist(){
		CriteriaImportance object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(CriteriaImportanceServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindCriteriaImportanceByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindCriteriaImportanceByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindCriteriaImportanceByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindCriteriaImportanceByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllCriteriaImportanceIsOkWhenCriteriaImportanceCollectionContainsElements(){
		Collection<CriteriaImportance> objects = service.findAll();
		assertEquals(CriteriaImportanceServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllCriteriaImportanceIsOkWhenCriteriaImportanceCollectionIsEmpty(){
		CriteriaImportanceServiceTest.objects.clear();
		Collection<CriteriaImportance> empty = new ArrayList<CriteriaImportance>();
		Collection<CriteriaImportance> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenCriteriaImportanceIsNotNull(){
		CriteriaImportance object = service.create(CriteriaImportanceServiceTest.object);
		assertEquals(CriteriaImportanceServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenCriteriaImportanceIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenCriteriaImportanceIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		CriteriaImportance object = new CriteriaImportance();
		object.setWeight(2);
		object.setOrderOfImportance(2);
		CriteriaImportance updated = service.update(object, 1L);
		assertEquals(updated.getWeight(),object.getWeight());
		assertEquals(updated.getOrderOfImportance(),object.getOrderOfImportance());
		assertTrue(updated.getIdCriteriaImportance()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenCriteriaImportanceIsNull(){
		service.update(null, 1L);
	}

}
