package com.example.demo;

import com.pepit.compareTout.entity.Search;
import com.pepit.compareTout.repository.SearchRepository;
import com.pepit.compareTout.service.SearchService;
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
public class SearchServiceTest {

	@InjectMocks
	private SearchService service;

	@Mock
	private SearchRepository repository;

	private static Search object;

	private static Collection<Search> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our Search
		object = new Search();
		object.setDate(Instant.now());
		object.setIdSearch(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<Search>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<Search>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindSearchByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndSearchWithThisIdExist(){
		Search object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(SearchServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindSearchByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindSearchByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindSearchByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindSearchByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllSearchIsOkWhenSearchCollectionContainsElements(){
		Collection<Search> objects = service.findAll();
		assertEquals(SearchServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllSearchIsOkWhenSearchCollectionIsEmpty(){
		SearchServiceTest.objects.clear();
		Collection<Search> empty = new ArrayList<Search>();
		Collection<Search> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenSearchIsNotNull(){
		Search object = service.create(SearchServiceTest.object);
		assertEquals(SearchServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenSearchIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenSearchIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		Search object = new Search();
		object.setIdSearch(6L);
		object.setDate(Instant.now());
		Search updated = service.update(object, 1L);
		assertEquals(updated.getDate(),object.getDate());
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenSearchIsNull(){
		service.update(null, 1L);
	}

}
