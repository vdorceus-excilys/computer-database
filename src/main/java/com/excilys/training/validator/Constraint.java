package com.excilys.training.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Constraint {
	String clazz() default "java.lang.String";
	double minValue()  default Double.MIN_VALUE;
	double maxValue() default Double.MAX_VALUE;
	boolean blank() default true;
	int minSize() default 0;
	int maxSize() default Integer.MAX_VALUE;
	String inList() default "";
	String matches() default "";
	boolean nullable() default true;
	String validateWith() default "";
	String minDate() default "";
	String maxDate() default "";
	String ref() default "" ;
	String greaterThan() default "";
	String lowerThan() default "";
}
