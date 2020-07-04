package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.Criteria;
import com.pepit.compareTout.entity.ValueCriteriaProduct;
import com.pepit.compareTout.repository.ValueCriteriaProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ValueCriteriaProductService {

    @Autowired
    private ValueCriteriaProductRepository repository;

    @Autowired
    private ProductService productService;

    @Transactional
    public void delete(Long id) {
        repository.deleteByIdValueCriteriaProduct(id);
    }

    @Transactional
    public void deleteByCriteria(Criteria criteria) {
        productService.removeCriteriaProductListByCriteria(criteria);
        repository.deleteByCriteria(criteria);
    }

    public Collection<ValueCriteriaProduct> findAllByCriteria(Criteria criteria){
        return repository.findAllByCriteria(criteria);
    }

    public List<String> findDistinctValueByCriteria(Criteria criteria){
        List<ValueCriteriaProduct> valueCriteriaProducts = repository.findDistinctByCriteria(criteria);
        return repository.findDistinctByCriteria(criteria).stream().map(ValueCriteriaProduct::getValue).distinct().collect(Collectors.toList());
    }

}
