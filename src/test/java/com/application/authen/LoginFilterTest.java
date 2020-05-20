package com.application.authen;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginFilterTest {
    @Autowired
    TestRestTemplate testRestTemplate;
    @Test
    void testLogin(){
        HttpEntity<Login> resquest = new HttpEntity<Login>(new Login("user","1234"));
        ResponseEntity<String> response = testRestTemplate.postForEntity("/login",resquest,String.class);
        assertEquals(HttpStatus.UNAUTHORIZED,response.getStatusCode());
    }
}