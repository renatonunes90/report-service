package com.reportservice.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class BaseEntity {

	protected int createHashCode(Object... props) {
		final int prime = 31;
		int result = 1;
		for (int i = 0; i < props.length; i++) {
			result = prime * result + ((props[i] == null) ? 0 : props[i].hashCode());
		}
		return result;
	}

	protected String stringify(Object object) {
		if (object == null) {
			return "null";
		}

		Class<?> clazz = object.getClass();
		StringBuilder sb = new StringBuilder(clazz.getSimpleName()).append(" [");

		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			if (!Modifier.isStatic(f.getModifiers())) {
				try {
					f.setAccessible(true);
					sb.append(f.getName()).append(" = ").append(f.get(object)).append(", ");
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		if (sb.lastIndexOf(",") > 0) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		return sb.append("]").toString();
	}

	protected boolean compareProp(Object prop1, Object prop2) {
		if (prop1 == null) {
			if (prop2 != null) {
				return false;
			}
		} else if (!prop1.equals(prop2)) {
			return false;
		}
		return true;
	}
}
