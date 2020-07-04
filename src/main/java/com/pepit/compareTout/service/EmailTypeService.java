package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.EmailType;
import com.pepit.compareTout.repository.EmailTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmailTypeService {

    @Autowired
    private EmailTypeRepository repository;

    public EmailType findById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<EmailType> object = repository.findById(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            throw new NoSuchElementException("No EmailType with this id");
        }
    }

    public EmailType update(EmailType objectDetails, Long id) {
        if(objectDetails == null) {
            throw new IllegalArgumentException("EmailType must not be null");
        }
        EmailType object = this.findById(id);
        object.setName(objectDetails.getName());
        return repository.save(object);
    }

    public EmailType create(EmailType object) {
        if(object == null){
            throw new IllegalArgumentException("EmailType must not be null");
        }
        EmailType finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<EmailType> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        EmailType object = this.findById(id);
        repository.delete(object);
    }
}
