<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<style>
.infoDetail p{
	width: 400px;
}
</style>
<div class="pageContent">
    <form id="infForm" method="post" class="pageForm required-validate"
    	  action="${ctx}/cust/info/saveOrSubmitRegInfo.do"
          onsubmit="return validateCallback(this, navTabAjaxDone);">
        <div class="pageFormContent infoDetail" layoutH="56">
        	<p>
                <label>用  户  名：</label>
                <input name="loginName" type="text" size="30" readonly="readonly" value="${loginTcust.loginName}"/>
                <input type="hidden" name="icust" value="${loginTcust.icust}">
            </p>
            <p>
                <label>注 册  码：</label>
                <input type="text" size="30" readonly="readonly" value="${loginTcust.custCode}"/>
            </p>
        	<c:choose>
        		<c:when test='${loginTcust.custStatus == "REG" || loginTcust.custStatus == "AUDIT_NOPASS"}'>
        			<p>
		                <label>采购框架合同编号：</label>
		                <input type="text" maxlength="50"   class="required"  name="contractNo" value="${tcustReg.contractNo}"/>
			         </p>
		            <p>
		                <label>签署日期：</label>
		                <input type="text" class="date required" maxDate="{%y}-%M-{%d}" size="30" readonly="readonly"  name="signatureDateForm" value="<fmt:formatDate value="${tcustReg.signatureDate}" pattern="yyyy-MM-dd" />"/>
		            </p>
		            <p>
		                <label>单位简称：</label>
		                <input name="name" type="text" size="30" class="required" value="${tcustReg.name}"/>
		            </p>
		            <p>
		                <label>单位全称：</label>
		                <input name="longName" type="text" size="30" class="required" value="${tcustReg.longName}"/>
		            </p>
		            <p>
		                <label>客户类型：</label>
		                <c:choose>
		                	<c:when test="${tcustReg.custType != null}">
		                		<posmall:dictSelect selectName="custType" dictType="cust_type" defaultValue="${tcustReg.custType}" needAll="NO" cssClass="required"/>
		                	</c:when>
		                	<c:otherwise>
		                		<posmall:dictSelect selectName="custType" dictType="cust_type" defaultValue="THIRD_PARTY" needAll="NO" cssClass="required"/>
		                	</c:otherwise>
		                </c:choose>
		            </p>
		            <p>
		                <label>联系人姓名：</label>
		                <input name="contactName" type="text" size="30" class="required"  value="${tcustReg.contactName}"/>
		            </p>
		            <p>
		                <label>固定电话：</label>
		                <input name="tel" type="text" size="30" class="required phone" value="${tcustReg.tel}"/>
		            </p>
		            <p>
		                <label>手机号：</label>
		                <input name="mobile" type="text" size="30" class="required telphone"  value="${tcustReg.mobile}"/>
		            </p>
		            <p>
		                <label>传真：</label>
		                <input name="fax" type="text" size="30" class="fax" value="${tcustReg.fax}"/>
		                <span class="info">(可为空)</span>
		            </p>
		            <p>
		                <label>邮箱：</label>
		                <input name="email" type="text" size="30" class="required email"  value="${tcustReg.email}"/>
		            </p>
		            <div class="divider"></div>
		            
		            <!-- 开票信息 -->
		            <fieldset style="float: left;">
		            <legend style="padding: 5px 10px 5px 10px;margin-left: 10px;font-size: 15px;background-color: #B8D0D6;">开 票 信 息</legend>
		            <p>
		                <label>票面单位：</label>
		                <input name="invoiceCorporation" type="text" size="30" class="required"  value="${tcustReg.invoiceCorporation}"/>
		            </p>
		            <p>
		                <label>发票类型：</label>
                		<posmall:dictSelect selectName="invoiceType" dictType="invoice_type" defaultValue="${tcustReg.invoiceType}" needAll="NO" cssClass="required"/>
		            </p>
		            <p>
		                <label>税号：</label>
		                <input name="taxID" type="text" size="30" class="required"  value="${tcustReg.taxID}"/>
		            </p>
		            <p>
		            	<label>银行账号：</label>
		            	<input name="account" type="text" size="30" class="required" value="${tcustReg.account}"/>
		            </p>
		            <p>
		            	<label>开户行：</label>
		            	<input name="bank" type="text" size="30" class="required" value="${tcustReg.bank}"/>
		            </p>
		            <p>
		                <label>注册电话：</label>
		                <input name="regTel" type="text" size="30" class="required phone"  value="${tcustReg.regTel}"/>
		            </p>
		            <p style="height: 50px;">
		                <label>注册地址：</label>
		                <textarea name="regAddr" class="required" style="width:52%;height:45px">${tcustReg.regAddr}</textarea>
		            </p>
		            <p>
		                <label>其它：</label>
		                <textarea name="remarks" style="width:52%;height:45px">${tcustReg.remarks}</textarea>
		            </p>
		            </fieldset>
        		</c:when>
        		<c:when test='${loginTcust.custStatus == "AUDIT_PASS"}'>
        			<p>
		                <label>采购框架合同编号：</label>
		                <input type="text" maxlength="50" readonly="readonly" value="${tcustReg.contractNo}"/>
			         </p>
		            <p>
		                <label>签署日期：</label>
		                <input type="text" size="30" readonly="readonly" value="<fmt:formatDate value="${tcustReg.signatureDate}" pattern="yyyy-MM-dd" />"/>
		            </p>
		            <p>
		                <label>单位简称：</label>
		                <input name="name" type="text" size="30" readonly="readonly" value="${tcustReg.name}"/>
		            </p>
		            <p>
		                <label>单位全称：</label>
		                <input name="longName" type="text" size="30" readonly="readonly" value="${tcustReg.longName}"/>
		            </p>
		            <p>
		                <label>客户类型：</label>
		                <input name="custType" type="hidden" value="${tcustReg.custType}" />
		                <input type="text" size="30" readonly="readonly" value='<posmall:dict dictType="cust_type" dictCode="${tcustReg.custType}" />'/>
		            </p>
		            <p>
		                <label>联系人姓名：</label>
		                <input name="contactName" type="text" size="30" class="required"  value="${tcustReg.contactName}"/>
		            </p>
		            <p>
		                <label>固定电话：</label>
		                <input name="tel" type="text" size="30" class="required phone" value="${tcustReg.tel}"/>
		            </p>
		            <p>
		                <label>手机号：</label>
		                <input name="mobile" type="text" size="30" class="required telphone"  value="${tcustReg.mobile}"/>
		            </p>
		            <p>
		                <label>传真：</label>
		                <input name="fax" type="text" size="30" class="fax" value="${tcustReg.fax}"/>
		                <span class="info">(可为空)</span>
		            </p>
		            <p>
		                <label>邮箱：</label>
		                <input name="email" type="text" size="30" class="required email"  value="${tcustReg.email}"/>
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
		                <textarea name="remarks" style="width:52%;height:45px">${tcustReg.remarks}</textarea>
		            </p>
		            </fieldset>
        		</c:when>
        		<c:when test='${loginTcust.custStatus == "AUDIT_ING" || loginTcust.custStatus == "WAIT_AUDIT"}'>
        		     <p>
		                <label>采购框架合同编号：</label>
		                <input type="text" maxlength="50" readonly="readonly" value="${tcustReg.contractNo}"/>
			         </p>
		            <p>
		                <label>签署日期：</label>
		                <input type="text" size="30" readonly="readonly" value="<fmt:formatDate value="${tcustReg.signatureDate}" pattern="yyyy-MM-dd" />"/>
		            </p>
		            <p>
		                <label>单位简称：</label>
		                <input name="name" type="text" size="30" readonly="readonly" value="${tcustReg.name}"/>
		            </p>
		            <p>
		                <label>单位全称：</label>
		                <input name="longName" type="text" size="30" readonly="readonly" value="${tcustReg.longName}"/>
		            </p>
		            <p>
		                <label>客户类型：</label>
		                <input name="custType" type="text" size="30" readonly="readonly" value='<posmall:dict dictType="cust_type" dictCode="${tcustReg.custType}" />'/>
		            </p>
		            <p>
		                <label>联系人姓名：</label>
		                <input name="contactName" type="text" size="30" readonly="readonly"  value="${tcustReg.contactName}"/>
		            </p>
		            <p>
		                <label>固定电话：</label>
		                <input name="tel" type="text" size="30" readonly="readonly" value="${tcustReg.tel}"/>
		            </p>
		            <p>
		                <label>手机号：</label>
		                <input name="mobile" type="text" size="30" readonly="readonly"  value="${tcustReg.mobile}"/>
		            </p>
		            <p>
		                <label>传真：</label>
		                <input name="fax" type="text" size="30" readonly="readonly" value="${tcustReg.fax}"/>
		            </p>
		            <p>
		                <label>邮箱：</label>
		                <input name="email" type="text" size="30" readonly="readonly" value="${tcustReg.email}"/>
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
        		</c:when>
        	</c:choose>
			<p>
                <label>状态：</label>
                <input type="text" size="30" readonly="readonly" value='<posmall:dict dictType="cust_status" dictCode="${loginTcust.custStatus}" />'/>
            </p>
            <c:if test='${loginTcust.custStatus == "AUDIT_NOPASS"}'>
            	<p style="width: 680px"><label style="color: red;font-size: 18px;width: 600px;">审核未通过原因：${tcustReg.refuseReason}</label></p>
        	</c:if>
        </div>
        <div class="formBar">
            <ul>
	            <c:choose>
	        		<c:when test='${loginTcust.custStatus == "REG" || loginTcust.custStatus == "AUDIT_NOPASS"}'>
	                <li>
	                    <div class="buttonActive">
	                        <div class="buttonContent">
	                            <button type="button" onclick="beforeSubmit();">提交审核</button>
	                        </div>
	                    </div>
	                </li>
	                <li>
	                    <div class="buttonActive">
	                        <div class="buttonContent">
	                            <button type="button" onclick="beforeSave();">保存</button>
	                        </div>
	                    </div>
	                </li>
	                </c:when>
	                <c:when test='${loginTcust.custStatus == "AUDIT_PASS"}'>
	                <li>
	                    <div class="buttonActive">
	                        <div class="buttonContent">
	                            <button type="button" onclick="beforeSave();">保存</button>
	                        </div>
	                    </div>
	                </li>
                    </c:when>
	            </c:choose>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button type="button" class="close">返回</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <!-- 隐藏域 -->
		<input type="hidden" id="operType" name="operType" value="" />
    </form>
</div>
<script language="JavaScript">
	
    function beforeSave() {
    	$("#operType").val("");
    	$("#infForm").submit();
    }
    
    function beforeSubmit() {
    	$("#operType").val("1");
    	$("#infForm").submit();
    }

</script>