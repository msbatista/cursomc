package com.marcelo.cursomc.services;

import java.util.Date;
import java.util.Optional;

import com.marcelo.cursomc.domain.ItemPedido;
import com.marcelo.cursomc.domain.PagamentoComBoleto;
import com.marcelo.cursomc.domain.Produto;
import com.marcelo.cursomc.domain.enums.EstadoPagamento;
import com.marcelo.cursomc.repository.ItemPedidoReopository;
import com.marcelo.cursomc.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcelo.cursomc.domain.Pedido;
import com.marcelo.cursomc.repository.PedidoRepository;
import com.marcelo.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	ItemPedidoReopository itemPedidoReopository;

	public Pedido findById(Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		return pedido.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id));
	}

	public Pedido insert(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);

		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoCompleto(pagamentoComBoleto, pedido.getInstante());
		}

		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());

		for (ItemPedido itemPedido:pedido.getItens()) {
			itemPedido.setDesconto(0.0);
			Produto produto = produtoService.findById(itemPedido.getProduto().getId());
			itemPedido.setPreco(produto.getPrice());
			itemPedido.setPedido(pedido);
		}

		itemPedidoReopository.saveAll(pedido.getItens());

		return pedido;
	}

}
