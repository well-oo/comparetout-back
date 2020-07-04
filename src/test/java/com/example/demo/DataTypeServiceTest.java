package com.example.demo;

import com.pepit.compareTout.entity.DataType;
import com.pepit.compareTout.repository.DataTypeRepository;
import com.pepit.compareTout.service.DataTypeService;
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
public class DataTypeServiceTest {

	@InjectMocks
	private DataTypeService service;

	@Mock
	private DataTypeRepository repository;

	private static DataType object;

	private static Collection<DataType> objects;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		//Init our DataType
		object = new DataType();
		object.setName("datatype");
		object.setIdDataType(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(object));
		when(repository.findById(2L)).thenReturn(Optional.empty());
		//Init an arrayList for testing findAll method
		objects = new ArrayList<DataType>();
		objects.add(object);
		when(repository.findAll()).thenReturn((List<DataType>) objects);
		when(repository.save(object)).thenReturn(object);
	}

	@Test
	public void shouldFindDataTypeByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndDataTypeWithThisIdExist(){
		DataType object = service.findById(1L);
		verify(repository).findById(1L);
		assertEquals(DataTypeServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindDataTypeByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
		service.findById(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindDataTypeByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
		service.findById(-5L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFindDataTypeByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
		service.findById(0L);
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldFindDataTypeByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
		service.findById(2L);
		verify(repository).findById(2L);
	}

	@Test
	public void shouldFindAllDataTypeIsOkWhenDataTypeCollectionContainsElements(){
		Collection<DataType> objects = service.findAll();
		assertEquals(DataTypeServiceTest.objects, objects);
	}

	@Test
	public void shouldFindAllDataTypeIsOkWhenDataTypeCollectionIsEmpty(){
		DataTypeServiceTest.objects.clear();
		Collection<DataType> empty = new ArrayList<DataType>();
		Collection<DataType> objects = service.findAll();
		assertEquals(empty, objects);
	}

	@Test
	public void shouldCreateIsOkWhenDataTypeIsNotNull(){
		DataType object = service.create(DataTypeServiceTest.object);
		assertEquals(DataTypeServiceTest.object, object);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldCreateThrowIllegalArgumentExceptionWhenDataTypeIsNull(){
		service.create(null);
	}

	@Test
	public void shouldDeleteIsOkWhenDataTypeIsReallyDelete(){
		service.delete(1L);
		verify(repository, times(1)).delete(object);
	}

	@Test
	public void shouldUpdateIsOk(){
		DataType object = new DataType();
		object.setName("toto");
		DataType updated = service.update(object, 1L);
		assertEquals(updated.getName(),object.getName());
		assertTrue(updated.getIdDataType()==1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldUpdateThrowIllegalArgumentExceptionWhenDataTypeIsNull(){
		service.update(null, 1L);
	}

}
