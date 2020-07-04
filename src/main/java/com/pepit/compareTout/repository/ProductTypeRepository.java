package com.pepit.compareTout.repository;

import com.pepit.compareTout.entity.Criteria;
import com.pepit.compareTout.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    Collection<ProductType> findAllByCriteriaListContains(Criteria criteria);

}
