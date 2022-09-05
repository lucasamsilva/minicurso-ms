package com.minicurso.vendas.rabbit.listener;

import com.minicurso.vendas.domain.Pedido;
import com.minicurso.vendas.service.PedidosService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    @Autowired
    private PedidosService service;

    @RabbitListener(queues = "${filas.atualizar}")
    public void resultadoProcessarPagamento(@Payload Pedido pedido) {
        service.atualizarPedido(pedido);
    }
}
