package com.minicurso.vendas.camel.logic;

import com.minicurso.vendas.domain.Produto;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ValorTotalProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        var produtos = (ArrayList<Produto>) exchange.getIn().getBody(ArrayList.class);
        var valorTotal = produtos.stream()
                .map(produto -> produto.getValor().multiply(new BigDecimal(produto.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        exchange.setProperty("valorTotal", valorTotal);
    }
}
