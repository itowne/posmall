<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/admin/order/orderList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="name" value="${name}" />
	<input type="hidden" name="ordStatus" value="${ordStatus}" />
	<input type="hidden" name="payStatus" value="${payStatus}" />
	<input type="hidden" name="startDate" value="${startDate}" />
	<input type="hidden" name="endDate" value="${endDate}" />
</form>

<div class="pageHeader">
	<form name="ordForm" id="ordForm" onsubmit="return navTabSearch(this);" action="" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					客户名称：<input type="text" name="name" value="${name}" />
				</td>
				<td>
					订单状态：
					 <posmall:dictSelect selectName="ordStatus" defaultValue="${ordStatus}" dictType="ord_status"></posmall:dictSelect>
				</td>
				<td>
					支付状态：
					   <posmall:dictSelect selectName="payStatus" defaultValue="${payStatus}" dictType="pay_status"></posmall:dictSelect>
				</td>
			</tr>
			<tr>
			   <td colspan="3"> 
			     	登记日期:<input class="date" readonly="readonly" name="startDate" value="${startDate}"/> - <input class="date" readonly="readonly" name="endDate" value="${endDate}"/>
			   </td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="button">
 						<posmall:authA authCode="KHDDGL_LSXZ" href="${ctx}/admin/order/ordDownLoadHis.do" target="dialog" mask="true" title="历史下载" width="640" height="540" value="历史下载" />
					</div></li>
 				<li><div class="buttonActive"><div class="buttonContent">
 					<posmall:authButton type="button" authCode="KHDDGL_XZ" onclick="toSubmit('downLoad')" displayVal="下载"/>
 					</div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="toSubmit('query')">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
    
	<table class="list" width="100%" layoutH="118">
		<thead>
			<tr>
				<th width="80">订单编号</th>
				<th width="120">客户名称</th>
				<th width="100">金额</th>
				<th width="120">登记时间</th>
				<th width="120">更新时间</th>
				<th width="100">订单状态</th>
				<th width="100">支付状态</th>
				<th width="100">备注</th>
				<th>操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${torderList}" >
				<tr>
				  <td><a href="${ctx}/admin/order/orderDetail.do?iord=${list.iord}"
						target="dialog" mask="true" title="详细信息" width="850" height="480"
						>&nbsp;${list.ordNo}</a></td>
				     <td>&nbsp;<posmall:dict dictType="custreg_name" dictCode="${list.icust}" ></posmall:dict></td>
				     <td align="right"><fmt:formatNumber value="${list.amt}" pattern="¥#,##0.00#"/>&nbsp;</td>
				     <td>&nbsp;<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${list.genTime}"/></td>
				     <td>&nbsp;<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${list.updTime}"/></td>
				     <td>&nbsp;<posmall:dict dictCode="${list.ordStatus}" dictType="ord_status" /></td>
				     <td>&nbsp;<posmall:dict dictCode="${list.payStatus}" dictType="pay_status" /></td>
				     <td><span title="${list.remark}" style="display:inline-block;width:100px;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;overflow: hidden;">&nbsp;${list.remark}</span></td>
				     <td>
						<div class="panelBar tablebtn">
							<ul class="toolBar">
							  <c:if test='${(list.payStatus == "HAVE_PAY" || list.payStatus == "PART_PAY" ) && list.ordStatus == "WAIT_AUDIT" }'>
								<li><posmall:authA authCode="KHDDGL_SH" cssClass="icon" href="${ctx}/admin/order/toOrderAudit.do?iord=${list.iord}" target="dialog" mask="true" warn="" width="850" height="480" value="审核" /></li>
							  </c:if>
							  <c:if test='${list.ordStatus == "WAIT_AUDIT" && (list.payStatus == "WAIT_PAY" || list.payStatus == "NO_PASS")}'>
							  		<li><posmall:authA authCode="KHDDGL_CX" cssClass="delete" href="${ctx}/admin/order/removeOrder.do?iord=${list.iord}" target="ajaxTodo" mask="true" title="是否确认撤销订单" value="撤销" /></li>
							  </c:if>
							  <c:if test='${(list.ordStatus != "WAIT_AUDIT" || list.payStatus != "WAIT_PAY") && list.ordStatus != "STOP"}'>
						  		<li><posmall:authA authCode="KHDDGL_ZZDD" cssClass="delete" href="${ctx}/admin/order/toOrderStop.do?iord=${list.iord}" target="dialog" mask="true" width="850" height="480" title="终止订单" value="终止" /></li>
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
<script type="text/javascript">
	function toSubmit(flag){
		if(flag=='query'){
			document.ordForm.action="${ctx}/admin/order/orderList.do";
			document.ordForm.onsubmit();
		}else{
			//document.ordForm.action="${ctx}/admin/order/downLoadOrd.do";
			document.ordForm.action="${ctx}/admin/order/checkDownLoad.do";
			$("#ordForm").attr("target","_blank");
			document.ordForm.submit();
		}
	}
</script>