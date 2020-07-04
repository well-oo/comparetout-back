package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.ComparisonTypeRecord;
import com.pepit.compareTout.repository.ComparisonTypeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ComparisonTypeRecordService {

    @Autowired
    private ComparisonTypeRecordRepository repository;

    public ComparisonTypeRecord findById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<ComparisonTypeRecord> object = repository.findById(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            throw new NoSuchElementException("No ComparisonTypeRecord with this id");
        }
    }

    public ComparisonTypeRecord update(ComparisonTypeRecord objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("ComparisonTypeRecord must not be null");
        }
        ComparisonTypeRecord object = this.findById(id);
        return repository.save(object);
    }

    public ComparisonTypeRecord create(ComparisonTypeRecord object){
        if(object == null){
            throw new IllegalArgumentException("ComparisonTypeRecord must not be null");
        }
        ComparisonTypeRecord finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<ComparisonTypeRecord> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        ComparisonTypeRecord object = this.findById(id);
        repository.delete(object);
    }
}
