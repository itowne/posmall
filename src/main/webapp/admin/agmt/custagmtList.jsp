<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/posmall-tags" prefix="posmall"%>

<script type="text/javascript">
  function deleteAgmt( id){
	  var obj = $.ajax({
			url : "${ctx}/admin/agmt/validateDeleteAgmt.do?iagmt="+ id+"&time="+new Date(),
			type : "GET",
			async : false
		});
		var temp = eval("("+obj.responseText+")");
		if(temp.result.trim().length == 0){
			alertMsg.info("该协议已付款不能撤销...");		
		}else if(temp.result == "N"){
			 alertMsg.confirm("确认是否撤销协议？",{
					okCall: function(){
						$.ajax({  
						    type: "GET",  
						    contentType : "application/json",
						    url: "${ctx}/admin/agmt/removeAgmt.do?iagmt="+id,
							dataType:"json",
						    async:false,  
						    success: function(data){  
						    	if(data.result == "true"){
						    		toSubmit("query");
						    	}
						    }
						   });
						
					}
			  });
		}else if (temp.result == "Y"){
			alertMsg.info("该协议已付款不能撤销...");		
		}else{
			alertMsg.info("该协议已付款不能撤销...");		
		}
	  
  }
  
  function pdtNameMin(v){
	  var result = v;
	  if(v.length > 26){
		  result = v.substring(0,25)+"...";
	  }
	  
	  return result;
  }
  
</script>


	<form id="pagerForm" method="post"
		action="${ctx}/admin/agmt/custagmtList.do">
		<input type="hidden" name="pageNum" value="${pageNum}" /> 
		<input type="hidden" name="numPerPage" value="${numPerPage}" /> 
		<input type="hidden" name="startTime" value="${startTime}" /> 
		<input type="hidden" name="ednTime" value="${ednTime}" /> 
		<input type="hidden" name="agmtStatus" value="${agmtStatus}" />
		<input type="hidden" name="name" value="${name}" />
	</form>

	<div class="pageHeader">

		<form id="custagmForm" name="custagmForm" onsubmit="return navTabSearch(this);"
			action="" method="post">
			<div class="searchBar">
				<table class="searchContent">
					
					<tr>
						<td><label >客户名称：</label> 
						      <input type="text" name="name" value="${name}" />
						</td>
						<td><label style="width: 50px;">状态：</label> <posmall:dictSelect
								selectName="agmtStatus" dictType="agmt_status"
								defaultValue="${agmtStatus}"></posmall:dictSelect></td>
					</tr>
					<tr>
						<td colspan="2"><label >有效时间：</label>
							<p style="float: left">
											<input id="startTime"  name="startTime" type="text" class="date" readonly="readonly"
									value="${startTime}" />
							</p> <label style="width: 20px; text-align: center">－</label>
							<p style="float: left">
								<input id="endTime" type="text" class="date" name="endTime" readonly="readonly" value="${endTime}" />
								
							</p></td>
					</tr>
				</table>
				
				<div class="subBar">
					<ul>
						<li>
						<div class="buttonActive">
								<div class="buttonContent">
									<posmall:authButton type="button" authCode="KHDHXYGL_XZ" onclick="toSubmit('downLoad')" displayVal="下载"/>
								</div>
							</div>
						</li>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="toSubmit('query');">查询</button>
								</div>
							</div></li>
					</ul>
				</div>
				<script type="text/javascript">
					function beforeCheck() {
						var startTime = $("#startTime").val(), endTime = $(
								"#endTime").val();
						if (startTime && endTime && startTime > endTime) {
							alertMsg.info("起始日期不能大于结束日期");
							return false;
						}
					}
				</script>
			</div>
		</form>
	</div>
	<div class="pageContent">

		<table class="list" width="100%" layoutH="118">
			<thead>
				<tr>
					<th align="center" width="80">协议编号</th>
					<th align="center" width="100">客户名称</th>
					<th align="center" width="70">协议数量</th>
					<th align="center" width="90">总金额</th>
					<th align="center" width="80">保证金</th>
					<th align="center" width="80">起始日期</th>
					<th align="center" width="80">终止日期</th>
					<th align="center" width="90">协议状态</th>
					<th align="center" width="80">生效日期</th>
					<th align="center" width="80">物流欠费</th>
					<th align="center" width="300">操作</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="list" items="${custagmtList}">
					<tr title='产品[<posmall:dict dictType="pdt_name_all" dictCode="${list[11]}" outSeparator=","></posmall:dict>]'>
						<td>&nbsp;<a
							href="${ctx}/admin/agmt/custagmtDetail.do?iagmt=${list[0]}"
							rel="KHJDXYGL_XX" target="dialog" mask="true" title="详细信息"
							width="850" height="480" ><span>${list[1]}</span></a>
						</td>
						<td align="left">&nbsp;<posmall:dict dictType="custreg_name" dictCode="${list[10]}"/></td>
						<td align="right">${list[12]}台&nbsp;</td>
						<td align="right"><fmt:formatNumber value="${list[13]}" pattern="¥#,##0.00#"/>&nbsp;</td>
						<td align="right"><fmt:formatNumber value="${list[4]}" pattern="¥#,##0.00#"/>&nbsp;</td>
						<td align="left">&nbsp;${list[2]}</td>
						<td align="left">&nbsp;${list[3]}</td>
						<td align="left">&nbsp;<posmall:dict dictType="agmt_status"
								dictCode="${list[9]}"></posmall:dict>
						</td>
						<td>&nbsp;<fmt:formatDate value="${list[8]}"
								pattern="yyyy-MM-dd" /></td>
					    <td>
					       <c:choose>
					         <c:when test='${list[14]=="true"}'>是</c:when>
					         <c:otherwise>否</c:otherwise>
					       </c:choose>
					    </td>
						<td>
							<div class="panelBar tablebtn">
								<ul class="toolBar">
									<c:choose>
										<c:when test="${list[9] == 'AGMT_SUBMIT'}">
											<li><posmall:authA authCode="KHDHXYGL_XYEDQR" cssClass="icon"
												href="${ctx}/admin/agmt/custagmtLimitCheckPage.do?iagmt=${list[0]}"
												target="dialog" mask="true" rel="KHJDXYGL_XYEDQR"
												title="协议额度确认" width="850" height="480" value="额度确认" /></li>
											<li><posmall:authA authCode="KHDHXYGL_CX" cssClass="delete" href="#" onclick="deleteAgmt(${list[0]});"  rel="KHJDXYGL_SC" title="" value="撤销" /></li>
										</c:when>
										<c:when test="${list[9] == 'AGMT_QUOTA_CONFIRM'}">
											<li><posmall:authA authCode="KHDHXYGL_CX" cssClass="delete" href="#" onclick="deleteAgmt(${list[0]});"  rel="KHJDXYGL_SC" title="" value="撤销" /></li>
										</c:when>
										<c:when test="${list[9] == 'PAY_PASS'}">
											<li><posmall:authA authCode="KHDHXYGL_SH" cssClass="icon"
												href="${ctx}/admin/agmt/custagmtPayPassCheckPage.do?iagmt=${list[0]}"
												target="dialog" mask="true" rel="KHJDXYGL_SH" title="审核"
												width="850" height="480" value="确认" /></li>
										</c:when>
										<c:when test="${list[9] == 'SUBMIT_CHANGE'}">
											<li><posmall:authA authCode="KHDHXYGL_SH" cssClass="icon"
												href="${ctx}/admin/agmt/custagmtPayPassCheckPage.do?iagmt=${list[0]}"
												target="dialog" mask="true" rel="KHJDXYGL_SH" title="变更审核"
												width="850" height="480" value="变更确认" /></li>
										</c:when>
									</c:choose>
									<li><posmall:authA authCode="KHDHXYGL_SZDDH" cssClass="icon"
												href="${ctx}/admin/agmt/setErpOrdId.do?iagmt=${list[0]}"
												target="dialog" mask="true" rel="KHDHXYGL_SZDDH" title="设置erp订单号"
												width="400" height="180" value="设置erp订单号" /></li>
								</ul>
							</div>
						</td>
						<td></td>
					</tr>
				</c:forEach>
		</table>
		<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option <c:if test="${numPerPage==10}">selected="selected"</c:if> value="10" >10</option>
				<option <c:if test="${numPerPage==20}">selected="selected"</c:if> value="20">20</option>
				<option <c:if test="${numPerPage==50}">selected="selected"</c:if> value="50">50</option>
				<option <c:if test="${numPerPage==100}">selected="selected"</c:if> value="100">100</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>
		
		<div class="pagination"  targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>

	</div>
	</div>
<script type="text/javascript">
	function toSubmit(flag){
		if(flag=='query'){
			document.custagmForm.action="${ctx}/admin/agmt/custagmtList.do";
			document.custagmForm.onsubmit();
		}else{
			document.custagmForm.action="${ctx}/admin/agmt/downLoadCustAgmt.do";
			$("#custagmForm").attr("target","_blank");
			document.custagmForm.submit();
		}
	}
</script>