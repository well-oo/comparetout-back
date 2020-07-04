package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.ComparisonTypeRecordWithEnumValue;
import com.pepit.compareTout.repository.ComparisonTypeRecordWithEnumValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ComparisonTypeRecordWithEnumValueService {

    @Autowired
    private ComparisonTypeRecordWithEnumValueRepository repository;

    public ComparisonTypeRecordWithEnumValue findById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<ComparisonTypeRecordWithEnumValue> object = repository.findById(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            throw new NoSuchElementException("No ComparisonTypeRecordWithEnumValue with this id");
        }
    }

    public ComparisonTypeRecordWithEnumValue update(ComparisonTypeRecordWithEnumValue objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("ComparisonTypeRecordWithEnumValue must not be null");
        }
        ComparisonTypeRecordWithEnumValue object = this.findById(id);
        object.setValue(objectDetails.getValue());
        return repository.save(object);
    }

    public ComparisonTypeRecordWithEnumValue create(ComparisonTypeRecordWithEnumValue object){
        if(object == null){
            throw new IllegalArgumentException("ComparisonTypeRecordWithEnumValue must not be null");
        }
        ComparisonTypeRecordWithEnumValue finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<ComparisonTypeRecordWithEnumValue> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        ComparisonTypeRecordWithEnumValue object = this.findById(id);
        repository.delete(object);
    }
}
