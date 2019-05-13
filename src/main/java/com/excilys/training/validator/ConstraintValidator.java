package com.excilys.training.validator;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.excilys.training.validator.exception.*;


public class ConstraintValidator implements Validator<Object>{

	private HashMap<String,EvalBox> refs = new HashMap<>();
	
	@Override
	public void validate(Object pojo) throws FailedValidationException, ParseException {
		// TODO Auto-generated method stub
		if(pojo==null) throw new FailedValidationException("Validation on null object");
		
		Field [] fields = pojo.getClass().getDeclaredFields();
		TreeSet<EvalBox> fieldSet = new TreeSet<>();
		Constraint constraint;	
		for (Field field : fields) {			
			constraint = field.getAnnotation(Constraint.class);
			if(constraint != null) {
				String fieldName = field.getName();
				Object content;
				try {
					field.setAccessible(true);
					content = field.get(pojo);					
					EvalBox evalBox=(new EvalBox(fieldName,content,constraint));
					if(!constraint.ref().equals("")) {
						refs.put(constraint.ref(),evalBox);
					}
					fieldSet.add(evalBox);
				} catch (IllegalArgumentException | IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				
			}			
		}
		for(EvalBox evalBox : fieldSet) {
			evaluate(evalBox);
		}
		
		
		
		
	}
	
	private void evaluate(EvalBox evalBox) throws FailedValidationException,ParseException{	
		nullable(evalBox);
		switch(evalBox.constraint.clazz()) {
		case "java.lang.String" : evaluateAsText(evalBox); break;		
		case "java.lang.Long": 
		case "java.lang.Integer":
		case "java.lang.Double" :
		case "java.lang.Float":
		case "java.lang.Short": evaluateAsNumeric(evalBox); break;
		case "java.util.Date": evaluateAsDate(evalBox);break;
		default : this.validate(evalBox.content);
		}
	}
	
	private void evaluateAsText(EvalBox evalBox) throws FailedValidationException{
		blank(evalBox);
		minSize(evalBox);
		maxSize(evalBox);
		inList(evalBox);
		matches(evalBox);
		//notEquals(eval)
		
	}
	private void evaluateAsNumeric(EvalBox evalBox) throws FailedValidationException{
		minValue(evalBox);
		maxValue(evalBox);
		//notEquals(evalBox);
		greaterThan(evalBox);
		nullable(evalBox);
		lowerThan(evalBox);				
		
	}
	private void  evaluateAsDate(EvalBox evalBox) throws FailedValidationException{
		//minDate(evalBox);
		//maxDate(evalBox);
		greaterThan(evalBox);
		lowerThan(evalBox);
	}
	private void lowerThan(EvalBox evalBox) throws ComparaisonConstraintException{
		String target = evalBox.constraint.lowerThan();		
		if(target.isEmpty()) 
			return;
		if(!refs.containsKey(target)) {
			throw new ComparaisonConstraintException("No target found to compare to");
		}				
		if(evalBox.constraint.clazz().equals("java.util.Date")) {
			Date beforeDate = (Date) evalBox.content;
			Date afterDate = (Date) refs.get(target).content;
			if(afterDate.before(beforeDate))//
				throw new ComparaisonConstraintException("The value of "+evalBox.fieldName+" isn't after the date in ::"+target);
			//completed without issues
		}		
	}
	private void greaterThan(EvalBox evalBox) throws ComparaisonConstraintException{
		String target = evalBox.constraint.greaterThan();		
		if(target.isEmpty()) 
			return;
		if(!refs.containsKey(target)) {
			throw new ComparaisonConstraintException("No target found to compare to");
		}				
		if(evalBox.constraint.clazz().equals("java.util.Date")) {
			Date afterDate = (Date) evalBox.content;
			Date beforeDate = (Date) refs.get(target).content;
			if(afterDate.before(beforeDate))//
				throw new ComparaisonConstraintException("The value of "+evalBox.fieldName+" isn't after the date in ::"+target);
			//completed without issues
		}		
	}
	private void matches(EvalBox evalBox) throws MatchConstraintException{
		String regex = evalBox.constraint.matches().trim();
		if(!regex.isEmpty()) {
			String value = evalBox.content.toString();
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(value);
			if(!matcher.find())
				throw new MatchConstraintException("The value of "+evalBox.fieldName+" doesn't match the regex condition :: "+regex);
		}
	}
	private void inList(EvalBox evalBox) throws ExpectedValueConstraintException{
		String list = evalBox.constraint.inList().trim();		
		if(!list.isEmpty()) {
			Set<String> acceptedValues = new HashSet<>(Arrays.asList(list.split(",")));
			String value  = evalBox.content.toString();
			if(!acceptedValues.contains(value))
				throw new ExpectedValueConstraintException("The value of "+evalBox.fieldName+" is not in the list of accepted values");
		}
	}
	private void maxSize(EvalBox evalBox) throws SizeConstraintException{
		String value = (String)  evalBox.content;
		int maxSize =  evalBox.constraint.maxSize();
		if(value!=null  &&  value.length()>maxSize)
			throw new SizeConstraintException("The size of "+evalBox.fieldName+" is not allowed to be greater than :: "+maxSize);
	}
	private void minSize(EvalBox evalBox) throws SizeConstraintException{
		String value = (String)  evalBox.content;
		int minSize =  evalBox.constraint.minSize();
		if(value!=null  &&  value.length()<minSize)
			throw new SizeConstraintException("The size of "+evalBox.fieldName+" is not allowed to be lower than :: "+minSize);
	}
	private void blank(EvalBox evalBox) throws BlankConstraintException{
		String value = (String)  evalBox.content;
		if(value!=null  &&  value.isEmpty() && !evalBox.constraint.blank())
			throw new BlankConstraintException("The value of "+evalBox.fieldName+" is not allowed to be empty");
	}
	private void nullable(EvalBox evalBox) throws NullConstraintException{
		if(evalBox.content==null && !evalBox.constraint.nullable())
			throw new NullConstraintException("The value of "+evalBox.fieldName+" is not allowed to be null");
	}
	private void maxValue(EvalBox evalBox) throws ValueConstraintException{
		Double maxValue = evalBox.constraint.maxValue();
		Double value = Double.valueOf(evalBox.content.toString());
		if(value>maxValue)
			throw new ValueConstraintException("The value of "+evalBox.fieldName+" is greater than the maximum accepted");
	}
	private void minValue(EvalBox evalBox) throws ValueConstraintException{
		Double minValue = evalBox.constraint.minValue();
		Double value = Double.valueOf(evalBox.content.toString()) ;
		if(value<minValue)
			throw new ValueConstraintException("The value of "+evalBox.fieldName+" is lower than the minimum accepted");
	}
	

}
class EvalBox implements Comparable<EvalBox>{
	public  String fieldName;
	public Object content;
	public Constraint constraint;
	public EvalBox(String fieldName, Object content, Constraint constraint) {
		this.fieldName=fieldName;
		this.content= content;
		this.constraint = constraint;
	}
	@Override
	public int compareTo(EvalBox e) {
		if(this.constraint.ref().equals("")) {
			return 1;
		}else if(e.constraint.ref().equals("")) {
			return -1;
		}
		else {
			return this.constraint.ref().compareTo(e.constraint.ref());
		}
	}
}
