<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="pageContent">
	<form method="post" action="${ctx}/admin/pdt/modifyStock.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
		    <input type="hidden" name="ipdt" value="${tpdt.ipdt}">
		    <p>
				<label>产品型号：</label>
				<input type="text" value="${tpdt.pdtNo}" readonly="readonly" size="30"/>
			</p>
			<p>
				<label>产品型号：</label>
				<input type="text" value="${tpdt.name}" readonly="readonly" size="30"/>
			</p>
			<p>
				<label>产品全称：</label>
				<input type="text" value="${tpdt.longName}" readonly="readonly" size="30"/>
			</p>
			<p>
				<label>产品库存：</label>
				<input type="text" name="num" class="required digits" value="${tpdtStock.num}" size="30"/>
				<span style="line-height: 21px;margin: 5px;">台</span>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit" class="submit">&nbsp;&nbsp;修&nbsp;&nbsp;&nbsp;&nbsp;改&nbsp;&nbsp;</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
						</div>
					</div></li>

			</ul>
		</div>
	</form>
</div>