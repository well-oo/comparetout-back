package com.example.demo.unitTesting;

import com.pepit.compareTout.entity.Role;
import com.pepit.compareTout.entity.User;
import com.pepit.compareTout.repository.UserRepository;
import com.pepit.compareTout.security.JwtTokenProvider;
import com.pepit.compareTout.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private static User user;

    private static Collection<User> users;

    @Before()
    public void init(){
        /*
        MockitoAnnotations.initMocks(this);

        //Init our user
        user = new User();
        user.setUsername("test");
        user.setEmail("test@email.com");
        user.setPassword("test");
        user.setId(1L);
        user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_SUPPLIER)));

        users = new ArrayList<>();
        users.add(user);

        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(user);
        when(userRepository.existsByUsername(Mockito.anyString())).thenReturn(false);
        when(userRepository.findAll()).thenReturn((List<User>) users);
        when(jwtTokenProvider.createToken(Mockito.anyString(), ArgumentMatchers.anyList())).thenReturn("jwtToken");
        */
    }

    @Test
    public void test(){

    }
}
