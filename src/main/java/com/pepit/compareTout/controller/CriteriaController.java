package com.pepit.compareTout.controller;

import com.pepit.compareTout.entity.Criteria;
import com.pepit.compareTout.service.CriteriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/criteria")
@Slf4j
public class CriteriaController {

    @Autowired
    private CriteriaService criteriaService;

    @GetMapping("/")
    public ResponseEntity<Collection<Criteria>> findAll(){
        return ResponseEntity.ok(criteriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Criteria> findById(@PathVariable Long id){
        try {
            Criteria criteria = criteriaService.findById(id);
            return ResponseEntity.ok(criteria);
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Criteria> create(@Valid @RequestBody Criteria criteria){
        try {
            return ResponseEntity.ok(criteriaService.create(criteria));
        }catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Criteria> update(@Valid @RequestBody Criteria criteria, @PathVariable Long id){
        try {
            return ResponseEntity.ok(criteriaService.update(criteria, id));
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            criteriaService.delete(id);
            return ResponseEntity.ok().build();
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        }
    }

}
