<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pageContent">
	<form id="pdtDetailForm" method="post" action="${ctx}/admin/pdt/pdtDetail.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<p>
				<label>产品名称：</label>
				<input type="text" name="tpdt.name" value="${tpdt.name}" readonly="readonly" size="30"/>
			</p>
			<p>
				<label>产品型号：</label>
				<input type="text" name="tpdt.pdtNo" value="${tpdt.pdtNo}" readonly="readonly" size="30"/>
			</p>
			<p>
				<label>产品全称：</label>
				<input type="text" name="tpdt.longName" value="${tpdt.longName}" readonly="readonly" size="30"/>
			</p>
			<p>
				<label>产品库存：</label>
				<input type="text" value="${tpdtStock.num}台" readonly="readonly" size="30"/>
			</p>
			<p>
				<label>产品单价：</label>
				<input type="text" name="tpdt.price" value="${tpdt.price}" readonly="readonly" size="30"/>
			</p>
			<p>
				<label>产品号段前缀：</label>
				<input type="text" value="${tnoSegCfg.pre}" readonly="readonly" size="30"/>
			</p>
			<p>
				<label>产品号段区间：</label>
				<input type="text" value="${tnoSegCfg.start}&nbsp;&nbsp;--&nbsp;&nbsp;${tnoSegCfg.end}" readonly="readonly" size="30"/>
			</p>
<!-- 			<p> -->
<!-- 				<label>已使用号段：</label> -->
<%-- 				<input type="text" value='<c:if test="${tnoSegCfg.idx > 1 && tnoSegCfg.start < tnoSegCfg.idx}">${tnoSegCfg.start}&nbsp;&nbsp;--&nbsp;&nbsp;${tnoSegCfg.idx}</c:if>' readonly="readonly" size="30"/> --%>
<!-- 			</p> -->
			<p>
				<label>登记日期：</label>
				<input type="text" name="tpdt.genTime" value='<fmt:formatDate value="${tpdt.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' readonly="readonly" size="30"/>
			</p>
			<p>
				<label>说明：</label>
				<input type="text" name="tpdt.memo" value='${tpdt.memo}' readonly="readonly" size="30"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>