/*==============================================================*/
/* 拆单相关语句                                             */
/*==============================================================*/
alter table t_logistics_ord_addr add pre_serial int;

insert into t_user(i_user,login_name,password,gen_time,upd_time,version,del_flag) values (44,'ADMIN011','Yhb4p1/Vuz1fIrb5lYze3j/AhsI=',now(),now(),1,'N');
insert into t_user(i_user,login_name,password,gen_time,upd_time,version,del_flag) values (45,'ADMIN012','Yhb4p1/Vuz1fIrb5lYze3j/AhsI=',now(),now(),1,'N');

insert into t_user_userrole(i_user,i_userrole) values(44,6);
insert into t_user_userrole(i_user,i_userrole) values(45,7);

insert into t_user_sub(i_user,last_login,name,depart,gen_time,upd_time,version,email) values (44,now(),'客服','客服中心',now(),now(),1,'');
insert into t_user_sub(i_user,last_login,name,depart,gen_time,upd_time,version,email) values (45,now(),'应用版本管理员','应用中心',now(),now(),1,'');

