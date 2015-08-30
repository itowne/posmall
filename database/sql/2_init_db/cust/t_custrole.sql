delete from t_custrole;

insert into t_custrole(i_custrole,name,memo,gen_time,upd_time,version,del_flag) 
values(1,'已注册的客户角色','已注册的客户角色',now(),now(),1,'N'); 

insert into t_custrole(i_custrole,name,memo,gen_time,upd_time,version,del_flag) 
values(2,'已审核的客户角色','已审核的客户角色',now(),now(),1,'N'); 