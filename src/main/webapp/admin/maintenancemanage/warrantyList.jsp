<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/admin/warranty/warrantyList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	
	<input type="hidden" name="seqNo" value="${condition.seqNo}" />
	<input type="hidden" name="warrantyStatus" value="${condition.warrantyStatus}" />
	<input type="hidden" name="custName" value="${condition.custName}" />
	<input type="hidden" name="startTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startTime}"/>' />
	<input type="hidden" name="endTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endTime}"/>' />
</form>


<div class="pageHeader">
	<form name="warrantyForm" id="warrantyForm" onsubmit="return navTabSearch(this);" action="" method="post">
	<div class="searchBar">
        <table class="searchContent">
			<tr>
			    <td>
					产品序列号：<input type="text" name="seqNo" value="${condition.seqNo}" />
				</td>
				<td>
					状态：
					<posmall:dictSelect selectName="warrantyStatus" defaultValue="${condition.warrantyStatus}" dictType="warranty_status" />
				</td>
				<td colspan="3"> 
			     	报修日期：<input class="date" readonly="readonly" name="startTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${startTime}"/>'/> - 
			     	<input class="date" readonly="readonly" name="endTime" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${endTime}"/>'/>
			    </td>
			</tr>
			<tr>
				<td>
					客户名称：<input type="text" name="custName" value="${condition.custName}" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
				   <div class="button">
					  <posmall:authA authCode="BXSL_LSXZ" href="${ctx}/admin/warranty/warrantyDownLoadHis.do" target="dialog" mask="true" title="历史下载" width="640" height="540" value="历史下载" />
				   </div>
				</li>
				<li><div class="buttonActive"><div class="buttonContent">
					<posmall:authButton type="button" authCode="BXSL_XZ" onclick="toSubmit('downLoad')" displayVal="报修受理" />
					</div></div>
				</li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="toSubmit('query')">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
			    <th width="120">受理编号</th>
			    <th width="120">客户名称</th>
			    <th width="120">产品序列号</th>
				<th width="120">产品型号</th>
				<th width="120">报修时间</th>
				<th width="120">修复日期</th>
				<th width="80">状态</th>
				<th width="120">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		  <c:forEach var="warranty" items="${list}">
			<tr>
			    <td align="left">&nbsp;<a href="${ctx}/admin/warranty/warrantyDetail.do?iwarranty=${warranty.iwarranty}" target="dialog" mask="true" title="详细信息" width="850" height="480">&nbsp;${warranty.iwarranty}</a></td>
			    <td align="left">&nbsp;${warranty.custName}</td>
			    <td align="left">&nbsp;${warranty.seqNo}</td>
			    <td align="left">&nbsp;${warranty.pdtNo}</td>
				<td align="center">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" value="${warranty.warrantyTime}"/></td>
				<td align="center">&nbsp;<fmt:formatDate pattern="yyyy-MM-dd" value="${warranty.repairedTime}"/></td>
				<td align="left">&nbsp;<posmall:dict dictCode="${warranty.warrantyStatus}" dictType="warranty_status" /></td>
				<td align="left">
					<div class="panelBar tablebtn">
						<ul class="toolBar">
						    <c:if test="${warranty.warrantyStatus == 'HAVE_ACCEPT'}">
						       <li><posmall:authA authCode="BXSL_XF" cssClass="icon" href="${ctx}/admin/warranty/toWarrantyRepaired.do?iwarranty=${warranty.iwarranty}" target="dialog" mask="true"  width="500" height="420"  value="修复" /></li>
						    </c:if>
						    <c:if test="${warranty.warrantyStatus == 'HAVE_SUBMIT' || warranty.warrantyStatus == 'HAVE_ACCEPT'}">
							   <li><posmall:authA authCode="BXSL_CX" cssClass="delete" href="${ctx}/admin/warranty/warrantyCancel.do?iwarranty=${warranty.iwarranty}" target="ajaxTodo" mask="true" title="是否确认撤销" value="撤销" /></li>
							</c:if>
						</ul>
					</div>
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
				<option <c:if test="${numPerPage==10}">selected="selected"</c:if> value="10">10</option>
				<option <c:if test="${numPerPage==20}">selected="selected"</c:if> value="20">20</option>
				<option <c:if test="${numPerPage==50}">selected="selected"</c:if> value="50">50</option>
				<option <c:if test="${numPerPage==100}">selected="selected"</c:if> value="100">100</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
	</div>
</div>
<script type="text/javascript">

	function toSubmit(flag){
	 if(flag=="query"){
			document.warrantyForm.action="${ctx}/admin/warranty/warrantyList.do";
			document.warrantyForm.onsubmit();
		}else{
			document.warrantyForm.action="${ctx}/admin/warranty/checkDownLoad.do";
			$("#warrantyForm").attr("target","_blank");
			document.warrantyForm.submit();
		}
	}
	
</script>