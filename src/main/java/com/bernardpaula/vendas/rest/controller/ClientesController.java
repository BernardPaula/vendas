package com.bernardpaula.vendas.rest.controller;

 
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bernardpaula.vendas.domain.entity.Cliente;
import com.bernardpaula.vendas.domain.repository.ClienteRepository;

@Controller
@RequestMapping("/api")
public class ClientesController {

	private ClienteRepository clienteRepository;
	
	public ClientesController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	

	@GetMapping("/cliente/{id}")   // é equivalente ao metodo requestMapping com o metodo GET
	@ResponseBody   // ele transforma o retorno do metodo em um  objeto do tipo Json
	public ResponseEntity<Cliente> GetClienteById(@PathVariable Integer id) {	
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		if(cliente.isPresent()) {
			/*
			HttpHeaders headers = new HttpHeaders();    // ----------Explicação de Header --------
			headers.put("Authorization", "token");
			ResponseEntity<Cliente> responseEntity = 
					new ResponseEntity<>(cliente.get(), headers,  HttpStatus.OK);  */
			
			return ResponseEntity.ok(cliente.get());     // .ok ->siginifica status 200	
		}
		return ResponseEntity.notFound().build();   // 404
	}
	
	
	@PostMapping("/inserir")
	@ResponseBody
	public ResponseEntity save(@RequestBody Cliente cliente){
		Cliente clienteSalvo = clienteRepository.save(cliente);
		return ResponseEntity.ok(clienteSalvo);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public ResponseEntity delete(@PathVariable Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isPresent()) {
			clienteRepository.delete(cliente.get());  // cliente.get() -> Porque ele é um Optional<>
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/atualizar/{id}")
	@ResponseBody
	public ResponseEntity update(
							@PathVariable Integer id,
							@RequestBody Cliente cliente) {
		return clienteRepository
				.findById(id)       // o findById retorna um Optional, que possui o método map()
				.map( clienteExistente -> {     // O metodo map sempre tem que retornar um objeto
					cliente.setId(clienteExistente.getId());  
					clienteRepository.save(cliente);
					return ResponseEntity.noContent().build();
				}).orElseGet(() -> ResponseEntity.notFound().build()); //orElseGet é um metodo que recebe como parâmetro um supplier(é uma interface funcional que recebe um parametro e retorna qualquer coisa)
	}
	
	@GetMapping("/clientes")
	public ResponseEntity find (Cliente filtro) {
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withIgnoreCase()
									.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(filtro, matcher);
		List<Cliente> lista = clienteRepository.findAll(example);
		return ResponseEntity.ok(lista);
										
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
