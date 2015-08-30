<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><div class="pageContent">
<div class="pageContent">
	<form method="post" action="pdtplanquarter/pdtplanquarterList.html"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="68">
			<table >
			   <tr>
			      <td c>
			      <p id="teamAct">
				<label>年度：</label>
				<input type="text" name="teamAct.actName" cssClass="required" />
				</p>
				</td>
			    <td><p id="teamAct">
				<label>季度：</label>
				<select>
				   <option>一季度</option><option>二季度</option><option>三季度</option><option>四季度</option>
				</select>
				</p></td>
			   </tr>
			   <tr>
			      <td colspan="2"> <fieldset >
						<legend>产品A</legend>
						<dl>
							<dt>1月产量：</dt>
							<dd><input name="field1" type="text" /></dd>
						</dl>
						<dl>
							<dt>2月产量：</dt>
							<dd><input name="field2" type="text" alt=""/></dd>
						</dl>
						<dl>
							<dt>3月产量：</dt>
							<dd><input  name="field3" type="text" /></dd>
						</dl>
					
					</fieldset></td>
			  </tr>
			  <tr>
					  <td colspan="2"> <fieldset>
						<legend>产品B</legend>
						<dl>
							<dt>1月产量：</dt>
							<dd><input name="field1" type="text" /></dd>
						</dl>
						<dl>
							<dt>2月产量：</dt>
							<dd><input name="field2" type="text" alt=""/></dd>
						</dl>
						<dl>
							<dt>3月产量：</dt>
							<dd><input  name="field3" type="text" /></dd>
						</dl>
					</fieldset></td>
			   
			   </tr>
			
			</table>
			
			
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;认&nbsp;</button>
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