package com.minicurso.vendas.camel.logic;

import com.minicurso.vendas.domain.Produto;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import java.util.ArrayList;

public class ProdutosAggregator implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Produto produto = newExchange.getIn().getBody(Produto.class);
        var quantidade = newExchange.getProperty("quantidade", Integer.class);
        produto.setQuantidade(quantidade);
        ArrayList<Produto> list = null;
        if (oldExchange == null) {
            list = new ArrayList<Produto>();
            list.add(produto);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(ArrayList.class);
            list.add(produto);
            return oldExchange;
        }
    }
}
