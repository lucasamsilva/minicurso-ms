package com.minicurso.vendas.config;

import com.minicurso.vendas.camel.CamelContextWrapper;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    @Bean
    public ProducerTemplate camelContextWrapper(RouteBuilder... routes) throws Exception {
        return new CamelContextWrapper(routes).createProducerTemplate();
    }

}
