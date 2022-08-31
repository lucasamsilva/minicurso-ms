package com.minicurso.estoque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import com.minicurso.estoque.repository.ProdutoRepository;
import com.minicurso.estoque.domain.Produto;
import com.minicurso.estoque.exception.NotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	public List<Produto> buscarProdutos() {
		return repository.findAll();
	}
	
	public Produto buscarProduto(Long id) {
		return repository.findById(id).orElseThrow(NotFoundException::new);
	}
	
}
