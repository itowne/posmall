package com.newland.posmall.file.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.ohuyo.commons.format.annotation.BeanClass;
import org.ohuyo.commons.format.annotation.BeanElement;
import org.ohuyo.commons.format.annotation.BeanElementType;
import org.ohuyo.commons.format.annotation.BeanField;

@BeanClass
public class TestBean {
	@BeanField(index = 0)
	private String one;
	@BeanField(index = 1)
	private String two;
	@BeanField(index = 2,element = @BeanElement(type = BeanElementType.bigDecimal, pattern="0.00"))
	private BigDecimal three;
	@BeanField(index = 3, element = @BeanElement(type = BeanElementType.date, pattern="yyyyMMddHHmmss"))
	private Date four;
	public String getOne() {
		return one;
	}
	public void setOne(String one) {
		this.one = one;
	}
	public String getTwo() {
		return two;
	}
	public void setTwo(String two) {
		this.two = two;
	}
	public BigDecimal getThree() {
		return three;
	}
	public void setThree(BigDecimal three) {
		this.three = three;
	}
	public Date getFour() {
		return four;
	}
	public void setFour(Date four) {
		this.four = four;
	}

	

}
