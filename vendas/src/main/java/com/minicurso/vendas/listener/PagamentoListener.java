package com.minicurso.vendas.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PagamentoListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "${filas.atualizar}")
    public void receive(@Payload String fileBody) throws JsonProcessingException {
        System.out.println("Message " + fileBody);
        var json = new ObjectMapper().writeValueAsString(fileBody).getBytes();
        Message message = new Message(json);
        rabbitTemplate.send("pagamentos", "pagamentos.processar", message);
    }
}
