/**    
* @Title: CTUtils.java  
* @Package com.newland.posmall.utils  
* @Description: TODO(用一句话描述该文件做什么)  
* @author chenwenjing    
* @date 2014-10-14 下午3:09:45  
* @version V1.0    
*/ 

package com.newland.posmall.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**  
 * @ClassName: CtUtils  
 * @Description: 常用工具类
 * @author chenwenjing
 * @date 2014-10-14 下午3:09:45  
 *    
 */

public class CtUtils {
	/**
	 * @前补足0至长度等于length
	 * @param t
	 * @param length
	 * @return
	 */
	public static <T> String addHeadZero(T t, int length) {
		String src = t.toString();
		if (isEmpty(src) || src.length() >= length) {
			return src;
		}
		String rest = String.format("%1$0" + (length - src.length()) + "d", 0)
				+ src;
		return rest;
	}
	/**
	 * 判断对象是否为空
	 * 
	 * @param pObj
	 * @return
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null) {
			return true;
		}
		if (pObj == "") {
			return true;
		}
		if (pObj instanceof String) {
			if (((String) pObj).trim().length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if ((pObj instanceof Map) && ((Map) pObj).size() == 0) {
			return true;
		}

		return false;
	}
	/**
	 * 
	* @Description: 分割序号字段
	* @author chenwenjing    
	* @date 2014-10-14 下午3:41:29
	 */
	public static List<String> getSn(String sn){
		List<String> snList = new ArrayList<String>();
		String[] snArray = sn.split(",");
		for(int i=0;i<snArray.length;i++){
			String[] snA = snArray[i].split("-");
			int beginIndex = Integer.parseInt(snA[0].substring(4,12));
			String preFix = snA[0].substring(0,4);
			int endIndex = beginIndex;
			if(snA.length>1){
				endIndex = Integer.parseInt(snA[1].substring(4,12));
			}
			for(int j=beginIndex;j<=endIndex;j++){
				String finalSn = preFix+addHeadZero(j,8);
				snList.add(finalSn);
			}
		}
		return snList;
	}
	/**
	 * 
	 * @Description: TODO将序列号组成序列号段显示
	 * @author chenwenjing
	 * @date 2014-11-17下午3:59:43
	 */
	public static String showSns(List<String> sList){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<sList.size();i++){
			if(i==0){
				sb.append(sList.get(i));
				if(sList.size()>1
						&&(Integer.parseInt(sList.get(i+1).substring(4,12))-Integer.parseInt(sList.get(i).substring(4,12)))>1){
					sb.append(",");
				}
			}else if(i>0&&i<(sList.size()-1)
					&&(Integer.parseInt(sList.get(i+1).substring(4,12))-Integer.parseInt(sList.get(i).substring(4,12)))>1
					&&(Integer.parseInt(sList.get(i).substring(4,12))-Integer.parseInt(sList.get(i-1).substring(4,12)))==1){
				sb.append("-"+sList.get(i)+",");
			}else if(i>0&&i<(sList.size()-1)
					&&(Integer.parseInt(sList.get(i).substring(4,12))-Integer.parseInt(sList.get(i-1).substring(4,12)))>1
					&&(Integer.parseInt(sList.get(i+1).substring(4,12))-Integer.parseInt(sList.get(i).substring(4,12)))==1){
				sb.append(sList.get(i));
			}else if(i>0&&i<(sList.size()-1)
					&&(Integer.parseInt(sList.get(i).substring(4,12))-Integer.parseInt(sList.get(i-1).substring(4,12)))>1
					&&(Integer.parseInt(sList.get(i+1).substring(4,12))-Integer.parseInt(sList.get(i).substring(4,12)))>1){
				sb.append(sList.get(i)+",");
			}else if(i==(sList.size()-1)&&(sList.size()-1)!=0){
				if((Integer.parseInt(sList.get(i).substring(4,12))-Integer.parseInt(sList.get(i-1).substring(4,12)))>1){
					sb.append(sList.get(i));
				}else{
					sb.append("-"+sList.get(i));
				}
			}
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		List<String> sList = new ArrayList<String>();
		
		sList.add("Q1NL10210801");
		
		sList.add("Q1NL10210805");
		
		sList.add("Q1NL10210807");
		
		sList.add("Q1NL10210811");
		sList.add("Q1NL10210812");
		sList.add("Q1NL10210813");
		sList.add("Q1NL10210814");
		sList.add("Q1NL10210815");
		sList.add("Q1NL10210816");
		sList.add("Q1NL10210817");
		sList.add("Q1NL10210818");

		sList.add("Q1NL10210820");
		sList.add("Q1NL10210821");
		sList.add("Q1NL10210822");
		sList.add("Q1NL10210823");
		sList.add("Q1NL10210824");
		sList.add("Q1NL10210825");
		sList.add("Q1NL10210826");
		sList.add("Q1NL10210827");
		sList.add("Q1NL10210828");
		sList.add("Q1NL10210829");
		
		sList.add("Q1NL10210833");
		
		sList.add("Q1NL10210839");
		sList.add("Q1NL10210840");
		sList.add("Q1NL10210841");
		sList.add("Q1NL10210842");
		sList.add("Q1NL10210843");
		sList.add("Q1NL10210844");
		sList.add("Q1NL10210845");
		sList.add("Q1NL10210846");
		
		sList.add("Q1NL10210850");
		sList.add("Q1NL10210851");
		sList.add("Q1NL10210852");
		sList.add("Q1NL10210853");
		sList.add("Q1NL10210854");
		sList.add("Q1NL10210855");
		
		sList.add("Q1NL10210859");
		sList.add("Q1NL10210864");
		sList.add("Q1NL10210865");
		sList.add("Q1NL10210866");
		sList.add("Q1NL10210869");
		String s = CtUtils.showSns(sList);
		
		System.out.println(s);
		
		
	}
}
