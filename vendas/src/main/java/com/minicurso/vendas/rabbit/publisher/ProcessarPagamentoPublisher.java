package com.minicurso.vendas.rabbit.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.minicurso.vendas.domain.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProcessarPagamentoPublisher {

    public static final String PAGAMENTOS_EXCHANGE = "pagamento";
    public static final String PROCESSAR_PAGAMENTO_KEY = "pagamento.processar";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void postarFilaProcessarPagamento(Pedido pedido) {
        var pedidoId = pedido.getId().toString().getBytes();
        Message message = new Message(pedidoId);
        rabbitTemplate.send(PAGAMENTOS_EXCHANGE, PROCESSAR_PAGAMENTO_KEY, message);
    }

}
