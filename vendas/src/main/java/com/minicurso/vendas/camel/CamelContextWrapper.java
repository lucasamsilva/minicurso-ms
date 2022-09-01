package com.minicurso.vendas.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class CamelContextWrapper {

    private final CamelContext context;

    public CamelContextWrapper(RouteBuilder... routes) throws Exception {
        this.context = new DefaultCamelContext();
        for (RouteBuilder route : routes) {
            this.context.addRoutes(route);
        }
        this.context.start();
    }

    public ProducerTemplate createProducerTemplate() {
        return this.context.createProducerTemplate();
    }

    public ConsumerTemplate consumerTemplate() {
        return this.context.createConsumerTemplate();
    }

}
