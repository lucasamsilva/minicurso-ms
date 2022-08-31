package com.minicurso.vendas.mapper;

import com.minicurso.vendas.domain.Produto;
import com.minicurso.vendas.representation.ProdutoRequest;
import com.minicurso.vendas.representation.RealizarPedidoRequest;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static List<Produto> toDomain(RealizarPedidoRequest request) {
        return request.getProdutos().parallelStream()
                .map(PedidoMapper::toDomain)
                .collect(Collectors.toList());
    }

    private static Produto toDomain(ProdutoRequest produto) {
        return Produto.builder()
                .id(produto.getId())
                .quantidade(produto.getQuantidade())
                .build();
    }

}
