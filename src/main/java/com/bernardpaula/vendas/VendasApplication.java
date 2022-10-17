package com.bernardpaula.vendas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bernardpaula.vendas.domain.entity.Cliente;
import com.bernardpaula.vendas.domain.repository.ClienteRepository;

@SpringBootApplication
public class VendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);  //Comando para inicializar a aplicação Spring Boot
	}

	/*
	@Bean
	public CommandLineRunner commandLineRunner(@Autowired ClienteRepository repo ) {
		
		return args ->{
			Cliente cliente = new Cliente(null, "Fulano");
			repo.save(cliente);
		};
		
	}
	*/
	
	/*
	
	@Bean
	public CommandLineRunner init (
			@Autowired ClienteRepository clientes,
			@Autowired PedidosRepository pedidos) {
		return args -> {
			
			clientes.save(new Cliente("Felipe"));
			Cliente cliente2 = new Cliente("João");
			clientes.save(cliente2);
			
			
			Pedido p = new Pedido();
			p.setCliente(cliente2);
			p.setDataPedido(LocalDate.now());   // Colocar a data de hoje  // LocalDateTime.now seria a data e horario atual
			p.setTotal(BigDecimal.valueOf(100));
			
			pedidos.save(p);
			
			//Cliente cliente = clientes.findClienteFatchPedidos(cliente2.getId());
			//System.out.println(cliente);
			//System.out.println(cliente.getPedidos());
	
			pedidos.findByCliente(cliente2).forEach(System.out::println);
			
			
			List<Cliente> result = clientes.encontrarPorNome("Bernard");
			result.forEach(System.out::println);
		
			
			// boolean existe = clientes.existsByNome("Bernard");
			// System.out.println("Existe um registro com o nome Bernard? " + existe);
			
			
			
			 
			List<Cliente> todosClientes = clientes.findAll();
			todosClientes.forEach(System.out::println);
			
			System.out.println("Atualizando clientes");
			todosClientes.forEach(c -> {
				c.setNome(c.getNome() + " atualizado.");
				clientes.save(c);
			});
			
			todosClientes = clientes.findAll();
			todosClientes.forEach(System.out::println);
			
			System.out.println("Buscando clientes");
			clientes.findByNomeLike("Cli").forEach(System.out::println);
			
			System.out.println("deletando clientes");
			clientes.findAll().forEach(c -> {
				clientes.delete(c);
			});
			
			todosClientes = clientes.findAll();
			if(todosClientes.isEmpty()) {
				System.out.println("Nenhum cliente encontrado.");
			} else {
				todosClientes.forEach(System.out::println);
			}
			
			
		};
	}
			
			*/
	
	
	
}
