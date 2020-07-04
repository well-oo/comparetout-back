package com.pepit.compareTout.repository;

import com.pepit.compareTout.entity.Criteria;
import com.pepit.compareTout.entity.ValueCriteriaProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ValueCriteriaProductRepository extends JpaRepository<ValueCriteriaProduct, Long> {
    void deleteByIdValueCriteriaProduct(Long id);
    void deleteByCriteria(Criteria criteria);
    Collection<ValueCriteriaProduct> findAllByCriteria(Criteria criteria);
    List<ValueCriteriaProduct> findDistinctByCriteria(Criteria criteria);

}
