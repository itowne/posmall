<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<style type="text/css">
	.table{
		margin: 20px 5px;
	}
</style>


<form id="agmtlimitForm" method="post" action="${ctx}/admin/agmt/custagmtLimitCheck.do" class="pageForm required-validate"
	onsubmit="return validateCallback(this, dialogAjaxDone);">
<div class="pageContent" layoutH="38">
<script type="text/javascript">
  //修改折扣率
  function modifyRate(v){
	    var rate = parseFloat($("#depositRateFloat").val());
	    $(v).val($(v).val().trim());
	    //修改折扣价
	    var t =  $(v).parent().parent().find(" > td input[id='price']").val() * $(v).val();
	    $(v).parent().parent().find(" > td input[id='zkj']").val(t.toFixed(2));
	    
	    //修改总金额
	    var totalAmt = 0;
	    $("#agmtDetailTbody > tr").each(function() {
			totalAmt += $(this).find(" > td input[id='num']").val() * $(this).find(" > td input[id='zkj']").val();
		});
	    $("#agmtlimitForm  input[name='modifyAmt']").val(totalAmt.toFixed(2));
	    $("#agmtlimitForm  input[name='modifyDeposit']").val((totalAmt*rate).toFixed(2));
	    $("#agmtlimitForm  input[name='remainDeposit']").val((totalAmt*rate).toFixed(2));
	  
  }
   //修改数量
  function modifyNum(v){
	    var rate = parseFloat($("#depositRateFloat").val());
	  	$(v).val($(v).val().trim());
	  	if(parseInt($(v).parent().children().first().val()) < parseInt($(v).val()) ){
	  		alertMsg.info("额度不允许大于客户所填的额度");
	  		$(v).val($(v).parent().children().first().val());
	  	}
	   
	    //修改订货量 
	    var totalnum = 0;
	    $("#agmtDetailTbody > tr").each(function() {
	    	totalnum +=  parseInt($(this).find(" > td input[id='num']").val());
		});
	    $("#agmtlimitForm  input[name='sumnum']").val(totalnum);
	    
	    //修改总金额
	    var totalAmt = 0;
	    $("#agmtDetailTbody > tr").each(function() {
			totalAmt +=  parseInt($(this).find(" > td input[id='num']").val()) *  parseFloat($(this).find(" > td input[id='zkj']").val());
		});
	    $("#agmtlimitForm  input[name='modifyAmt']").val(totalAmt.toFixed(2));
	    $("#agmtlimitForm  input[name='modifyDeposit']").val((totalAmt*rate).toFixed(2));
	    $("#agmtlimitForm  input[name='remainDeposit']").val((totalAmt*rate).toFixed(2));
	  
  }
   function quotaConfirm(){
	   var flag = false;
	    $("#agmtDetailTbody  input[id='num']").each(function(){
	    	if(! /^((([1-9][0-9]{0,8})[0]{2})||0)$/.test($(this).val())){
	    		flag = (flag || true);
	    	}
	    });
	    if(flag){
	    	alertMsg.info("产品的订货量为整百数");
	    	return;
	    }

	  var sumnum =  $("#agmtlimitForm  input[name='sumnum']").val();
	  var minnum = $("#agmtlimitForm_minNum").val();
	   if(parseInt(sumnum) < parseInt(minnum)){
		   alertMsg.info("协议总订货量不能低于" + minnum + "台");
	   }else if( !/^(([1-9][0-9]{0,7})[0]{3})$/.test(sumnum)){
		   alertMsg.info("协议总订货量增量为1000");
	   }else{
		   $("#agmtlimitForm").submit();
	   }
	
   }
  

</script>
	<div class="pageFormContent">
	    <input type="hidden" id="agmtlimitForm_minNum" value="${minNum}">
	    <input type="hidden" id="depositRateFloat" value="${depositRateFloat}">
		<p>
			<label>协议编号：</label>
			<input type="text" value="${tagmt.agmtNo}"  readonly="readonly"/>
		</p>
		<p>
			<label>客户名称：</label>
			<input type="text" value="${tcustReg.name}"  readonly="readonly"/>
		</p>
		<p>
			<label>协议生效开始时间：</label>
			<input type="text" value='<fmt:formatDate value="${tagmt.startTime}" pattern="yyyy-MM-dd" />'  readonly="readonly"/>
		</p>
		<p>
			<label>协议生效结束时间：</label>
			<input type="text" value='<fmt:formatDate value="${tagmt.endTime}" pattern="yyyy-MM-dd" />'  readonly="readonly"/>
		</p>
<!-- 		<p> -->
<!-- 			<label>已用保证金：</label> -->
			<input type="hidden" value="${tagmt.usedDeposit}"  readonly="readonly"/><label style="width:30px"></label>
<!-- 		</p> -->
<!-- 		<p> -->
<!-- 			<label>剩余保证金：</label> -->
			<input type="hidden" name="remainDeposit"  value="${tagmt.remainDeposit}"  readonly="readonly"/><label style="width:30px"></label>
<!-- 		</p> -->
		<p>
			<label>协议状态：</label>
			<input type="text" value="<posmall:dict dictType='agmt_status' dictCode='${tagmt.agmtStatus}'></posmall:dict>" readonly="readonly"/>
		</p>
      	<input type="hidden" name="iagmt" value="${tagmt.iagmt}"/>
      	<p/>
	  <div class="table">
		<table class="list" width="100%">
			<thead>
				<tr>
					<th width="200">产品名称</th>
					<th width="100">单价</th>
					<th width="100">协议数量</th>
					<th width="100">折扣率</th>
					<th width="100">折扣价</th>
				</tr>
			</thead>
			<tbody id="agmtDetailTbody">
				<c:forEach items="${agmtTpdtList}" var="agmtTpdt" varStatus="status">			
				<tr>
					<td>${agmtTpdt[1]}</td>
					<td align="right"><input type="hidden" name="agmtDetailList[${status.index}].iagmtDetail" value="${agmtTpdt[5]}"> <input type="text" id="price" readonly="readonly" value="${agmtTpdt[2]}"></td>
					<td><input type="hidden" name="oldNum${status.index}" value="${agmtTpdt[3]}"> <input type="text" id="num" onkeyup="modifyNum(this);" class="required digits" name="agmtDetailList[${status.index}].num" value="${agmtTpdt[3]}"></td>
					<td><input type="text" class="required deposit_rate" id="rate" name="agmtDetailList[${status.index}].rate" value="${agmtTpdt[4]}" onkeyup="modifyRate(this);"/></td>
					<td><input type="text" readonly="readonly" id="zkj" name="zkj" value='${agmtTpdt[6]}'/></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	  </div>
		<p style="width: 240px;margin-top: 20px;">
			<label style="width: 60px;">订货总量：</label>
			<input type="text" name="sumnum" value="${sumnum}" readonly="readonly"/>
			<span class="info">台</span>
		</p>
		<p style="width: 240px;margin-top: 20px;">
			<label style="width: 50px;">总金额：</label>
			<input type="text" name="modifyAmt" value="${sumamt}" readonly="readonly"/>
			<span class="info">元</span>
		</p>
		<p style="width: 240px;margin-top: 20px;">
			<label style="width: 50px;">保证金：</label>
			<input type="text" name="modifyDeposit" value="${tagmt.deposit}" readonly="readonly"/>
		</p>
  	</div>
</div>


	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" onclick="quotaConfirm();">&nbsp;额度确认&nbsp;</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">&nbsp;&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</form>