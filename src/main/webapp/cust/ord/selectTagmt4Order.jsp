<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<script type="text/javascript">
	//异常、错误信息提示 
	var _tagmtList = "${tagmtList}";
	if(_tagmtList == null || _tagmtList == '' || _tagmtList == "[]") {
		alertMsg.error("尚未签署订货协议，或者协议当前状态不允许点单！");
		$.pdialog.closeCurrent();
	}
	
	$(function(){
		$("#submitA").parent().hide();
		
		// tr点击支持整行选中 
		$("tr.data").click(function () {
        	$(this).children().first().children().attr("checked", "checked");
        	var id = $(this).children().first().children().val();
        	$("#submitA").attr("href", "${ctx}/cust/ord/toOrdAdd.do?iagmt=" + id);
        	$("#submitA").parent().show();
        });
		
		// tr双击选中 
		$("tr.data").dblclick(function () {
        	$(this).children().first().children().attr("checked", "checked");
        	var id = $(this).children().first().children().val();
        	$("#submitA").attr("href", "${ctx}/cust/ord/toOrdAdd.do?iagmt=" + id);
        	$("#submitA").click();
        });
		
	});
	
</script>
<div class="pageContent">
	<form id="ordAddForm" method="post" action="${ctx}/cust/ord/toOrdAdd.do"
		class="pageForm required-validate" >
			<div class="pageFormContent" layoutH="65">
				<p style="width: 200px;float: none;font-size: 18px;">
					请选择协议：
				</p>
				<table border="1" class="list" width="95%" style="margin: 0 auto;">
					<thead>
						<tr>
							<th width="20"></th>
							<th width="100">协议编号</th>
							<th width="160">协议有效期</th>
							<th width="100">已缴纳保证金</th>
							<th width="100">已用保证金</th>
							<th width="100">剩余保证金</th>
							<th>状态</th>
						</tr>
					</thead>
					<c:forEach items="${tagmtList}" var="tagmt" >
						<tbody>
							<tr class="data" style="cursor: pointer;">
								<td>
									<input name="iagmt" type="radio" value="${tagmt.iagmt}"/>
								</td>
								<td>&nbsp;${tagmt.agmtNo}</td>
								<td align="center">
									<fmt:formatDate value="${tagmt.startTime}" pattern="yyyy-MM-dd" />至<fmt:formatDate value="${tagmt.endTime}" pattern="yyyy-MM-dd" />
								</td>
								<td align="right">
									<fmt:formatNumber value="${tagmt.deposit}" pattern="¥#,##0.00#" />&nbsp;
								</td>
								<td align="right">
									<fmt:formatNumber value="${tagmt.usedDeposit}" pattern="¥#,##0.00#" />&nbsp;
								</td>
								<td align="right">
									<fmt:formatNumber value="${tagmt.remainDeposit}" pattern="¥#,##0.00#" />&nbsp;
								</td>
								<td align="center">
									<posmall:dict dictCode="${tagmt.agmtStatus}" dictType="agmt_status" />
								</td>
							</tr>
						</tbody>
					</c:forEach>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<a id="submitA" class="add" href="${ctx}/cust/ord/toOrdAdd.do?iagmt=" 
								target="dialog" mask="true" width="880" height="620"
								rel="POSDDGL_DD2" title="第二步：订单信息填写"><span>下&nbsp;一&nbsp;步</span></a>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	</form>
</div>