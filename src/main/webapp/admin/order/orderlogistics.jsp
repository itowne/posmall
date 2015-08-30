<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">
<style>
  .textSize {size:8}
</style>
<div class="pageContent">
	<form method="post" action="pdtplanquarter/pdtplanquarterList.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<table >
			   <tr>
			      <td colspan="2">
			      <p id="teamAct">
				<label>公司名称：</label>
				<input type="text" name="teamAct.actName" class="required" />
				</p>
				</td></tr><tr>
			    <td colspan="2"><p id="teamAct">
				<label>公司编号：</label>
				   <input type="text" name="teamAct.actName" class="required" />
				
				</p></td>
				
			   </tr>
			   <tr>
			      <td colspan="2">
			                 <label>点单日期：</label>
			                 8月25日
				</td></tr>
			   <tr>
			      <td > <fieldset >
						<legend >产品A<input type="checkbox" group="ids" class="checkboxCtrl"></legend>
						<table >
						   <tr>
						      <td><input name="ids" value="xxx" type="checkbox"></td>
						      <td>9月1日 &nbsp;</td>
						      <td>存量：2000台 &nbsp;</td>
						      <td><input type="text" size="8"/>台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						   <tr>
						      <td><input name="ids" value="xxx" type="checkbox"></td>
						      <td>9月2日&nbsp;</td>
						      <td>存量：2000台&nbsp;</td>
						      <td><input type="text"  size="8"/>台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						   <tr>
						      <td><input name="ids" value="xxx" type="checkbox"></td>
						      <td>9月3日&nbsp;</td>
						      <td>存量：2000台&nbsp;</td>
						      <td><input type="text"  size="8" />台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						   <tr>
						      <td><input name="ids" value="xxx" type="checkbox"></td>
						      <td>9月4日&nbsp;</td>
						      <td>存量：2000台&nbsp;</td>
						      <td><input type="text"  size="8" />台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						   <tr>
						      <td><input name="ids" value="xxx" type="checkbox"></td>
						      <td>9月5日&nbsp;</td>
						      <td>存量：2000台&nbsp;</td>
						      <td><input type="text"  size="8" />台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						   <tr>
						      <td><input name="ids" value="xxx" type="checkbox"></td>
						      <td>9月6日&nbsp;</td>
						      <td>存量：2000台&nbsp;</td>
						      <td><input type="text"   size="8"/>台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						</table>
					</fieldset>
					</td><td>
<!-- 			  </tr> -->
<!-- 			  <tr> -->
					  <fieldset>
						<legend>产品B<input type="checkbox" group="idsB" class="checkboxCtrl"></legend>
							<table >
						   <tr>
						      <td><input name="idsB" value="xxx" type="checkbox"></td>
						      <td>9月1日 &nbsp;</td>
						      <td>存量：2000台 &nbsp;</td>
						      <td><input type="text" size="8"/>台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						   <tr>
						      <td><input name="idsB" value="xxx" type="checkbox"></td>
						      <td>9月2日&nbsp;</td>
						      <td>存量：2000台&nbsp;</td>
						      <td><input type="text"  size="8"/>台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						   <tr>
						      <td><input name="idsB" value="xxx" type="checkbox"></td>
						      <td>9月3日&nbsp;</td>
						      <td>存量：2000台&nbsp;</td>
						      <td><input type="text"  size="8" />台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						   <tr>
						      <td><input name="idsB" value="xxx" type="checkbox"></td>
						      <td>9月4日&nbsp;</td>
						      <td>存量：2000台&nbsp;</td>
						      <td><input type="text"  size="8" />台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						   <tr>
						      <td><input name="idsB" value="xxx" type="checkbox"></td>
						      <td>9月5日&nbsp;</td>
						      <td>存量：2000台&nbsp;</td>
						      <td><input type="text"  size="8" />台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						   <tr>
						      <td><input name="idsB" value="xxx" type="checkbox"></td>
						      <td>9月6日&nbsp;</td>
						      <td>存量：2000台&nbsp;</td>
						      <td><input type="text"   size="8"/>台&nbsp;</td>
						      <td><input type="text" /></td>
						   </tr>
						</table>
					</fieldset></td>
			   
			   </tr>
			</table>
	
			<div class="panelBar">
				<ul class="toolBar">
					<li><a class="add" href="order/orderlogisticsAdd.html" target="dialog" mask="true"  width="850" height="480"><span>新增物流单</span></a></li>
<!-- 					<li><a class="icon" href="order/orderDetail.html?uid={sid_user}" target="dialog" mask="true" warn="" width="850" height="480"><span>审核</span></a></li> -->
<!-- 					<li><a class="delete"  href="ajaxDone.html?uid={sid_user}" target="ajaxTodo" mask="true"  ><span>撤销</span></a></li> -->
				</ul>
			</div>
			<table class="list">
			   <thead>
					<tr>
						<th width="200">物流编号</th>
						<th width="80">发货数量</th>
						<th width="120">产品名称</th>
						<th width="120">发货地址</th>
						<th width="120">预计发货日期</th>
						<th width="120">状态</th>
						<th>操作</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
				  <tr rel="1">
				  <td>SX1212312323</td>
				  <td>300</td>
				  <td>产品A，产品B</td>
				  <td>地址XXXX</td>
				  <td>2014-01-01</td>
				  <td>待发货</td>
				  <td>
				  	<div class="panelBar">
						<ul class="toolBar">
<!-- 							<li><a class="add" href="order/orderlogisticsAdd.html" target="dialog" mask="true"  width="850" height="480"><span>新增物流单</span></a></li> -->
							<li><a class="icon" href="order/orderDetail.html" target="dialog" mask="true" warn="" width="850" height="480"><span>审核</span></a></li>
							<li><a class="delete"  href="ajaxDone.html" target="ajaxTodo" mask="true"  ><span>撤销</span></a></li>
						</ul>
					</div>
				  </td>
				  <td></td>
				  </tr>
				   <tr rel="2">
				  <td>SX1212312323</td>
				  <td>300</td>
				  <td>产品A，产品B</td>
				  <td>地址XXXX</td>
				  <td>2014-01-01</td>
				  <td>付款审核</td>
				   <td>
				  	<div class="panelBar">
						<ul class="toolBar">
<!-- 							<li><a class="add" href="order/orderlogisticsAdd.html" target="dialog" mask="true"  width="850" height="480"><span>新增物流单</span></a></li> -->
							<li><a class="icon" href="order/orderDetail.html" target="dialog" mask="true" warn="" width="850" height="480"><span>审核</span></a></li>
							<li><a class="delete"  href="ajaxDone.html" target="ajaxTodo" mask="true"  ><span>撤销</span></a></li>
						</ul>
					</div>
				  </td>
				  <td></td>
				  </tr>
				   <tr rel="3">
				  <td>SX1212312323</td>
				  <td>300</td>
				  <td>产品A，产品B</td>
				  <td>地址XXXX</td>
				  <td>2014-01-01</td>
				  <td>末付款</td>
				   <td>
				  	<div class="panelBar">
						<ul class="toolBar">
<!-- 							<li><a class="add" href="order/orderlogisticsAdd.html" target="dialog" mask="true"  width="850" height="480"><span>新增物流单</span></a></li> -->
							<li><a class="icon" href="order/orderDetail.html" target="dialog" mask="true" warn="" width="850" height="480"><span>审核</span></a></li>
							<li><a class="delete"  href="ajaxDone.html" target="ajaxTodo" mask="true"  ><span>撤销</span></a></li>
						</ul>
					</div>
				  </td>
				  <td></td>
				  </tr>
				
				</tbody>
				
			</table>
			
			
	     </div>
	     <div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;回&nbsp;&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
		
	</form>
</div>