<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>
<style>
</style>
   <form method="post" action="${ctx}/admin/logistics/logisticsorder/removeSingleConfirm.do" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
	<div class="pageContent" selector="h1" layoutH="42">
		    <input name="ilogisticsOrdAddr" type="hidden" value="${tlogisticsOrdAddr.ilogisticsOrdAddr}"/>
		    <div class="pageFormContent">
		        <p style="width:900px;">
			      <label style="width:100px;text-align: right;">物流单编号：</label>
			      <input type="text" name="innerOrdno" value="${tlogisticsOrd.innerOrdno}" readonly="readonly"/>
		        </p>
		        <p style="width:900px;">
			      <label style="width:100px;text-align: right;">物流序号：</label>
			      <input type="text" name="serial" value="${tlogisticsOrdAddr.serial}" readonly="readonly"/>
		        </p>
		        <p style="width:900px;">
			      <label style="width:100px;text-align: right;">原发货数量：</label>
			      <input type="text" name="num" value="${tlogisticsOrdAddr.num}" readonly="readonly"/>
		        </p>
		        <p style="width:900px;">
			      <label style="width: 100px;text-align: right;">收货地址：</label>
			      <input style="width:500px;" type="text" name="innerOrdno" value="${tlogisticsOrdAddr.consigneeAddr}" readonly="readonly"/>
		        </p>
		        <p style="width:900px;">
			      <label style="width:100px;text-align: right;">联系人：</label>
			      <input type="text" name="consigneeName" value="${tlogisticsOrdAddr.consigneeName}" readonly="readonly"/>
		        </p>
		        <p style="width:900px;">
			      <label style="width:100px;text-align: right;">联系电话：</label>
			      <input type="text" name="consigneeMobile" value="${tlogisticsOrdAddr.consigneeMobile}" readonly="readonly"/>
		        </p>
			    <p style="width:900px;">
			      <label style="width: 100px;text-align: right;">拆单类型：</label>
			      <select name="remType">
			          <option value="0" selected="selected">分单</option>
			          <!-- <option value="1">撤单</option> -->
			      </select>
		        </p>
		        <p style="width:900px;">
			      <label style="width: 100px;text-align: right;">拆单数量：</label>
			      <input class="required digits" min="1" max="${tlogisticsOrdAddr.num-1}" type="text" name="remNum" value=""/>
		        </p>
			</div>
		</div>
		<div class="formBar">
			<ul>
			    <li>
					<div class="button">
						<a class="add" href="${ctx}/admin/logistics/logisticsorder/toRemoveSingleLogisticsOrd.do?ilogisticsOrd=${tlogisticsOrd.ilogisticsOrd}" 
								target="dialog" mask="true" width="960" height="480"
								rel="WLDGL_CD" title="返回上一步"><span>返回上一步</span></a>
					</div>
			    </li>
			    <li>
			       <div class="buttonActive">
					  <div class="buttonContent">
						<button type="submit">&nbsp;&nbsp;&nbsp;确&nbsp;&nbsp;认&nbsp;&nbsp;&nbsp;</button>
					  </div>
				   </div>
				</li>
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
       <script type="text/javascript">
		     $(function() {
		    	//异常、错误信息提示
		 		if("${ajaxResult.statusCode}" == "300") {
		 			alertMsg.error("${ajaxResult.message}");
		 			$.pdialog.closeCurrent();
		 		}else {
		 			if($("body").data("WLDGL_CD")) {
		 				$.pdialog.close("WLDGL_CD"); //关闭上一个页面
		 			}
		 			$("#dialogBackground").show(); //遮罩
		 		}
		    	 
		     });
		</script>