package com.minicurso.vendas.service;

import com.minicurso.vendas.domain.Pedido;
import com.minicurso.vendas.domain.Produto;
import com.minicurso.vendas.domain.ProdutoPedido;
import com.minicurso.vendas.domain.enums.StatusEnum;
import com.minicurso.vendas.rabbit.publisher.ProcessarPagamentoPublisher;
import com.minicurso.vendas.repository.EstoqueRepository;
import com.minicurso.vendas.repository.PedidoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.minicurso.vendas.camel.router.PedidoRouter.PEDIDO_ROUTER;

@Service
@Slf4j
public class PedidosService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ProducerTemplate producerTemplate;


	public Pedido realizarPedido(List<Produto> produtos) {
		return producerTemplate.requestBody(PEDIDO_ROUTER, produtos, Pedido.class);
	}

	@Transactional
	public void atualizarPedido(Pedido pedido) {
		pedidoRepository.updateStatus(pedido.getId(), pedido.getStatus());
	}
}
