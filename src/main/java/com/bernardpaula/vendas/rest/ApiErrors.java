package com.bernardpaula.vendas.rest;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErrors {

	@Getter
	private List<String> errors;
	
	public ApiErrors(List<String> list) {
		this.errors = list;
	}
	
	public ApiErrors(String mensagemErro) {   //Construtor
		this.errors = Arrays.asList(mensagemErro);
	}  				// Arrays.asList -> transforma o argumento em ArrayList | Transforma a String em um Array
	
	
}
