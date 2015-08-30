﻿delete from t_msg_tmp;

insert into t_msg_tmp values (1, 'EMAIL', 'REG_APPLY_NOTIFY', '注册通知，由客户发起，发送至商务管理部进行审核', '<strong>{0}：</strong><br /><br />&nbsp;&nbsp;注册申请[客户名称：{1},  客户编号：{2},  公司名称：{3},  电话：{4},  手机号：{5}] <br /><br />发送人：{6}', now(), now(), 'N',0);

insert into t_msg_tmp values (2, 'EMAIL', 'REG_AUDIT_NOTIFY', '客户注册审核通知，由商务部发起至客户邮箱', '<strong>尊敬的 {0}：</strong><br /><br />&nbsp; 您的注册信息经福建新大陆支付技术有限公司确认，已通过。<br />&nbsp; 现在您可登录“{1}”进行相关采购操作。 <br /><br />发送人：{2}', now(), now(), 'N', 0);

insert into t_msg_tmp values (3, 'EMAIL', 'AGMT_APPLY_NOTIFY', '协议提交通知，由客户发起到事业部', '<strong>{0}：</strong><br /><br />&nbsp;&nbsp;确认额度[协议编号：{1}, 客户名称：{2} 订货数量：{3}, 协议起止日期：{4}] <br /><br />发送人：{5}', now(), now(), 'N', 0);

insert into t_msg_tmp values (4, 'EMAIL', 'AGMT_CONFIRM_NOTIFY', '协义确认通知，事业部确认额度后发送到商务部', '<strong>{0}：</strong><br /><br />&nbsp;&nbsp;确认额度[协议编号：{1}, 客户名称：{2} 订货数量：{3}, 协议起止日期：{4}] <br /><br />发送人：{5}', now(), now(), 'N', 0);

insert into t_msg_tmp values (5, 'EMAIL', 'PAY_NOTIFY', '付款通知，协议保证金付款通知，由系统发起', '<strong>{0}：</strong><br /><br />&nbsp;&nbsp;通知[通知编号：{1}, 业务类型：{2}, 付款金额：{3,number,#,##0.00 元}] <br /><br />发送人：{4}', now(), now(), 'N', 0);

insert into t_msg_tmp values (6, 'EMAIL', 'CUST_PAY_NOTIFY', '订单支付，客户凭证上传通知，由客户发起至账务部', '<strong>{0}：</strong><br /><br />&nbsp;&nbsp;订单支付通知[协议编号：{1}, 订单编号：{2}, 保证金抵扣金额：{3,number,#,##0.00  元} 支付金额：{4,number,#,##0.00  元}] <br /><br />发送人：{5}', now(), now(), 'N', 0);

insert into t_msg_tmp values (7, 'EMAIL', 'AGMT_AUDIT_NOTIFY', '协议审核结果通知，由商务部发起到客户邮箱', '<strong>尊敬的 {0}：</strong><br /><br />&nbsp; 您预约的编号{1}协议的保证金{2,number,#,##0.00元}已支付成功，协议已生效。<br />&nbsp; 现在您可以登陆“{3}”进行采购点单要货，感谢您对福建新大陆支付技术有限公司的支持。<br /><br />发送人：{4}', now(), now(), 'N', 0);

insert into t_msg_tmp values (8, 'EMAIL', 'PAY_AUDIT_NOTIFY', '付款审核通知，审核不通过发送', '<strong>尊敬的 {0}：</strong><br /><br />&nbsp; 您{1}，编号{2}账单的{3}凭证上传异常，请重新登录“{4}”重新上传凭证。<br />&nbsp; 感谢您对福建新大陆支付技术有限公司的支持。<br /><br />发送人：{5}', now(), now(), 'N', 0);

insert into t_msg_tmp values (9, 'EMAIL', 'ORD_CONFIRM_NOTIFY', '订单确认通知，由商户部发起至客户邮箱', '<strong>尊敬的 {0}：</strong><br /><br />&nbsp; 您采购的编号{1}的订单,货款{2}，采购订单已生效。<br />&nbsp; 现在您可以登陆“{3}”进行通知发货，感谢您对福建新大陆支付技术有限公司的支持。<br /><br />发送人：{4}', now(), now(), 'N', 0);

insert into t_msg_tmp values (10, 'EMAIL', 'SERIAL_CONFIRM_NOTIFY', '序号确认通知，由商户部发起至生产部门确认序列号', '<strong>{0}：</strong><br /><br />&nbsp;&nbsp;订单产品序列号回写通知[客户编号：{1},  订单编号：{2}] <br /><br />发送人：{3}', now(), now(), 'N', 0);

insert into t_msg_tmp values (11, 'EMAIL', 'LOGISTICS_PAY_NOTIFY', '物流扣费通知，由商户部发起至客户邮箱', '<strong>尊敬的{0}：</strong><br /><br />&nbsp; 您的物流单编号：{1} 物流费用通知[协议编号：{2},账单支付编号：{3},保证金支付物流费用：{4,number,#,##0.00 元},账单支付状态:{5}] <br /><br />发送人：{6}', now(), now(), 'N', 0);

insert into t_msg_tmp values (12, 'AGMT', 'PROTOCOL', '合同', '<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n<html>\r\n <head>\r\n   <meta charset=\"utf-8\"/>\r\n   <title>订货协议</title>\r\n   <style>\r\n	@page {\r\n	  size: auto;\r\n	  margin:10%;\r\n	  @top-left {\r\n	    font-family: \"SimSun\";\r\n	    margin:20px;\r\n	    content: \"新大陆支付技术公司\";\r\n	    font-style: italic;\r\n	  }\r\n	}\r\n	background:element(#title)\r\n   </style>\r\n </head>\r\n <body>\r\n	<div style=\"text-align: center;\">\r\n		<span style=\"font-size: 20pt; \"><strong><span style=\"font-family:SimSun;margin-bottom:30px;\"><div id=\"title\">新大陆易收银-YPOS 订货协议</div></span></strong></span>\r\n	</div>\r\n	<div style=\"width:100%;font-size:10 pt;\">\r\n		<p style=\"text-align: left; font-size: 10.5pt; font-weight: bold;margin-bottom:5px;\">\r\n			<span style=\"font-family:SimSun;\">电子协议编号：${agmt.agmtNo!}</span>\r\n		</p>\r\n		<p style=\"text-align: left; font-size: 10.5pt; font-weight: bold;\">\r\n		</p>\r\n		<div>\r\n			<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\"> \r\n				<tbody>\r\n					<tr style=\"text-align:center;font-family:SimSun;\">\r\n						<td >\r\n							序号\r\n						</td>\r\n						<td>\r\n							产品名称\r\n						</td>\r\n						<td >\r\n							规格/型号\r\n						</td>\r\n						<td >\r\n							采购数量\r\n						</td>\r\n						<td >\r\n							单价\r\n						</td>\r\n						<td >\r\n							金额\r\n						</td>\r\n					</tr>\r\n					<#assign idx = 1 />\r\n					<#list prodList as detl>\r\n					\r\n					<tr style=\"text-align:center;font-family:SimSun;\">\r\n						<td>\r\n							${idx}\r\n						</td>\r\n						<td >\r\n							${detl.tpdtHis.name}\r\n						</td>\r\n						<td >\r\n							${detl.tpdtHis.pdtNo}\r\n						</td>\r\n						<td>\r\n							${detl.tagmtDetail.num}台\r\n						</td>\r\n						<td>\r\n							${detl.tpdtHis.price * detl.tagmtDetail.rate}元\r\n						</td>\r\n						<td>\r\n							${detl.tpdtHis.price * detl.tagmtDetail.num * detl.tagmtDetail.rate}元\r\n						</td>\r\n					</tr>\r\n					<#assign idx = idx + 1/>\r\n					</#list>\r\n					\r\n				</tbody>\r\n			</table>\r\n			<span style=\"font-family:SimSun;\"><br />\r\n			</span>\r\n		</div>\r\n		\r\n		<div style=\"width:100%;\">\r\n		<p style=\"text-align: left;\">\r\n			<span style=\"font-family:SimSun;\">说明：</span>\r\n		</p>\r\n		<ul style=\"list-style-type:decimal;\">\r\n		<li>\r\n		    <span style=\"font-family:SimSun;\">\r\n		    <p style=\"line-height:20px;\">甲乙双方同意本电子协议在甲方收到乙方发送的电子档后生效.</p></span>\r\n		</li>\r\n		<li>\r\n		    <span style=\"font-family:SimSun;\">\r\n		    <p style=\"line-height:30px;\">产品价格、配置标准、相关服务及其它条件均按照甲乙双方于\r\n			${custReg.signatureDate?string(\'yyyy年MM月dd日\')}签署的合同编号为${custReg.contractNo!}的《<u>新大陆易收银-YPOS采购框架合同</u>》执行.\r\n		    </p></span>\r\n		</li>\r\n		<li>\r\n			<span style=\"font-family:SimSun;\"><p style=\"text-align: left;line-height:30px\">\r\n			甲方有权在${agmt.startTime?string(\'yyyy年MM月dd日\')}至${agmt.endTime?string(\'yyyy年MM月dd日\')}之间依据\r\n		《<u>新大陆易收银-YPOS 采购框架合同</u>》向乙方下达《<u>新大陆易收银-YPOS 采购订单</u>》\r\n			</p>\r\n			</span>\r\n		</li>\r\n		<li>\r\n		\r\n		<span style=\"font-family:SimSun;\">协议处理联系方式：</span>\r\n		<table border=\"0\" width=\"100%\" style=\"margin-top:40px\">\r\n		<tr>\r\n			<td width=\"40%\" align=\"left\"><span style=\"font-family:SimSun;\">联系人：谢清</span></td><td width=\"10%\"></td><td  align=\"left\" ><span style=\"font-family:SimSun;\">传真：0591-83979700</span></td>\r\n		</tr>\r\n		<tr>\r\n			<td width=\"40%\" align=\"left\">&nbsp;</td><td width=\"10%\">&nbsp;</td><td  align=\"left\" >&nbsp;</td>\r\n		</tr>\r\n		<tr>\r\n			<td align=\"left\"><span style=\"font-family:SimSun;\">联系电话：13600880856</span></td><td></td><td align=\"left\"><span style=\"font-family:SimSun;\">网址：www.yposmall.com</span></td>\r\n		</tr>\r\n		</table>\r\n		<br/>\r\n		<br/>\r\n		<br/>\r\n		<br/>\r\n		<br/>\r\n		<table border=\"0\" width=\"100%\">\r\n		<tr>\r\n			<td width=\"40%\" align=\"left\"><span style=\"font-family:SimSun;\">${custReg.longName!}</span></td><td width=\"10%\"></td><td  align=\"left\"><span style=\"font-family:SimSun;\">新大陆支付技术有限公司</span></td>\r\n		</tr>\r\n		<tr>\r\n			<td width=\"40%\" align=\"left\">&nbsp;</td><td width=\"10%\">&nbsp;</td><td  align=\"left\" >&nbsp;</td>\r\n		</tr>\r\n		<tr>\r\n			<td align=\"left\"><span style=\"font-family:SimSun;\">联系人(签字)：${custReg.contactName!}</span></td><td></td><td align=\"left\"><span style=\"font-family:SimSun;\">联系人(签字)：谢清</span></td>\r\n		</tr>\r\n		<tr>\r\n			<td width=\"40%\" align=\"left\">&nbsp;</td><td width=\"10%\">&nbsp;</td><td  align=\"left\" >&nbsp;</td>\r\n		</tr>\r\n		<tr>\r\n			<td align=\"left\"><span style=\"font-family:SimSun;\">日期：${agmt.efficientTime?string(\'yyyy年MM月dd日\')}</span></td><td></td><td align=\"left\"><span style=\"font-family:SimSun;\">日期：${agmt.efficientTime?string(\'yyyy年MM月dd日\')}</span></td>\r\n		</tr>\r\n		</table>\r\n		</li>\r\n		</ul>\r\n		</div>\r\n	</div>\r\n </body>\r\n</html>', '2014-10-8 20:28:37', '2014-10-8 20:28:37', 'N', 0);

insert into t_msg_tmp values (13, 'EMAIL', 'CUST_ORDER_NOTIFY', '客户点单通知，由客户发起到销管', '<strong>{0}：</strong><br /><br />&nbsp;&nbsp;客户点单通知[订单编号：{1}, 订货数量：{2}台] <br /><br />发送人：{3}', now(), now(), 'N', 0);

insert into t_msg_tmp values (14, 'EMAIL', 'DEPOSIT_PAY_NOTIFY', '保证金支付，客户凭证上传通知，由客户发起至账务部', '<strong>{0}：</strong><br /><br />&nbsp;&nbsp;保证金支付通知[协议编号：{1},  汇款单编号：{2},  保证金支付金额：{3,number,#,##0.00  元}] <br /><br />发送人：{4}', now(), now(), 'N', 0);

insert into t_msg_tmp values (15, 'EMAIL', 'CUST_LOGISTICS_NOTIFY', '客户制定物流单通知，由客户发起到销管', '<strong>{0}：</strong><br /><br />&nbsp;&nbsp;客户物流单通知[物流单编号：{1}, 发货数量：{2}台] <br /><br />发送人：{3}', now(), now(), 'N', 0);

insert into t_msg_tmp values (16, 'EMAIL', 'AGMT_CHANGE_AUDIT_NOTIFY', '协议变更审核结果通知，由商务部发起到客户邮箱', '<strong>尊敬的 {0}：</strong><br /><br />&nbsp; 您的协议{1}变更申请已审核通过。<br />&nbsp; 现在您可以登陆“{2}”继续采购订单，感谢您对福建新大陆支付技术有限公司的支持。<br /><br />发送人：{3}', now(), now(), 'N', 0);