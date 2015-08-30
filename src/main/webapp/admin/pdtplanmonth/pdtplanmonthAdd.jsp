<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
	  function inputDayPdt(){
		 
      	$("#ppmAdd_pdtPlanDay").loadUrl("${ctx}/admin/pdtplanmonth/pdtPlanDayList.do", 
	   	 {"year":$("#ppmAdd_selectYear option:selected").val(),
    	  "month":$("#ppmAdd_selectMonth option:selected").val(),
    	  "ipdt":$("#ppmAdd_selectPdt option:selected").val()
    	  });
      
      }
	  
	  function cleanDays(){
		  $("#pdtPlanMonthAddForm_dayPdt").html("请重新生成日排产信息");
	  }
	  

</script>
<div class="pageContent">
	<form id="pdtPlanMonthAddForm" method="post" action="${ctx}/admin/pdtplanmonth/pdtPlanMonthAdd.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<table >
			   <tr>
				    <td>
							<label style="width: 40px">年度：</label>
							 <select id="ppmAdd_selectYear" name="year" onchange="cleanDays();" >
						      <c:forEach var="map" items="${yearMap}" >
						          <option value="${map.key}" <c:if test="${year==map.key}">selected="selected"</c:if>>${map.value}</option>
						      </c:forEach>
						    </select>
					</td>
					<td >
							<label style="width: 40px">月份：</label>
							<select name="month" id="ppmAdd_selectMonth" onchange="cleanDays();">
								<option value="1" <c:if test='${month=="1"}'>selected="selected"</c:if>>一月</option>
								<option value="2" <c:if test='${month=="2"}'>selected="selected"</c:if>>二月</option>
								<option value="3" <c:if test='${month=="3"}'>selected="selected"</c:if>>三月</option>
								<option value="4" <c:if test='${month=="4"}'>selected="selected"</c:if>>四月</option>
								<option value="5" <c:if test='${month=="5"}'>selected="selected"</c:if>>五月</option>
								<option value="6" <c:if test='${month=="6"}'>selected="selected"</c:if>>六月</option>
								<option value="7" <c:if test='${month=="7"}'>selected="selected"</c:if>>七月</option>
								<option value="8" <c:if test='${month=="8"}'>selected="selected"</c:if>>八月</option>
								<option value="9" <c:if test='${month=="9"}'>selected="selected"</c:if>>九月</option>
								<option value="10" <c:if test='${month=="10"}'>selected="selected"</c:if>>十月</option>
								<option value="11" <c:if test='${month=="11"}'>selected="selected"</c:if>>十一月</option>
								<option value="12" <c:if test='${month=="12"}'>selected="selected"</c:if>>十二月</option>
							</select>
					</td>
					<td>
					<label style="width: 40px">产品：</label>
					      <select name="ipdt" id="ppmAdd_selectPdt" onchange="cleanDays();">
						      <c:forEach var="map" items="${pdtNameMap}">
						          <option value="${map.key}" <c:if test="${ipdt==map.key}">selected="selected"</c:if>>${map.value}</option>
						      </c:forEach>
					    </select>
					</td>
				</tr>
		   </table>
					   <div class="formBar">
						<ul>
							<li><div class="button">
									<div class="buttonContent">
										<button type="button" onclick="inputDayPdt();">&nbsp;&nbsp;手工输入&nbsp;&nbsp;</button>
									</div>
								</div></li>
<!-- 							<li><div class="button"> -->
<!-- 									<div class="buttonContent"> -->
<!-- 										<button type="button" onclick="excelDayPdt();">&nbsp;&nbsp;excel导入&nbsp;&nbsp;</button> -->
<!-- 									</div> -->
<!-- 								</div></li> -->
						</ul>
					</div>
					<div class="divider"/>
					<div id="ppmAdd_pdtPlanDay">
					<s:include value="pdtplanday.jsp"></s:include>
					</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="submit">&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;&nbsp;交&nbsp;&nbsp;</button>
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