package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.notNullValue;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
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
