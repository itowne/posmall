<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<script type="text/javascript">
function validContract(){
	var name = $("#contractAddForm input[name='contractName']").val();
	var v;
	   $.ajax({  
	    type: "GET",  
	    contentType : "application/json",
	    url: "${ctx}/admin/contract/validateContractUniq.do?name="+encodeURI(name)+"&time="+new Date(),
	    dataType:"json",
	    async:false,  
	    success: function(data){  
	 	  if(data.result == "true"){
	 		 v = true;
	 	  }else{
	 		  alertMsg.info("合同名称不能重复");
	 		 v = false;
	 	  }
	    }
	   });
	return v;
}
 function changeCustCode(v){
	 var str = $(v).find("option:selected").attr("id").split("_");
	   $(v).next().val(str[0]);
	   $(v).parent().next().find("input").val(str[2]);
 }
 

</script>
<div class="pageContent">
	<form id="contractAddForm" method="post" action="${ctx}/admin/contract/contractAdd.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			
		    <p>
				<label>注册码：</label>
				
				<select name="custCode" onclick="changeCustCode(this)">
				    <option value="" id="_">-请选择-</option>
				   <c:forEach var="map" items="${custCodeMap}">
				      <option value="${map.value}" id="${map.key}_${map.value}_<posmall:dict dictType='custreg_name' dictCode='${map.key}'/>">${map.value}</option>
				   </c:forEach>
				</select>
				<input type="hidden" name="icust" />
			</p>
			<p>
				<label>公司名称：</label>
				<input type="text" name="custRegName"  readonly="readonly"  value=""  />
			</p>
			<p>
				<label>合同名称：</label>
				<input type="text" name="contractName"  class="required" value="${tcontract.contractName}"  />
			</p>
			<p>
				<label>采购框架合同编号：</label>
				<input type="text" name="contractNo"  class="required" value="${tcontract.contractNo}"  />
			</p>
			<p style="width:500px">
				<label>内容：</label>
				<textarea type="text" name="content" class="required" cols="35"  rows="6">${tcontract.content}</textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit" >&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;&nbsp;交&nbsp;&nbsp;</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>