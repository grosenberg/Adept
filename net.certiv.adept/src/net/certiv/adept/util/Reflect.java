/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Reflect {

	private Reflect() {}

	public static Object get(Object target, String fieldName) {
		try {
			Field f = target.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(target);
		} catch (Exception e) {
			Log.error(Reflect.class, "Reflect get failed on " + fieldName);
		}
		return null;
	}

	public static void set(Object target, String fieldName, Object value) {
		try {
			Field f = target.getClass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(target, value);
		} catch (Exception e) {
			Log.error(Reflect.class, "Reflect set failed on " + fieldName);
		}
	}

	public static Object getSuper(Object target, String fieldName) {
		try {
			Field f = target.getClass().getSuperclass().getDeclaredField(fieldName);
			f.setAccessible(true);
			return f.get(target);
		} catch (Exception e) {
			Log.error(Reflect.class, "Reflect getSuper failed on " + fieldName);
		}
		return null;
	}

	public static void setSuper(Object target, String fieldName, Object value) {
		try {
			Field f = target.getClass().getSuperclass().getDeclaredField(fieldName);
			f.setAccessible(true);
			f.set(target, value);
		} catch (Exception e) {
			Log.error(Reflect.class, "Reflect setSuper failed on " + fieldName);
		}
	}

	public static Object invoke(Object target, String methodName, Class<?>[] params, Object[] args) {

		try {
			Method m = target.getClass().getMethod(methodName, params);
			m.setAccessible(true);
			return m.invoke(target, args);
		} catch (Exception e) {
			Log.error(Reflect.class, "Invoke failed for method " + methodName);
		}
		return null;
	}

	public static Object invokeSuperDeclared(Object target, String methodName, Class<?>[] params, Object[] args) {

		try {
			Method m = target.getClass().getSuperclass().getDeclaredMethod(methodName, params);
			m.setAccessible(true);
			return m.invoke(target, args);
		} catch (Exception e) {
			Log.error(Reflect.class, "InvokeSuper failed for method " + methodName);
		}
		return null;
	}

	public static Object make(Class<?> clazz, Object[] args) {
		Constructor<?> c = clazz.getDeclaredConstructors()[0];
		c.setAccessible(true);
		Object object = null;
		try {
			object = c.newInstance(args);
		} catch (Exception e) {
			Log.error(Reflect.class, "Make failed for " + clazz.getName());
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
