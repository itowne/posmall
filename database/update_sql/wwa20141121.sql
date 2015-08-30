insert into t_userauth(i_userauth,name,code,url,parent_i_userauth,gen_time,upd_time,version,auth_type)
values(12200,'详情追踪管理','XQZZGL','/admin/trace/detailTraceList.do',null,now(),now(),1,'MENU');

insert into t_userrole_userauth(i_userrole,i_userauth)
values(1,12200);

alter table t_detail_trace add cust_name varchar(50);

UPDATE t_detail_trace t1 set cust_name=(SELECT name from t_cust_reg t2 where t2.i_cust=t1.i_cust) ;