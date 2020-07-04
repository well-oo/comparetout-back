package com.pepit.compareTout.controller;

import com.pepit.compareTout.entity.Criteria;
import com.pepit.compareTout.service.CriteriaService;
import com.pepit.compareTout.service.ValueCriteriaProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/valueCriteriaProduct")
@Slf4j
public class ValueCriteriaProductController {
    @Autowired
    CriteriaService criteriaService;

    @Autowired
    ValueCriteriaProductService valueCriteriaProductService;

    @GetMapping("/{idCriteria}")
    public ResponseEntity<List<String>> getDistinctValueEnumStringFromCriteria(@PathVariable Long idCriteria){
        try{
            Criteria criteria = criteriaService.findById(idCriteria);

            return ResponseEntity.ok(valueCriteriaProductService.findDistinctValueByCriteria(criteria));
        }
        catch(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
        catch(NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
