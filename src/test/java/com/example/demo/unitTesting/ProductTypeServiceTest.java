package com.example.demo.unitTesting;
import com.pepit.compareTout.entity.ProductType;
import com.pepit.compareTout.repository.ProductTypeRepository;
import com.pepit.compareTout.service.ProductTypeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class ProductTypeServiceTest {

    @InjectMocks
    private ProductTypeService productTypeService;

    @Mock
    private ProductTypeRepository productTypeRepository;

    private static ProductType productType;

    private static Collection<ProductType> productTypes;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        //Init our productType
        productType = new ProductType();
        productType.setIdProductType(1L);
        productType.setName("Voiture");

        when(productTypeRepository.findById(1L)).thenReturn(Optional.of(productType));
        when(productTypeRepository.findById(2L)).thenReturn(Optional.empty());
        //Init an arrayList for testing findAll method
        productTypes = new ArrayList<>();
        productTypes.add(productType);
        when(productTypeRepository.findAll()).thenReturn((List<ProductType>) productTypes);
        when(productTypeRepository.save(productType)).thenReturn(productType);
    }

    @Test
    public void shouldFindProductTypeByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndProductTypeWithThisIdExist(){
        ProductType productType = productTypeService.findById(1L);
        verify(productTypeRepository).findById(1L);

        assertEquals(ProductTypeServiceTest.productType, productType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindProductTypeByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
        productTypeService.findById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindProductTypeByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
        productTypeService.findById(-5L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindProductTypeByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
        productTypeService.findById(0L);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldFindProductTypeByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
        productTypeService.findById(2L);
        verify(productTypeRepository).findById(2L);
    }

    @Test
    public void shouldFindAllProductTypeIsOkWhenProductTypeCollectionContainsElements(){
        Collection<ProductType> productTypes = productTypeService.findAll();
        assertEquals(ProductTypeServiceTest.productTypes, productTypes);
    }

    @Test
    public void shouldFindAllProductTypeIsOkWhenProductTypeCollectionIsEmpty(){
        ProductTypeServiceTest.productTypes.clear();
        Collection<ProductType> empty = new ArrayList<>();
        Collection<ProductType> productTypes = productTypeService.findAll();
        assertEquals(empty, productTypes);
    }

    @Test
    public void shouldCreateIsOkWhenProductTypeIsNotNull(){
        ProductType productType = productTypeService.create(ProductTypeServiceTest.productType);
        assertEquals(ProductTypeServiceTest.productType, productType);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldCreateThrowIllegalArgumentExceptionWhenProductTypeIsNull(){
        productTypeService.create(null);
    }


    @Test
    public void shouldDeleteIsOkWhenProductExist(){
        productTypeService.delete(1L);
        verify(productTypeRepository, times(1)).delete(productType);
    }
}
