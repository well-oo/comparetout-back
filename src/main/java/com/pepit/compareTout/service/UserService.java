package com.pepit.compareTout.service;

import com.pepit.compareTout.entity.*;
import com.pepit.compareTout.exception.CustomException;
import com.pepit.compareTout.repository.ProductRepository;
import com.pepit.compareTout.repository.UserRepository;
import com.pepit.compareTout.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProductService productService;

    @Autowired
    private ValueCriteriaProductService valueCriteriaProductService;

    public String signin(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            if(!jwtTokenProvider.checkToken(user.getApiKey())){
                user.setApiKey(jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), 30));
            }
            return jwtTokenProvider.createToken(username, user.getRoles(), 1);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String signup(User user){
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setApiKey(jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), 30));
            userRepository.save(user);
            return jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), 1);
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public User update(User user, Long id){
        if(user == null){
            throw new IllegalArgumentException("User must not be null");
        } else{
            User userModified = this.findByID(id);
            if(user.getPassword() != null && !user.getPassword().equals("")){
                userModified.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userModified.setEmail(user.getEmail());
            userModified.setApiKey(user.getApiKey());
            userRepository.save(userModified);
            return userModified;
        }
    }

    public User createSupplier(User user){
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_SUPPLIER)));
            user.setApiKey(jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), 30));
            userRepository.save(user);
            return user;
        } else {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void delete(String username) {
        User u = userRepository.findByUsername(username);
        Collection<Product> products = productService.findByUser(u);
        for (Product product: products) {
            List<ValueCriteriaProduct> values = product.getValueCriteriaProductList();
            for(ValueCriteriaProduct value : values){
                valueCriteriaProductService.delete(value.getIdValueCriteriaProduct());
            }
            productService.delete(product.getIdProduct());
        }
        userRepository.deleteByUsername(username);
    }

    public Collection<User> findAll(){
        return userRepository.findAll();
    }

    public User whoami(HttpServletRequest req) {
        return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public String refresh(String username) {
        return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getRoles(), 1);
    }

    public String refreshApiKey(HttpServletRequest req){
        User user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
        String apiKey = jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), 30);
        user.setApiKey(apiKey);
        userRepository.save(user);
        return apiKey;
    }

    public String refreshApiKey(String username, String password){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userRepository.findByUsername(username);
            String apiKey = jwtTokenProvider.createToken(user.getUsername(), user.getRoles(), 30);
            user.setApiKey(apiKey);
            userRepository.save(user);
            return apiKey;
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public void updatePassword(String username, String oldPassword, String newPassword, String verifyPassword){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
            if(!newPassword.equals(verifyPassword)){
                throw new CustomException("Invalid newPassword/verifyPassword supplied", HttpStatus.BAD_REQUEST);
            }
            User user = userRepository.findByUsername(username);
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } catch (AuthenticationException e){
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public User findByName(String name){
        return userRepository.findByUsername(name);
    }

    public User findByID(Long id){
        return userRepository.findById(id).get();
    }

    public Collection<UserWithProduct> getAllByRole(Role role){
        return userRepository.findAllByRolesEqualsWithProductCount(role);
    }
}
