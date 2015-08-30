<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
   	<input type="hidden" value="${tpayNotify.ipayNotify}" name="ipayNotify"/>
	<input type="hidden" name="payStatus"/>
    <p>
		<label>账单编号：</label>
		<input type="text" value="${tpayNotify.payNotifyNo}"  readonly="readonly"/>
	</p>
		    <p>
		<label>账单类型：</label>
		<input type="text" value="<posmall:dict dictType='pay_type' dictCode='${tpayNotify.payType}'></posmall:dict>"  readonly="readonly"/>
	</p>
	<p>
		<label>账单状态：</label>
		<input type="text" value="<posmall:dict dictType='pay_status' dictCode='${tpayNotify.payNotifyStatus}'></posmall:dict>"  readonly="readonly"/>
	</p>
	<c:if test='${tpayNotify.payNotifyStatus != "HAVE_PAY"}'>
		<p>
		<label>已支付金额：</label>
		<input type="text" id="payNotifyInfo_havepayAmt" value="<fmt:formatNumber value="${tpayNotify.havepayAmt}" pattern="¥#,##0.00#"/>"  readonly="readonly"/>
		</p>
	</c:if>

