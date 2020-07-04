package com.pepit.compareTout.service;

import com.pepit.compareTout.ProductSpecification;
import com.pepit.compareTout.entity.*;
import com.pepit.compareTout.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

@Transactional
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private CriteriaService criteriaService;

    @Autowired
    private ValueCriteriaProductService valueCriteriaProductService;

    @Autowired
    private UserService userService;

    public Product findById(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        } else {
            throw new NoSuchElementException("No product with this id");
        }
    }

    public Product update(Product productDetails, Long id){
        if(productDetails == null){
            throw new IllegalArgumentException("Product must not be null");
        }
        Product product = this.findById(id);
        product.setDescription(productDetails.getDescription());
        product.setProductType(productDetails.getProductType());
        product.setPicture(productDetails.getPicture());
        return productRepository.save(product);
    }

    public Product create(Product product){
        if(product == null){
            throw new IllegalArgumentException("Product must not be null");
        }
        if(product.getProductType() == null){
            throw new IllegalArgumentException("ProductType must not be null");
        }
        product.setAddProduct(Instant.now());
        return productRepository.save(product);
    }

    public Collection<Product> findAll(){
        return productRepository.findAll();
    }

    public Collection<Product> findAllByUserAndProductType(String username, Long idProductType){
        User user = userService.findByName(username);
        ProductType productType = productTypeService.findById(idProductType);

        return productRepository.findByUserAndProductType(user, productType);
    }

    public void delete(Long id) {
        productRepository.delete(this.findById(id));
    }

    public Collection<Product> findByProductType(Long idProductType){
        if(idProductType <=0) throw new IllegalArgumentException("ProductType cant be null");

        ProductType productType = productTypeService.findById(idProductType);

        return productRepository.findByProductType(productType);
    }

    public Collection<Product> findByUser(User user){
        if(user == null) throw new IllegalArgumentException("User cant be null");
        return productRepository.findByUser(user);
    }

    public void removeCriteriaProductListByCriteria(Criteria criteria) {
        Collection<ValueCriteriaProduct> valueCriteriaProducts = valueCriteriaProductService.findAllByCriteria(criteria);
        for (ValueCriteriaProduct valueCriteriaProduct : valueCriteriaProducts) {
            Collection<Product> products = productRepository.findAllByValueCriteriaProductListContains(valueCriteriaProduct);
            for (Product product : products) {
                Collection<ValueCriteriaProduct> valueCriteriaProducts1 = product.getValueCriteriaProductList();
                valueCriteriaProducts1.removeIf(valueCriteriaProduct1 -> valueCriteriaProduct1.getCriteria().equals(criteria));
                product.setValueCriteriaProductList((List<ValueCriteriaProduct>) valueCriteriaProducts1);
                productRepository.save(product);
            }
        }
    }

    public Page<Product> findProducts(Long idProductType, String search, Pageable pageable, String name) {
        if (idProductType <= 0) throw new IllegalArgumentException("ProductType cant be null");
        ProductType productType = productTypeService.findById(idProductType);

        return productRepository.findAll( new ProductSpecification(search, productType, criteriaService, name), pageable );
    }

    public Page<Product> findAllPagination(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage;
    }

    public Page<Product> findByProductTypePagination(Long idProductType, Pageable pageable) {
        if(idProductType <=0) throw new IllegalArgumentException("ProductType cant be null");

        ProductType productType = productTypeService.findById(idProductType);

        return productRepository.findByProductType(productType, pageable);
    }
}
