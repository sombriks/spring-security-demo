package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetHelloStranger() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("hello, stranger!")));
    }

    @Test
    void shouldLogin() throws Exception {
        mockMvc.perform(formLogin()
                        .user("bobby@tables.net")
                        .password("password"))
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void shouldFailedLogin() throws Exception {
        mockMvc.perform(formLogin().user("user").password("wrongpassword"))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    @WithMockUser(username = "bobby@tables.net")
    void shouldGetHelloUser() throws Exception {
        mockMvc.perform(get("/protected"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("hello, bobby@tables.net!")));
    }

    @Test
    @WithMockUser(username = "root@root.com", authorities = {"ADM"})
    void shouldGetHelloAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsStringIgnoringCase("hello, admin root@root.com!")));
    }

    @Test
    void shouldNotGetHelloUser() throws Exception {
        mockMvc.perform(get("/protected"))
                .andExpect(status().isFound()) // does not fail rightaway, it tries to auth first
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = "bobby@tables.net")
    void shouldNotGetHelloAdmin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

}
