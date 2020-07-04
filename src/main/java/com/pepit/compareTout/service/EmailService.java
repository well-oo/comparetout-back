package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.Email;
import com.pepit.compareTout.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private EmailRepository repository;

    public Email findById(Long id) {
        if(id == null || id <= 0) {
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<Email> object = repository.findById(id);
        if(object.isPresent()) {
            return object.get();
        } else {
            throw new NoSuchElementException("No Email with this id");
        }
    }

    public Email update(Email objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("Email must not be null");
        }
        Email object = this.findById(id);
        object.setEmailType(objectDetails.getEmailType());
        object.setFromWho(objectDetails.getFromWho());
        object.setObject(objectDetails.getObject());
        object.setSendDate(objectDetails.getSendDate());
        object.setToWho(objectDetails.getToWho());
        return repository.save(object);
    }

    public Email create(Email object){
        if(object == null){
            throw new IllegalArgumentException("Email must not be null");
        }
        Email finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<Email> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        Email object = this.findById(id);
        repository.delete(object);
    }
}
