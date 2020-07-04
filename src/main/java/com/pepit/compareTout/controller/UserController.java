package com.pepit.compareTout.controller;

import com.pepit.compareTout.entity.Role;
import com.pepit.compareTout.entity.User;
import com.pepit.compareTout.entity.UserWithProduct;
import com.pepit.compareTout.exception.CustomException;
import com.pepit.compareTout.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
@Api(tags = "Supplier")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(userService.signin(username, password));
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteByUsername(@PathVariable  String username) {
        try{
            userService.delete(username);
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(value = "", hidden = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/suppliers/create")
    public ResponseEntity<User> createSupplier(@Valid @RequestBody User user){
        User supplier = userService.createSupplier(user);
        return ResponseEntity.ok(supplier);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/")
    public ResponseEntity<Collection<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @ApiOperation(value = "", hidden = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<User> update(@Valid @RequestBody User user, @PathVariable Long id){
        try{
            return ResponseEntity.ok(userService.update(user,id));
        }
        catch(CustomException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPPLIER')")
    public User whoami(HttpServletRequest req) {
        return userService.whoami(req);
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPPLIER')")
    public String refresh(HttpServletRequest req) {
        return userService.refresh(req.getRemoteUser());
    }

    @GetMapping("/suppliers/refreshApiKey")
    @ApiOperation(value = "Refresh your token API when you already have a valid token API", authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    @PreAuthorize("hasRole('ROLE_SUPPLIER')")
    public String refreshApiKey(HttpServletRequest req) {
        return userService.refreshApiKey(req);
    }

    @GetMapping("/suppliers/refreshApiKeyWithCredentials")
    @ApiOperation(value = "Refresh your token API with your credentials")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied")})
    public String refreshApiKey(@RequestParam String username, @RequestParam String password) {
        return userService.refreshApiKey(username, password);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPPLIER')")
    @PostMapping("/updatePassword")
    public ResponseEntity updatePassword(HttpServletRequest req, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String verifyPassword){
        try{
            userService.updatePassword(req.getRemoteUser(), oldPassword, newPassword, verifyPassword);
            return ResponseEntity.ok().build();
        }
        catch(CustomException e){
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @ApiOperation(value = "", hidden = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/suppliers")
    public ResponseEntity<Collection<UserWithProduct>> getAllSuppliers(){
        return ResponseEntity.ok(userService.getAllByRole(Role.ROLE_SUPPLIER));
    }
}
