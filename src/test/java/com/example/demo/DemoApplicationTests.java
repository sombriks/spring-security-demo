package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@AutoConfigureTestRestTemplate
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldGetHelloStranger() {
        var result = restTemplate
                .getForObject("/", String.class);
        assertThat(result, notNullValue());
        assertThat(result, containsStringIgnoringCase("hello, stranger!"));
    }

    @Test
    void shouldGetHelloUser() {
        var result = restTemplate
                .withBasicAuth("bobby@tables.net", "password")
                .getForObject("/protected", String.class);
        assertThat(result, notNullValue());
        assertThat(result, containsStringIgnoringCase("hello, bobby@tables.net!"));
    }

    @Test
    void shouldGetHelloAdmin() {
        var result = restTemplate
                .withBasicAuth("root@root.com", "password")
                .getForObject("/admin", String.class);
        assertThat(result, notNullValue());
        assertThat(result, containsStringIgnoringCase("hello, admin root@root.com!"));
    }

    @Test
    void shouldNotGetHelloUser() {
        var result = restTemplate
                .getForEntity("/protected", String.class);
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode().is4xxClientError(), is(true));
    }

    @Test
    void shouldNotGetHelloAdmin() {
        var result = restTemplate
                .withBasicAuth("bobby@tables.net", "password")
                .getForEntity("/admin", String.class);
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode().is4xxClientError(), is(true));
    }
}
