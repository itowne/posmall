<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<style type="text/css">
	.table{
		margin: 20px 5px;
	}
</style>
<div class="pageContent" layoutH="38">
	<div class="pageFormContent">
	  
	    <jsp:include page="payNotifyInfo.jsp"></jsp:include>  
	    <div class="divider"></div>
		<p>
			<label>仓管费的日期：</label>
			<input type="text" value="<fmt:formatDate value="${twareHouse.genTime}" pattern="yyyy-MM-dd" />"  readonly="readonly"/>
		</p>
		<p>
			<label>客户名称：</label>
			<input type="text" value="${tcustReg.name}"  readonly="readonly"/>
		</p>
		<p>
			<label>支付状态：</label>
			 <input type="text" readonly="readonly" value='<posmall:dict dictCode="${twareHouse.payStatus}" dictType="pay_status" />'/>
        </p>
		<p>
			<label>仓管费用：</label>
			<input type="text" value="<fmt:formatNumber value="${twareHouse.amt}" pattern="¥#,##0.00#"/>"  readonly="readonly"/>
		</p>
		<p>
		<label >仓管费的库存情况 :</label>
		</p>
		   <div class="table">
			<table class="list" width="100%">
				<thead>
					<tr>
					    <th width="100">协议编号</th>
					    <th width="100">订单编号</th>
						<th width="100">产品型号</th>
						<th width="100">下单日期</th>
						<th width="100">剩余订货量</th>
						<th width="100">仓管费用</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${twareHouseDetailList}" var="list" varStatus="status">			
					<tr>
					    <td>${list.iagmt}</td>
					    <td>${list.iord}</td>
						<td>&nbsp;<posmall:dict dictType="pdt_name_all" dictCode="${list.ipdt}"></posmall:dict></td>
						<td align="right">${list.dateVarchar}&nbsp;</td>
						<td align="right">${list.remainQuota} 台&nbsp;</td>
						<td><fmt:formatNumber value="${list.amt}" pattern="¥#,##0.00#"/></td>
						<td></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		

		<jsp:include page="payLists.jsp"></jsp:include>
    </div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" class="close">&nbsp;&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;</button>
				</div>
			</div>
		</li>
	</ul>
</div>