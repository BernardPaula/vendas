package com.bernardpaula.vendas.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import javax.validation.Valid;

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

	public ProdutosRepository repo;
	
	public ProdutosController(ProdutosRepository repo) {
		this.repo = repo;
	}
	
	
	@GetMapping("{id}")
	public Produto find(@PathVariable Integer id) {
		return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}
	
	
	@PostMapping("/inserir")
	@ResponseStatus(CREATED)
	public void insert(@RequestBody @Valid Produto obj) {
		repo.save(obj);
	}
	
	
	@DeleteMapping("/deletar/{id}")
	@ResponseStatus(NO_CONTENT)
	public void delete(@PathVariable Integer id) {									// supplier
		repo.findById(id).map( obj -> { repo.delete(obj); return Void.TYPE; })
											.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}
	

	
	
	@PutMapping("/atualizar/{id}")
	@ResponseStatus(NO_CONTENT)
	public void atualizar(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
		repo.findById(id).map(obj -> { produto.setId(obj.getId());
										repo.save(produto);
										return obj;
										}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}
	
	
	
	@GetMapping("/filtrar")
	public List<Produto> filtrar(Produto filtro){
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(filtro, matcher);
		List<Produto> list = repo.findAll(example);
		return list;
	}
	
	
}
