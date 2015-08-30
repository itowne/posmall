<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<style>
#warrantyAddForm span.error {
	width: 60px;
	left: 300px;
}
</style>

<div class="pageContent" selector="h1" layoutH="42">
	<div class="pageFormContent">
		<p>
			<label>受理编号：</label>
			<input type="text" readonly="readonly" value="${twarranty.iwarranty}" />
		</p>
		<p>
			<label>产品序列号：</label>
			<input type="text" readonly="readonly" value="${twarranty.seqNo}" />
		</p>
		<p>
			<label>产品型号：</label>
			<input type="text" readonly="readonly" value="${twarranty.pdtNo}" />
		</p>
		<%-- <p>
			<label>产品名称：</label>
			<input type="text" readonly="readonly" value="${maintenance.pm}" />
		</p> --%>
		<p>
			<label>报修时间：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${twarranty.warrantyTime}"/>' />
		</p>
		<p>
			<label>受理时间：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${twarranty.acceptTime}"/>' />
		</p>
		<%-- <p>
			<label>发货日期：</label>
			<input type="text" readonly="readonly" value="${maintenance.fhDate}" />
		</p>
		<p>
			<label>保修期限：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${maintenance.warrantyPeriod}"/>' />
		</p>
		<p>
			<label>报修次数：</label>
			<input type="text" readonly="readonly" value="${maintenance.repairNum}" />
		</p> --%>
		<p>
			<label>最后修复日期：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${twarranty.repairedTime}"/>' />
		</p>
		<p>
			<label>当前状态：</label>
			<input type="text" readonly="readonly" value='<posmall:dict dictCode="${twarranty.warrantyStatus}" dictType="warranty_status" />' />
		</p>
		<p>
			<label>维修人员：</label>
			<input type="text" readonly="readonly" value="${twarranty.warrantyPerson}" />
		</p>
		<p style="height: 100px;">
			<label>故障描述：</label>
			<textarea rows="5" cols="30" readonly="readonly">${twarranty.remark}</textarea>
		</p>
		<p style="height: 100px;">
			<label>修复反馈：</label>
			<textarea rows="5" cols="30" readonly="readonly">${twarranty.repairedRemark}</textarea>
		</p>
	</div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" class="close">&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;&nbsp;</button>
				</div>
			</div>
		</li>
	</ul>
</div>
