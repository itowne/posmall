<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
	<form method="post" action="${ctx}/admin/cust/custRateModify.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
		   	
		   <c:forEach var="list" items="${tcustRateList}" varStatus="status">	  
			<fieldset>
				<legend><posmall:dict dictType="pdt_name_all" dictCode="${list.ipdt}"></posmall:dict></legend>
				<p>
				 	<input type="hidden"  name="tcustRateList[${status.index}].ipdt" value="${list.ipdt}" />
				 	<input type="hidden"  name="tcustRateList[${status.index}].icustRate" value="${list.icustRate}" />
				 	<input type="hidden"  name="tcustRateList[${status.index}].icust" value="${list.icust}" />
					<label> 折扣率：</label> <input type="text"  name="tcustRateList[${status.index}].rate" class="required deposit_rate" value="${list.rate}" />		
				</p>		       			
			</fieldset>
		  </c:forEach>	
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit" >&nbsp;&nbsp;确认修改&nbsp;&nbsp;</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>