package com.bernardpaula.vendas.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardpaula.vendas.domain.entity.Cliente;
import com.bernardpaula.vendas.domain.entity.ItemPedido;
import com.bernardpaula.vendas.domain.entity.Pedido;
import com.bernardpaula.vendas.domain.entity.Produto;
import com.bernardpaula.vendas.domain.enums.StatusPedido;
import com.bernardpaula.vendas.domain.repository.ClienteRepository;
import com.bernardpaula.vendas.domain.repository.ItensPedidosRepository;
import com.bernardpaula.vendas.domain.repository.PedidosRepository;
import com.bernardpaula.vendas.domain.repository.ProdutosRepository;
import com.bernardpaula.vendas.exception.PedidoNaoEncontradoException;
import com.bernardpaula.vendas.exception.RegraNegocioException;
import com.bernardpaula.vendas.rest.dto.ItemPedidoDTO;
import com.bernardpaula.vendas.rest.dto.PedidoDTO;
import com.bernardpaula.vendas.service.PedidoService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor     //Gera um construtor com todos argumentos obrigatórios -> os que tem final(Que devem ser instanciados no memento da criação da classe)
@Service
public class PedidoServiceImpl implements PedidoService{

	private final PedidosRepository pedidoRepo;
	private final ClienteRepository clienteRepo;
	private final ProdutosRepository produtoRepo;
	private final ItensPedidosRepository itensPedidoRepo;
	
	/*
	public PedidoServiceImpl(PedidosRepository pedidoRepo, ClienteRepository clienteRepo, ProdutosRepository produtoRepo) {
		this.pedidoRepo = pedidoRepo;
		this.clienteRepo = clienteRepo;
		this.produtoRepo = produtoRepo;
	}     */
	

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {
		Integer idCliente = dto.getCliente();
		Cliente cliente = clienteRepo.findById(idCliente)
						.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		
		List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
		pedidoRepo.save(pedido);
		itensPedidoRepo.saveAll(itensPedido);
		pedido.setItens(itensPedido);
		return pedido;
	}

	
	private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
		
		if(itens.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
		}
		
		return itens.stream()
				.map( dto -> {
					Integer idProduto = dto.getProduto();
					Produto produto = produtoRepo
							.findById(idProduto)
							.orElseThrow(
									() -> new RegraNegocioException("Código de produto inválido: " + idProduto));
					
					ItemPedido itemPedido = new ItemPedido();
					itemPedido.setQuantidade(dto.getQuantidade());
					itemPedido.setPedido(pedido);
					itemPedido.setProduto(produto);
					return itemPedido;
				}).collect(Collectors.toList());
	}


	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return pedidoRepo.findByIdFetchItens(id);
	}


	@Override
	@Transactional
	public void atualizaStatus(Integer id, StatusPedido statusPedido) {
		pedidoRepo.findById(id)
				.map( pedido -> {
					pedido.setStatus(statusPedido);
					return pedidoRepo.save(pedido);
				}).orElseThrow(() -> new PedidoNaoEncontradoException());
		
	}
	
	
}
