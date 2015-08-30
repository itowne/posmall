delete from t_userauth ;

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10100,'客户信息管理','KHXXGL','/admin/cust/custList.do',NULL,now(),now(),1,'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10101,'客户信息管理-审核','KHXXGL_SH','/admin/cust/toCustAudit.do',10100,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10102,'客户信息管理-修改','KHXXGL_XG','/admin/cust/toCustModify.do',10100,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10103,'客户信息管理-修改折扣率','KHXXGL_XGZKL','/admin/cust/toCustRateModify.do',10100,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10104,'客户信息管理-下载','KHXXGL_XZ','/admin/cust/downLoadCustInfo.do',10100,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10200,'客户订货协议管理','KHDHXYGL','/admin/agmt/custagmtList.do',NULL,now(),now(),1,'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10202,'客户订货协议管理-协议额度确认','KHDHXYGL_XYEDQR','/admin/agmt/custagmtLimitCheckPage.do',10200,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10204,'客户订货协议管理-审核','KHDHXYGL_SH','/admin/agmt/custagmtPayPassCheckPage.do',10200,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10205,'客户订货协议管理-撤销','KHDHXYGL_CX','admin/agmt/removeAgmt.do',10200,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10206,'客户订货协议管理-下载','KHDHXYGL_XZ','/admin/agmt/downLoadCustAgmt.do',10200,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10300,'客户订单管理','KHDDGL','/admin/order/orderList.do',NULL,now(),now(),1,'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10303,'客户订单管理-审核','KHDDGL_SH','/admin/order/toOrderAudit.do',10300,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10304,'客户订单管理-撤销','KHDDGL_CX','/admin/order/removeOrder.do',10300,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10305,'客户订单管理-终止订单','KHDDGL_ZZDD','/admin/order/toOrderStop.do',10300,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10306,'客户订单管理-下载','KHDDGL_XZ','/admin/order/checkDownLoad.do',10300,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10307,'客户订单管理-历史下载','KHDDGL_LSXZ','/admin/order/ordDownLoadHis.do',10300,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10400,'账单支付管理','FKTZSGL','/admin/paynotice/paynoticeList.do',NULL,now(),now(),1,'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10402,'账单支付管理-审核','FKTZSGL_SH','/admin/paynotice/toPaynoticeAudit.do',10400,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10403,'账单支付管理-下载','FKTZSGL_XZ','/admin/paynotice/downLoadPayNotify.do',10400,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10600,'物流单管理','WLDGL','/admin/logistics/logisticsorder/logisticsorderList.do',NULL,now(),now(),1,'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10601,'物流单管理-审核','WLDGL_SH','/admin/logistics/logisticsorder/toAuditMoreLogisticsOrd.do',10600,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10602,'物流单管理-撤销','WLDGL_CX','/admin/logistics/logisticsOrdCancel.do',10600,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10603,'物流单管理-下载','WLDGL_XZ','/admin/logistics/logisticsorder/checkDownLoad.do',10600,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10604,'物流单管理-历史下载','WLDGL_LSXZ','/admin/logistics/logisticsorder/logisticsordDownLoadHis.do',10600,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10605,'物流单管理-填写物流信息','WLDGL_TXWLXX','/admin/logistics/logisticsorder/toFillActualMore.do',10600,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10700,'产品信息维护','CPXXWH','/admin/pdt/pdtList.do',NULL,now(),now(),1,'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10701,'产品信息维护-新增','CPXXWH_XZ','/admin/pdt/toPdtAdd.do',10700,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10702,'产品信息维护-修改','CPXXWH_XG','/admin/pdt/toPdtModify.do',10700,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10703,'产品信息维护-删除','CPXXWH_SC','/admin/pdt/toPdtRemove.do',10700,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10704,'产品信息维护-历史单价','CPXXWH_LSDJ','/admin/pdt/pdtHisList.do',10700,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10705,'产品信息维护-修改库存','CPXXWH_XGKC','/admin/pdt/toModifyStock.do',10700,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10800,'月排产管理','YPCGL','/admin/pdtplanmonth/pdtPlanMonthList.do',NULL,now(),now(),1,'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10801,'月排产管理-新增','YPCGL_XZ','/admin/pdtplanmonth/toPdtPlanMonthAdd.do',10800,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10803,'月排产管理-修改','YPCGL_XG','/admin/pdtplanmonth/toPdtPlanMonthModify.do',10800,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10804,'月排产管理-删除','YPCGL_SC','/admin/pdtplanmonth/pdtPlanMonthRemove.do',10800,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11300,'注册码管理','KHMGL','/admin/cust/custCodeList.do',NULL,now(),now(),1,'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11301,'注册码管理-生成注册码','KHMGL_SCZCM','/admin/cust/generateCustCode.do',11300,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11500,'密码修改','ADMINMMXG','/admin/password/passwordMod.do',NULL,now(),now(),1,'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) values (11600, '保修查询', 'BXCH', '/admin/maintenancemanage/maintenancemanageList.do', null,now(),now(), '1', 'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) values (11700, '续保管理', 'XBGL', '/admin/renew/renewList.do', null,now(),now(), '1', 'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11701,'续保管理-审核','XBGL_SH','/admin/renew/toRenewAudit.do',11700,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11702,'续保管理-撤销','XBGL_CX','/admin/renew/renewCancel.do',11700,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) values (11800, '报修受理', 'BXSL', '/admin/warranty/warrantyList.do', null,now(),now(), '1', 'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11801,'报修受理-下载','BXSL_XZ','/admin/warranty/checkDownLoad.do',11800,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11802,'报修受理-历史下载','BXSL_LSXZ','/admin/warranty/warrantyDownLoadHis.do',11800,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11803,'报修受理-修复','BXSL_XF','/admin/warranty/toWarrantyRepaired.do',11800,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11804,'报修受理-撤销','BXSL_CX','/admin/warranty/warrantyCancel',11800,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) values (11900, '应用版本管理', 'YYBBGL', '/admin/custappver/custAppverList.do', null,now(),now(), '1', 'MENU');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11901,'应用版本管理-添加','YYBBGL_TJ','/admin/custappver/toCustAppverAdd.do',11900,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11902,'应用版本管理-修改','YYBBGL_XG','/admin/custappver/toCustAppverMod.do',11900,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11903,'应用版本管理-删除','YYBBGL_SC','/admin/custappver/custAppverDel.do',11900,now(),now(),1,'URL');

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) values (12100, '汇总报表', 'REPORT', '/admin/reportForm/reportFormList.do', null,now(),now(), '1', 'MENU');