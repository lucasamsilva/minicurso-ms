package com.minicurso.vendas.service;

import com.minicurso.vendas.domain.Pedido;
import com.minicurso.vendas.domain.Produto;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.minicurso.vendas.camel.router.PedidoRouter.PEDIDO_ROUTER;

@Service
@Slf4j
public class PedidosService {

	@Autowired
	private ProducerTemplate producerTemplate;


	public Pedido realizarPedido(List<Produto> produtos) {
		return producerTemplate.requestBody(PEDIDO_ROUTER, produtos, Pedido.class);
	}
}
