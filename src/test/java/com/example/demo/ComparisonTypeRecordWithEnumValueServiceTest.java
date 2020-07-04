package com.example.demo;

import com.pepit.compareTout.entity.ComparisonTypeRecordWithEnum;
import com.pepit.compareTout.entity.ComparisonTypeRecordWithEnumValue;
import com.pepit.compareTout.repository.ComparisonTypeRecordWithEnumValueRepository;
import com.pepit.compareTout.service.ComparisonTypeRecordWithEnumValueService;
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
public class ComparisonTypeRecordWithEnumValueServiceTest {

	@InjectMocks
	private ComparisonTypeRecordWithEnumValueService service;

	@Mock
	private ComparisonTypeRecordWithEnumValueRepository repository;

	private static ComparisonTypeRecordWithEnumValue object;

	private static Collection<ComparisonTypeRecordWithEnumValue> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our ComparisonTypeRecordWithEnumValue
		object = new ComparisonTypeRecordWithEnumValue("titi");
		object.setValue("toto");
		object.setIdComparisonTypeRecordWithEnumValue(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<ComparisonTypeRecordWithEnumValue>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<ComparisonTypeRecordWithEnumValue>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindComparisonTypeRecordWithEnumValueByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndComparisonTypeRecordWithEnumValueWithThisIdExist(){
		ComparisonTypeRecordWithEnumValue object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(ComparisonTypeRecordWithEnumValueServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordWithEnumValueByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordWithEnumValueByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindComparisonTypeRecordWithEnumValueByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindComparisonTypeRecordWithEnumValueByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllComparisonTypeRecordWithEnumValueIsOkWhenComparisonTypeRecordWithEnumValueCollectionContainsElements(){
		Collection<ComparisonTypeRecordWithEnumValue> objects = service.findAll();
		assertEquals(ComparisonTypeRecordWithEnumValueServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllComparisonTypeRecordWithEnumValueIsOkWhenComparisonTypeRecordWithEnumValueCollectionIsEmpty(){
		ComparisonTypeRecordWithEnumValueServiceTest.objects.clear();
		Collection<ComparisonTypeRecordWithEnumValue> empty = new ArrayList<ComparisonTypeRecordWithEnumValue>();
		Collection<ComparisonTypeRecordWithEnumValue> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenComparisonTypeRecordWithEnumValueIsNotNull(){
		ComparisonTypeRecordWithEnumValue object = service.create(ComparisonTypeRecordWithEnumValueServiceTest.object);
		assertEquals(ComparisonTypeRecordWithEnumValueServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenComparisonTypeRecordWithEnumValueIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenComparisonTypeRecordWithEnumValueIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		ComparisonTypeRecordWithEnumValue object = new ComparisonTypeRecordWithEnumValue("toto");
		object.setValue("toto2");
		ComparisonTypeRecordWithEnumValue updated = service.update(object, 1L);
		assertEquals(updated.getValue(),object.getValue());
		assertTrue(updated.getIdComparisonTypeRecordWithEnumValue()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenComparisonTypeRecordWithEnumValueIsNull(){
		service.update(null, 1L);
	}

}
