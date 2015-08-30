<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>

<div class="pageContent" selector="h1" layoutH="42">
	<div class="pageFormContent">
		<p>
			<label>产品序列号：</label>
			<input type="text" readonly="readonly" value="${maintenance.sn}" />
		</p>
		<p>
			<label>产品名称：</label>
			<input type="text" readonly="readonly" value="${maintenance.pm}" />
		</p>
		<p>
			<label>产品型号：</label>
			<input type="text" readonly="readonly" value="${maintenance.ph}" />
		</p>
		<p>
			<label>保修期起始日期：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${maintenance.lifeStartTime}"/>' />
		</p>
		<p>
			<label>保修期结束日期：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${maintenance.warrantyPeriod}"/>' />
		</p>
		<p>
			<label>报修次数：</label>
			<input type="text" readonly="readonly" value="${maintenance.repairNum}" />
		</p>
		<p>
			<label>最后修复日期：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${maintenance.lastRepairedDate}"/>' />
		</p>
		<p>
			<label>状态：</label>
			<input type="text" readonly="readonly" value='<posmall:dict dictCode="${maintenance.validStatus}" dictType="valid_status" />' />
		</p>
		<c:if test="${last != null}">
			<p>
				<label>更换设备前序列号：</label>
				<input type="text" readonly="readonly" value="${last.sn}" />
			</p>
		</c:if>
		<c:if test="${newMaintenance != null}">
			<p>
				<label>更换设备新序列号：</label>
				<input type="text" readonly="readonly" value="${newMaintenance.sn}" />
			</p>
		</c:if>
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
