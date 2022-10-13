package com.bernardpaula.vendas.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bernardpaula.vendas.domain.entity.Produto;

public interface ProdutosRepository extends JpaRepository<Produto, Integer>{

}
