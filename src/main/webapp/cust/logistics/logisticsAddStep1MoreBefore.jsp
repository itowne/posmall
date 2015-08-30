<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<script type="text/javascript">
	//异常、错误信息提示
	var _tordList = "${ord4page}";
	if (_tordList == "[]") {
		alertMsg.error("你没有可以发货的订单");
		$.pdialog.closeCurrent();
	}

	$(function() {
		$("#submitAMore").parent().hide();
		
		// tr点击支持整行选中 
		$("tr.data").click(function () {
        	$(this).children().first().children().attr("checked", "checked");
        	var id = $(this).children().first().children().val();
        	$("#submitAMore").attr("href", "${ctx}/cust/logistics/toAddStep1More.do?iord=" + id);
        	$("#submitAMore").parent().show();
        });
		
		// tr双击选中 
		$("tr.data").dblclick(function () {
        	$(this).children().first().children().attr("checked", "checked");
        	var id = $(this).children().first().children().val();
        	$("#submitAMore").attr("href", "${ctx}/cust/logistics/toAddStep1More.do?iord=" + id);
        	$("#submitAMore").click();
        });
	});
</script>
<div class="pageContent">
	<form id="" method="post" action="${ctx}/cust/logistics/toAddStep1More.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="65">
			<p style="width: 200px;float: none;font-size: 18px;">
				请选择订单：
			</p>
			<table border="1" class="list" width="95%" layoutH="130"
				style="margin: 0 auto;">
				<thead>
					<tr>
						<th width="30"></th>
						<th width="130">订单编号</th>
						<th width="130">订单创建时间</th>
						<th width="130">订单货款</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${ord4page}" var="ord4page" varStatus="tordStatus">
						<tr title="${ord4page.tips}" class="data" style="cursor: pointer;">
							<td align="center">
								<input name="iord" type="radio" value="${ord4page.tord.iord}" />
							</td>
							<td align="center">${ord4page.tord.ordNo}</td>
							<td align="center">
								<fmt:formatDate value="${ord4page.tord.genTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td align="right"><fmt:formatNumber value="${ord4page.tord.amt}" pattern="¥#,##0.00#"/></td>
							<td></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<a id="submitAMore" class="add" href="" target="dialog" mask="true"
							width="640" height="540" rel="CZWLXXGL_XZ2" title="第二步：上传数据模板"><span>&nbsp;&nbsp;下&nbsp;&nbsp;一&nbsp;&nbsp;步&nbsp;&nbsp;</span></a>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>