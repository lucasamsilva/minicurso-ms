package com.minicurso.vendas.camel.router;

import com.minicurso.vendas.camel.logic.MontarPedidoProcessor;
import com.minicurso.vendas.camel.logic.ProdutosAggregator;
import com.minicurso.vendas.camel.logic.ValorTotalProcessor;
import com.minicurso.vendas.rabbit.publisher.ProcessarPagamentoPublisher;
import com.minicurso.vendas.repository.EstoqueRepository;
import com.minicurso.vendas.repository.PedidoRepository;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoRouter extends RouteBuilder {

    public static final String PEDIDO_ROUTER = "direct:pedidoFlow";

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProcessarPagamentoPublisher publisher;

    @Override
    public void configure() {
        from(PEDIDO_ROUTER)
                .split(body(), new ProdutosAggregator())
                .parallelProcessing()
                    .setProperty("quantidade", simple("${body.quantidade}"))
                    .bean(estoqueRepository, "getProduto(${body.id})")
                .end()
                .process(new ValorTotalProcessor())
                .process(new MontarPedidoProcessor())
                .bean(pedidoRepository, "save")
                .bean(publisher, "postarFilaProcessarPagamento")
                .end();
    }
}
