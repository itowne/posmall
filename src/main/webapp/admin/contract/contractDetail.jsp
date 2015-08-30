<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
		<div class="pageFormContent" layoutH="68">
			<p>
				<label>注册码：</label>
				<input type="text"   readonly="readonly"  value="${tcontract.custCode}"  />
			</p>
			<p>
				<label>公司名称：</label>
				<input type="text" readonly="readonly"  value="<posmall:dict dictType='custreg_name' dictCode='${tcontract.icust}'/>"  />
			</p>
			<p>
				<label>合同名称：</label>
				<input type="text" name="noteName"  readonly="readonly" value="${tcontract.contractName}"  />
			</p>
			<p>
				<label>采购框架合同编号：</label>
				<input type="text" name="contractNo"  readonly="readonly" class="required" value="${tcontract.contractNo}"  />
			</p>
			<p style="width:500px">
				<label>内容：</label>
				<textarea type="text" name="content" readonly="readonly" cols="35"  rows="6">${tcontract.content}</textarea>
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
</div>