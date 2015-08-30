<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">
<div class="pageContent">
	<form method="post" action="pdtplanquarter/pdtplanquarterList.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<table >
			   <tr>
			      <td colspan="3">
				<label>收货地址：</label>
				<select><option>福建省</option></select>
				<select><option>福州</option><option>厦门</option></select>
				<input type="text" name="teamAct.actName" class="required" size="30"/>
				</td></tr>
				<tr>
			    <td><p>
				 <label>联系人：</label>
				<input type="text" name="teamAct.actName" class="required" />
				</p>
			
				<p id="teamAct">
				<label>联系人电话：</label>
				   <input type="text" name="teamAct.actName" class="required" />
				
				</p></td>
				<td><p id="teamAct">
				<label>物流公司：</label>
				   <select>
				      <option>顺风物流</option>
				      <option>圆通物流</option>
				      <option>畅通物流</option>
				      <option>EMS</option>
				   </select>
				
				</p></td></tr>
			    <tr>
			        <td>
			          <p>
			             <label>产品：</label>
			             <select>
			               <option>产品A</option>
			               <option>产品B</option>
			             </select>
			          </p>
			       	   <p>
			             <label>数量：</label>
			              <input type="text" name="teamAct.actName" class="required" />
			          </p>
			        </td>
			        <td><input type="button" value="添加至发货单"/></td>
			    
			    </tr>
			    
			</table>
			<table class="list" id="asdf">
			   <thead><tr><th>产品名称</th><th>发货数量</th></tr></thead>
		   <tbody>
			     <tr><td>产品A</td><td>200</td></tr>
			   </tbody>
			</table>
			<div>
			        物流费用： 2000元，时效：3天，发货日期：
			  <input style="float: none" type="text" name="date5" class="date" dateFmt="yyyy-MM-dd" minDate="{%y}-%M-%d"/>
			      支付状态：末支付
			</div>
			
			
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;定&nbsp;&nbsp;</button>
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