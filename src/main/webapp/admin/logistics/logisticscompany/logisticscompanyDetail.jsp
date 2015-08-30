<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
	<form id="pdtAddForm" method="post" action="${ctx}/admin/logistics/logisticscompany/logisticscompanyDetail.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
		    <input type="hidden" name="ilogisticsCom" value="${tlogisticsCom.ilogisticsCom}">
			<p>
				<label>物流简称：</label>
				<input type="text" name="name" value="${tlogisticsCom.name}" readonly="readonly"/>
			</p>
			<p>
				<label>物流全称：</label>
				<input type="text" name="fullname" value="${tlogisticsCom.fullname}" readonly="readonly"/>
			</p>
			<p>
				<label>时效：</label>
				<input type="text" name="aging" value="${tlogisticsCom.aging}" readonly="readonly"/>
			</p>
			<p>
				<label>是否收费：</label>
				<input type="text" name="feeFlag" value="<posmall:dict dictType="yes_no_type" dictCode="${tlogisticsCom.feeFlag}" ></posmall:dict>" readonly="readonly"/>
			</p>
			<p>
				<label>单价：</label>
				<input type="text" name="price" value="${tlogisticsCom.price}"  readonly="readonly"/>
			</p>
			<p>
				<label>状态：</label>
				<input type="text" name="logisticsStatus" value="<posmall:dict dictType="logistics_status" dictCode="${tlogisticsCom.logisticsStatus}" ></posmall:dict>" readonly="readonly"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>