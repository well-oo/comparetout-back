package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.DataType;
import com.pepit.compareTout.repository.DataTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class DataTypeService {

    @Autowired
    private DataTypeRepository repository;

    public DataType findById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<DataType> object = repository.findById(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            throw new NoSuchElementException("No DataType with this id");
        }
    }

    public DataType update(DataType objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("DataType must not be null");
        }
        DataType object = this.findById(id);
        object.setName(objectDetails.getName());
        return repository.save(object);
    }

    public DataType create(DataType object){
        if(object == null){
            throw new IllegalArgumentException("DataType must not be null");
        }
        DataType finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<DataType> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        DataType object = this.findById(id);
        repository.delete(object);
    }
}
