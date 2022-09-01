package com.minicurso.vendas.mapper;

import com.minicurso.vendas.domain.Pedido;
import com.minicurso.vendas.domain.Produto;
import com.minicurso.vendas.domain.ProdutoPedido;
import com.minicurso.vendas.representation.ProdutoRequest;
import com.minicurso.vendas.representation.ProdutoResponse;
import com.minicurso.vendas.representation.RealizarPedidoRequest;
import com.minicurso.vendas.representation.RealizarPedidoResponse;

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

    public static RealizarPedidoResponse toResponse(Pedido pedido) {
       return RealizarPedidoResponse.builder()
                .dataPedido(pedido.getDataPedido())
                .valorTotal(pedido.getValorTotal())
                .id(pedido.getId())
                .status(pedido.getStatus())
                .produtos(pedido.getProdutos().stream()
                        .map(PedidoMapper::toResponse)
                        .collect(Collectors.toList())
                )
                .build();
    }

    private static ProdutoResponse toResponse(ProdutoPedido produto) {
        return ProdutoResponse.builder()
                .id(produto.getId())
                .quantidade(produto.getQuantidade())
                .nome(produto.getNome())
                .valorUnitario(produto.getValorUnitario())
                .build();
    }

}
