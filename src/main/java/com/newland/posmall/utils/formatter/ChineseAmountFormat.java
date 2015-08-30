/*
 * Created on 2004-8-26
 *
 * Project: CashManagement
 */
package com.newland.posmall.utils.formatter;
/**
 * ChineseAmountFormat
 * Project: CashManagement
 * @author shen
 *
 * 2004-8-26
 */
public class ChineseAmountFormat extends Formatter {

	/**
	 * @see com.intensoft.base.format.Formatter#format(java.lang.Object)
	 */
	public String format(Object aObject) {
		if (aObject instanceof Number)
			return toCString(((Number)aObject).doubleValue());
		else if (aObject == null) return null;
		else return  aObject.toString();
	}

    @Override
    public void setPattern(String pattern) {
    }

	/**
	 * @see com.intensoft.base.format.Formatter#unformat(java.lang.String)
	 */
	public Object unformat(String aString) {
		// TODO Auto-generated method stub
		return null;
	}

 	public static String cDigits = "零壹贰叁肆伍陆柒捌玖";
 	public static String cUnits  = "仟佰拾万仟佰拾亿仟佰拾万仟佰拾元";	// 角和分另外处理

	public static String toCString(double value)
	{
		String strInt;
		String strDec;
		StringBuffer result;		// 转换结果
		String fu;
		char a,b,b2,c,d;

        result = new StringBuffer();

		//处理金额小于零情况
		if (value < 0) {
			value = Math.abs(value);
            fu = "负";
		} else fu = "";

		// 取得整数值及整数串
		// strInt = Type.toString(value, "0");
		strDec = NumberFormatter.format(new Double(value), "#.00");
		strInt = strDec.substring(0, strDec.length()-3);
		strDec = strDec.substring(strDec.length()-2, strDec.length());

		//转换整数部分
		b2 = 0x00;
        for (int i=0; i<strInt.length(); i++) {
			// a为小写数字， b为对应的大写字符
            // c为对应大写单位, d为当前大写字符串的最后一个汉字
			a = strInt.charAt(i);								// small digit
			b = cDigits.charAt(Integer.parseInt(String.valueOf(a)));	// hanzi digit
			c = cUnits.charAt(cUnits.length() - strInt.length() + i); 	// hanzi unit
			if (result.length() > 0)
				d = result.charAt(result.length()-1);
			else d = 0x00;

			// 处理'0'
			//    1) 不出现两个'零', 前单位元前不出现'零'
			if ( b == '零' && (d == '零' || b == b2 || c=='元' || c == '万' || c == '亿'))
				b = 0x00;
                        //    2)
			if (a == '0' && c != '元' && c != '万' && c != '亿')
				c = 0x00;

			if ((c == '元' || c == '万' || c == '亿') && d == '零' && a == '0') {
				result = new StringBuffer(result.substring(0, result.length()-1));
				d = result.charAt(result.length()-1);

				if (c =='万' && d == '亿')
					c = 0x00;
			}
			if (b != 0x00) result.append(b);
			if (c != 0x00) result.append(c);
			b2 = b;
		}

		//处理金额小于1的情况
		if (result.length() <= 1) result = new StringBuffer("");
		//转换小数部分
		if (!strDec.equals("00")) {
			b = cDigits.charAt(Integer.parseInt(String.valueOf(strDec.charAt(0))));
			// result.append(b);	// 元后不出现'零角'
			if (b != '零') {
				result.append(b);
				result.append('角');
			}
			b = cDigits.charAt(Integer.parseInt(String.valueOf(strDec.charAt(1))));
			if (b != '零')
				result.append(b).append('分');
			else result.append('整');
		} else {
			if (result.length() == 0) result.append("零元整");
			else result.append("整");
		}
		return fu+result.toString();
	}

}
