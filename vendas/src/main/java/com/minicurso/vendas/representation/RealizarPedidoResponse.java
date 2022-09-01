package com.minicurso.vendas.representation;

import com.minicurso.vendas.domain.enums.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RealizarPedidoResponse {

    private Long id;
    private BigDecimal valorTotal;
    private LocalDateTime dataPedido;
    private StatusEnum status;
    private List<ProdutoResponse> produtos;


}
