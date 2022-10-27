package com.bernardpaula.vendas.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class InternacionalizacaoConfig {

	@Bean    // É a fonte de mensagens.. q no caso é o messages.properties
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource(); //Implementa o arquivo properties
		messageSource.setBasename("classpath:messages");  //setando o arquivo com as mensagens
		messageSource.setDefaultEncoding("ISO-8859-1"); //Em q codificação estão as mensagens
		messageSource.setDefaultLocale(Locale.getDefault());  // Ele vai sempre pegar o local padrao em q está rodando a aplicação//Pode se colocar d forma dinamica... podendo colocar outro arquivo .properties e colocando mensagens em inglês .. por exemplo
		return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean validateFactoryBean() { // Ele será responsável por fazer a interpolação-> Ele faz o messageSource trocar a chave na validação e troca-la pela mensagem no arquivo .properties
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
		
	} 
	
}
