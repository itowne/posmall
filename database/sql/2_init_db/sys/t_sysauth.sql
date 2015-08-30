delete from t_sysauth;

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10001,'管理员维护','GLYWH','#',NULL,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10002,'后台人员维护','HTRYWH','#',NULL,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10003,'客户维护','KHWH','#',NULL,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10004,'参数维护','CSWH','#',NULL,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10100,'管理员角色管理','GLYJSGL','/sys/sysrole/sysroleList.do',10001,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10101,'管理员角色管理-新增','GLYJSGL_XZ','/sys/sysrole/sysroleAdd.do',10100,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10102,'管理员角色管理-修改','GLYJSGL_XG','/sys/sysrole/sysroleMod.do',10100,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10104,'管理员角色管理-删除','GLYJSGL_SC','/sys/sysrole/sysroleDel.do',10100,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10200,'管理员信息管理','GLYXXGL','/sys/sys/sysList.do',10001,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10201,'管理员信息管理-新增','GLYXXGL_XZ','/sys/sys/sysAdd.do',10200,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10202,'管理员信息管理-修改','GLYXXGL_XG','/sys/sys/sysModify.do',10200,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10203,'管理员信息管理-重置密码','GLYXXGL_CZMM','/sys/sys/resetPass.do',10200,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10204,'管理员信息管理-删除','GLYXXGL_SC','/sys/sys/sysRemove.do',10200,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10300,'管理员角色关联管理','GLYJSGLGL','/sys/syssysrole/sysSysroleList.do',10001,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10301,'管理员角色关联管理-新增','GLYJSGLGL_XZ','/sys/sysrole/sysroleAdd.do',10300,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10303,'管理员角色关联管理-修改','GLYJSGLGL_XG','/sys/sysrole/sysroleMod.do',10300,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10304,'管理员角色关联管理-删除','GLYJSGLGL_SC','/sys/sysrole/sysroleDel.do',10300,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10400,'后台人员角色管理','HTRYJSGL','/sys/userrole/userroleList.do',10002,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10401,'后台人员角色管理-新增','HTRYJSGL_XZ','/sys/userrole/userroleAdd.do',10400,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10403,'后台人员角色管理-修改','HTRYJSGL_XG','/sys/userrole/userroleMod.do',10400,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10404,'后台人员角色管理-删除','HTRYJSGL_SC','/sys/userrole/userroleDel.do',10400,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10500,'后台人员信息管理','HTRYXXGL','/sys/user/userList.do',10002,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10501,'后台人员角色管理-新增','HTRYXXGL_XZ','/sys/user/userAdd.do',10500,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10503,'后台人员角色管理-修改','HTRYXXGL_XG','/sys/user/userModify.do',10500,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10504,'后台人员角色管理-删除','HTRYXXGL_SC','/sys/user/userRemove.do',10500,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10505,'后台人员角色管理-重置密码','HTRYXXGL_CZMM','/sys/user/resetPass.do',10500,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10600,'后台人员角色关联管理','HTRYJSGLGL','/sys/useruserrole/userUserroleList.do',10002,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10601,'后台人员角色管理-新增','HTRYJSGLGL_XZ','/sys/useruserrole/userUserroleAdd.do',10600,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10602,'后台人员角色管理-删除','HTRYJSGLGL_SC','/sys/useruserrole/userUserroleDel.do',10600,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10603,'后台人员角色管理-修改','HTRYJSGLGL_XG','/sys/useruserrole/userUserroleMod.do',10600,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10700,'客户角色管理','KHJSGL','/sys/custrole/custroleList.do',10003,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10701,'客户角色管理-新增','KHJSGL_XZ','/sys/custrole/custroleAdd.do',10700,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10703,'客户角色管理-删除','KHJSGL_SC','/sys/custrole/custroleDel.do',10700,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10704,'客户角色管理-修改','KHJSGL_XG','/sys/custrole/custroleMod.do',10700,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10800,'客户信息管理','KHXXGL','/sys/cust/custList.do',10003,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10802,'客户信息管理-重置','KHXXGL_CZ','/sys/cust/resetPass.do',10800,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10900,'客户角色关联管理','KHJSGLGL','/sys/custcustrole/custCustroleList.do',10003,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10901,'客户角色关联管理-新增','KHJSGLGL_XZ','/sys/custcustrole/custCustroleAdd.do',10900,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10902,'客户角色关联管理-删除','KHJSGLGL_SC','/sys/custcustrole/custCustroleDel.do',10900,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (10903,'客户角色关联管理-修改','KHJSGLGL_XG','/sys/custcustrole/custCustroleMod.do',10900,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11000,'参数管理','CSGL','/sys/sysparam/sysparamList.do',10004,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11001,'参数管理-修改','CSGL_XG','/sys/sysparam/sysparamModify.do',11000,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11100,'定时任务管理','DSQGL','/sys/timer/timerList.do',10004,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11101,'定时任务管理-新增','DSQGL_XZ','demo_page4.html',11100,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11200,'节假日管理','JJRGL','/sys/holiday/holidayList.do',10004,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11201,'节假日管理-新增','JJRGL_XZ','/sys/holiday/holidayAdd.do',11200,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11203,'节假日管理-删除','JJRGL_SC','/sys/holiday/holidayDel.do',11200,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11204,'节假日管理-修改','JJRGL_XG','/sys/holiday/holidayMod.do',11200,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11300,'消息模板管理','XXMBGL','/sys/msgtmp/msgtmpList.do',10004,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11301,'消息模板管理-新增','XXMBGL_XZ','/sys/msgtmp/toMsgTmpAdd.do',11300,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11303,'消息模板管理-删除','XXMBGL_SC','msgtmpRemove.do',11300,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11304,'消息模板管理-修改','XXMBGL_XG','/sys/msgtmp/toMsgTmpModify.do',11300,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11400,'邮件通知人管理','YJTZRGL','/sys/notifycfg/notifycfgList.do',10004,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11401,'邮件通知人管理-新增','YJTZRGL_XZ','/sys/notifycfg/toNotifycfgAdd.do',11400,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11402,'邮件通知人管理-修改','YJTZRGL_XG','/sys/notifycfg/toNotifycfgModify.do',11400,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11404,'邮件通知人管理-删除','YJTZRGL_SC','/sys/notifycfg/notifycfgRemove.do',11400,now(),now(),1,'URL');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11500,'日志查询','RZCX','/sys/log/logList.do',NULL,now(),now(),1,'MENU');

insert into t_sysauth (i_sysauth,name,code,url,parent_i_sysauth,gen_time,upd_time,version,auth_type) values (11600,'密码修改','SYSMMXG','/sys/sysPassword/passwordMod.do',NULL,now(),now(),1,'MENU');