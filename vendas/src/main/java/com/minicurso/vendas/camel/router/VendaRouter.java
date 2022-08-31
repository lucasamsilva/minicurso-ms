package com.minicurso.vendas.camel.router;

import org.apache.camel.builder.RouteBuilder;

public class VendaRouter extends RouteBuilder {

    public static final String VENDAS_ROUTER = "direct:vendasFlow";

    @Override
    public void configure() throws Exception {
        from(VENDAS_ROUTER)
                .end();
    }
}
