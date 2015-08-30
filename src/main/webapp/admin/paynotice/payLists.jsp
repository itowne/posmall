<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
 <c:if test="${tpayAuditedList != null}">
	   	<div class="divider"></div>
	   	  <div class="pageFormContent">
	   	 <c:forEach var="tpay" varStatus="st" items="${tpayAuditedList}" >
	           <fieldset>
				<legend> 已审核的付款凭证信息：</legend>
				<p>
	              <label>支付方式：</label>
                 <input type="text"  readonly="readonly" value="<posmall:dict dictCode='${tpay.payMethod}' dictType='pay_method'></posmall:dict>"/>
				</p>
	            <p>
	              <label>支付状态：</label>
                 <input type="text"  readonly="readonly" value="<posmall:dict dictCode='${tpay.payStatus}' dictType='pay_status'></posmall:dict>"/>
				</p>
			  <c:choose>
			    <c:when test='${tpay.payMethod == "REMITTANCE"}'>
	            <p>
	              <label>汇款单编号：</label>
                 <input type="text"  readonly="readonly" value="${tpay.remittanceNo}"/>
				</p>
				<p>
	              <label>汇款账号：</label>
                <input type="text" readonly="readonly" value="${tpay.account}"/>
				</p>
				<p>
	              <label>开户行：</label>
                <input type="text" readonly="readonly" value="${tpay.bank}"/>
				</p>
				<p>
	              <label>汇款金额：</label>
                <input type="text"  readonly="readonly" value="<fmt:formatNumber value="${tpay.amt}" pattern="¥#,##0.00#"/>"/>
				</p>
				<c:if test='${tpay.payStatus != "NO_PASS" || tpay.payAmt != null}'>
				<p>
	              <label>付款金额：</label>
                <input type="text"  readonly="readonly" value="<fmt:formatNumber value="${tpay.payAmt}" pattern="¥#,##0.00#"/>"/>
				</p>
			    </c:if>
				<p>
				  <label>付款扫描件：</label>
				 <c:choose>
	                <c:when test="${tfileAuditedList != null && tfileAuditedList[st.index] != null}">
	                    
	                      <a style="color: blue;text-decoration:underline; line-height: 21px" target="_blank" href="${ctx}/file/imgShow.jsp?imgSrc=${tfileAuditedList[st.index].uuid}">扫描件查看</a>
	                </c:when>
	                <c:otherwise><label>未上传文件</label></c:otherwise>
                </c:choose>
                </p>
                <c:if test='${tpay.payStatus == "NO_PASS"}'>
				  <p>
				     <label>拒绝理由：</label>
				     <span style="color: red;font-size: 18px">${tpay.refuseReason}</span>
				  </p>
			    </c:if>
              </c:when>
              <c:otherwise>
              	<p>
	              <label>金额：</label>
                  <input type="text"  readonly="readonly" value="<fmt:formatNumber value="${tpay.amt}" pattern="¥#,##0.00#"/>"/>
				</p>
				<c:if test='${tpay.payStatus != "NO_PASS" || tpay.payAmt != null}'>
				<p>
	              <label>付款金额：</label>
                <input type="text"  readonly="readonly" value="<fmt:formatNumber value="${tpay.payAmt}" pattern="¥#,##0.00#"/>"/>
				</p>
				</c:if>
              </c:otherwise>
             </c:choose>
           
	           </fieldset>
	     </c:forEach>
           </div>
	  </c:if>
	  <c:if test="${tpayWaitAuditList != null}">
	   	<div class="divider"></div>
	   	  <div class="pageFormContent">
	   	    <c:forEach var="tpayWaitAudit" varStatus="st" items="${tpayWaitAuditList}" >
	           <fieldset>
				<legend> 待审核的付款凭证信息：</legend>
				<p>
	              <label>支付方式：</label>
                 <input type="text"  readonly="readonly" value="<posmall:dict dictCode='${tpayWaitAudit.payMethod}' dictType='pay_method'></posmall:dict>"/>
				</p>
	            <p>
	              <label>支付状态：</label>
                 <input type="text"  readonly="readonly" value="<posmall:dict dictCode='${tpayWaitAudit.payStatus}' dictType='pay_status'></posmall:dict>"/>
				</p>
				 <c:choose>
			    <c:when test='${tpayWaitAudit.payMethod == "REMITTANCE"}'>
		            <p>
		              <label>汇款单编号：</label>
	                 <input type="text"  readonly="readonly" value="${tpayWaitAudit.remittanceNo}"/>
					</p>
					<p>
		              <label>汇款账号：</label>
	                <input type="text" readonly="readonly" value="${tpayWaitAudit.account}"/>
					</p>
					<p>
		              <label>开户行：</label>
	                <input type="text" readonly="readonly" value="${tpayWaitAudit.bank}"/>
					</p>
					<p>
		              <label>汇款金额：</label>
	                <input type="text"  readonly="readonly" value="<fmt:formatNumber value="${tpayWaitAudit.amt}" pattern="¥#,##0.00#"/>"/>
					</p>
					<p>
		              <label>剩余金额：</label>
	                  <input type="text" name="availableAmt "  readonly="readonly" value="<fmt:formatNumber value="${tpayWaitAudit.remainAmt}" pattern="¥#,##0.00#"/>"/>
					</p>
					<p>
					  <label>付款扫描件：</label>
	             <c:choose>
	                <c:when test="${tfileWaitAuditList != null && tfileWaitAuditList[st.index] != null}">
	                    
	                      <a style="color: blue;text-decoration:underline; line-height: 21px" target="_blank" href="${ctx}/file/imgShow.jsp?imgSrc=${tfileWaitAuditList[st.index].uuid}">扫描件查看</a>
	                </c:when>
	                <c:otherwise><label>未上传文件</label></c:otherwise>
                </c:choose>
	                </p>
	             </c:when>
	             <c:otherwise>
	                 <p>
		              <label>金额：</label>
	                  <input type="text"  readonly="readonly" value="<fmt:formatNumber value="${tpayWaitAudit.amt}" pattern="¥#,##0.00#"/>"/>
					</p>
<!-- 					<p> -->
<!-- 		              <label>剩余金额：</label> -->
<%-- 	                  <input type="text" name="availableAmt" readonly="readonly" id="payLists_remainAmt" value="<fmt:formatNumber value="${tpayWaitAudit.remainAmt}" pattern="¥#,##0.00#"/>"/> --%>
<!-- 					</p> -->
	             </c:otherwise>
	             </c:choose>
	           </fieldset>
	         </c:forEach>
           </div>
	  </c:if>

