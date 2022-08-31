package com.minicurso.vendas.representation;

import com.minicurso.vendas.domain.Produto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProdutoResponse {

    private Long id;
    private String nome;
    private BigDecimal valor;
    private Integer quantidade;

}
