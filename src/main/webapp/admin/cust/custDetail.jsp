<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<div class="pageContent">
	<form method="post" action="${ctx}/admin/cust/custDetail.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			
			<p>
                <label>注册码：</label>
                <input  type="text" size="30" readonly="readonly" value="${tcust.custCode}"/>
            </p>  
            <p>
                <label>客户状态：</label>
                <input  type="text" size="30" readonly="readonly" value='<posmall:dict dictType="cust_status" dictCode="${tcust.custStatus}"></posmall:dict>'/>
            </p>
             <p>
                <label>采购框架合同编号：</label>
                <input type="text" maxlength="50" readonly="readonly" value="${tcustReg.contractNo}"/>
	         </p>
            <p>
                <label>签署日期：</label>
                <input type="text" size="30" readonly="readonly" value="<fmt:formatDate value="${tcustReg.signatureDate}" pattern="yyyy-MM-dd" />"/>
            </p>
            <p>
                <label>信用状态：</label>
                <input  type="text" size="30" readonly="readonly" value='<posmall:dict dictType="credit_level" dictCode="${tcust.creditLevel}"></posmall:dict>'/>
            </p>
            
            <p>
                <label>客户编号：</label>
                <input name="custNo" type="text" size="30" readonly="readonly" value="${tcustReg.custNo}"/>
            </p>
             
            <p>
                <label>单位名称：</label>
                <input name="name" type="text" size="30" readonly="readonly" value="${tcustReg.name}"/>
            </p>

            <p>
                <label>单位全称：</label>
                <input name="longName" type="text" size="30" readonly="readonly" value="${tcustReg.longName}"/>
            </p>
            <p>
                <label>客户类型：</label>
                <input type="hidden" name="custType" value="${tcustReg.custType }"/>
                <input type="text" readonly="readonly" value="<posmall:dict dictType='cust_type' dictCode='${tcustReg.custType}'/>" />
            </p>
            <c:if test="${tcust.custStatus == 'AUDIT_NOPASS'}">
	            <p>
	                <label>拒绝理由：</label>
	                <label style="color: red;font-size: 18px">${tcustReg.refuseReason} </label>
	            </p>
            </c:if>
            <p>
                <label>联系人姓名：</label>
                <input name="contactName" type="text" size="30" readonly="readonly" value="${tcustReg.contactName}"/>
            </p>

            <p>
                <label>电话：</label>
                <input name="tel" type="text" size="30" readonly="readonly" value="${tcustReg.tel}"/>
            </p>

            <p>
                <label>移动电话：</label>
                <input name="mobile" type="text" size="30" readonly="readonly" value="${tcustReg.mobile}"/>
            </p>

            <p>
                <label>传真：</label>
                <input name="fax" type="text" size="30" readonly="readonly" value="${tcustReg.fax}"/>
            </p>

            <p>
                <label>邮箱：</label>
                <input name="email" type="text" size="30" readonly="readonly" value="${tcustReg.email}"/>
            </p>
             <p>
                <label>业务员姓名：</label>
                <input name="salesmanName" type="text"  size="30" readonly="readonly" value="${tcustReg.salesmanName}"/>
            </p>
            <p>
                <label>业务员邮箱：</label>
                <input name="salesmanEmail" readonly="readonly" type="text" size="30"  value="${tcustReg.salesmanEmail}"/>
            </p>
			 <!-- 开票信息 -->
            <fieldset style="float: left;">
            <legend style="padding: 5px 10px 5px 10px;margin-left: 10px;font-size: 15px;background-color: #B8D0D6;">开 票 信 息</legend>
            <p>
                <label>票面单位：</label>
                <input name="invoiceCorporation" type="text" size="30" readonly="readonly" value="${tcustReg.invoiceCorporation}"/>
            </p>
            <p>
                <label>发票类型：</label>
               <input name="invoiceType" type="hidden" size="30" readonly="readonly" value="${tcustReg.invoiceType}"/>
               <input type="text" size="30" readonly="readonly" value="<posmall:dict dictType="invoice_type" dictCode="${tcustReg.invoiceType}" />"/>
            </p>
            <p>
                <label>税号：</label>
                <input name="taxID" type="text" size="30" readonly="readonly" value="${tcustReg.taxID}"/>
            </p>
            <p>
            	<label>银行账号：</label>
            	<input name="account" type="text" size="30" readonly="readonly" value="${tcustReg.account}"/>
            </p>
            <p>
            	<label>开户行：</label>
            	<input name="bank" type="text" size="30" readonly="readonly" value="${tcustReg.bank}"/>
            </p>
            <p>
                <label>注册电话：</label>
                <input name="regTel" type="text" size="30" readonly="readonly" value="${tcustReg.regTel}"/>
            </p>
            <p style="height: 50px;">
                <label>注册地址：</label>
                <textarea name="regAddr" readonly="readonly" style="width:52%;height:45px">${tcustReg.regAddr}</textarea>
            </p>
            <p>
                <label>其它：</label>
                <textarea name="remarks" readonly="readonly" style="width:52%;height:45px">${tcustReg.remarks}</textarea>
            </p>
            </fieldset>

			
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