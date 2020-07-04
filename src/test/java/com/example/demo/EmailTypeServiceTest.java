package com.example.demo;

import com.pepit.compareTout.entity.EmailType;
import com.pepit.compareTout.repository.EmailTypeRepository;
import com.pepit.compareTout.service.EmailTypeService;
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
public class EmailTypeServiceTest {

	@InjectMocks
	private EmailTypeService service;

	@Mock
	private EmailTypeRepository repository;

	private static EmailType object;

	private static Collection<EmailType> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our EmailType
		object = new EmailType();
		object.setName("toto l'email type");
		object.setIdEmailType(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<EmailType>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<EmailType>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindEmailTypeByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndEmailTypeWithThisIdExist(){
		EmailType object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(EmailTypeServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindEmailTypeByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindEmailTypeByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindEmailTypeByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindEmailTypeByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllEmailTypeIsOkWhenEmailTypeCollectionContainsElements(){
		Collection<EmailType> objects = service.findAll();
		assertEquals(EmailTypeServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllEmailTypeIsOkWhenEmailTypeCollectionIsEmpty(){
		EmailTypeServiceTest.objects.clear();
		Collection<EmailType> empty = new ArrayList<EmailType>();
		Collection<EmailType> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenEmailTypeIsNotNull(){
		EmailType object = service.create(EmailTypeServiceTest.object);
		assertEquals(EmailTypeServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenEmailTypeIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenEmailTypeIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		EmailType object = new EmailType();
		object.setName("toto l'email type");
		EmailType updated = service.update(object, 1L);
		assertEquals(updated.getName(),object.getName());
		assertTrue(updated.getIdEmailType()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenEmailTypeIsNull(){
		service.update(null, 1L);
	}

}
