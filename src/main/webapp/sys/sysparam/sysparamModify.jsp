<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="pageContent">
	<form method="post" action="${ctx}/sys/sysparam/sysparamModify.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input name="isysParam" type="hidden" value="${tsysParam.isysParam}">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>参数名称：</label> 
				<input name="name" type="text" size="30" value="${tsysParam.name}" readonly="readonly"/>
			</p>
			<p>
				<label>参数类型：</label>
				<input name="sysParamType" type="text" size="30" value="${tsysParam.sysParamType}" readonly="readonly"/>
			</p>
			<p>
				<label>参数编码：</label>
				<input name="code" type="text" size="30" value="${tsysParam.code}" readonly="readonly"/>
			</p>
			<p>
				<label>参数数值：</label>
				<input name="value" type="text" size="30" value="${tsysParam.value}"/>
			</p>
			<p>
				<label>有效起始时间：</label>
				<input type="text" name="startTime" class="date" dateFmt="HH:mm" mmStep="1" value="${tsysParam.startTime}" style="width: 50px;"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<p>
				<label>有效结束时间：</label>
				<input type="text" name="endTime" class="date" dateFmt="HH:mm" mmStep="1" value="${tsysParam.endTime}" style="width: 50px;"/>
				<a class="inputDateButton" href="javascript:;">选择</a>
			</p>
			<p>
				<label>备注：</label>
				<input name="memo" type="text" size="30" value="${tsysParam.memo}" readonly="readonly"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" >提交</button>
						</div>
					</div></li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">关闭</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
