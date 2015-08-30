<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<form id="" method="post" action="${ctx}/cust/logistics/add.do"
	class="pageForm required-validate"
	onsubmit="return validateCallback(this, dialogAjaxDone);">
	
	<div class="pageContent sortDrag" selector="h1" layoutH="42">
		<div class="panel collapse" defH="">
			<h1 align="left">收货信息</h1>
			<div>
				<table border="1" class="list" width="100%" height="100%">
					<tr>
						<td colspan="4" align="center" style="font-weight: bolder;">
							<input id="iaddr" name="taddr.iaddr" value="" type="hidden"/>
							<a href="${ctx}/cust/addr/addrLookupSingle.do" lookupGroup="taddr">请先单击此处选择常用收货地址</a>
						</td>
					</tr>
					<tr>
						<td width="100" align="left" style="font-weight: bolder;">收货地址</td>
						<td align="left" colspan="3">
							<label style="float: none;">
								<input name="taddr.province" type="text" lookupGroup="taddr" readonly="readonly"/>
								<input name="taddr.city" type="text" lookupGroup="taddr" readonly="readonly"/>
								<input name="taddr.county" type="text" lookupGroup="taddr" readonly="readonly"/>
								<!-- 
								<select id="province" name="province" style="float: none;width: 150px;"></select>
								<select id="city" name="city" style="float: none;margin-left: 4px;width: 150px;"></select>
								<select id="county" name="county" style="float: none;margin-left: 4px;width: 150px;"></select>
								 -->
							</label>
						</td>
					</tr>
					<tr>
						<td width="100" align="left" style="font-weight: bolder;">街道地址</td>
						<td align="left" colspan="3">
							<textarea rows="3" cols="80" name="taddr.area" lookupGroup="taddr" class="required" readonly="readonly"></textarea>
						</td>
					</tr>
					<tr>
						<td width="100" align="left" style="font-weight: bolder;">邮政编码</td>
						<td align="left">
							<input class="required" name="taddr.postalCode" type="text" lookupGroup="taddr" readonly="readonly"/>
						</td>
						<td width="100" align="left" style="font-weight: bolder;">收货人姓名</td>
						<td align="left">
							<input class="required" name="taddr.name" type="text" lookupGroup="taddr" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td width="100" align="left" style="font-weight: bolder;">手机号码</td>
						<td align="left">
							<input name="taddr.mobile" type="text" lookupGroup="taddr" readonly="readonly"/>
						</td>
						<td width="100" align="left" style="font-weight: bolder;">电话号码</td>
						<td align="left">
							<input name="taddr.tel" type="text" lookupGroup="taddr" readonly="readonly"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="panel collapse" defH="">
			<h1 align="left">发货信息</h1>
			<div>				
				<table border="1" class="list" width="100%" height="100%">
					<thead>
						<tr>
							<td width="100" align="center" style="font-weight: bolder;">产品ID</td>
							<td width="100" align="center" style="font-weight: bolder;">库存订单</td>
							<td width="100" align="center" style="font-weight: bolder;">现货量</td>
							<td align="center" style="font-weight: bolder;">发货量</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${tCustStockList}" var="tsList" varStatus="tsListStatus">
							<tr>
								<td align="center">
									<posmall:dict dictType="pdt_name_all" dictCode="${tsList.ipdt}"/>
								</td>
								<td align="center">${tsList.sumOrdNum}</td>
								<td align="center">${tsList.sumSpodNum}</td>
								<td align="center">
									<input type="text" name="logisticsOrdObjList[${tsListStatus.index}].count" maxlength="8" class="digits"/>台
									<input type="hidden" name="logisticsOrdObjList[${tsListStatus.index}].ipdt" value="${tsList.ipdt}"/>
								</td>
							</tr>
						</c:forEach>
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
							<posmall:dictSelect selectName="ilogisticsCom" dictType="logistics_com" needAll="NO"/>
						</td>
						<td width="100" align="left" style="font-weight: bolder;">是否选用付费物流</td>
						<td align="left">
							<input type="text" id="feeFlag" name="feeFlag" readonly="readonly">
						</td>
					</tr>
					<tr>
						<td width="100" align="left" style="font-weight: bolder;">时效</td>
						<td align="left">
							<input type="text" id="aging" name="aging" readonly="readonly">
						</td>
						<td width="100" align="left" style="font-weight: bolder;">物流费用</td>
						<td align="left">
							<input type="text" id="fee" name="fee" readonly="readonly">
						</td>
					</tr>
					<tr>
						<td width="100" align="left" style="font-weight: bolder;">预计发货日期</td>
						<td align="left">
							<input type="text" id="specifyDelivery" name="specifyDelivery" class="date required" minDate="{%y}-%M-%d" >
						</td>
						<td width="100" align="left" style="font-weight: bolder;">是否保证金支付</td>
						<td align="left">
							<posmall:dictSelect selectName="depositFlag" dictType="yes_no_type" needAll="NO"/>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="submit">确认</button>
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
<script type="text/javascript">
/****************** 初始化省市区三级联动 *******************/
//	$(function(){
//		_init_area();
//	});
	
	function _init_area(){  //初始化函数
		document.getElementById("province").options[0]=new Option("省份", "");
		document.getElementById("city").options[0]=new Option("地级市", "");
		document.getElementById("county").options[0]=new Option("市、县级市", "");
		
		initProvince();
		document.getElementById("province").onchange=new Function("provinceChange()");
		document.getElementById("city").onchange=new Function("cityChange()");
	}
	
	function initProvince() {
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/cust/addr/getProvince.do",
			success : function(data) {
				var idx = 1;
				for(var code in data) {
					document.getElementById("province").options[idx++]=new Option(data[code], code);
				}
			}
		});
	}
	
	function provinceChange() {
		$("#city").empty();
		document.getElementById("city").options[0]=new Option("地级市", "");
		$("#county").empty();
		document.getElementById("county").options[0]=new Option("市、县级市", "");
		
		var _provCode = $("#province").val();
		if(_provCode == null || _provCode.trim() == "") {
			return;
		}
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/cust/addr/getCity.do?provCode=" + _provCode,
			success : function(data) {
				var idx = 1;
				for(var code in data) {
					document.getElementById("city").options[idx++]=new Option(data[code], code);
				}
			}
		});
	}
	
	function cityChange() {
		$("#county").empty();
		document.getElementById("county").options[0]=new Option("市、县级市", "");
		
		var _provCode = $("#city").val();
		if(_provCode == null || _provCode.trim() == "") {
			return;
		}
		$.ajax({
			type : "POST",
			contentType : 'application/json',
			dataType : 'json',
			url : "${ctx}/cust/addr/getCity.do?provCode=" + _provCode,
			success : function(data) {
				var idx = 1;
				for(var code in data) {
					document.getElementById("county").options[idx++]=new Option(data[code], code);
				}
			}
		});
	}
</script>
