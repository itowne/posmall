<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/admin/paynotice/paynoticeList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="name" value="${name}" />
	<input type="hidden" name="payType" value="${payType}" />
	<input type="hidden" name="payNotifyStatus" value="${payNotifyStatus}" />
	<input type="hidden" name="startDate" value="${startDate}" />
	<input type="hidden" name="endDate" value="${endDate}" />
</form>
  

<div class="pageHeader">
<script type="text/javascript">

 function submitBefore(flag){
	 if(flag=='query'){
		 var startTime = $("#adminPayNoticeListForm input[name='startDate']").val(),endTime = $("#adminPayNoticeListForm input[name='endDate']").val();
		  if( startTime && endTime && startTime > endTime ){
			   alertMsg.info("起始日期不能大于结束日期");
			   return false;
		   }else{
		   	document.adminPayNoticeListForm.action="${ctx}/admin/paynotice/paynoticeList.do";
			document.adminPayNoticeListForm.onsubmit();
		   }
	 }else{
		 document.adminPayNoticeListForm.action="${ctx}/admin/paynotice/downLoadPayNotify.do";
		 $("#adminPayNoticeListForm").attr("target","_blank");
		 document.adminPayNoticeListForm.submit();
	 }
 }
 
</script>
	<form id="adminPayNoticeListForm" name="adminPayNoticeListForm" onsubmit="return navTabSearch(this);" action="" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					客户名称：<input type="text" name="name" value="${name}" />
				</td>
				<td>
					账单类型：<posmall:dictSelect selectName="payType" dictType="pay_type" defaultValue="${payType}"/>
				</td>
				<td>
					支付状态： <posmall:dictSelect selectName="payNotifyStatus" dictType="pay_status" defaultValue="${payNotifyStatus}"/>
				</td>
				<tr>
			   <td colspan="3"> 
			     	登记日期:<input  class="date" readonly="readonly" name="startDate" value="${startDate}"/> - <input class="date" readonly="readonly" name="endDate" value="${endDate}"/>
			   </td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent">
					<posmall:authButton type="button" authCode="FKTZSGL_XZ" onclick="submitBefore('downLoad')" displayVal="下载"/>
					</div></div>
				</li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitBefore('query');">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
 
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80">账单编号</th>
				<th width="80">客户名称</th>
				<th width="85">协议编号</th>
				<th width="120">订单编号</th>
				<th width="120">物流单编号</th>
				<th width="80">账单类型</th>
				<th width="100">金额</th>
				<th width="150">生成时间</th>
				<th width="100">状态</th>
				<th width="150">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${tpayNotifyList}" >
				<tr>
				<td><a href="${ctx}/admin/paynotice/paynoticeDetail.do?ipayNotify=${list[0]}" 
						target="dialog" rel="FKTZSGL_XXXX" mask="true" title="详细信息" width="850" height="530"><span>${list[4]}</span></a></td>
				<td><posmall:dict dictType="custreg_name" dictCode="${list[1]}"/></td>
				<td>${list[10]}</td>
				<td>${list[11]}</td>
				<td>${list[12]}</td>
				<td><posmall:dict dictType="pay_type" dictCode="${list[3]}"></posmall:dict></td>
				<td align="right"><fmt:formatNumber value="${list[9]}" pattern="¥#,##0.00#"/>&nbsp;</td>
				<td><fmt:formatDate value="${list[6]}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><posmall:dict dictType="pay_status" dictCode="${list[8]}"></posmall:dict></td>
				<td>
				 <c:choose>
					<c:when test='${list[8] == "WAIT_AUDIT"}'>
					   <div class="panelBar tablebtn">
							<ul class="toolBar">
								<li><posmall:authA authCode="FKTZSGL_SH" cssClass="icon" rel="FKTZSGL_SH"  href="${ctx}/admin/paynotice/toPaynoticeAudit.do?ipayNotify=${list[0]}" target="dialog" mask="true" width="850" height="530" value="审核" /></li>
							</ul>
						</div>
				   </c:when>
				   <c:otherwise></c:otherwise>
				 </c:choose>  
				</td>
				<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option <c:if test="${numPerPage==10}">selected="selected"</c:if> value="10" >10</option>
				<option <c:if test="${numPerPage==20}">selected="selected"</c:if> value="20">20</option>
				<option <c:if test="${numPerPage==50}">selected="selected"</c:if> value="50">50</option>
				<option <c:if test="${numPerPage==100}">selected="selected"</c:if> value="100">100</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>
		
		<div class="pagination"  targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
	</div>
</div>