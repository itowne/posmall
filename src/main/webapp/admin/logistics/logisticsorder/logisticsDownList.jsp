<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<form id="pagerForm" method="post" action="${ctx}/admin/warranty/warrantyDownLoadHis.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
</form>
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
			<c:forEach var="list" items="${tLogisticsDownList}" >
				<tr>
				     <td>&nbsp;${list.batchNo}</td>
				     <td>&nbsp;<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${list.genTime}"/></td>
				     <td>
						<div class="panelBar tablebtn">
							<ul class="toolBar">
								<li><a class="icon" href="${ctx}/admin/logistics/logisticsorder/logisticsordDownDetail.do?id=${list.batchNo}" target="dialog" mask="true" warn="" width="640" height="540" rel="logistics_down_detail"><span>下载详情</span></a></li>
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
				<option <c:if test="${numPerPage==10}">selected="selected"</c:if> value="10">10</option>
				<option <c:if test="${numPerPage==20}">selected="selected"</c:if> value="20">20</option>
				<option <c:if test="${numPerPage==50}">selected="selected"</c:if> value="50">50</option>
				<option <c:if test="${numPerPage==100}">selected="selected"</c:if> value="100">100</option>
			</select>
			<span>条，共${totalCount}条</span>
		</div>
		<div class="pagination" targetType="navTab" totalCount="${totalCount}" numPerPage="${numPerPage}" pageNumShown="10" currentPage="${pageNum}"></div>
	</div>
	</div>
	<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">关闭</button>
						</div>
					</div>
				</li>
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