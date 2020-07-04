package com.example.demo;

import com.pepit.compareTout.entity.*;
import com.pepit.compareTout.repository.SubscriptionRepository;
import com.pepit.compareTout.service.SubscriptionService;
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
public class SubscriptionServiceTest {

	@InjectMocks
	private SubscriptionService service;

	@Mock
	private SubscriptionRepository repository;

	private static Subscription object;

	private static Collection<Subscription> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our Subscription
		object = new Subscription();
		object.setSearch(new Search(Instant.parse("2019-10-23T10:12:35Z"), new ArrayList<SearchCriteria>()));
		object.setEmail(new Email("Objet du mail ",  "emailFrom@from.com", "emailTo@to.com", Instant.now(),new EmailType("Abonnement")));
		object.setIdSubscription(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<Subscription>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<Subscription>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindSubscriptionByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndSubscriptionWithThisIdExist(){
		Subscription object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(SubscriptionServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindSubscriptionByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindSubscriptionByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindSubscriptionByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindSubscriptionByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllSubscriptionIsOkWhenSubscriptionCollectionContainsElements(){
		Collection<Subscription> objects = service.findAll();
		assertEquals(SubscriptionServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllSubscriptionIsOkWhenSubscriptionCollectionIsEmpty(){
		SubscriptionServiceTest.objects.clear();
		Collection<Subscription> empty = new ArrayList<Subscription>();
		Collection<Subscription> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenSubscriptionIsNotNull(){
		Subscription object = service.create(SubscriptionServiceTest.object);
		assertEquals(SubscriptionServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenSubscriptionIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenSubscriptionIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		Subscription object = new Subscription();
		object.setSearch(new Search(Instant.parse("2019-10-23T10:12:35Z"), new ArrayList<SearchCriteria>()));
		object.setEmail(new Email("Objet du mail ",  "emailFrom@from.com", "emailTo@to.com", Instant.now(),new EmailType("Abonnement")));
		Subscription updated = service.update(object, 1L);
		assertEquals(updated.getSearch().getDate(),object.getSearch().getDate());
		assertTrue(updated.getIdSubscription()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenSubscriptionIsNull(){
		service.update(null, 1L);
	}

}
