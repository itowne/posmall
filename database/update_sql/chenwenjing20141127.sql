/*==============================================================*/
/* erp订单编号相关语句                                             */
/*==============================================================*/
alter table t_agmt add erp_ord_id varchar(100);

insert into t_userauth (i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type) VALUES (10207,'客户订货协议管理-设置erp订单号','KHDHXYGL_SZDDH','/admin/agmt/setErpOrdId.do',10200,now(),now(),1,'URL');
insert into t_userrole_userauth values (1, 10207);


