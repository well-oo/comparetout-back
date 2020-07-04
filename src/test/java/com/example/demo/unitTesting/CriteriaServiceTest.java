package com.example.demo.unitTesting;
import com.pepit.compareTout.entity.Criteria;
import com.pepit.compareTout.entity.ProductType;
import com.pepit.compareTout.repository.CriteriaRepository;
import com.pepit.compareTout.service.CriteriaService;
import com.pepit.compareTout.service.ProductTypeService;
import com.pepit.compareTout.service.ValueCriteriaProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class CriteriaServiceTest {

    @InjectMocks
    private CriteriaService criteriaService;

    @Mock
    private ProductTypeService productTypeService;

    @Mock
    private CriteriaRepository criteriaRepository;

    @Mock
    private ValueCriteriaProductService valueCriteriaProductService;

    private static Criteria criteria;

    private static Collection<Criteria> criteriaCollection;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        //Init our criteria
        criteria = new Criteria();
        criteria.setIdCriteria(1L);
        criteria.setName("prix");
        //Add a productType
        ProductType pt = new ProductType();

        when(criteriaRepository.findById(1L)).thenReturn(Optional.of(criteria));
        when(criteriaRepository.findById(2L)).thenReturn(Optional.empty());
        //Init an arrayList for testing findAll method
        criteriaCollection = new ArrayList<>();
        criteriaCollection.add(criteria);
        when(criteriaRepository.findAll()).thenReturn((List<Criteria>) criteriaCollection);
        when(criteriaRepository.save(criteria)).thenReturn(criteria);
    }

    @Test
    public void shouldFindCriteriaByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndProductWithThisIdExist(){
        Criteria criteria = criteriaService.findById(1L);
        verify(criteriaRepository).findById(1L);

        assertEquals(CriteriaServiceTest.criteria, criteria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindCriteriaByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
        criteriaService.findById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindCriteriaByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
        criteriaService.findById(-5L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindCriteriaByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
        criteriaService.findById(0L);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldFindCriteriaByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
        criteriaService.findById(2L);
        verify(criteriaRepository).findById(2L);
    }

    @Test
    public void shouldFindAllCriteriaIsOkWhenCriteriaCollectionContainsElements(){
        Collection<Criteria> criteriaCollection = criteriaService.findAll();
        assertEquals(CriteriaServiceTest.criteriaCollection, criteriaCollection);
    }

    @Test
    public void shouldFindAllCriteriaIsOkWhenCriteriaCollectionIsEmpty(){
        CriteriaServiceTest.criteriaCollection.clear();
        Collection<Criteria> empty = new ArrayList<>();
        Collection<Criteria> criteriaCollection = criteriaService.findAll();
        assertEquals(empty, criteriaCollection);
    }

    @Test
    public void shouldCreateIsOkWhenCriteriaIsNotNull(){
        Criteria criteria = criteriaService.create(CriteriaServiceTest.criteria);
        assertEquals(CriteriaServiceTest.criteria, criteria);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldCreateThrowIllegalArgumentExceptionWhenCriteriaIsNull(){
        criteriaService.create(null);
    }

    @Test
    public void shouldDeleteIsOkWhenCriteriaIsReallyDelete(){
        criteriaService.delete(1L);
        verify(criteriaRepository, times(1)).delete(criteria);
    }
}
