package com.minicurso.vendas.camel.logic;

import com.minicurso.vendas.domain.Pedido;
import com.minicurso.vendas.domain.Produto;
import com.minicurso.vendas.domain.ProdutoPedido;
import com.minicurso.vendas.domain.enums.StatusEnum;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MontarPedidoProcessor implements Processor {
    @Override
    public void process(Exchange exchange) {
        var valorTotal = exchange.getProperty("valorTotal", BigDecimal.class);
        var produtos = exchange.getIn().getBody(List.class);
        var pedido = Pedido.builder()
                .dataPedido(LocalDateTime.now())
                .valorTotal(valorTotal)
                .status(StatusEnum.AGUARDANDO_PAGAMENTO)
                .build();
        adicionarProdutosPedido(pedido, produtos);
        exchange.getIn().setBody(pedido);
        exchange.setProperty("pedido", pedido);
    }

    private void adicionarProdutosPedido(Pedido pedido, List<Produto> produtosPedido) {
        var produtos = produtosPedido.parallelStream()
                .map(produto -> ProdutoPedido.builder()
                        .pedido(pedido)
                        .produtoId(produto.getId())
                        .quantidade(produto.getQuantidade())
                        .nome(produto.getNome())
                        .valorUnitario(produto.getValor())
                        .build()).collect(Collectors.toList());
        pedido.setProdutos(produtos);
    }
}
