<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/posmall-tags" prefix="posmall" %>
<form id="pagerForm" method="post" action="${ctx}/admin/cust/custList.do">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<input type="hidden" name="name" value="${name}" />
	<input type="hidden" name="custStatus" value="${custStatus}" />
	<input type="hidden" name="creditLevel" value="${creditLevel}" />

</form>

<div class="pageHeader">
	<form id="queryForm" name="queryForm" onsubmit="return navTabSearch(this);" action="" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					公司名称：<input type="text" name="name" value="${name}" />
				</td>
				<td>
					状态：<posmall:dictSelect selectName="custStatus" dictType="cust_status" defaultValue="${custStatus}" />
				</td>
				<td>
					信用等级： <posmall:dictSelect selectName="creditLevel" dictType="credit_level" defaultValue="${creditLevel}" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent">
					<posmall:authButton type="button" authCode="KHXXGL_XZ" onclick="toSbumit('downLoad')" displayVal="下载"/>
					</div></div>
				</li>	
				<li><div class="buttonActive"><div class="buttonContent">
					<button type="button" onclick="toSbumit('query')">查询</button>
					</div></div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="list" width="100%" layoutH="98">
		<thead>
			<tr>
				<th align="center" width="80">登陆名</th>
				<th align="center" width="150">公司名称</th>
				<th align="center" width="120">客户编号</th>
				<th align="center" width="120">联系人</th>
				<th align="center" width="120">联系人电话</th>
				<th align="center" width="140">登记日期</th>
				<th align="center" width="80">状态</th>
				<th align="center" width="60">信用等级</th>
				<th align="center" width="250">操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		    <c:forEach var="list" items="${custList}" >
		      <tr>
		        <td>&nbsp;${list[8]}</td>
		        <td>&nbsp;${list[5]}</td>
		        <td>&nbsp;${list[4]}</td>
		        <td>&nbsp;${list[6]}</td>
		        <td>&nbsp;${list[7]}</td>
		        <td>&nbsp;<fmt:formatDate value="${list[3]}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		        <td>&nbsp;<posmall:dict dictType="cust_status" dictCode="${list[1]}" /></td>
		        <td>&nbsp;<posmall:dict dictType="credit_level" dictCode="${list[2]}" /></td>
				<td>
					<c:choose>
						<c:when test='${ list[1]=="REG"}'>
						</c:when>
				   		<c:when test='${list[4]!=null && (list[1]=="WAIT_AUDIT" || list[1]=="AUDIT_ING")}'>
				      		<div class="panelBar tablebtn">
							    <ul class="toolBar">
							    	<li> 
							    		<posmall:authA authCode="KHXXGL_SH" cssClass="icon" href="${ctx}/admin/cust/toCustAudit.do?icust=${list[0]}" rel="KHXXGL_SH" target="dialog"  height="560" width="820" mask="true" value="审核"/>
							    	</li>
								</ul>
							</div>
				   		</c:when>
				   		<c:when test='${list[4]!=null && list[1]=="AUDIT_PASS"}'>
				      		<div class="panelBar tablebtn">
								<ul class="toolBar">
									<li>
										<posmall:authA authCode="KHXXGL_XGZKL" cssClass="edit" href="${ctx}/admin/cust/toCustRateModify.do?icust=${list[0]}" rel="KHXXGL_XGZKL" target="dialog" height="450" width="520" mask="true" value="修改折扣率"/>
									</li>
								    <li>
								    	<posmall:authA authCode="KHXXGL_XG" cssClass="edit" href="${ctx}/admin/cust/toCustModify.do?icust=${list[0]}" rel="KHXXGL_XG" target="dialog" height="560" width="820" mask="true" value="修改"/>
									</li>
									<li>
										<a class="icon" href="${ctx}/admin/cust/custDetail.do?icust=${list[0]}" rel="KHXXGL_ZLMX" target="dialog" height="560" width="820" mask="true"><span>资料明细</span></a>
									</li>
								</ul>
							</div>
						</c:when>
						<c:when test='${list[4]!=null && (list[1]=="AUDIT_NOPASS" ||  list[1]=="INVALID")}'>
						<div class="panelBar tablebtn">
						    <ul class="toolBar">
								<li>
									<a class="icon" href="${ctx}/admin/cust/custDetail.do?icust=${list[0]}" rel="KHXXGL_ZLMX" target="dialog" height="560" width="820" mask="true"><span>资料明细</span></a>
								</li>
							</ul>
						</div>
				   		</c:when>
				   		<c:otherwise></c:otherwise>
					</c:choose> 
					</td>
					<td></td>
			   </tr>
		    </c:forEach>
		
		</tbody>
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
	function toSbumit(flag){
		if(flag=='query'){
			document.queryForm.action="${ctx}/admin/cust/custList.do";
			document.queryForm.onsubmit();
		}else{
			document.queryForm.action="${ctx}/admin/cust/downLoadCustInfo.do";
			$("#queryForm").attr("target","_blank");
			document.queryForm.submit();
		}
		
	}
</script>