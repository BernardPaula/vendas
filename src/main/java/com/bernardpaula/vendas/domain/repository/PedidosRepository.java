package com.bernardpaula.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardpaula.vendas.domain.entity.Pedido;

public interface PedidosRepository extends JpaRepository<Pedido, Integer>{

}
