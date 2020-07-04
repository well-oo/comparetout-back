package com.example.demo.unitTesting;
import com.pepit.compareTout.entity.Product;
import com.pepit.compareTout.entity.ProductType;
import com.pepit.compareTout.repository.ProductRepository;
import com.pepit.compareTout.service.ProductService;
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
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private static Product product;

    private static Collection<Product> products;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        //Init our product
        product = new Product();
        product.setIdProduct(1L);
        //Add a productType
        ProductType pt = new ProductType();
        product.setProductType(pt);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.findById(2L)).thenReturn(Optional.empty());
        //Init an arrayList for testing findAll method
        products = new ArrayList<Product>();
        products.add(product);
        when(productRepository.findAll()).thenReturn((List<Product>) products);
        when(productRepository.save(product)).thenReturn(product);
    }

    @Test
    public void shouldFindProductByIdIsOkWhenIdIsGreaterThanZeroAndNotNullAndProductWithThisIdExist(){
        Product product = productService.findById(1L);
        verify(productRepository).findById(1L);

        assertEquals(ProductServiceTest.product, product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindProductByIdThrowIllegalArgumentExceptionWhenIdIsNull(){
        productService.findById(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindProductByIdThrowIllegalArgumentExceptionWhenIdLessThanZero(){
        productService.findById(-5L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFindProductByIdThrowIllegalArgumentExceptionWhenIdIsZero(){
        productService.findById(0L);
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldFindProductByIdThrowNoSuchElementExceptionWhenNoElementWithThisId(){
        productService.findById(2L);
        verify(productRepository).findById(2L);
    }

    @Test
    public void shouldFindAllProductIsOkWhenProductCollectionContainsElements(){
        Collection<Product> products = productService.findAll();
        assertEquals(ProductServiceTest.products, products);
    }

    @Test
    public void shouldFindAllProductIsOkWhenProductCollectionIsEmpty(){
        ProductServiceTest.products.clear();
        Collection<Product> empty = new ArrayList<Product>();
        Collection<Product> products = productService.findAll();
        assertEquals(empty, products);
    }

    @Test
    public void shouldCreateIsOkWhenProductIsNotNull(){
        Product product = productService.create(ProductServiceTest.product);
        assertEquals(ProductServiceTest.product, product);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldCreateThrowIllegalArgumentExceptionWhenProductIsNull(){
        productService.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldCreateThrowIllegalArgumentExceptionWhenProductHasProductTypeNull(){
        Product product = new Product();
        product.setIdProduct(3L);
        productService.create(product);
    }

    @Test
    public void shouldDeleteIsOkWhenProductIsReallyDelete(){
        productService.delete(1L);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void shouldUpdateIsOk(){
        Product product = new Product();
        product.setIdProduct(6L);
        product.setPicture("img");
        product.setDescription("description");
        product.setProductType(new ProductType());
        Product updated = productService.update(product, 1L);
        assertEquals(updated.getDescription(),product.getDescription());
        assertEquals(updated.getPicture(),product.getPicture());
        assertEquals(updated.getProductType(),product.getProductType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldUpdateThrowIllegalArgumentExceptionWhenProductIsNull(){
        productService.update(null, 1L);
    }

}
