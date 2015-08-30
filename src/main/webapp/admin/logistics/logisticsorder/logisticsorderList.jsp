<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<form id="pagerForm" method="post" action="${ctx}/admin/logistics/logisticsorder/logisticsorderList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="name" value="${name}" />
	<input type="hidden" name="innerOrdno" value="${tlogisticsOrd.innerOrdno}" />
	<input type="hidden" name="logisticsOrdStatus" value="${tlogisticsOrd.logisticsOrdStatus}" />
	<input type="hidden" name="payStatus" value="${tlogisticsOrd.payStatus}" />
	<input type="hidden" name="consigneeName" value="${consigneeName}" />
</form>


<div class="pageHeader">
	<form name="logisticsForm" id="logisticsForm" onsubmit="return navTabSearch(this);" action="" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					客户名称：<input type="text" name="name" value="${name}" />
				</td>
				<td>
					物流单编号：<input type="text" name="innerOrdno" value="${tlogisticsOrd.innerOrdno}" />
				</td>
				<td>
					物流单状态：
					<posmall:dictSelect selectName="logisticsOrdStatus" defaultValue="${tlogisticsOrd.logisticsOrdStatus}" dictType="logistics_ord_status"></posmall:dictSelect>
				</td>
				<td>
					支付状态：
					<posmall:dictSelect selectName="payStatus" defaultValue="${tlogisticsOrd.payStatus}" dictType="pay_status"></posmall:dictSelect>
				</td>
				<td>
					联系人：<input type="text" name="consigneeName" value="${consigneeName}" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="button">
					<posmall:authA authCode="WLDGL_LSXZ" href="${ctx}/admin/logistics/logisticsorder/logisticsordDownLoadHis.do" target="dialog" mask="true" title="历史下载" width="640" height="540" value="历史下载" />
					</div></li>
				<li><div class="buttonActive"><div class="buttonContent">
					<posmall:authButton type="button" authCode="WLDGL_XZ" onclick="toSubmit('downLoad')" displayVal="下载"/>
					</div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="toSubmit('query')">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="list" width="100%" layoutH="98">
		<thead>
			<tr>
				<th width="100" align="center">物流单编号</th>
				<th width="100" align="center">订单编号</th>
				<th width="100" align="center">客户名称</th>
				<th width="80" align="center">物流单状态</th>
				<th width="80" align="center">支付状态</th>
				<th width="80" align="center">是否多地址</th>
				<th width="80" align="center">发货数量</th>
				<th width="100" align="center">预计发货日期</th>
				<th width="100" align="center">物流费用</th>
				<th width="180" align="center">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tLogisticsOrdList}" var="list">
			<tr>
				<td align="left">&nbsp;
					<c:choose>
					   	<c:when test='${list.ifile == "-1"}'>
							<a target="dialog" href="${ctx}/admin/logistics/logisticsorder/logisticsorderDetail.do?ilogisticsOrd=${list.ilogisticsOrd}" width="850" height="480" mask="true" rel="WLDGL_XQ">${list.innerOrdno}</a>
						</c:when>
					   	<c:otherwise>
					  	 	<a target="dialog" href="${ctx}/admin/logistics/logisticsorder/logisticsorderDetailMore.do?ilogisticsOrd=${list.ilogisticsOrd}" width="1050" height="480" mask="true" rel="WLDGL_XQ">${list.innerOrdno}</a>
					   	</c:otherwise>
					 </c:choose>
				</td>
				<td align="left"><a href="${ctx}/admin/order/orderDetail.do?iord=${list.tord.iord}"
						target="dialog" mask="true" title="详细信息" width="850" height="480"
						>&nbsp;${list.tord.ordNo}</td>
				<td>&nbsp;<posmall:dict dictCode="${list.icust}" dictType="custreg_name" /></td>
				<td>&nbsp;<posmall:dict dictCode="${list.logisticsOrdStatus}" dictType="logistics_ord_status" /></td>
			    <td>&nbsp;<posmall:dict dictCode="${list.payStatus}" dictType="pay_status" /></td>
				<td>
				   <c:choose>
				   	<c:when test='${list.ifile == "-1"}'>否</c:when>
				   	<c:otherwise>是</c:otherwise>
				   </c:choose>
				</td>
				<td align="right">${list.num}&nbsp;</td>
				<td align="center">&nbsp;<fmt:formatDate value="${list.specifyDelivery}" pattern="yyyy-MM-dd"/></td>
				<td align="right"><fmt:formatNumber value="${list.logisticsFreight}" pattern="¥#,##0.00#"/>&nbsp;</td>
				<td>
					<div class="panelBar tablebtn">
							<ul class="toolBar">
							  <c:if test='${list.logisticsOrdStatus == "WAIT_AUDIT"}'>
							     <c:choose>
								   	<c:when test='${list.ifile == "-1"}'>
										<li><posmall:authA authCode="WLDGL_SH" cssClass="add" href="${ctx}/admin/logistics/logisticsorder/toAuditLogisticsOrd.do?ilogisticsOrd=${list.ilogisticsOrd}" target="dialog" mask="true" warn="" width="850" height="480" value="审核"></posmall:authA></li>
									</c:when>
								   	<c:otherwise>
										<li><posmall:authA authCode="WLDGL_SH" cssClass="add" href="${ctx}/admin/logistics/logisticsorder/toAuditMoreLogisticsOrd.do?ilogisticsOrd=${list.ilogisticsOrd}" target="dialog" mask="true" warn="" width="950" height="480" value="审核"></posmall:authA></li>
								   	</c:otherwise>
								 </c:choose>
							    <li> 
							    	<posmall:authA authCode="WLDGL_CX" cssClass="delete" target="ajaxTodo" href="${ctx}/admin/logistics/logisticsOrdCancel.do?ilogisticsOrd=${list.ilogisticsOrd}" title="确定要撤销吗?" rel="WLDGL_CX" value="撤销" />
							    </li>
							  </c:if>
							  <c:if test='${list.logisticsOrdStatus == "HAVE_LIBRARY" || list.logisticsOrdStatus == "SHIPPED" || list.logisticsOrdStatus == "PARTIAL_DELIVERY"}'>
							 		<c:choose>
								   	<c:when test='${list.ifile == "-1"}'>
										<li><posmall:authA authCode="WLDGL_TXWLXX" cssClass="add" href="${ctx}/admin/logistics/logisticsorder/toFillActual.do?ilogisticsOrd=${list.ilogisticsOrd}"  target="dialog" mask="true" warn="" width="850" height="480"  value="填写物流信息" /></li>
									</c:when>
								   	<c:otherwise>
										<li><posmall:authA authCode="WLDGL_TXWLXX" cssClass="add" href="${ctx}/admin/logistics/logisticsorder/toFillActualMore.do?ilogisticsOrd=${list.ilogisticsOrd}"  target="dialog" mask="true" warn="" width="1250" height="480" value="填写物流信息" /></li>
								   	</c:otherwise>
								 </c:choose>
							  </c:if>
							  <%-- <li><posmall:authA authCode="WLDGL_CD" cssClass="add" href="${ctx}/admin/logistics/logisticsorder/toRemoveSingleLogisticsOrd.do?ilogisticsOrd=${list.ilogisticsOrd}" target="dialog" mask="true" warn="" width="950" height="480" value="拆单" rel="WLDGL_CD"></posmall:authA></li> --%>
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
	 if(flag=="query"){
			document.logisticsForm.action="${ctx}/admin/logistics/logisticsorder/logisticsorderList.do";
			document.logisticsForm.onsubmit();
		}else{
			//document.logisticsForm.action="${ctx}/admin/logistics/logisticsorder/logisticsordDownLoad.do";
			document.logisticsForm.action="${ctx}/admin/logistics/logisticsorder/checkDownLoad.do";
			$("#logisticsForm").attr("target","_blank");
			document.logisticsForm.submit();
		}
	}
	
</script>