package com.minicurso.vendas.service;

import com.minicurso.vendas.camel.CamelContextWrapper;
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
	private EstoqueRepository estoqueRepository;
	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ProcessarPagamentoPublisher publisher;

	@Transactional
	public Pedido realizarPedido(List<Produto> produtos) {
		var produtosPedido = produtos.stream()
				.map(this::buscaProduto)
				.collect(Collectors.toList());
		var valorTotal = calculaValorTotal(produtosPedido);
		var pedido = salvarPedido(produtosPedido, valorTotal);
		publisher.postarFilaProcessarPagamento(pedido);
		return pedido;
	}

	private Produto buscaProduto(Produto produto) {
		var produtoInfo = estoqueRepository.getProduto(produto.getId());
		return Produto.builder()
				.id(produtoInfo.getId())
				.quantidade(produto.getQuantidade())
				.valor(produtoInfo.getValor())
				.nome(produtoInfo.getNome())
				.build();
	}

	private BigDecimal calculaValorTotal(List<Produto> produtos) {
		return produtos.stream()
				.map(produto -> produto.getValor().multiply(new BigDecimal(produto.getQuantidade())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private Pedido salvarPedido(List<Produto> produtosPedido, BigDecimal valorTotal) {
		var pedido = Pedido.builder()
				.dataPedido(LocalDateTime.now())
				.valorTotal(valorTotal)
				.status(StatusEnum.AGUARDANDO_PAGAMENTO)
				.build();
        adicionarProdutosPedido(pedido, produtosPedido);
		return pedidoRepository.save(pedido);
	}

    private void adicionarProdutosPedido(Pedido pedido, List<Produto> produtosPedido) {
        var produtos = produtosPedido.parallelStream()
                .map(produto -> ProdutoPedido.builder()
                        .pedido(pedido)
                        .produtoId(produto.getId())
                        .quantidade(produto.getQuantidade())
                        .nome(produto.getNome())
                        .valorUnitario(produto.getValor())
                        .build()).collect(Collectors.toList());
        pedido.setProdutos(produtos);
    }


	@Transactional
	public void atualizarPedido(Pedido pedido) {
		pedidoRepository.updateStatus(pedido.getId(), pedido.getStatus());
	}
}
