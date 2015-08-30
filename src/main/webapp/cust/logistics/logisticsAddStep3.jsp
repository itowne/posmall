<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<form id="pagerForm" method="post" action="${ctx}/cust/logistics/toAddStep3Forword.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="name" value="${name}" />
	<input type="hidden" name="specifyDelivery" value="${specifyDelivery}" />
	<input type="hidden" name="tlogisticsOrd.ilogisticsOrd" value="${logisticsOrd4Page.tlogisticsOrd.ilogisticsOrd}">
	<input type="hidden" id="toAddStep4Form_pagerForm_ilogisticsCom" name="ilogisticsCom" value="${logisticsOrd4Page.ilogisticsCom}">
</form>
<form method="post" action="${ctx}/cust/logistics/toAddStep4.do"
	id="toAddStep4Form" class="pageForm"
	onsubmit="return validateCallback(this, dialogAjaxDone);">

	<div class="pageContent" selector="h1" layoutH="42">
		<input type="hidden" name="tlogisticsOrd.ilogisticsOrd" value="${logisticsOrd4Page.tlogisticsOrd.ilogisticsOrd}">
		<div class="pageFormContent">
			<div class="panel collapse" defH="">
				<h1 align="left">收货地址</h1>
				<div>
					<table >
						<tr>
							<td><label style="width: 60px">收货人：</label><input type="text" name="name" value="${name}" />
							</td>
							<td>
							<div class="buttonActive">
									<div class="buttonContent">
										<button  type="button" onclick="query();">查询</button>
									</div>
									<a class="button" href="${ctx}/cust/addr/toAdd.do?specifyDelivery=${specifyDelivery}&ilogisticsOrd=${logisticsOrd4Page.tlogisticsOrd.ilogisticsOrd}" target="dialog" mask="true"
									   title="新增收货地址" width="660" height="480" rel="CZWLXXGL_XZ" ><span>新增地址</span></a>
									
							</div>
						    </td>
						</tr>
					</table>	
					<table border="1" class="list" width="100%" height="100%">
						<c:forEach items="${taddrList}" var="taddr">
							<tr>
								<td>
									<input type="radio" name="iaddr_radio" class="radio" value="${taddr.iaddr}">
								</td>
								<td>${taddr.result}&nbsp;&nbsp;邮编(${taddr.postalCode})&nbsp;&nbsp;收货人(${taddr.name})&nbsp;&nbsp;联系电话(${taddr.mobile}&nbsp;${taddr.tel})</td>
							</tr>
						</c:forEach>
					</table>
				<div class="panelBar" >
					<div class="pages">
					    <input type="hidden" id="dialog_totalCount" name="totalCount" value="${totalCount}"/>
						<span id="totalCount">共${totalCount}条</span>
					</div>
			
					<div id="pageNumDIV" class="pagination" targetType="dialog" totalCount="${totalCount}"
						numPerPage="5" pageNumShown="10" currentPage="${pageNum}"></div>
			     </div>
		     	</div>
				<!-- 物流地址id -->
				<input type="hidden" name="iaddr" value="" />
			</div>
			<div class="panel collapse" defH="">
				<h1 align="left">发货信息</h1>
				<div>
				<table border="1" class="list" width="100%" height="100%">
					<thead>
						<tr>
							<th width="150">产品名称</th>
							<th>发货量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${logisticsOrd4Page.logisticsOrdAddr4PageList}"
							var="logisticsOrdAddr4Page"
							varStatus="logisticsOrdAddr4PageStatus">
							<tr>
								<td align="left">&nbsp;${logisticsOrdAddr4Page.pdtName}</td>
								<td align="center"><span class="eachNum">${logisticsOrdAddr4Page.tlogisticsOrdAddr.num}</span>&nbsp;台</td>
							</tr>
						</c:forEach>
						<tr>
							<td align="center">合&nbsp;&nbsp;&nbsp;&nbsp;计</td>
							<td align="center"><span class="allNum"></span>&nbsp;台</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div class="panel collapse" defH="">
				<h1 align="left">物流信息</h1>
				<div>
					<table border="1" class="list" width="100%" height="100%">
						<tr>
							<td width="100" align="left" style="font-weight: bolder;">物流公司</td>
							<td align="left">
								<posmall:dictSelect selectName="ilogisticsCom" dictType="logistics_com" defaultValue="${tlogisticsCom.ilogisticsCom}" cssClass="NO"/>
							</td>
							<td width="100" align="left" style="font-weight: bolder;">时效</td>
							<td align="left">
								<input type="text" id="aging" readonly="readonly" value="${tlogisticsCom.aging}">(天)
							</td>
						</tr>
						<tr>
							<td width="120" align="left" style="font-weight: bolder;">是否选用付费物流</td>
							<td align="left">
								<input type="text" id="feeFlag" readonly="readonly" value="<posmall:dict dictType="yes_no_type" dictCode="${tlogisticsCom.feeFlag}"></posmall:dict>">
							</td>
							<td width="100" align="left" style="font-weight: bolder;">单价</td>
							<td align="left">
								<input type="text" id="price" readonly="readonly" value="${tlogisticsCom.price}">(元/台)
							</td>
						</tr>
						<tr>
							<td width="100" align="left" style="font-weight: bolder;">物流费用</td>
							<td align="left">
								<input type="text" id="fee" readonly="readonly" value="<fmt:formatNumber value="${fee}" pattern="¥#,##0.00#"/>">
							</td>
							<td width="100" align="left" style="font-weight: bolder;">预计发货日期</td>
							<td align="left">
								<input type="text" id="specifyDelivery" name="specifyDelivery" class="date required"
								minDate="${specifyDelivery}" value="${specifyDelivery}">
							</td>
						</tr>
					</table>
				</div>
			</div>
			
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="button" onclick="return beforeSubmit();">&nbsp;&nbsp;&nbsp;确&nbsp;&nbsp;定&nbsp;&nbsp;&nbsp;</button>
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
<script type="text/javascript">

	$(function() {
		/****************************************************************/
		var _all_num = 0;
		$(".eachNum").each(function() {
			if($(this).html()) {
				_all_num = _all_num + parseInt($(this).html());
			}
		});
		$(".allNum").html(_all_num);
		
		/****************************************************************/
		$("select[name = 'ilogisticsCom']").on("change", function(e) {
			//alert($(this).val());
			var selectedId = $(this).val();
			if(selectedId) {
				$("#toAddStep4Form_pagerForm_ilogisticsCom").val(selectedId);
				getLogisticsComData(selectedId);
			}
		});
		
		/****************************************************************/
		$(".radio").on("click", function() {
			$("input[name = 'iaddr']").val($(this).val());
		});
		
		$(".dialogHeader_c > h1").html("第三步：发货信息填写");
	});
	
	function query(){
		   $("#toAddStep4Form").attr("action","${ctx}/cust/logistics/toAddStep3Forword.do");
		   $("#toAddStep4Form").attr("onsubmit","return dialogSearch(this);");
		   $("#toAddStep4Form").submit();
	   }
	
	function getLogisticsComData(id) {
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/cust/logistics/getLogisticsCom4Ajax.do?ilogisticsCom=" + id,
			success : function(data) {
				//alert(JSON.stringify(data));
				//$("input[name = 'ilogisticsCom']").val(data.ilogisticsCom);
				//$("input[name = 'feeFlag']").val(data.feeFlag);
				//$("input[name = 'aging']").val(data.aging);
				//$("input[name = 'price']").val(data.price);
				if(data.feeFlag == "YES") {
					$("#feeFlag").val("是");
				}else {
					$("#feeFlag").val("否");
				}
				$("#aging").val(data.aging);
				$("#price").val(data.price);
				var _all_num = 0;
				if($(".allNum").html()) {
					_all_num = parseInt($(".allNum").html());
				}
				var _price = parseFloat(data.price);
				$("#fee").val("¥" + (_all_num * _price).formatCurrency());
			}
		});
	}
	
	function beforeSubmit() {
		var _iaddr = $("input[name = 'iaddr']").val();
		if(_iaddr == null || _iaddr.trim() == "") {
			alertMsg.error("请选择收货地址！");
			return false;
		}
		var _specifyDelivery = $("#specifyDelivery").val();
		if(_specifyDelivery == null || _specifyDelivery.trim() == "") {
			alertMsg.error("请预计发货日期！");
			return false;
		}
		var _ilogisticsCom = $("select[name = 'ilogisticsCom']").val();
		if(_ilogisticsCom == null || _ilogisticsCom.trim() == "") {
			alertMsg.error("请选择物流公司！");
			return false;
		}
		$("#toAddStep4Form").attr("action","${ctx}/cust/logistics/toAddStep4.do");
		$("#toAddStep4Form").attr("onsubmit","return validateCallback(this, dialogAjaxDone);");
		$("#toAddStep4Form").submit();
	}
</script>