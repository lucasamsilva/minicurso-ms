package com.minicurso.vendas.rabbit.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minicurso.vendas.domain.Pedido;
import com.minicurso.vendas.service.PedidosService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    @Autowired
    private PedidosService service;

    @RabbitListener(queues = "${filas.atualizar}")
    public void resultadoProcessarPagamento(@Payload Pedido pedido) throws JsonProcessingException {
        service.atualizarPedido(pedido);
    }
}
