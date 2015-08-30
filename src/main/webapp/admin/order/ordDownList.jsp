<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="pageContent">
		<div class="pageFormContent" layoutH="68">
						<table class="list" width="100%" layoutH="100">
		<thead>
			<tr>
				<th width="80">批次号</th>
				<th width="120">生成时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${tOrdDownList}" >
				<tr>
				     <td>&nbsp;${list.batchNo}</td>
				     <td>&nbsp;<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${list.genTime}"/></td>
				     <td>
						<div class="panelBar tablebtn">
							<ul class="toolBar">
								<li><a class="icon" href="${ctx}/admin/order/ordDownDetail.do?id=${list.batchNo}" target="dialog" mask="true" warn="" width="640" height="540" rel="ord_down_detail"><span>下载详情</span></a></li>
								<li><a class="add" href="${ctx}/file/download.do?fileid=${list.fileUUid}"><span>重新下载</span></a></li>
							</ul>
						</div>
					</td>
				</tr>
			</c:forEach>	
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>
	</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;&nbsp;</button>
						</div>
					</div></li>
			</ul>
		</div>
</div>
<script type="text/javascript">
	function downLoad(flag){
		if(flag=="query"){
			
		}else if(flag=="download"){
			document.logisticsForm.action="${ctx}/admin/logistics/logisticsorder/logisticsordDownLoad.do";
			$("#logisticsForm").attr("target","_blank");
			document.logisticsForm.submit();
		}else if(flag=="his"){
			
		}
	}
	function downLoadHis(){
		
	}
</script>