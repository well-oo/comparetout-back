package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.ComparisonMethod;
import com.pepit.compareTout.repository.ComparisonMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ComparisonMethodService {

    @Autowired
    private ComparisonMethodRepository repository;

    public ComparisonMethod findById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<ComparisonMethod> object = repository.findById(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            throw new NoSuchElementException("No ComparisonMethod with this id");
        }
    }

    public ComparisonMethod update(ComparisonMethod objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("ComparisonMethod must not be null");
        }
        ComparisonMethod object = this.findById(id);
        object.setComparisonType(objectDetails.getComparisonType());
        object.setDataType(objectDetails.getDataType());
        return repository.save(object);
    }

    public ComparisonMethod create(ComparisonMethod object){
        if(object == null){
            throw new IllegalArgumentException("ComparisonMethod must not be null");
        }
        ComparisonMethod finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<ComparisonMethod> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        ComparisonMethod object = this.findById(id);
        repository.delete(object);
    }
}
