package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.ComparisonTypeRecordWithEnum;
import com.pepit.compareTout.repository.ComparisonTypeRecordWithEnumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ComparisonTypeRecordWithEnumService {

    @Autowired
    private ComparisonTypeRecordWithEnumRepository repository;

    public ComparisonTypeRecordWithEnum findById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<ComparisonTypeRecordWithEnum> object = repository.findById(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            throw new NoSuchElementException("No ComparisonTypeRecordWithEnum with this id");
        }
    }

    public ComparisonTypeRecordWithEnum update(ComparisonTypeRecordWithEnum objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("ComparisonTypeRecordWithEnum must not be null");
        }
        ComparisonTypeRecordWithEnum object = this.findById(id);
        return repository.save(object);
    }

    public ComparisonTypeRecordWithEnum create(ComparisonTypeRecordWithEnum object){
        if(object == null){
            throw new IllegalArgumentException("ComparisonTypeRecordWithEnum must not be null");
        }
        ComparisonTypeRecordWithEnum finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<ComparisonTypeRecordWithEnum> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        ComparisonTypeRecordWithEnum object = this.findById(id);
        repository.delete(object);
    }
}
