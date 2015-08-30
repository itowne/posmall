package com.newland.posmall.file;

import java.util.ArrayList;
import java.util.List;

import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanElement;
import org.ohuyo.commons.format.annotation.BeanElementType;
import org.ohuyo.commons.format.annotation.BeanField;


@BeanClass
public class One {
	@BeanField()
	String one = "one";
	@BeanField()
	String two = "two";
	
	@BeanField(element = @BeanElement(clz = ArrayList.class), subElement = @BeanElement(clz = Three.class))
	List<Two> twos;
	
	@BeanField(element = @BeanElement(type = BeanElementType.classField))
	Three three = new Three();
	
	public One(){
		twos = new ArrayList<Two>();
		for (int i = 0 ; i < 10; i++){
			Two two = new Two();
			twos.add(two);
		}
	}

}
