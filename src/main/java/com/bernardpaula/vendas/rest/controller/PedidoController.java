package com.bernardpaula.vendas.rest.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bernardpaula.vendas.domain.entity.ItemPedido;
import com.bernardpaula.vendas.domain.entity.Pedido;
import com.bernardpaula.vendas.domain.enums.StatusPedido;
import com.bernardpaula.vendas.rest.dto.AtualizacaoStatusPedidoDTO;
import com.bernardpaula.vendas.rest.dto.InformacaoItemPedidoDTO;
import com.bernardpaula.vendas.rest.dto.InformacoesPedidoDTO;
import com.bernardpaula.vendas.rest.dto.PedidoDTO;
import com.bernardpaula.vendas.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private PedidoService service;
	
	public PedidoController(PedidoService service) {
		this.service = service;
	}
	
	
	
	@PostMapping
	@ResponseStatus(CREATED)
	public Integer save(@RequestBody @Valid PedidoDTO dto) {   // Você monta um Pedido e sua Lista de ItemPedido apartir do DTO enviado pela requisição
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}
	

	@GetMapping("{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) { // Atravéz de um Id de Pedido você busca o Pedido e monta um DTO para retorná-lo ao cliente
		return service
				.obterPedidoCompleto(id)
				.map( p -> converterPedidoCompleto(p) )
				.orElseThrow (() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado."));
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus(	@PathVariable Integer id,
								@RequestBody AtualizacaoStatusPedidoDTO dto) {
		String novoStatus = dto.getNovoStatus();
		service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
	}
	
	
	private InformacoesPedidoDTO converterPedidoCompleto(Pedido pedido) {
		return InformacoesPedidoDTO.builder()
						.codigo(pedido.getId())  // LocalDate - data do pedido
						.dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyy"))) // Transforma o LocalDate em uma string
						.cpf(pedido.getCliente().getCpf())
						.nomeCliente(pedido.getCliente().getNome())
						.total(pedido.getTotal())
						.itens(converter(pedido.getItens()))
						.status(pedido.getStatus().name())
						.build();
	}
	
	
	private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
		
		if(CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		
		return itens.stream().map(
				item -> InformacaoItemPedidoDTO
						.builder()
						.descricaoProduto(item.getProduto().getDescricao())
						.precoUnitario(item.getProduto().getPreco())
						.quantidade(item.getQuantidade())
						.build()
				).collect(Collectors.toList());
	}
	
}
