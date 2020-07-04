package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.Subscription;
import com.pepit.compareTout.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository repository;

    public Subscription findById(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<Subscription> object = repository.findById(id);
        if(object.isPresent()){
            return object.get();
        } else {
            throw new NoSuchElementException("No Subscription with this id");
        }
    }

    public Subscription update(Subscription objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("Subscription must not be null");
        }
        Subscription object = this.findById(id);
        return repository.save(object);
    }

    public Subscription create(Subscription object) {
        if(object == null){
            throw new IllegalArgumentException("Subscription must not be null");
        }
        object.setEmail(object.getEmail());
        object.setSearch(object.getSearch());
        Subscription finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<Subscription> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        Subscription object = this.findById(id);
        repository.delete(object);
    }
}
