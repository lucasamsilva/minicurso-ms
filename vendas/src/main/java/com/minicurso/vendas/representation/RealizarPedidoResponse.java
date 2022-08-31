package com.minicurso.vendas.representation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class RealizarPedidoResponse {

    private List<ProdutoResponse> produtos;
    private BigDecimal valorTotal;

}
