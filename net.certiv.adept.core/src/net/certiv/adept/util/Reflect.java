package net.certiv.adept.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflect {

	private Reflect() {}

	public static void set(Object target, String fieldName, Object value) {
		try {
			Field f = target.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(target, value);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void setSuper(Object target, String fieldName, Object value) {
		try {
			Field f = target.getClass().getSuperclass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(target, value);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static Object get(Object target, String fieldName) {
		try {
			Field f = target.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(target);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object getSuper(Object target, String fieldName) {
		try {
			Field f = target.getClass().getSuperclass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(target);
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object invoke(Object target, String methodName, Class<?>[] params, Object[] args) {

		try {
			Method m = target.getClass().getMethod(methodName, params);
			m.setAccessible(true);
			return m.invoke(target, args);
		} catch (SecurityException | NoSuchMethodException | IllegalArgumentException
				| IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object invokeSuperDeclared(Object target, String methodName, Class<?>[] params, Object[] args) {

		try {
			Method m = target.getClass().getSuperclass().getDeclaredMethod(methodName, params);
			m.setAccessible(true);
			return m.invoke(target, args);
		} catch (SecurityException | NoSuchMethodException | IllegalArgumentException
				| IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Object make(Class<?> clazz, Object[] args) {
		Constructor<?> c = clazz.getDeclaredConstructors()[0];
		c.setAccessible(true);
		Object object = null;
		try {
			object = c.newInstance(args);
		} catch (SecurityException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return object;
	}

	public static String simpleClassName(Object arg) {
		String name = arg.getClass().getCanonicalName();
		if (name != null) {
			int mark = name.lastIndexOf('.');
			if (mark > 0) {
				return name.substring(mark + 1);
			}
		}
		return "<unknown>";
	}

}
