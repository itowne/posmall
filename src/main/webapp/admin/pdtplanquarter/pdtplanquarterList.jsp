<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">
<form id="pagerForm" method="post" action="pdtplanquarter/pdtplanquarterList.html">
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="${model.pageNum}" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="pdtplanquarter/pdtplanquarterList.html" method="post">
	<div class="searchBar">
<!-- 		<table class="searchContent"> -->
<!-- 			<tr> -->
<!-- 				<td> -->
<!-- 					状态：<select> -->
<!-- 					      <option>有效</option> -->
<!-- 					      <option>无效</option> -->
<!-- 					    </select> -->
<!-- 				</td> -->
<!-- 			</tr> -->
<!-- 		</table> -->
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
    <div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="pdtplanquarter/pdtplanquarterAdd.html" target="dialog" mask="true" warn="新增供应商" width="850" height="380"><span>新增计划</span></a></li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80">计划编号</th>
				<th width="120">年度</th>
				<th width="120">季度</th>
				<th width="100">登记日期</th>
				<th width="150">状态</th>
				<th>操作</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr target="sid_user" rel="1">
				<td><a href="pdtplanquarter/pdtplanquarterDetail.html"
						target="dialog" mask="true" title="详细信息" width="850" height="380"
						>XXX123123</a></td>
				<td>2014</td>
				<td>一季度</td>
				<td>2014-01-01</td>
				<td>有效</td>
				<td>
					<div class="panelBar">
						<ul class="toolBar">
							<li><a class="edit" href="pdtplanquarter/pdtplanquarterModify.html" target="dialog" mask="true"  width="850" height="380"><span>修改</span></a></li>
						</ul>
					</div>
				</td>
				<td></td>
			</tr>
			<tr target="sid_user" rel="2">
				<td><a href="pdtplanquarter/pdtplanquarterDetail.html"
						target="dialog" mask="true" title="详细信息" width="850" height="380"
						>XXX32323</a></td>
				<td>2014</td>
				<td>二季度</td>
				<td>2014-01-01</td>
				<td>有效</td>
				<td>
					<div class="panelBar">
						<ul class="toolBar">
							<li><a class="edit" href="pdtplanquarter/pdtplanquarterModify.html" target="dialog" mask="true"  width="850" height="380"><span>修改</span></a></li>
						</ul>
					</div>
				</td>
				<td></td>
			</tr>
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