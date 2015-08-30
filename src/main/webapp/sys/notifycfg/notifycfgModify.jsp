<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<script type="text/javascript">
function validType(){
	var notifyType=$("#notifycfgModifyForm select[name='notifyType'] option:selected").val();
	var v;
	if(notifyType==""){
		v = true;
	}else{
	   $.ajax({  
	    type: "GET",  
	    contentType : "application/json",
	    url: "${ctx}/sys/notifycfg/validateNotifyTypeUniq.do?notifyType="+encodeURI(notifyType)+"&id="+$("#notifycfgModifyForm input[name='inotifyCfg']").val()+"&userId="+$("#notifycfgModifyForm input[name='iuser']").val(),
		dataType:"json",
	    async:false,  
	    success: function(data){  
	    	if(data.result != null && data.result != "null" && data.result != "") {
	    		alertMsg.warn(data.result);
		 		v = false;
	    	}else {
	    		v = true;
	    	}
	    }
	   });
	}
	return v;
}

</script>
<div class="pageContent">
	<form id="notifycfgModifyForm" method="post" action="${ctx}/sys/notifycfg/notifycfgModify.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
		    <input type="hidden" name="inotifyCfg" value="${tnotifyCfg.inotifyCfg}">
			<p>
				<label>通知类型：</label>
				<posmall:dictSelect selectName="notifyType" dictType="notify_type" cssClass="required" defaultValue="${tnotifyCfg.notifyType}" needAll="NO"></posmall:dictSelect>
			</p>
			<p>
				<label>用户编号：</label>
				<input type="text" name="iuser"   value="${tnotifyCfg.iuser}"   class="required digits" />
			</p>
			<p>
				<label>用户姓名：</label>
				<input type="text" name="userName"   value="${tnotifyCfg.userName}"  class="required" />
			</p>
			<p style="width:500px">
				<label>说明：</label>
				<textarea type="text" name="memo"  cols="35" class="required" rows="6">${tnotifyCfg.memo}</textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit" onclick="return validType();">&nbsp;&nbsp;修&nbsp;&nbsp;&nbsp;&nbsp;改&nbsp;&nbsp;</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
						</div>
					</div></li>

			</ul>
		</div>
	</form>
</div>