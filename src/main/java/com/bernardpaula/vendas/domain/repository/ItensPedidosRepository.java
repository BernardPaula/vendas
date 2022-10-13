package com.bernardpaula.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardpaula.vendas.domain.entity.ItemPedido;

public interface ItensPedidosRepository extends JpaRepository<ItemPedido, Integer>{

}
