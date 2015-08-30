package com.newland.posmall.file;

import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanField;

@BeanClass
public class Two {
	@BeanField()
	private String one = "two.one";
	
	@BeanField()
	private String two = "two.two";
	
	public Two(){
		
	}

}
