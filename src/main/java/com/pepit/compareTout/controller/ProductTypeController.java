package com.pepit.compareTout.controller;

import com.pepit.compareTout.entity.ProductType;
import com.pepit.compareTout.service.ProductTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@RequestMapping("/api/productTypes")
@Slf4j
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping("/")
    public ResponseEntity<Collection<ProductType>> findAll(){
        return ResponseEntity.ok(productTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductType> findById(@PathVariable Long id) {
        try {
            ProductType productType = productTypeService.findById(id);
            return ResponseEntity.ok(productType);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ProductType> create(@Valid @RequestBody ProductType productType){
        try {
            return ResponseEntity.ok(productTypeService.create(productType));
        }catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            productTypeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductType> update(@Valid @RequestBody ProductType productType, @PathVariable Long id){
        try {
            return ResponseEntity.ok(productTypeService.update(productType, id));
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        }
    }

}
