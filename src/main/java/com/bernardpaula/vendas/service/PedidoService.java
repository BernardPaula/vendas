package com.bernardpaula.vendas.service;

import java.util.Optional;

import com.bernardpaula.vendas.domain.entity.Pedido;
import com.bernardpaula.vendas.domain.enums.StatusPedido;
import com.bernardpaula.vendas.rest.dto.PedidoDTO;

public interface PedidoService {

	Pedido salvar( PedidoDTO dto );
	
	Optional<Pedido> obterPedidoCompleto( Integer id );
	
	void atualizaStatus(Integer id, StatusPedido statusPedido);
	
}
