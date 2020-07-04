package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.Criteria;
import com.pepit.compareTout.repository.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CriteriaService {

    @Autowired
    private CriteriaRepository criteriaRepository;

    @Autowired
    private ValueCriteriaProductService valueCriteriaProductService;

    @Autowired
    private ProductTypeService productTypeService;

    public Criteria findById(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<Criteria> criteria = criteriaRepository.findById(id);
        if(criteria.isPresent()){
            return criteria.get();
        } else {
            throw new NoSuchElementException("No criteria with this id");
        }
    }

    public Criteria create(Criteria criteria){
        if(criteria == null){
            throw new IllegalArgumentException("Criteria must not be null");
        }
        return criteriaRepository.save(criteria);
    }

    public Criteria update(Criteria criteriaDetails, Long id){
        if(criteriaDetails == null){
            throw new IllegalArgumentException("Criteria must not be null");
        }
        Criteria criteria = this.findById(id);
        criteria.setName(criteriaDetails.getName());
        criteria.setMesure(criteriaDetails.getMesure());
        criteria.setEstObligatoire(criteriaDetails.isEstObligatoire());
        criteria.setIsCriteriaListDynamical(criteriaDetails.getIsCriteriaListDynamical());
        criteria.setComparisonMethod(criteriaDetails.getComparisonMethod());
        return criteriaRepository.save(criteria);
    }

    public Collection<Criteria> findAll(){
        return criteriaRepository.findAll();
    }

    public void delete(Long id){
        Criteria criteria = this.findById(id);
        productTypeService.removeCriteria(criteria);
        valueCriteriaProductService.deleteByCriteria(criteria);
        criteriaRepository.delete(criteria);
    }

    public Criteria findByName(String name){
        return criteriaRepository.findByName(name);
    }

}
