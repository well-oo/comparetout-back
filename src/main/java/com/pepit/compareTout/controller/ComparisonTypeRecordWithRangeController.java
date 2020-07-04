package com.pepit.compareTout.controller;

import com.pepit.compareTout.entity.ComparisonTypeRecordWithRange;
import com.pepit.compareTout.service.ComparisonTypeRecordWithRangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/ComparisonTypeRecordsWithRange")
@Slf4j
public class ComparisonTypeRecordWithRangeController {

    @Autowired
    private ComparisonTypeRecordWithRangeService service;

    @GetMapping
    public ResponseEntity<Collection<ComparisonTypeRecordWithRange>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComparisonTypeRecordWithRange> findById(@PathVariable Long id) {
        try {
            ComparisonTypeRecordWithRange object = service.findById(id);
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
    public ResponseEntity create(@Valid @RequestBody ComparisonTypeRecordWithRange object) {
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
