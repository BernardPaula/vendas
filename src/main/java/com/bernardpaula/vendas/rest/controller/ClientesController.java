package com.bernardpaula.vendas.rest.controller;

 
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

import com.bernardpaula.vendas.domain.entity.Cliente;
import com.bernardpaula.vendas.domain.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {

	private ClienteRepository clienteRepo;
	
	public ClientesController(ClienteRepository clienteRepo) {
		this.clienteRepo = clienteRepo;
	}
	

	@GetMapping("{id}")   // é equivalente ao metodo requestMapping com o metodo GET
	public Cliente GetClienteById(@PathVariable Integer id) {	
		return clienteRepo.findById(id)
					  	 .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));	
	}
	
	
	@PostMapping("/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@RequestBody @Valid Cliente cliente){
		return  clienteRepo.save(cliente);
	}
	
	
	@DeleteMapping("/deletar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		 clienteRepo.findById(id)
								.map(  cliente -> { clienteRepo.deleteById(id);  return cliente; }   )
								.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
	}
		

	
	@PutMapping("/atualizar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(
							@PathVariable Integer id,
							@RequestBody @Valid Cliente cliente) {
		clienteRepo
				.findById(id)       // o findById retorna um Optional, que possui o método map()
				.map( clienteExistente -> {     // O metodo map sempre tem que retornar um objeto
					cliente.setId(clienteExistente.getId());  
					clienteRepo.save(cliente);
					return clienteExistente;
				}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")); //orElseGet é um metodo que recebe como parâmetro um supplier(é uma interface funcional que recebe um parametro e retorna qualquer coisa)
	}
	

	
	@GetMapping("/filtrar")
	public List<Cliente> find (Cliente filtro) {   
		ExampleMatcher matcher = ExampleMatcher      	// Busca por Query Params ->Cabeçalho da requisição
									.matching()
									.withIgnoreCase()   //withIgonreCase é para ignorar letras de caixa-alta ou caixa-baixa
									.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); //Faz a busca contendo um trecho da string passada para o Example
		Example example = Example.of(filtro, matcher);
		return clienteRepo.findAll(example);
			
		/*    O recurso Example implementa este códio 
		String sql = "select * from cliente ";
		if(filtro.getNome() != null) {
			sql += "where nome = :nome";
		}
		if(filtro.getCpf() != null) {
			sql += "and cpf = :cpf"
		}
		*/
	}

	
	
	/*
	@RequestMapping(
			value = {"/hello/{nome}", "/api/hello"}, 
			method = RequestMethod.GET,
			consumes = {"application/json", "application/xml"},  //(é o que a aplicação irá receber) Recebe um minetipe do tipo de conteudo que este metodo pode receber
			produces = {"application/json", "application/xml"})  //(é a forma como iremos retortar o nosso objeto) 
	@ResponseBody
	public String helloCliente(@PathVariable("nome") String nomeCliente) {
		return String.format("Hellor %s ", nomeCliente);
	}
	*/
}
