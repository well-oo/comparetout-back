package com.example.demo.testIntegration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pepit.compareTout.CompareToutApplication;
import com.pepit.compareTout.entity.Role;
import com.pepit.compareTout.entity.User;
import com.pepit.compareTout.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = CompareToutApplication.class)
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    UserService userService;

    @Test
    public void testGetAllUsers() throws IOException {
        User user = new User();
        user.setUsername("UserTest");
        user.setPassword("password");
        user.setEmail("admin@admin.com");
        user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
        userService.signup(user);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/users", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(response.getBody());

        assertThat(responseJson.isMissingNode(), is(false));
        //assertThat(responseJson.toString(), equalTo("[]"));
    }
}
