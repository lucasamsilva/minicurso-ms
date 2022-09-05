package com.minicurso.vendas.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Produto {

	private Long id;
	private String nome;
	private BigDecimal valor;
	private Integer quantidade;
	
}
