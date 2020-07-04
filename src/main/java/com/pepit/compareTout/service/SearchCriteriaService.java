package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.SearchCriteria;
import com.pepit.compareTout.repository.SearchCriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SearchCriteriaService {

    @Autowired
    private SearchCriteriaRepository repository;

    public SearchCriteria findById(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("incorrect value of id");
        }
        Optional<SearchCriteria> object = repository.findById(id);
        if(object.isPresent()){
            return object.get();
        } else {
            throw new NoSuchElementException("No SearchCriteria with this id");
        }
    }

    public SearchCriteria update(SearchCriteria objectDetails, Long id){
        if(objectDetails == null){
            throw new IllegalArgumentException("SearchCriteria must not be null");
        }
        SearchCriteria object = this.findById(id);
        object.setCriteria(objectDetails.getCriteria());
        object.setCriteriaImportance(objectDetails.getCriteriaImportance());
        return repository.save(object);
    }

    public SearchCriteria create(SearchCriteria object){
        if(object == null){
            throw new IllegalArgumentException("SearchCriteria must not be null");
        }
        SearchCriteria finalObject = repository.save(object);
        return finalObject;
    }

    public Collection<SearchCriteria> findAll(){
        return repository.findAll();
    }

    public void delete(Long id) {
        SearchCriteria object = this.findById(id);
        repository.delete(object);
    }
}
