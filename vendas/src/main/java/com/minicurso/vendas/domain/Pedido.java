package com.minicurso.vendas.domain;

import com.minicurso.vendas.domain.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    private List<ProdutoPedido> produtos;
    private BigDecimal valorTotal;
    private LocalDateTime dataPedido;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

}
