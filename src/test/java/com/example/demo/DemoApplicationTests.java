package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    AuthService authService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void contextLoads() {
        // spring just works
    }

    @Test
    void shouldListUsers() {
        List<MyLogin> myLogins = repository.findAll();
        assertThat(myLogins, notNullValue());
        assertThat(myLogins, is(not(empty())));
    }

    @Test
    void shouldEncodePassword() {
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
    void shouldGetLoginByUsernameAndPassword() {
        var username = "root@root.com";
        var result = repository
                .getByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        assertThat(encoder.matches("password", result.getPassword()), is(true));
    }

    @Test
    void shouldGetHelloStranger() {
        var result = restTemplate.getForObject("/", String.class);
        assertThat(result, notNullValue());
        assertThat(result, containsStringIgnoringCase("hello, stranger!"));
    }

    @Test
    void shouldGetHelloUser() {
        HttpHeaders headers = login("bobby@tables.net", "password");
        var result = restTemplate.exchange("/protected", HttpMethod.GET, new HttpEntity<Void>(headers), String.class);
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(result.getBody(), containsStringIgnoringCase("hello, bobby@tables.net!"));
    }

    @Test
    void shouldGetHelloAdmin() {
        HttpHeaders headers = login("root@root.com", "password");
        var result = restTemplate.exchange("/admin", HttpMethod.GET, new HttpEntity<Void>(headers), String.class);
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode().is2xxSuccessful(), is(true));
        assertThat(result.getBody(), containsStringIgnoringCase("hello, admin root@root.com!"));
    }

    @Test
    void shouldNotGetHelloUser() {
        HttpHeaders headers = new HttpHeaders();
        var result = restTemplate.exchange("/protected", HttpMethod.GET, new HttpEntity<Void>(headers), String.class);
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode().is4xxClientError(), is(true));
    }

    @Test
    void shouldNotGetHelloAdmin() {
        HttpHeaders headers = login("bobby@tables.net", "password");
        var result = restTemplate.exchange("/admin", HttpMethod.GET, new HttpEntity<Void>(headers), String.class);
        assertThat(result, notNullValue());
        assertThat(result.getStatusCode().is4xxClientError(), is(true));
    }

    private HttpHeaders login(String username, String password) {
        var token = restTemplate.postForObject("/auth", new LoginDTO(username, password), String.class);
        var headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers;
    }
}
