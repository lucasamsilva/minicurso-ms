package com.minicurso.vendas.repository;

import com.minicurso.vendas.domain.Produto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
@FeignClient(name = "estoque-service", path="/v1/produtos")
public interface EstoqueRepository {

	@GetMapping
	public List<Produto> getProdutos();

	@GetMapping("/{id}")
	public Produto getProduto(@PathVariable Long id);
	
}
