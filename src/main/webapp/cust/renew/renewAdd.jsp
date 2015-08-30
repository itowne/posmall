<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<script type="text/javascript">
	$(".dialogHeader_c > h1").html("第二步：填写续保数据");
	
	/*********************计算续保合计费用**************************/
	setRenewAmt($("select[name = 'renewLife']").val());
	
	$("select[name = 'renewLife']").on("change", function() {
		setRenewAmt($(this).val());
	});
	
	function setRenewAmt(life) {
		var _life = parseInt(life);
		var _pdtNum = parseInt($("#pdtNum").val());
		var _renewPrice = parseFloat($("#renewPrice").val());
		$("#renew_amt").val((_life * _pdtNum * _renewPrice).toFixed(2));
	}
</script>
<div class="pageContent">
	<form method="post" action="${ctx}/cust/renew/toRenewAddConfirm.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input type="hidden" id="renewPrice" value="${renewPrice}" />
		<input type="hidden" id="pdtNum" value="${pdtNum}" />
		<div class="pageFormContent" layoutH="65">
			<!-- 数据错误提示 -->
			<c:if test='${valid == false}'>
				<p style="width: 700px; text-align: left;height: auto;">
					<label style="width: 700px; font-size: 15px;font-weight: bolder;color: red;">数据有如下错误：${errMsg}。请返回上一步，修改完数据再重新上传</label>
				</p>
			</c:if>
			<table border="1" class="list" width="100%">
				<thead>
					<tr>
						<th width="120">产品序列号</th>
						<th width="120">产品型号</th>
						<th width="100">原保修起始日期</th>
						<th width="100">原保修结束日期</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${renewDataList}" var="renewData" varStatus="status">
					<tr>
						<td align="center"><input type="text" class="required" name="renewDataList[${status.index}].seqNo" value="${renewData.seqNo}" /></td>
						<td align="center"><input type="text" class="required" style="text-align: center;" name="renewDataList[${status.index}].pdtNo" value="${renewData.pdtNo}" /></td>
						<td align="center">
							<fmt:formatDate value="${renewData.lifeStartTime}" pattern="yyyy-MM-dd" />
						</td>
						<td align="center">
							<fmt:formatDate value="${renewData.lifeEndTime}" pattern="yyyy-MM-dd" />
						</td>
						<td>${renewData.errTips}</td>
						<td></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<p style="padding-top: 20px;">
				<label style="font-size: 17px;">选择续保期限：</label>
				<select name="renewLife" style="font-size: 17px;">
					<option value="1">一年</option>
				</select>
			</p>
			<p style="padding-top: 20px;">
				<label style="font-size: 17px;">续保合计费用：</label>
				<input id="renew_amt" name="renewAmt" value="0" type="text" readonly="readonly" style="text-align: right;width: 100px;" /><span class="info">元</span>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<a class="add" href="${ctx}/cust/renew/toUploadExcel.do"
							target="dialog" mask="true" width="640" height="540"
							rel="CPXB_CPXB" title="第一步：上传续保数据文件"><span>返回上一步</span></a>
					</div>
				</li>
				<li>
					<div
						<c:choose> <c:when test='${valid == false}'>class="buttonDisabled"</c:when><c:otherwise>class="button"</c:otherwise> </c:choose>>
						<div class="buttonContent">
							<button
								<c:choose> <c:when test='${valid == false}'> type="button"</c:when><c:otherwise> type="submit"</c:otherwise> </c:choose>>&nbsp;&nbsp;&nbsp;下一步&nbsp;&nbsp;&nbsp;</button>
						</div>
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
