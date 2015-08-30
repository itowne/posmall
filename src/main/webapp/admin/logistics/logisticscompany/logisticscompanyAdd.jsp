<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
	<form id="pdtAddForm" method="post" action="${ctx}/admin/logistics/logisticscompany/logisticscompanyAdd.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<p>
				<label>物流简称：</label>
				<input type="text" name="name" value=" " class="required"  />
			</p>
			<p>
				<label>物流全称：</label>
				<input type="text" name="fullname" value=" " class="required"  />
			</p>
			<p>
				<label>时效：</label>
				<input type="text" name="aging" value=""   class="required number" />
			</p>
			<p>
				<label>单价：</label>
				<input type="text" name="price" value="" size="12"  class="required money" /><span style="line-height: 21px">元</span>
			</p>
			<p>
				<label>状态：</label>
				<posmall:dictSelect selectName="logisticsStatus" dictType="logistics_status" defaultValue="SELECT" needAll="NO"></posmall:dictSelect>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit">&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;&nbsp;交&nbsp;&nbsp;</button>
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