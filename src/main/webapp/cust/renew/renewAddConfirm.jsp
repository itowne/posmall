<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<script type="text/javascript">
	$(".dialogHeader_c > h1").html("第三步：续保确认");
</script>
<div class="pageContent">
	<form method="post" action="${ctx}/cust/renew/renewAdd.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone);">
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
					<c:forEach items="${renewData4Page.renewDataList}" var="renewData" varStatus="status">
					<tr>
						<td align="center">${renewData.seqNo}
							<input type="hidden" name="renewDataList[${status.index}].seqNo" value="${renewData.seqNo}" />
						</td>
						<td align="center">${renewData.pdtNo}
							<input type="hidden" style="text-align: center;" name="renewDataList[${status.index}].pdtNo" value="${renewData.pdtNo}" />
						</td>
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
				<input type="hidden" name="renewLife" value="${renewData4Page.renewLife}" />
				<label style="font-size: 17px;">续保期限：</label>
				<label style="font-size: 17px;color: red;">
				<c:if test="${renewData4Page.renewLife == 1}">一年</c:if>
				<c:if test="${renewData4Page.renewLife == 2}">两年</c:if>
				<c:if test="${renewData4Page.renewLife == 3}">三年</c:if>
				</label>
			</p>
			<p style="padding-top: 20px;">
				<label style="font-size: 17px;">续保合计费用：</label>
				<input type="text" value="${renewData4Page.renewAmt}" readonly="readonly" style="text-align: right;width: 100px;" /><span class="info">元</span>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<a class="add" href="${ctx}/cust/renew/toUploadExcel.do"
							target="dialog" mask="true" width="640" height="540"
							rel="CPXB_CPXB" title="第一步：上传续保数据文件"><span>&nbsp;&nbsp;&nbsp;返&nbsp;&nbsp;回&nbsp;&nbsp;&nbsp;</span></a>
					</div>
				</li>
				<li>
					<div
						<c:choose> <c:when test='${valid == false}'>class="buttonDisabled"</c:when><c:otherwise>class="button"</c:otherwise> </c:choose>>
						<div class="buttonContent">
							<button
								<c:choose> <c:when test='${valid == false}'> type="button"</c:when><c:otherwise> type="submit"</c:otherwise> </c:choose>>&nbsp;&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;&nbsp;</button>
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
