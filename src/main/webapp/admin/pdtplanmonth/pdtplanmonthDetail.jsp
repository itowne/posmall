<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<style type="text/css">
	#pdtPlanMonthDetailForm_dayPdt p{
		width:  170px;
	}
	#pdtPlanMonthDetailForm_dayPdt p span{
		width: 80px;
		display: inline-block;
	}
</style>
<div class="pageContent">
	<form method="post" action="pdtplanmonthmanage/pdtplanmonthmanageDetail.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<table >
			  <tr>
			        <td>
							<label >年度：${tpdtPlanMonth.year}</label>
					</td>
					<td >
							<label >月份：${tpdtPlanMonth.month}</label>
					</td>
					<td>
					<label>产品：<posmall:dict dictType="pdt_name_all" dictCode="${tpdtPlanMonth.ipdt}"></posmall:dict></label>
					</td>
				</tr>
				  </table>
					<div class="divider"/>
				    	<fieldset id="pdtPlanMonthDetailForm_dayPdt">
							<legend ><posmall:dict dictType="pdt_name_all" dictCode="${tpdtPlanMonth.ipdt}"></posmall:dict></legend>
							<c:forEach var="list" items="${tpdtPlanDayList}" varStatus="status">
								<p>
									<span>${status.count}日产量：</span>
							        <span>${list.num}台</span>
							</c:forEach>
						</fieldset>
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
	</form>
</div>