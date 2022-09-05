package com.minicurso.vendas.representation;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProdutoResponse {

    private Long id;
    private String nome;
    private BigDecimal valorUnitario;
    private Integer quantidade;

}
