<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>

<script type="text/javascript">
$("#dialogBackground").show();

		var logisticsNo='${ordAddr.realOrdno}';
		var logisticsName='${ordAddr.logisticsCompany.name}';
		var logisticsCode='${ordAddr.logisticsCompany}';
		
		$.ajax({ 
	        async: false, 
	      	url: "http://www.kuaidi100.com/query", 
	        type: "POST", 
	        dataType: 'jsonp', 
	        data: {type:logisticsCode,postid:logisticsNo}, 
	        timeout: 1000, 
	        //返回Json类型 
	        contentType: "application/json;utf-8", 
	        ContentEncoding : "System.Text.Encoding.UTF8",
	        success: function (result) { 
	        	if(result.status==403||result.status==201){
	        		var str = "<div style='text-align:center;color:red;font-size:12px;font-style:normal;font-weight:bold;'>"+result.message+"</div>";
	        		$("#info").html(str);
	        		return;
	        	}

	    		var str="<div style='text-align:center;color:#448ff4;font-size:15px;font-style:normal;font-weight:bold;'>"+logisticsName+"&nbsp;&nbsp;快递单号:"+logisticsNo+"</div><br><table border='1' class='list' width='100%' height='100%'>";
	        	 for(var i=0;i<result.data.length;i++){  
	        		 if(i==0){
	        			 str+="<tr><td width='45px'><font style='color:red'>"+result.data[i].time+"</font></td><td width='200px'><font style='color:red'>"+result.data[i].context+"</font></td></tr>";
	        		 }else{
	        			 str+="<tr><td width='45px'>"+result.data[i].time+"</td><td width='200px'>"+result.data[i].context+"</td></tr>";
	        		 }
	        	 }
	        	 $("#info").html(str);
	        	 str+="</table>";
	        	 $("#background").hide();
	        	 $("#progressBar").hide();
	        }, 
	        error: function (jqXHR, textStatus, errorThrown) { 
	            alert(textStatus+" ,获取物信息失败!"); 
	        } 
	    });
</script>
<div class="pageContent" selector="h1" layoutH="42">
	<div class="pageFormContent">
		<div id="info">
		</div>
	</div>
</div>
<div class="formBar">
	<ul>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" class="close">&nbsp;&nbsp;&nbsp;关&nbsp;&nbsp;闭&nbsp;&nbsp;&nbsp;</button>
				</div>
			</div>
		</li>
	</ul>
</div>
