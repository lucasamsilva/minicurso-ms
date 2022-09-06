package com.minicurso.vendas.camel.router;

import com.minicurso.vendas.domain.Pedido;
import com.minicurso.vendas.repository.PedidoRepository;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtualizarPedidoRouter extends RouteBuilder {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public void configure() throws Exception {
        from("spring-rabbitmq:pagamento?queues=atualizar")
                .convertBodyTo(String.class)
                .unmarshal().json(Pedido.class)
                .bean(pedidoRepository, "updateStatus(${body.id}, ${body.status})")
                .end();
    }
}
