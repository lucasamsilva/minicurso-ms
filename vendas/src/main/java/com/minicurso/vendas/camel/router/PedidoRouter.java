package com.minicurso.vendas.camel.router;

import com.minicurso.vendas.camel.logic.MontarPedidoProcessor;
import com.minicurso.vendas.camel.logic.ProdutosAggregator;
import com.minicurso.vendas.camel.logic.ValorTotalProcessor;
import com.minicurso.vendas.rabbit.publisher.ProcessarPagamentoPublisher;
import com.minicurso.vendas.repository.EstoqueRepository;
import com.minicurso.vendas.repository.PedidoRepository;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PedidoRouter extends RouteBuilder {

    public static final String PEDIDO_ROUTER = "direct:pedidoFlow";
    private static final String PAGAMENTOS_EXCHANGE = "pagamento";
    private static final String PROCESSAR_PAGAMENTO_KEY = "pagamento.processar";
    private static final String FILA_PROCESSAR = String.format("spring-rabbitmq:%s?routingKey=%s", PAGAMENTOS_EXCHANGE, PROCESSAR_PAGAMENTO_KEY);

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
                .marshal().json(JsonLibrary.Jackson)
                .to(ExchangePattern.InOnly, FILA_PROCESSAR)
                .setBody(getPedido())
//               .bean(publisher, "postarFilaProcessarPagamento")
                .end();
    }

    private Function<Exchange, Object> getPedido() {
        return exchange -> exchange.getProperty("pedido");
    }
}
