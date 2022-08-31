package com.minicurso.estoque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minicurso.estoque.domain.Produto;
import com.minicurso.estoque.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService service;
	
	@GetMapping
	public List<Produto> getProdutos() {
		return service.buscarProdutos();
	}

	@GetMapping("/{id}")
	public Produto getProduto(@PathVariable Long id) {
		return service.buscarProduto(id);
	}

}
