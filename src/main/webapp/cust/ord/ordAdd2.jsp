<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
.tips {
		float: left;
		padding: 15px 0 15px 0;
	}
</style>
<form id="ordAdd2Form" method="post" action="${ctx}/cust/ord/ordAdd.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<!-- 协议编号 -->
	<input type="hidden" name="iagmt" value="${tagmt.iagmt}">
	<div class="tips"><jsp:include page="_orderTips.jsp" /></div>
	<div class="pageContent">
		<!-- <div class="pageFormContent"> -->
		<div class="tabs" currentIndex="0" eventType="click">
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
					<c:forEach items="${ordPdtModel4PageList}" var="ordPdtModel4Page" varStatus="status">
						<li><a href="javascript:;"><span style="font-size: 17px;padding: 0 10px 0 10px;color: red;">${ordPdtModel4Page.pdtName}</span></a></li>
					</c:forEach>
					</ul>
				</div>
			</div>
			<div class="tabsContent" style="height:310px;">
				<c:forEach items="${ordPdtModel4PageList}" var="ordPdtModel4Page" varStatus="status">
				<div>
				<input type="hidden" name="ordPdtModel4PageList[${status.index}].ipdt" value="${ordPdtModel4Page.ipdt}"/>
				<input type="hidden" name="ordPdtModel4PageList[${status.index}].ipdtHis" value="${ordPdtModel4Page.ipdtHis}"/>
				<input type="hidden" name="ordPdtModel4PageList[${status.index}].pdtName" value="${ordPdtModel4Page.pdtName}"/>
				<input type="hidden" name="ordPdtModel4PageList[${status.index}].price" value="${ordPdtModel4Page.price}"/>
				<input type="hidden" name="ordPdtModel4PageList[${status.index}].rate" value="${ordPdtModel4Page.rate}"/>
				<input type="hidden" name="ordPdtModel4PageList[${status.index}].priceAfterRate" value="${ordPdtModel4Page.priceAfterRate}"/>
				<input type="hidden" name="ordPdtModel4PageList[${status.index}].remainAgmtQuota" value="${ordPdtModel4Page.remainAgmtQuota}"/>
				<h1 align="center" style="font-size: 15px;">
				产品：${ordPdtModel4Page.pdtName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				单价：<fmt:formatNumber value="${ordPdtModel4Page.priceAfterRate}" pattern="¥#,##0.00#" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			            剩余额度：${ordPdtModel4Page.remainAgmtQuota}台&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				可供货量合计：<span id="allSupplyNum${status.index}" class="allSupplyNum"></span>台
				</h1>
				<table border="1" class="list" width="97%">
					<thead>
						<tr>
							<th width="120">可选日期</th>
							<th width="100">可供货量</th>
							<th width="120">点单台数</th>
							<th width="100">金&nbsp;&nbsp;&nbsp;额</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ordPdtModel4Page.ordPdtModelList}" var="ordPdtModelDaily" varStatus="status2">
						<tr>
							<td align="center">
								<c:if test="${ordPdtModelDaily.ordDetailPdtType == 'STOCK'}">${today}库存量</c:if>
								<c:if test="${ordPdtModelDaily.ordDetailPdtType != 'STOCK'}">${ordPdtModelDaily.date}</c:if>
							</td>
							<td align="right">
								<span class="allSupplyNum${status.index}">${ordPdtModelDaily.supplyNum}</span>台&nbsp;
							</td>
							<td align="right"> 
								<input type="text" name="ordPdtModel4PageList[${status.index}].ordPdtModelList[${status2.index}].orderNumber" class="digits orderNumber" style="width: 80px;">&nbsp;台&nbsp;
								<input type="hidden" class="priceAfterRate" value="${ordPdtModel4Page.priceAfterRate}">
								<input type="hidden" name="ordPdtModel4PageList[${status.index}].ordPdtModelList[${status2.index}].date" value="${ordPdtModelDaily.date}" />
								<input type="hidden" name="ordPdtModel4PageList[${status.index}].ordPdtModelList[${status2.index}].ordDetailPdtType" value="${ordPdtModelDaily.ordDetailPdtType}" />
								<input type="hidden" name="ordPdtModel4PageList[${status.index}].ordPdtModelList[${status2.index}].supplyNum" value="${ordPdtModelDaily.supplyNum}" />
							</td>
							<td align="right">¥<span>0.00</span></td>
							<td><input type="hidden" value=""/>
								<c:if test="${ordPdtModelDaily.ordDetailPdtType == 'STOCK' && ordPdtModelDaily.orderNumber != ''}">因库存产品灌装程序需要时间，具体发货日期需线下协商确认。<br/>联系人：谢清  联系电话：13600880856</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
				</c:forEach>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
		<div class="pageFormContent">
		<h1 align="center" style="font-size: 15px;">请在备注框里注明本次点单的应用程序版本与对应数量，无程序的就写裸机。</h1>
		<p style="width:280px;">
			<label style="width: 100px;text-align: right;">剩余可用保证金：</label>
			<input type="text" readonly="readonly" value='<fmt:formatNumber value="${tagmt.remainDeposit}" pattern="¥#,##0.00#" />' style="text-align: right;"/>
			<input id="remain_deposit" type="hidden" value="${tagmt.remainDeposit}"/>
		</p>
		<p style="width:280px;">
			<label style="width: 100px;text-align: right;">订单总货款：</label>
			<input id="order_all_amt" type="text" readonly="readonly" value="¥0.00" style="text-align: right;"/>
			<input type="hidden" id="depositScale4Tord" value="${depositScale4Tord}"/>
		</p>
		<p style="width:280px;">
		   <label style="width: 70px;text-align: right;">备注：</label>
		   <textarea rows="3" cols="20" name="remark" class="required"></textarea>
		</p>
		<p style="width:280px;">
			<label style="width: 100px;text-align: right;">保证金可抵扣：</label>
			<input id="deposit_4_pay" type="text" readonly="readonly" value="¥0.00" style="text-align: right;"/>
		</p>
		<p style="width:280px;">
			<label style="width: 100px;text-align: right;">应付金额：</label>
			<input id="shoule_pay_amt" type="text" readonly="readonly" value="¥0.00" style="text-align: right;"/>
		</p>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">&nbsp;&nbsp;&nbsp;下一步&nbsp;&nbsp;&nbsp;</button>
					</div>
				</div></li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">&nbsp;&nbsp;&nbsp;取&nbsp;&nbsp;消&nbsp;&nbsp;&nbsp;</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>
<script type="text/javascript">
	$(function() {
		/***************************************************************/
		//异常、错误信息提示
		if("${ajaxResult.statusCode}" == "300") {
			alertMsg.error("${ajaxResult.message}");
			$.pdialog.closeCurrent();
		}else {
			if($("body").data("POSDDGL_DD")) {
				$.pdialog.close("POSDDGL_DD"); //关闭上一个页面
			}
			$("#dialogBackground").show(); //遮罩
		}
		
		/***************************每款产品可供货量合计************************************/
		$(".allSupplyNum").each(function() {
			var _all_supply_num = 0;
			var _id = $(this).attr("id");
			$("." + _id).each(function() {
				if($(this).html()) {
					_all_supply_num = _all_supply_num + parseInt($(this).html());
				}else {
					_all_supply_num = _all_supply_num + 0;
				}
			});
			$(this).html(_all_supply_num);
		});
		
		$(".digits").on("keypress", function(event) {
			var keyCode = event.which;
	        if (!(keyCode >= 48 && keyCode <= 57) && (keyCode != 8)) { //只能输入数字、回退键、删除键
	        	return false;
	        }
		});
		
		var _remainDeposit = parseFloat($("#remain_deposit").val()); //剩余可以使用保证金
		var _depositScale4Tord = parseFloat($("#depositScale4Tord").val()); //保证金抵扣比例
		$(".orderNumber").on("keyup", function() {
			/***************************************************************/
			if($(this).val().startWith("0")) { //去0化
				$(this).val("");
			}
			
			/***************************************************************/
			var _inputOrderNum = $(this).val(); //输入量
			var _supNumInt = parseInt($(this).parent().prev().children().html());
			if(_inputOrderNum) {
				var _inputOrderNumInt = parseInt(_inputOrderNum);
				if(_inputOrderNumInt > _supNumInt) { // 输入产品数量不应大于可供货量 
					$(this).val(_supNumInt);
					_inputOrderNumInt = _supNumInt;
				}
				
				/*******************************产品金额计算********************************/
				var _priceAfterRateFloat = parseFloat($(this).parent().children(".priceAfterRate").val()); //产品折扣价 
				var money = _inputOrderNumInt * _priceAfterRateFloat;
				$(this).parent().next().children().html(money.formatCurrency());
				$(this).parent().next().next().children().val(money);
			}else {
				$(this).parent().next().children().html("0.00");
				$(this).parent().next().next().children().val(0);
			}
			
			/*******************************订单总金额、保证金抵扣、应付款计算********************************/
			var _orderAllAmt = 0.00;  //订单总金额
			var _deposit4Pay = 0.00;  //保证金抵扣
			var _shouldPayAmt = 0.00; //应付款
			$(".orderNumber").each(function() {
				var _eachPdtAmt = $(this).parent().next().next().children().val();
				if(_eachPdtAmt) {
					var _eachPdtAmtFloat = parseFloat(_eachPdtAmt);
					_orderAllAmt = _orderAllAmt + _eachPdtAmtFloat;
				}else {
					_orderAllAmt = _orderAllAmt + 0;
				}
			});
			_deposit4Pay = _orderAllAmt * _depositScale4Tord;
			if(_deposit4Pay > _remainDeposit) {
				_deposit4Pay = _remainDeposit;
			}
			$("#order_all_amt").val("¥" + _orderAllAmt.formatCurrency());
			$("#deposit_4_pay").val("¥" + _deposit4Pay.formatCurrency());
			$("#shoule_pay_amt").val("¥" + (_orderAllAmt - _deposit4Pay).formatCurrency());
		});
	});
	
</script>