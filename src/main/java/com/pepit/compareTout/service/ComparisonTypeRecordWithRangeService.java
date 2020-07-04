package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.ComparisonTypeRecordWithRange;
import com.pepit.compareTout.repository.ComparisonTypeRecordWithRangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class ComparisonTypeRecordWithRangeService {

    @Autowired
    private ComparisonTypeRecordWithRangeRepository repository;

    public ComparisonTypeRecordWithRange findById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<ComparisonTypeRecordWithRange> object = repository.findById(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            throw new NoSuchElementException("No ComparisonTypeRecordWithRange with this id");
        }
    }

    public ComparisonTypeRecordWithRange update(ComparisonTypeRecordWithRange objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("ComparisonTypeRecordWithRange must not be null");
        }
        ComparisonTypeRecordWithRange object = this.findById(id);
        object.setLeftOperande(objectDetails.getLeftOperande());
        object.setRightOperande(objectDetails.getRightOperande());
        return repository.save(object);
    }

    public ComparisonTypeRecordWithRange create(ComparisonTypeRecordWithRange object){
        if(object == null){
            throw new IllegalArgumentException("ComparisonTypeRecordWithRange must not be null");
        }
        ComparisonTypeRecordWithRange finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<ComparisonTypeRecordWithRange> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        ComparisonTypeRecordWithRange object = this.findById(id);
        repository.delete(object);
    }
}
