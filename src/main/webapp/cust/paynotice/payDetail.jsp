<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<script type="text/javascript">
	$(function() {
		//异常、错误信息提示
		var errMsg = "${errMsg}";
		if(errMsg != null && errMsg.trim() != "") {
			alertMsg.error("${errMsg}");
			$.pdialog.closeCurrent();
		}
	});
</script>
<div class="pageContent">
	<form method="post" name="uploadVoucherForm" class="pageForm required-validate"
		action="${ctx}/cust/paynotice/uploadPayVoucher.do"
		onsubmit="return iframeCallback(this, dialogAjaxDone);"
		enctype="multipart/form-data">
		<div class="pageFormContent" layoutH="65">
			<p>
				<label>应支付金额：</label>
				<c:choose>
					<c:when test="${tpayNotify.payType == 'BAIL'}">
					<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tagmt.deposit}" pattern="¥#,##0.00#" />' />
					</c:when>
					<c:when test="${tpayNotify.payType == 'LOGISTICS'}">
					<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tlogisticsOrd.logisticsFreight}" pattern="¥#,##0.00#" />' />
					</c:when>
					<c:when test="${tpayNotify.payType == 'ORDER'}">
					<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tord.amt}" pattern="¥#,##0.00#" />' />
					</c:when>
					<c:when test="${tpayNotify.payType == 'WAREHOUSE'}">
					<input type="text" readonly="readonly" value='<fmt:formatNumber value="${twareHouse.amt}" pattern="¥#,##0.00#" />' />
					</c:when>
					<c:when test="${tpayNotify.payType == 'RENEW_AMT'}">
					<input type="text" readonly="readonly" value='<fmt:formatNumber value="${trenew.renewAmt}" pattern="¥#,##0.00#" />' />
					</c:when>
					<c:when test="${tpayNotify.payType == 'BAIL_SUPPLEMENT'}">
					<input type="text" readonly="readonly" value='<fmt:formatNumber value="${-tagmt.redundantDeposit}" pattern="¥#,##0.00#" />' />
					</c:when>
				</c:choose>
			</p>
			<p>
				<label>状态：</label>
				<input type="text" readonly="readonly" value='<posmall:dict dictType="pay_status" dictCode="${tpayNotify.payNotifyStatus}" />' />
			</p>
			<c:forEach items="${tpayList}" var="tpay" varStatus="status">
			<fieldset style="float: left;">
				<legend style="padding: 5px 20px;margin-left: 10px;background-color: #B8D0D6;">付款明细&nbsp;&nbsp;${status.index + 1}</legend>
				<c:choose>
					<c:when test='${tpay.payMethod == "DEPOSIT"}'>
						<p>
							<label>保证金支付：</label>
							<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tpay.amt}" pattern="¥#,##0.00#" />'/>
						</p>
						<p>
							<label>付款时间：</label>
							<input type="text" readonly="readonly" value='<fmt:formatDate value="${tpay.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'/>
						</p>
						<%-- <p>
							<label>支付状态：</label>
							<input type="text" readonly="readonly" value='<posmall:dict dictType="pay_status" dictCode="${tpay.payStatus}" />'/>
						</p> --%>
						<c:if test='${tpay.payStatus == "NO_PASS"}'>
							<p>
							<label>不通过理由：</label>
							<span style="color: red;font-size: 18px">${tpay.refuseReason}</span>
							</p>
						</c:if>
					</c:when>
					<c:otherwise>
						<p>
							<label>支付方式：</label> 
							<input type="text" readonly="readonly" value="<posmall:dict dictCode='${tpay.payMethod}' dictType='pay_method' />" />
						</p>
						<c:if test='${tpay.payMethod == "REMITTANCE"}'>
							<p>
								<label>汇款单编号：</label> 
								<input type="text" readonly="readonly" value="${tpay.remittanceNo}"/>
							</p>
							<p>
								<label>银行账号：</label>
								<input type="text" readonly="readonly" value="${tpay.account}" />
							</p>
							<p>
								<label>开户行：</label>
								<input type="text" readonly="readonly" value="${tpay.bank}" />
							</p>
						</c:if>
						<p>
							<label>已支付金额：</label>
							<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tpay.amt}" pattern="¥#,##0.00#" />'/>
						</p>
						<p>
							<label>凭证上传时间：</label>
							<input type="text" readonly="readonly" value='<fmt:formatDate value="${tpay.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'/>
						</p>
						<c:if test="${tpay.tfile.uuid != null && tpay.tfile.uuid != ''}">
							<p>
								<a href="${ctx}/file/imgShow.jsp?imgSrc=${tpay.tfile.uuid}" target="_blank" 
								style="color: blue;text-decoration: underline;line-height: 21px;">查看付款凭证</a>
							</p>
						</c:if>
						<%-- <p>
							<label>支付状态：</label>
							<input type="text" readonly="readonly" value='<posmall:dict dictType="pay_status" dictCode="${tpay.payStatus}" />'/>
						</p> --%>
						<c:if test='${tpay.payStatus == "NO_PASS"}'>
							<p>
							<label>不通过理由：</label>
							<span style="color: red;font-size: 18px">${tpay.refuseReason}</span>
							</p>
						</c:if>
					</c:otherwise>
				</c:choose>
			</fieldset>
			</c:forEach>
			<%-- <p style="height: 5px"></p>
			<p>
				<label>未支付金额：</label>
				<c:choose>
					<c:when test="${tpayNotify.payNotifyStatus == 'WAIT_AUDIT'}">
						<input type="text" readonly="readonly" value="待审核"/>
					</c:when>
					<c:otherwise>
						<c:choose>
						<c:when test="${tpayNotify.payType == 'BAIL'}">
						<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tagmt.deposit - tpayNotify.havepayAmt}" pattern="¥#,##0.00#" />' />
						</c:when>
						<c:when test="${tpayNotify.payType == 'LOGISTICS'}">
						<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tlogisticsOrd.logisticsFreight - tpayNotify.havepayAmt}" pattern="¥#,##0.00#" />' />
						</c:when>
						<c:when test="${tpayNotify.payType == 'ORDER'}">
						<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tord.amt - tpayNotify.havepayAmt}" pattern="¥#,##0.00#" />' />
						</c:when>
						<c:when test="${tpayNotify.payType == 'WAREHOUSE'}">
						<input type="text" readonly="readonly" value='<fmt:formatNumber value="${twareHouse.amt - tpayNotify.havepayAmt}" pattern="¥#,##0.00#" />' />
						</c:when>
					</c:choose>
					</c:otherwise>
				</c:choose>
			</p> --%>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>