package com.pepit.compareTout.repository;

import com.pepit.compareTout.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Collection<Product> findByProductType(ProductType productType);
    Collection<Product> findByUser(User user);
    Collection<Product> findByUserAndProductType(User user, ProductType productType);
    Collection<Product> findAllByValueCriteriaProductListContains(ValueCriteriaProduct valueCriteriaProduct);

    Collection<Product> findByProductTypeAndValueCriteriaProductList_CriteriaAndValueCriteriaProductList_Value(ProductType productType, Criteria criteria, String value);
    Collection<Product> findByProductTypeAndValueCriteriaProductList_CriteriaAndValueCriteriaProductList_ValueBetween(ProductType productType, Criteria criteria, String valueApprovedFrom, String valueApprovedTo);

    List<Product> findAll();
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByProductType(ProductType productType, Pageable pageable);
}
