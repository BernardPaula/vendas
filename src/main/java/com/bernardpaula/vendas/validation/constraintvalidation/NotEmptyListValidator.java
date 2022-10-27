package com.bernardpaula.vendas.validation.constraintvalidation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bernardpaula.vendas.validation.NotEmptyList;



public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List>{

	@Override       // Vai dizer se este objeto é válido
	public boolean isValid(List list, ConstraintValidatorContext context) {
		
		return list != null && !list.isEmpty();
	}

	@Override
	public void initialize(NotEmptyList constraintAnnotation) {
		// TODO Auto-generated method stub
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
	
}