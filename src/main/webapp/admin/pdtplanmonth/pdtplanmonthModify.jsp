<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<style type="text/css">
	#pdtPlanMonthModifyForm_dayPdt p{
		width: 260px;
	}
	#pdtPlanMonthModifyForm_dayPdt p label{
		width: 70px;
	}
</style>
<script type="text/javascript">
    if("${validateMsg}" == null || "${validateMsg}"==""){
    	
    }else{
    	alertMsg.info("${validateMsg}");
    	$.pdialog.closeCurrent();
    }
    
	function inputDayPdt(){
	 
  	$("#ppmAdd_pdtPlanDay").loadUrl("${ctx}/admin/pdtplanmonth/pdtPlanDayList.do", 
   	 {"year":$("#ppmModify_selectYear option:selected").val(),
	  "month":$("#ppmModify_selectMonth option:selected").val(),
	  "ipdt":$("#ppmModify_selectPdt option:selected").val()
	  });
  
  }
  
  function cleanDays(){
	  alertMsg.confirm("是否重新生成日排产计划？",{
			okCall: function(){
				 $("#pdtPlanMonthModifyForm_dayPdt").html("<s:include value='pdtplanday.jsp'></s:include>");
					$("#pdtPlanMonthModifyForm_dayPdt").loadUrl("${ctx}/admin/pdtplanmonth/pdtPlanDayList.do", 
				   	 {"year":$("#ppmModify_selectYear option:selected").val(),
					  "month":$("#ppmModify_selectMonth option:selected").val(),
					  "ipdt":$("#ppmModify_selectPdt option:selected").val()
					  });
					$("#isBind").val("Y");
			}
	  });
  }
  
  function selectPdtCleanDays(){
	  if("Y" == $("#isBind").val()){
		  alertMsg.confirm("是否重新生成日排产计划？",{
				okCall: function(){
					 $("#pdtPlanMonthModifyForm_dayPdt").html("<s:include value='pdtplanday.jsp'></s:include>");
						$("#pdtPlanMonthModifyForm_dayPdt").loadUrl("${ctx}/admin/pdtplanmonth/pdtPlanDayList.do", 
					   	 {"year":$("#ppmModify_selectYear option:selected").val(),
						  "month":$("#ppmModify_selectMonth option:selected").val(),
						  "ipdt":$("#ppmModify_selectPdt option:selected").val()
						  });
				}
		  });
	  }
  }
  
  function validateDayPlan(){
	  
	  var flag = true;
	  $("#pdtPlanMonthModifyForm input[class='min']").each(function() {
	    	oldNum = parseInt($(this).val());
	    	newNum =  parseInt($(this).prev().val());
	    	if(oldNum > newNum){
	    		flag = false;
	    	}
	  });
	  if(flag){
	  	$("#pdtPlanMonthModifyForm").submit();
	  }else{
		  alertMsg.info("修改日排产是量不能小于已使用额度！");
		  return;
	  }
  }
  
</script>
<div class="pageContent">
	<form id="pdtPlanMonthModifyForm" method="post" action="${ctx}/admin/pdtplanmonth/pdtPlanMonthModify.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
		<input type="hidden" id="isBind" value="N" />
			<table >
			  <tr>
			        <c:choose>
			           <c:when test="${flag== true || ( minFlag == true || minFlag == null)}">
			              <td>
							<label style="width: 40px">年度：</label>
							<input type="hidden" name="ipdtPlanMonth" value="${tpdtPlanMonth.ipdtPlanMonth}">
							<input type="text" readonly="readonly" name="year" value="${tpdtPlanMonth.year}">
							 
							</td>
							<td>
								<label style="width: 40px">月份：</label>
								<input type="hidden" name="month" value="${tpdtPlanMonth.month}">
								<input type="text" readonly="readonly" value="${tpdtPlanMonth.month}月">
									
							</td>
							<td>
							<label style="width: 40px">产品：</label>
							<input type="hidden" readonly="readonly" name="ipdt" value="${tpdtPlanMonth.ipdt}">
							<input type="text" readonly="readonly"  value="<posmall:dict dictType='pdt_name_all' dictCode='${tpdtPlanMonth.ipdt}'></posmall:dict>">
						    </td>
			           </c:when>
			           <c:otherwise>
			                <td>
							<label style="width: 40px">年度：</label>
							<input type="hidden" name="ipdtPlanMonth" value="${tpdtPlanMonth.ipdtPlanMonth}">
							 <select id="ppmModify_selectYear" name="year" onchange="cleanDays();">
						      <c:forEach var="map" items="${yearMap}">
						          <option value="${map.key}" <c:if test="${tpdtPlanMonth.year==map.key}">selected="selected"</c:if>>${map.value}</option>
						      </c:forEach>
						    </select>
							</td>
							<td>
									<label style="width: 40px">月份：</label>
									<select id="ppmModify_selectMonth" name="month" onchange="cleanDays();">
									<option value="1" <c:if test='${tpdtPlanMonth.month=="1"}'>selected="selected"</c:if>>一月</option>
									<option value="2" <c:if test='${tpdtPlanMonth.month=="2"}'>selected="selected"</c:if>>二月</option>
									<option value="3" <c:if test='${tpdtPlanMonth.month=="3"}'>selected="selected"</c:if>>三月</option>
									<option value="4" <c:if test='${tpdtPlanMonth.month=="4"}'>selected="selected"</c:if>>四月</option>
									<option value="5" <c:if test='${tpdtPlanMonth.month=="5"}'>selected="selected"</c:if>>五月</option>
									<option value="6" <c:if test='${tpdtPlanMonth.month=="6"}'>selected="selected"</c:if>>六月</option>
									<option value="7" <c:if test='${tpdtPlanMonth.month=="7"}'>selected="selected"</c:if>>七月</option>
									<option value="8" <c:if test='${tpdtPlanMonth.month=="8"}'>selected="selected"</c:if>>八月</option>
									<option value="9" <c:if test='${tpdtPlanMonth.month=="9"}'>selected="selected"</c:if>>九月</option>
									<option value="10" <c:if test='${tpdtPlanMonth.month=="10"}'>selected="selected"</c:if>>十月</option>
									<option value="11" <c:if test='${tpdtPlanMonth.month=="11"}'>selected="selected"</c:if>>十一月</option>
									<option value="12" <c:if test='${tpdtPlanMonth.month=="12"}'>selected="selected"</c:if>>十二月</option>
								</select>
							</td>
							<td>
							<label style="width: 40px">产品：</label>
							     <select name="ipdt" id="ppmModify_selectPdt" onchange="selectPdtCleanDays();">
								      <c:forEach var="map" items="${pdtNameMap}">
								          <option value="${map.key}" <c:if test="${tpdtPlanMonth.ipdt==map.key}">selected="selected"</c:if>>${map.value}</option>
								      </c:forEach>
							    </select>
						    </td>
			           
			           </c:otherwise>
			        </c:choose>
			   
				</tr>
				  </table>
					<div class="divider"></div>
					<div id="pdtPlanMonthModifyForm_dayPdt">
					 <fieldset>
						<legend ><posmall:dict dictType="pdt_name_all" dictCode="${tpdtPlanMonth.ipdt}"></posmall:dict></legend>
						<c:choose>
						   <c:when test="${flag== true  && minFlag== true }">
							<c:forEach var="list" items="${tpdtPlanDayList}" varStatus="status">
								<p> 
								<label>${list.day}日产量：</label>
								<input type="hidden" name="tpdtPlanDayList[${status.index}].ipdtPlanDay" value="${list.ipdtPlanDay}"/>
								<input type="text"  <c:if test="${modifyDay >= list.day}">readonly="readonly"</c:if>  name="tpdtPlanDayList[${status.index}].num" size="8" class="required" value="${list.num}"/>台
								<c:if test="${modifyDay < list.day }">(已使用:${list.tpdtPlanDayQuota.usedQuota}<input type="hidden" class="min" value="${list.tpdtPlanDayQuota.usedQuota}">)</c:if></p>
							</c:forEach>
						   </c:when>
						    <c:when test="${flag== false && minFlag == null }">
							<c:forEach var="list" items="${tpdtPlanDayList}" varStatus="status">
								<p> 
								<label>${list.day}日产量：</label>
								<input type="hidden" name="tpdtPlanDayList[${status.index}].ipdtPlanDay" value="${list.ipdtPlanDay}"/>
								<input type="text"   name="tpdtPlanDayList[${status.index}].num" size="8" class="required" value="${list.num}"/>台
								(已使用:${list.tpdtPlanDayQuota.usedQuota}<input type="hidden" class="min" value="${list.tpdtPlanDayQuota.usedQuota}">)</p>
							</c:forEach>
						   </c:when>
						   <c:when test="${flag== true }">
							<c:forEach var="list" items="${tpdtPlanDayList}" varStatus="status">
								<p> 
								<label>${list.day}日产量：</label>
								<input type="hidden" name="tpdtPlanDayList[${status.index}].ipdtPlanDay" value="${list.ipdtPlanDay}"/>
								<input type="text"  <c:if test="${modifyDay >= list.day}">readonly="readonly"</c:if>   name="tpdtPlanDayList[${status.index}].num" size="8" class="required" value="${list.num}"/>台
								<c:if test="${modifyDay < list.day}">(已使用:${list.tpdtPlanDayQuota.usedQuota}<input type="hidden" class="min" value="${list.tpdtPlanDayQuota.usedQuota}">)</c:if></p>
							</c:forEach>
						   </c:when>
						   <c:when test="${minFlag == true }">
							<c:forEach var="list" items="${tpdtPlanDayList}" varStatus="status">
								<p> 
								<label>${list.day}日产量：</label>
								<input type="hidden" name="tpdtPlanDayList[${status.index}].ipdtPlanDay" value="${list.ipdtPlanDay}"/>
								<input type="text"     name="tpdtPlanDayList[${status.index}].num" size="8" class="required" value="${list.num}"/>台
								(已使用:${list.tpdtPlanDayQuota.usedQuota}<input type="hidden" class="min" value="${list.tpdtPlanDayQuota.usedQuota}">)</p>
							</c:forEach>
						   </c:when>
							<c:otherwise>
							<c:forEach var="list" items="${tpdtPlanDayList}" varStatus="status">
								<p> 
								<label>${list.day}日产量：</label>
								<input type="hidden" name="tpdtPlanDayList[${status.index}].ipdtPlanDay" value="${list.ipdtPlanDay}"/>
								<input type="text"  name="tpdtPlanDayList[${status.index}].num" size="8" class="required" value="${list.num}"/>台
								</p>
							</c:forEach>
							</c:otherwise>
						</c:choose>
					</fieldset>
				    </div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onclick="validateDayPlan();" >&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;定&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>