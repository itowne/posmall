/*==============================================================*/
/* 保修管理相关语句                                             */
/*==============================================================*/
insert into t_userauth values (11600, '保修查询', 'BXCH', '/admin/maintenancemanage/maintenancemanageList.do', null,now(),now(), '1', 'MENU');
insert into t_userauth values (11700, '续保管理', 'XBGL', '/admin/renew/renewList.do', null,now(),now(), '1', 'MENU');
insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11701,'续保管理-审核','XBGL_SH','/admin/renew/toRenewAudit.do',11700,now(),now(),1,'URL');
insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11702,'续保管理-撤销','XBGL_CX','/admin/renew/renewCancel.do',11700,now(),now(),1,'URL');
insert into t_userauth values (11800, '报修受理', 'BXSL', '/admin/warranty/warrantyList.do', null,now(),now(), '1', 'MENU');
insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11801,'报修受理-下载','BXSL_XZ','/admin/warranty/checkDownLoad.do',11800,now(),now(),1,'URL');
insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11802,'报修受理-历史下载','BXSL_LSXZ','/admin/warranty/warrantyDownLoadHis.do',11800,now(),now(),1,'URL');
insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11803,'报修受理-修复','BXSL_XF','/admin/warranty/toWarrantyRepaired.do',11800,now(),now(),1,'URL');
insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11804,'报修受理-撤销','BXSL_CX','/admin/warranty/warrantyCancel',11800,now(),now(),1,'URL');
insert into t_userrole(i_userrole,name,memo,gen_time,upd_time,version,del_flag)values(6,'客服人员角色','客户人员角色',now(),now(),1,'N'); 
insert into t_userrole_userauth values (6,11600);
insert into t_userrole_userauth values (6,11700);
insert into t_userrole_userauth values (6,11701);
insert into t_userrole_userauth values (6,11702);
insert into t_userrole_userauth values (6,11800);
insert into t_userrole_userauth values (6,11801);
insert into t_userrole_userauth values (6,11802);
insert into t_userrole_userauth values (6,11803);
insert into t_userrole_userauth values (6,11804);

/*==============================================================*/
/* 业务员邮箱相关sql                                            */
/*==============================================================*/
alter table t_cust_reg add salesman_name varchar(50);
alter table t_cust_reg add salesman_email varchar(100);
update t_cust_reg set salesman_name='朱德景',salesman_email='zhudj@newlandpayment.com' where i_cust='10001';
update t_cust_reg set salesman_name='董吉庆',salesman_email='dongjq@newlandpayment.com' where i_cust='10101';
update t_cust_reg set salesman_name='李大力',salesman_email='lidl@newlandpayment.com' where i_cust='10102';
update t_cust_reg set salesman_name='吴奕隆',salesman_email='wuyl@newlandpayment.com' where i_cust='10201';
update t_cust_reg set salesman_name='杨晚成',salesman_email='yangwc@newlandpayment.com' where i_cust='10301';
update t_cust_reg set salesman_name='朱德景',salesman_email='zhudj@newlandpayment.com' where i_cust='10403';
update t_cust_reg set salesman_name='吴奕隆',salesman_email='wuyl@newlandpayment.com' where i_cust='10404';
update t_cust_reg set salesman_name='管耀刚',salesman_email='guanyaogang@newlandpayment.com' where i_cust='10401';
update t_cust_reg set salesman_name='高燃',salesman_email='gaor@newlandpayment.com' where i_cust='10402';

/*==============================================================*/
/* 应用版本管理相关sql                                            */
/*==============================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) values (1216, 'oper_type', 'CUSTAPPVER_MGR', '应用版本管理', 't_log-操作类型',now(),now(),1);
insert into t_userauth values ('11900', '应用版本管理', 'YYBBGL', '/admin/custappver/custAppverList.do', null,now(),now(), '1', 'MENU');
insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11901,'应用版本管理-添加','YYBBGL_TJ','/admin/custappver/toCustAppverAdd.do',11900,now(),now(),1,'URL');
insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11902,'应用版本管理-修改','YYBBGL_XG','/admin/custappver/toCustAppverMod.do',11900,now(),now(),1,'URL');
insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (11903,'应用版本管理-删除','YYBBGL_SC','/admin/custappver/custAppverDel.do',11900,now(),now(),1,'URL');
insert into t_userrole(i_userrole,name,memo,gen_time,upd_time,version,del_flag)values(7,'版本管理员角色','版本管理员角色',now(),now(),1,'N');
insert into t_userrole_userauth values (7,11900);
insert into t_userrole_userauth values (7,11901);
insert into t_userrole_userauth values (7,11902);
insert into t_userrole_userauth values (7,11903);


/*==============================================================*/
/* erp同步相关sql                                            */
/*==============================================================*/
alter table t_erp_sync add gen_time datetime;
alter table t_erp_sync add upd_time datetime;
alter table t_erp_sync add version bigint;
update t_erp_sync set version = 0 where version is NULL;
update t_erp_sync set gen_time = sjtq_date where gen_time is NULL;
update t_erp_sync set upd_time = sjtq_date where upd_time is NULL;

/*==============================================================*/
/* 物流地址新增删除标识相关sql                                  */
/*==============================================================*/
alter table t_logistics_ord_addr add del_flag char(1);
update t_logistics_ord_addr set del_flag='N';

