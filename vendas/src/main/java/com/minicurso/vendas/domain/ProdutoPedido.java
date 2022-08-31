package com.minicurso.vendas.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
public class ProdutoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long produtoId;
    @ManyToOne
    private Pedido pedido;
    private Integer quantidade;
    private String nome;
    private BigDecimal valorUnitario;

}
