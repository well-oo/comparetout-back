package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.ComparisonType;
import com.pepit.compareTout.repository.ComparisonTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ComparisonTypeService {

    @Autowired
    private ComparisonTypeRepository repository;

    public ComparisonType findById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<ComparisonType> object = repository.findById(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            throw new NoSuchElementException("No ComparisonType with this id");
        }
    }

    public ComparisonType update(ComparisonType objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("ComparisonType must not be null");
        }
        ComparisonType object = this.findById(id);
        object.setName(objectDetails.getName());
        return repository.save(object);
    }

    public ComparisonType create(ComparisonType object){
        if(object == null){
            throw new IllegalArgumentException("ComparisonType must not be null");
        }
        ComparisonType finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<ComparisonType> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        ComparisonType object = this.findById(id);
        repository.delete(object);
    }
}
