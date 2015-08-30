package com.newland.posmall.utils.formatter;

import java.math.BigDecimal;

public class WanyuanDecimalFormatter extends BigDecimalFormatter {

	@Override
	public String format(Object aObject) {
		if (aObject instanceof Number){
			double dec = ((Number)aObject).doubleValue();
			BigDecimal decimal = new BigDecimal(dec/10000);
			return super.format(decimal);
		}
		return super.format(aObject);
	}
	
	

}
