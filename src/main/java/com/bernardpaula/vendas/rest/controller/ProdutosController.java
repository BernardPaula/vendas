package com.bernardpaula.vendas.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bernardpaula.vendas.domain.entity.Produto;
import com.bernardpaula.vendas.domain.repository.ProdutosRepository;

@RestController
@RequestMapping("/api/produtos")
public class ProdutosController {

	@Autowired
	public ProdutosRepository repo;
	
	
	@GetMapping("/{id}")
	public Produto find(@PathVariable Integer id) {
		return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}
	
	
	@PostMapping("/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public void insert(@RequestBody Produto obj) {
		repo.save(obj);
	}
	
	
	@DeleteMapping("/deletar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {									// supplier
		repo.findById(id).map( obj -> { repo.delete(obj); return obj; })
											.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}
	
	@PutMapping("/atualizar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody Produto produto) {
		repo.findById(id).map(obj -> { produto.setId(obj.getId());
										repo.save(produto);
										return obj;
										}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}
	
	@GetMapping("/filtrar")
	public List<Produto> filtrar(Produto filtro){
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(filtro, matcher);
		List<Produto> list = repo.findAll(example);
		return list;
	}
	
	
}
