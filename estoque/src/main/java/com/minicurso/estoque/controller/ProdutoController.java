package com.minicurso.estoque.controller;

import com.minicurso.estoque.domain.Produto;
import com.minicurso.estoque.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@Value("${eureka.instance.instanceId}")
	String instanceId;


	@GetMapping("/instancia")
	public String instancia() {
		return instanceId;
	}

	@GetMapping
	public List<Produto> getProdutos() {
		return service.buscarProdutos();
	}

	@GetMapping("/{id}")
	public Produto getProduto(@PathVariable Long id) {
		return service.buscarProduto(id);
	}

}
