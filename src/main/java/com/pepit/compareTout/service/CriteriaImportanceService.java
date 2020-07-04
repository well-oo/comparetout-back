package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.CriteriaImportance;
import com.pepit.compareTout.repository.CriteriaImportanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class CriteriaImportanceService {

    @Autowired
    private CriteriaImportanceRepository repository;

    public CriteriaImportance findById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<CriteriaImportance> object = repository.findById(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            throw new NoSuchElementException("No CriteriaImportance with this id");
        }
    }

    public CriteriaImportance update(CriteriaImportance objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("CriteriaImportance must not be null");
        }
        CriteriaImportance object = this.findById(id);
        object.setOrderOfImportance(objectDetails.getOrderOfImportance());
        object.setWeight(objectDetails.getWeight());
        return repository.save(object);
    }

    public CriteriaImportance create(CriteriaImportance object){
        if(object == null){
            throw new IllegalArgumentException("CriteriaImportance must not be null");
        }
        CriteriaImportance finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<CriteriaImportance> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        CriteriaImportance object = this.findById(id);
        repository.delete(object);
    }
}
