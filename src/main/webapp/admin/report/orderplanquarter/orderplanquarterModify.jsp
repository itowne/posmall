<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">
<div class="pageContent">
	<form method="post" action="report/orderplanquarter/orderplanquarterDetail.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<s:hidden name="teamAct.iteamAct" />
			<p id="teamAct">
				<label>客户名称：</label>
				<input type="text" name="teamAct.actName" cssClass="required" readonly="readonly"/>
			</p>
			<p id="proName">
				<label>季度：</label>
				<input type="text" name="teamAct.proName" cssClass="required" readonly="readonly"/>
			</p>
			<p id="proUnitPrice" class="nowrap">
				<label>产品名称：</label>
				<input type="text" name="teamAct.proUnitPrice" cssClass="required" readonly="readonly"/>
			</p>
			<p id="proUnitPrice" class="nowrap">
				<label>订单量：</label>
				<input type="text" name="teamAct.proUnitPrice" cssClass="required" readonly="readonly"/>
			</p>
			<p id="registerDate" class="nowrap">
				<label>登记日期：</label>
				<input type="text" name="teamAct.actIntroduction" cssClass="required" readonly="readonly"/>
			</p>
			<p id="registerDate" class="nowrap">
				<label>状态：</label>
				<input type="text" name="teamAct.actIntroduction" cssClass="required" readonly="readonly"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;定&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>