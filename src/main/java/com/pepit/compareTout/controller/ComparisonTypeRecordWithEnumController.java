package com.pepit.compareTout.controller;

import com.pepit.compareTout.entity.ComparisonTypeRecordWithEnum;
import com.pepit.compareTout.service.ComparisonTypeRecordWithEnumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/ComparisonTypeRecordsWithEnum")
@Slf4j
public class ComparisonTypeRecordWithEnumController {

    @Autowired
    private ComparisonTypeRecordWithEnumService service;

    @GetMapping
    public ResponseEntity<Collection<ComparisonTypeRecordWithEnum>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComparisonTypeRecordWithEnum> findById(@PathVariable Long id) {
        try {
            ComparisonTypeRecordWithEnum object = service.findById(id);
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
    public ResponseEntity create(@Valid @RequestBody ComparisonTypeRecordWithEnum object) {
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
