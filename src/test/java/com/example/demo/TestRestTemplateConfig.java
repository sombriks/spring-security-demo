package com.example.demo;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.test.http.server.LocalTestWebServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestRestTemplateConfig {

    @Bean
    public TestRestTemplate testRestTemplate(ApplicationContext ctx) {
        LocalTestWebServer server = LocalTestWebServer.obtain(ctx);
        TestRestTemplate template = new TestRestTemplate(new RestTemplateBuilder(),
                null, null,
                TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
        template.setUriTemplateHandler(server.uriBuilderFactory());
        return template;
    }
}
