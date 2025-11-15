package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    MyLoginRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Test
    void contextLoads() {
    }

    @Test
    void shouldListUsers(){
        List<MyLogin> myLogins = repository.findAll();
        assertThat(myLogins, notNullValue());
        assertThat(myLogins,  is(not(empty())));
    }

    @Test
    void shouldEncodePassword(){
        var result = encoder.encode("password");
        assertThat(result, notNullValue());
        var challenge = encoder.encode("password");
        assertThat(challenge, notNullValue());
        // no collisions, never
        assertThat(challenge, not(equalTo(result)));
        // manually validating a password
        assertThat(encoder.matches("password", result), is(true));
    }

    @Test
    void shouldGetHelloStranger() {
        var result = restTemplate.getForObject("/", String.class);
        assertThat(result, notNullValue());
        assertThat(result, containsStringIgnoringCase("hello, stranger!"));
    }

    @Test
    void shouldGetHelloUser() {
        var result = restTemplate.getForObject("/protected", String.class);
        assertThat(result, notNullValue());
        assertThat(result, containsStringIgnoringCase("hello, bobby@tables.net!"));
    }

    @Test
    void shouldGetHelloAdmin() {
        var result = restTemplate.getForObject("/admin", String.class);
        assertThat(result, notNullValue());
        assertThat(result, containsStringIgnoringCase("hello, admin root@root.com!"));
    }

}
