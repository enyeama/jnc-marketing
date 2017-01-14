package com.sap.jnc.marketing.cluster.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

public class Expression {

	public static Object eval(Object target, String expr)
	  {
	    if ((expr == null) || (!expr.startsWith("."))) throw new RuntimeException("invalid expression: " + expr);

	    StringTokenizer tokenizer = new StringTokenizer(expr, ".");

	    Object ref = target;
	    while (tokenizer.hasMoreTokens()) {
	      String token = tokenizer.nextToken();
	      try
	      {
	        if (token.endsWith("()"))
	          ref = invokeMethod(token.replace("()", ""), ref);
	        else
	          ref = getField(token, ref);
	      }
	      catch (Throwable t) {
	        if ((t instanceof InvocationTargetException)) {
	          t = ((InvocationTargetException)t).getTargetException();
	        }

	        if ((t instanceof Error)) throw ((Error)t);
	        if ((t instanceof RuntimeException)) throw ((RuntimeException)t);
	        throw new RuntimeException(t);
	      }
	    }

	    return ref;
	  }

	  private static Object getField(String fieldName, Object ref)
	    throws IllegalArgumentException, IllegalAccessException
	  {
	    Class c = ref.getClass();
	    while (c != null) {
	      try {
	        Field field = c.getDeclaredField(fieldName);
	        field.setAccessible(true);
	        return field.get(ref);
	      } catch (NoSuchFieldException e) {
	        c = c.getSuperclass();
	      }

	    }

	    throw new NoSuchFieldError("No field named " + fieldName + " found in " + ref.getClass());
	  }

	  private static Object invokeMethod(String methodName, Object ref) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
	  {
	    Class c = ref.getClass();
	    while (c != null) {
	      try {
	        Method m = c.getDeclaredMethod(methodName, new Class[0]);
	        m.setAccessible(true);
	        return m.invoke(ref, new Object[0]);
	      } catch (NoSuchMethodException e) {
	        c = c.getSuperclass();
	      }

	    }

	    throw new NoSuchMethodError("No method named " + methodName + " found in " + ref.getClass());
	  }
}
