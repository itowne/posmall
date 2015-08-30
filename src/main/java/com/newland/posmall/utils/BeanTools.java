package com.newland.posmall.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.springframework.beans.BeanUtils;

public class BeanTools extends BeanUtils {
	
	public static Field[] getFields(Class<?> clazz){
		List<Field> list = new ArrayList<Field>();
		Field[] fields = null;
		do{
			fields = clazz.getDeclaredFields();
			for (Field field:fields){
				list.add(field);
			}
		}while((clazz = clazz.getSuperclass()) != Object.class);
		return list.toArray(new Field[list.size()]);
	}

	public static Object getValue(Object obj, Field field) throws Exception {
		boolean accessable = field.isAccessible();
		field.setAccessible(true);
		Object value = field.get(obj);
		field.setAccessible(accessable);
		return value;
	}
	
	public static void setValue(Object obj, Field field, Object value) throws Exception{
		BeanUtilsBean2.getInstance().setProperty(obj, field.getName(), value);
	}

}
