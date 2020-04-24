package com.moonlight.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.PARAMETER })
@Constraint(validatedBy = { SensitiveWordsValidator.class })
public @interface Sensitive {
	
	String message() default "不能包含敏感词";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
