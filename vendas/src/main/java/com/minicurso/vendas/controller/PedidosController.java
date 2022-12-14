package com.minicurso.vendas.controller;

import com.minicurso.vendas.mapper.PedidoMapper;
import com.minicurso.vendas.representation.RealizarPedidoRequest;
import com.minicurso.vendas.representation.RealizarPedidoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minicurso.vendas.service.PedidosService;

@RestController
@RequestMapping("/v1/pedidos")
public class PedidosController {

	@Autowired
	private PedidosService service;
	
	@PostMapping
	public RealizarPedidoResponse realizarPedido(@RequestBody RealizarPedidoRequest request) {
		return PedidoMapper.toResponse(service.realizarPedido(PedidoMapper.toDomain(request)));
	}
	
}
