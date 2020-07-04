package com.example.demo;

import com.pepit.compareTout.entity.Email;
import com.pepit.compareTout.entity.EmailType;
import com.pepit.compareTout.repository.EmailRepository;
import com.pepit.compareTout.service.EmailService;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

	@InjectMocks
	private EmailService service;

	@Mock
	private EmailRepository repository;

	private static Email object;

	private static Collection<Email> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our Email
		object = new Email();
		object.setToWho("toto@who.com");
		object.setSendDate(Instant.now());
		object.setObject("object");
		object.setFromWho("from@who.com");
		object.setEmailType(new EmailType());
		object.setIdEmail(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<Email>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<Email>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindEmailByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndEmailWithThisIdExist(){
		Email object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(EmailServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindEmailByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindEmailByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindEmailByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindEmailByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllEmailIsOkWhenEmailCollectionContainsElements(){
		Collection<Email> objects = service.findAll();
		assertEquals(EmailServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllEmailIsOkWhenEmailCollectionIsEmpty(){
		EmailServiceTest.objects.clear();
		Collection<Email> empty = new ArrayList<Email>();
		Collection<Email> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenEmailIsNotNull(){
		Email object = service.create(EmailServiceTest.object);
		assertEquals(EmailServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenEmailIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenEmailIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		Email object = new Email();
		object.setToWho("toto2@who.com");
		object.setSendDate(Instant.now());
		object.setObject("object2");
		object.setFromWho("from@who.com");
		object.setEmailType(new EmailType());
		Email updated = service.update(object, 1L);
		assertEquals(updated.getEmailType(),object.getEmailType());
		assertEquals(updated.getFromWho(),object.getFromWho());
		assertEquals(updated.getObject(),object.getObject());
		assertEquals(updated.getSendDate(),object.getSendDate());
		assertEquals(updated.getToWho(),object.getToWho());
		assertTrue(updated.getIdEmail()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenEmailIsNull(){
		service.update(null, 1L);
	}

}
