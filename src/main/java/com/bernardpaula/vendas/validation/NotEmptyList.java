package com.bernardpaula.vendas.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.bernardpaula.vendas.validation.constraintvalidation.NotEmptyListValidator;

@Retention(RetentionPolicy.RUNTIME)   // Para ser verificada em tempo de execução
@Target(ElementType.FIELD)   			// Onde podemos colocar esta anotation
@Constraint(validatedBy = NotEmptyListValidator.class)     // Qual a classe q implementará a validação
public @interface NotEmptyList {

	String message() default "A lista não pode ser vazia";
	
	
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
}
