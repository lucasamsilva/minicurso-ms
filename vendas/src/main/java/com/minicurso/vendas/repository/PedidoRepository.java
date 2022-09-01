package com.minicurso.vendas.repository;

import com.minicurso.vendas.domain.Pedido;
import com.minicurso.vendas.domain.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Modifying
    @Query("UPDATE Pedido p SET p.status = :status WHERE p.id = :id")
    void updateStatus(Long id, StatusEnum status);
}
