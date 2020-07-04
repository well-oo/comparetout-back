package com.pepit.compareTout.controller;

import com.pepit.compareTout.entity.SearchCriteria;
import com.pepit.compareTout.service.SearchCriteriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/searchCriterias")
@Slf4j
public class SearchCriteriaController {

    @Autowired
    private SearchCriteriaService service;

    @GetMapping
    public ResponseEntity<Collection<SearchCriteria>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SearchCriteria> findById(@PathVariable Long id) {
        try {
            SearchCriteria object = service.findById(id);
            return ResponseEntity.ok(object);
        }
        catch(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        catch(NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody SearchCriteria object) {
        try {
            return ResponseEntity.ok(service.create(object));
        }
        catch(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().build();
        }
        catch(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
