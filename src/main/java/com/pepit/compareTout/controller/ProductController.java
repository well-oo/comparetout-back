package com.pepit.compareTout.controller;

import com.pepit.compareTout.entity.Product;
import com.pepit.compareTout.service.ProductService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@RequestMapping("api/products")
@Slf4j
@Api(tags = "products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @Transactional
    @GetMapping("/")
    public ResponseEntity<Collection<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @ApiOperation(value = "Get all products with pagination")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping("/findAllPagination")
    public ResponseEntity findAllPagination(Pageable pageable) {
        return ResponseEntity.ok(productService.findAllPagination(pageable));
    }

    @ApiOperation(value = "Get products by product type")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Invalid product or uid"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @Transactional
    @GetMapping("/productTypePagination/{idProductType}")
    public ResponseEntity findByProductTypePagination(@PathVariable Long idProductType, Pageable pageable){
        try{
            return ResponseEntity.ok(productService.findByProductTypePagination(idProductType, pageable));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(value = "Get products by user", authorizations = { @Authorization(value="apiKey")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPPLIER')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Invalid product or uid"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @Transactional
    @GetMapping("/user/productType/{idProductType}")
    public ResponseEntity<Collection<Product>> findAllByUser(HttpServletRequest req, @PathVariable Long idProductType){
        return ResponseEntity.ok(productService.findAllByUserAndProductType(req.getRemoteUser(), idProductType));
    }

    @ApiOperation(value = "Get a product")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(product);
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Update a product", authorizations = { @Authorization(value="apiKey")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPPLIER')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Invalid product or uid"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@Valid @RequestBody Product product, @PathVariable Long id){
        try {
            return ResponseEntity.ok(productService.update(product, id));
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Create a product", authorizations = { @Authorization(value="apiKey")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPPLIER')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Invalid product"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody Product product){
        try {
            return ResponseEntity.ok(productService.create(product));
        }catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(value = "Delete a product", authorizations = { @Authorization(value="apiKey") })
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPPLIER')")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "Invalid product or uid"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        try {
            productService.delete(id);
            return ResponseEntity.ok().build();
        }
        catch(IllegalArgumentException ex){
            return ResponseEntity.badRequest().build();
        }
        catch (NoSuchElementException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/productType/{idProductType}")
    public ResponseEntity<Collection<Product>> findByProductType(@PathVariable Long idProductType){
        try{
            return ResponseEntity.ok(productService.findByProductType(idProductType));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<Product>> getSearchProducts(@RequestParam(required = true) String search,
                                                           @RequestParam(required = true) Long idProductType,
                                                           @RequestParam(required = true) String name,
                                                           Pageable pageable) {
        return ResponseEntity.ok( productService.findProducts(idProductType, search, pageable, name) );
    }
    //http://localhost:8080/api/products?idProductType=1&page=0&size=5&searchName=ASUS&search=3-LG,5-34 pouces,1-629/630&name=LG

}
