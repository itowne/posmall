insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (10900,'我要报修','CPBX','/cust/warranty/warrantyList.do',NULL,now(),now(),1,'MENU');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (11000,'我要续保','CPXB','/cust/renew/renewList.do',NULL,now(),now(),1,'MENU');

insert into t_custauth (i_custauth,name,code,url,parent_i_custauth,gen_time,upd_time,version,auth_type) values (11100,'保修查询','BXCX','/cust/warranty/maintenancemanageList.do',NULL,now(),now(),1,'MENU');



insert into t_custrole_custauth (i_custrole,i_custauth) values (2,10900);
insert into t_custrole_custauth (i_custrole,i_custauth) values (2,11000);
insert into t_custrole_custauth (i_custrole,i_custauth) values (2,11100);