<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<style type="text/css">
	#pdtPlanMonthAddForm_dayPdt p{
		width: 200px;
	}
	#pdtPlanMonthAddForm_dayPdt p label{
		width: 90px;
	}
</style>
<fieldset id="pdtPlanMonthAddForm_dayPdt">
   <c:choose>
    <c:when test="${errorMsg != null}"> 
    	${errorMsg }
    </c:when>
    <c:otherwise>
	<legend ><posmall:dict dictType="pdt_name_all" dictCode="${ipdt}"></posmall:dict></legend>
	<c:forEach var="list" items="${tpdtPlanDayList}" varStatus="status">
	<p>
		<label>${status.count}日产量：</label>
		<input type="hidden" name="tpdtPlanDayList[${status.index}].day" value="${status.count}"/>
		<input type="text" name="tpdtPlanDayList[${status.index}].num" size="8" class="required" value="${list.num}"/>台
	</p>
	</c:forEach>
	</c:otherwise>
   </c:choose>  
</fieldset>