<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<div class="pageContent">
	<div class="pageFormContent" layoutH="65">
		<p>
			<label>协议编号：</label>
			<input type="text" readonly="readonly" value="${agmt4Page.tagmt.agmtNo}"/>
		</p>
		<p>
			<label>协议生成时间：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${agmt4Page.tagmt.genTime}"/>'/>
		</p>
		<p>	
			<label>协议更新时间：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${agmt4Page.tagmt.updTime}"/>'/>
		</p>
		<p>	
			<label>协议生效时间：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${agmt4Page.tagmt.efficientTime}"/>'/>
		</p>
		<p>
			<label>协议状态：</label>
			<input type="text" readonly="readonly" value='<posmall:dict dictCode="${agmt4Page.tagmt.agmtStatus}" dictType="agmt_status" />'/>
		</p>
 		<p>
			<label>总货款金额：</label>
			<input value='<fmt:formatNumber value="${agmt4Page.totalAmt}" pattern="#,##0.00#"/>' readonly="readonly" style="text-align: right;"/>
		</p>
		<p style="margin-bottom: 20px;">
			<label>已用保证金：</label>
			<input value='<fmt:formatNumber value="${agmt4Page.tagmt.usedDeposit}" pattern="#,##0.00#"/>' readonly="readonly" style="text-align: right;"/>
		</p>
		<c:if test="${agmt4Page.tagmt.redundantDeposit > 0}">
			<p style="margin-bottom: 20px;">
				<label>多余保证金：</label>
				<input value='<fmt:formatNumber value="${agmt4Page.tagmt.redundantDeposit}" pattern="#,##0.00#"/>' readonly="readonly" style="text-align: right;"/>
			</p>
		</c:if>
		<c:if test="${agmt4Page.tagmt.redundantDeposit < 0}">
			<p style="margin-bottom: 20px;">
				<label>保证金补交：</label>
				<input value='<fmt:formatNumber value="${-agmt4Page.tagmt.redundantDeposit}" pattern="#,##0.00#"/>' readonly="readonly" style="text-align: right;"/>
			</p>
		</c:if>
		<div class="table">
			<table class="list" width="100%">
				<thead>
					<tr>
						<th width="150">产品名称</th>
						<th width="80">单价</th>
						<th width="80">折扣率</td>
						<th width="80">折扣价</td>
						<th width="80">协议订货量</th>
						<th width="80">已购买</th>
						<th width="80">协议剩余量</th>
						<th width="80">待发货</th>
						<th width="80">已发货</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${agmt4Page.agmtDetail4PageList}" var="agmtDetail4Page">
					<tr>
						<td align="center">&nbsp;${agmtDetail4Page.tpdtHis.name}</td>
						<td align="right"><fmt:formatNumber value="${agmtDetail4Page.tpdtHis.price}" pattern="¥#,##0.00#"/>&nbsp;</td>
						<td align="right">${agmtDetail4Page.tagmtDetail.rate}&nbsp;</td>
						<td align="right"><fmt:formatNumber value="${agmtDetail4Page.tagmtDetail.rate * agmtDetail4Page.tpdtHis.price}" pattern="¥#,##0.00#"/>&nbsp;</td>
						<td align="right"><span class="num">${agmtDetail4Page.tagmtDetail.num}</span>&nbsp;台</td>
						
						<td align="right">${agmtDetail4Page.tagmtDetail.usedQuota}&nbsp;台</td>
						<td align="right">${agmtDetail4Page.tagmtDetail.remainQuota}&nbsp;台</td>
						<td align="right">${agmtDetail4Page.pendingNum}&nbsp;台</td>
						<td align="right">${agmtDetail4Page.deliveryed}&nbsp;台</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="text-align: center;margin-top: 40px;">
		<a title="协议内容" target="dialog" href="${ctx}/cust/agmt/agmtContent.do" width="840" height="620" mask="true" rel="XY" style="color: #ff5b20;">《 新大陆“易收银-YPOS”采购框架合同》</a>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;&nbsp;</button></div></div>
			</li>
		</ul>
	</div>
</div>
