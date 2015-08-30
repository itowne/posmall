delete from t_sysrole;

insert into t_sysrole(i_sysrole,name,memo,gen_time,upd_time,version,del_flag) 
values(1,'系统后台管理员','系统后台管理员',now(),now(),1,'N'); 
