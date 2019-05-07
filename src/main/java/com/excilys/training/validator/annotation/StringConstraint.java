package com.excilys.training.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface StringConstraint {
	String regex() default "";
	int minLength() default 0;
	int maxLength() default -1;
	boolean nullable() default false;
	boolean empty() default false;
}
