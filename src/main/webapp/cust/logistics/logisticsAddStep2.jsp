<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<div class="pageContent">
<form method="post" action="${ctx}/cust/logistics/toAddStep3.do"
	class="pageForm required-validate" 
	onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageFormContent" layoutH="65">
		<p style="float: none;">
			<label style="font-size: 15px;">订单编号：</label>
			<input type="text" readonly="readonly" value="${ord4page.tord.ordNo}" style="font-size: 15px;"/>
			<input type="hidden" name="tord.iord" value="${ord4page.tord.iord}">
		</p>
		<p style="float: none;">
			<label style="font-size: 15px;">下单时间：</label>
			<input type="text" readonly="readonly" value='<fmt:formatDate value="${ord4page.tord.genTime}" pattern="yyyy-MM-dd HH:mm:ss"/>' style="font-size: 15px;"/>
		</p>
		<p style="float: none;">
			<label style="font-size: 15px;">订单金额：</label>
			<input type="text" readonly="readonly" value='<fmt:formatNumber value="${ord4page.tord.amt}" pattern="¥#,##0.00#"/>' style="font-size: 15px;"/>
		</p>
		<p style="float: none;">
			<label style="font-size: 15px;">已支付金额：</label>
			<input type="text" readonly="readonly" value='<fmt:formatNumber value="${ord4page.havePaid}" pattern="¥#,##0.00#"/>' style="font-size: 15px;"/>
		</p>
		<p style="float: none;">
			<label style="font-size: 15px;">已使用货款金额：</label>
			<input type="text" readonly="readonly" value='<fmt:formatNumber value="${ord4page.tord.amtOfDelivery}" pattern="¥#,##0.00#"/>' style="font-size: 15px;"/>
		</p>
		<p style="float: none;">
			<label style="font-size: 20px;width: 200px;">请填写发货量：</label>
		</p>
		<table border="1" class="list" width="100%">
			<thead>
				<tr>
					<th width="100">产品</th>
					<th width="90">总量</th>
					<th width="90">已发货</th>
					<th width="90">未发货</th>
					<th width="90">可发货</th>
					<th width="90">现货量</th>
					<th width="90">发货量</th>
					<th>预计最快发货日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${ord4page.ordDetail4PageList}" var="ordDetail4Page" varStatus="ordDetail4PageStatus">
					<tr>
						<td align="left">&nbsp;${ordDetail4Page.pdtName}</td>
						<td align="right">${ordDetail4Page.tordDetail.num}&nbsp;台</td>
						<td align="right">${ordDetail4Page.tordDetail.deliveryed}&nbsp;台</td>
						<td align="right">${ordDetail4Page.tordDetail.pendingNum}&nbsp;台</td>
						<td align="right">${ordDetail4Page.tordDetail.remainQuota}&nbsp;台
							<input type="hidden" id="remainQuota${ordDetail4PageStatus.index}" value="${ordDetail4Page.tordDetail.remainQuota}" />
						</td>
						<td align="right">${ordDetail4Page.spodNum}&nbsp;台</td>
						<td>
							<input type="text" id="shipmentNum${ordDetail4PageStatus.index}" name="ordDetail4PageList[${ordDetail4PageStatus.index}].shipmentNum" maxlength="9" class="digits shipmentNumClass" min="0" style="width: 65px;" >&nbsp;台
							<input type="hidden" id="iordDetail${ordDetail4PageStatus.index}" name="ordDetail4PageList[${ordDetail4PageStatus.index}].tordDetail.iordDetail" value="${ordDetail4Page.tordDetail.iordDetail}" >
							<input type="hidden" id="ipdtHis${ordDetail4PageStatus.index}" name="ordDetail4PageList[${ordDetail4PageStatus.index}].tordDetail.ipdtHis" value="${ordDetail4Page.tordDetail.ipdtHis}" >
						</td>
						<td align="right">
							<input id="specifyDelivery${ordDetail4PageStatus.index}" type="text" name="ordDetail4PageList[${ordDetail4PageStatus.index}].specifyDelivery" readonly="readonly" style="text-align: center;">
						</td>
					</tr>
				</c:forEach>		
			</tbody>
		</table>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<a class="add" href="${ctx}/cust/logistics/toAddStep1.do" 
							target="dialog" mask="true" width="640" height="540"
							rel="CZWLXXGL_XZ" title="第一步：订单选择"><span>&nbsp;&nbsp;&nbsp;上一步&nbsp;&nbsp;&nbsp;</span></a>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="submit" class="button">&nbsp;&nbsp;&nbsp;下一步&nbsp;&nbsp;&nbsp;</button>
					</div>
				</div>
			</li>
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
</div>
<script type="text/javascript">

/***************************************************************/
//异常、错误信息提示
if("${ajaxResult.statusCode}" == "300") {
	alertMsg.error("${ajaxResult.message}");
	$.pdialog.closeCurrent();
}

	$(".shipmentNumClass").on("keyup", function() {
		if($(this).val().startWith("0")) { //去0化
			$(this).val("");
			return;
		}
		
		var _shipmentNumId = $(this).attr("id");
		var shipmentNum=toNum(_shipmentNumId);
		if(shipmentNum===""){
			return;
		}
		var idx = _shipmentNumId.substr(11);//"shipmentNum"长度是11
		
		var _remainQuota = parseInt($("#remainQuota" + idx).val()); //可发货量
		var _shipmentNumInt = parseInt(shipmentNum);
		if(_shipmentNumInt > _remainQuota) {
			$(this).val(_remainQuota);
		}
		
		var _shipmentNum = $(this).val();
		var _ipdtHis = $("#ipdtHis" + idx).val();
		
		shipmentNumChange(idx,_shipmentNum,_ipdtHis);
	});
	
	function toNum(inputTagId){
		var tag=$("#"+inputTagId);
		var v=tag.val();
		v=v.replace(/[^0-9]+/,"");
		tag.val(v);
		return v;
	}
	
	function shipmentNumChange(idx,_shipmentNum,_ipdtHis) {
		$("#specifyDelivery").empty();
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/cust/logistics/getSpecifyDelivery.do?shipmentNum=" + _shipmentNum+"&ipdtHis="+_ipdtHis+"&iord=${ord4page.tord.iord}",
		    async:false,  
			success : function(data) {
				for(var key in data) {
					$("#specifyDelivery"+idx).val(data["specifyDelivery"]);
				};
			}
		});
	}
</script>