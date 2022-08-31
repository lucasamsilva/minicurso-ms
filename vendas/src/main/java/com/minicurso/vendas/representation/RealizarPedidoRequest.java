package com.minicurso.vendas.representation;

import lombok.Data;

import java.util.List;

@Data
public class RealizarPedidoRequest {

    List<ProdutoRequest> produtos;

}
