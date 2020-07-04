package com.example.demo;

import com.pepit.compareTout.entity.Criteria;
import com.pepit.compareTout.entity.CriteriaImportance;
import com.pepit.compareTout.entity.Search;
import com.pepit.compareTout.entity.SearchCriteria;
import com.pepit.compareTout.repository.SearchCriteriaRepository;
import com.pepit.compareTout.service.SearchCriteriaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class SearchCriteriaServiceTest {

	@InjectMocks
	private SearchCriteriaService service;

	@Mock
	private SearchCriteriaRepository repository;

	private static SearchCriteria object;

	private static Collection<SearchCriteria> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our SearchCriteria
		object = new SearchCriteria();
		object.setCriteriaImportance(new CriteriaImportance());
		object.setCriteria(new Criteria());
		object.setIdSearchCriteria(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<SearchCriteria>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<SearchCriteria>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindSearchCriteriaByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndSearchCriteriaWithThisIdExist(){
		SearchCriteria object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(SearchCriteriaServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindSearchCriteriaByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindSearchCriteriaByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindSearchCriteriaByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindSearchCriteriaByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllSearchCriteriaIsOkWhenSearchCriteriaCollectionContainsElements(){
		Collection<SearchCriteria> objects = service.findAll();
		assertEquals(SearchCriteriaServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllSearchCriteriaIsOkWhenSearchCriteriaCollectionIsEmpty(){
		SearchCriteriaServiceTest.objects.clear();
		Collection<SearchCriteria> empty = new ArrayList<SearchCriteria>();
		Collection<SearchCriteria> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenSearchCriteriaIsNotNull(){
		SearchCriteria object = service.create(SearchCriteriaServiceTest.object);
		assertEquals(SearchCriteriaServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenSearchCriteriaIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenSearchCriteriaIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		SearchCriteria object = new SearchCriteria();
		object.setCriteriaImportance(new CriteriaImportance());
		object.setCriteria(new Criteria());
		object.setIdSearchCriteria(6L);
		SearchCriteria updated = service.update(object, 1L);
		assertEquals(updated.getCriteriaImportance(),object.getCriteriaImportance());
		assertEquals(updated.getCriteria(),object.getCriteria());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenSearchCriteriaIsNull(){
		service.update(null, 1L);
	}

}
