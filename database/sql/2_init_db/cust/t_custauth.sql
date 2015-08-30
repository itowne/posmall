delete from t_custauth ;

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10100,'我要预订','DHXYGL','/cust/agmt/agmtList.do',NULL,now(),now(),1,'MENU');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10101,'我要预订-预约订单','DHXYGL_QSXY','/cust/agmt/agmtAdd.do',10100,now(),now(),1,'URL');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10200,'我要点单','POSDDGL','/cust/ord/ordList.do',NULL,now(),now(),1,'MENU');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10201,'我要点单-点单','POSDDGL_DD','/cust/ord/ordList.do',10200,now(),now(),1,'URL');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10400,'我要付款','FKTZSGL','/cust/paynotice/paynoticeList.do',NULL,now(),now(),1,'MENU');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10402,'我要付款-上传付款凭证','FKTZSGL_SCFKPZ','/cust/file/toUploadVoucher.do',10400,now(),now(),1,'URL');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10600,'我要发货','WLJHGL','/cust/logistics/logisticsList.do',NULL,now(),now(),1,'MENU');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10700,'公司注册信息管理','GSXXGL','/cust/info/infoDetail.do',NULL,now(),now(),1,'MENU');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10800,'密码修改','CUSTMMXG','/cust/custPassword/passwordMod.do',NULL,now(),now(),1,'MENU');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10900,'我要报修','CPBX','/cust/warranty/warrantyList.do',NULL,now(),now(),1,'MENU');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (11000,'我要续保','CPXB','/cust/renew/renewList.do',NULL,now(),now(),1,'MENU');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (11100,'保修查询','BXCX','/cust/warranty/maintenancemanageList.do',NULL,now(),now(),1,'MENU');