<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">
<form id="pagerForm" method="post" action="report/custlogistics/custlogisticsList.html">
	<input type="hidden" name="status" value="${param.status}">
	<input type="hidden" name="keywords" value="${param.keywords}" />
	<input type="hidden" name="pageNum" value="${model.pageNum}" />
	<input type="hidden" name="numPerPage" value="${model.numPerPage}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
</form>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="report/custlogistics/custlogisticsList.html" method="post">
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
			<li>
				<a class="icon" href="report/custlogistics/custlogisticsModify.html?uid={sid_user}" target="dialog" mask="true"  width="850" height="380">
					<span>下载</span>
				</a>
			</li>
		</ul>
	</div>
	<table class="list" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="120">计划编号</th>
				<th width="80">客户名称</th>
				<th width="80">季度</th>
				<th width="80">产品名称</th>
				<th width="80">订单量</th>
				<th width="150">登记日期</th>
				<th width="120">状态</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr target="sid_user" rel="1">
				<td>
					<a href="report/custlogistics/custlogisticsDetail.html" target="dialog" mask="true" itle="详细信息" width="550" height="480">XXX123123</a>
				</td>
				<td>传世企业</td>
				<td>第一季度</td>
				<td>新大陆pos机</td>
				<td>20000</td>
				<td>2014-07-20</td>
				<td>已付款</td>
				<td></td>
			</tr>
			<tr target="sid_user" rel="2">
				<td>
					<a href="report/custlogistics/custlogisticsDetail.html" target="dialog" mask="true" itle="详细信息" width="550" height="480">XXX123123</a>
				</td>
				<td>传世企业</td>
				<td>第一季度</td>
				<td>新大陆pos机</td>
				<td>20000</td>
				<td>2014-07-20</td>
				<td>已付款</td>
				<td></td>
			</tr>
			<tr target="sid_user" rel="3">
				<td>
					<a href="report/custlogistics/custlogisticsDetail.html" target="dialog" mask="true" itle="详细信息" width="550" height="480">XXX123123</a>
				</td>
				<td>传世企业</td>
				<td>第一季度</td>
				<td>新大陆pos机</td>
				<td>20000</td>
				<td>2014-07-20</td>
				<td>已付款</td>
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