<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>

<script type="text/javascript">
function validMsgTmp(){
	var tmpType=$("#msgtmpAddForm select[name='tmpType'] option:selected").val();
	var tmpCode = $("#msgtmpAddForm input[name='tmpCode']").val();
	var v;
	   $.ajax({  
	    type: "GET",  
	    contentType : "application/json",
	    url: "${ctx}/sys/msgtmp/validateMsgTmpUniq.do?tmpType="+encodeURI(tmpType)+"&tmpCode="+encodeURI(tmpCode),
	    dataType:"json",
	    async:false,  
	    success: function(data){  
	 	  if(data.result == "true"){
	 		 v = true;
	 	  }else{
	 		  alertMsg.info("同模板类型模板代码不能重复");
	 		 v = false;
	 	  }
	    }
	   });
	return v;
}

</script>
<div class="pageContent">
	<form id="msgtmpAddForm" method="post" action="${ctx}/sys/msgtmp/msgtmpAdd.do"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<p>
				<label>模板类型：</label>
				<posmall:dictSelect selectName="tmpType" cssClass="required" dictType='msg_tmp_type' needAll="NO" defaultValue="${tmsgtmp.tmpType}" > </posmall:dictSelect>
			</p>
			<p>
				<label>模板代码：</label>
				<input type="text" name="tmpCode" class="required"  value="${tmsgtmp.tmpCode}" style="width: 200px"/>
			</p>
			<p style="width:500px">
				<label>模板名称：</label>
				<textarea name="noteName" class="required" cols="35"  rows="6" style="height: 40px;width: 300px">${tmsgtmp.noteName}</textarea>
			</p>
			<p></p>
			<p style="width:500px">
				<label>内容：</label>
				<textarea name="content" class="required editor" cols="35"  rows="6">${tmsgtmp.content}</textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit" onclick="return validMsgTmp();" >&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;&nbsp;交&nbsp;&nbsp;</button>
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