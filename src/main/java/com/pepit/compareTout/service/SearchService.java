package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.Search;
import com.pepit.compareTout.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class SearchService {

    @Autowired
    private SearchRepository repository;

    public Search findById(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<Search> object = repository.findById(id);
        if(object.isPresent()){
            return object.get();
        } else {
            throw new NoSuchElementException("No Search with this id");
        }
    }

    public Search update(Search objectDetails, Long id) {
        if(objectDetails == null){
            throw new IllegalArgumentException("Search must not be null");
        }
        Search object = this.findById(id);
        object.setDate(objectDetails.getDate());
        return repository.save(object);
    }

    public Search create(Search object){
        if(object == null){
            throw new IllegalArgumentException("Search must not be null");
        }
        object.setDate(Instant.now());
        Search finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<Search> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        Search object = this.findById(id);
        repository.delete(object);
    }

}
