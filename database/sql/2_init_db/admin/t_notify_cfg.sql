delete from t_notify_cfg;

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (1, 'REG_APPLY_NOTIFY', 11, 'newlandadmin', '注册通知,由客户发起，发送至商务管理部进行审核', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (2, 'AGMT_APPLY_NOTIFY', 11, 'newlandadmin', '协议提交通知,由客户发起到事业部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (3, 'AGMT_CONFIRM_NOTIFY', 11, 'newlandadmin', '协义确认通知,事业部确认额度后发送到商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (4, 'CUST_PAY_NOTIFY', 11, 'newlandadmin', '客户凭证上传通知,由客户发起至账务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (5, 'PAY_AUDIT_NOTIFY', 11, 'newlandadmin', '付款审核通知,由账务部发起至商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (6, 'SERIAL_CONFIRM_NOTIFY', 11, 'newlandadmin', '序号确认通知,由商户部发起至生产部门确认序列号', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (7, 'CUST_ORDER_NOTIFY', 11, 'newlandadmin', '客户点单通知，由客户发送至销管', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (8, 'DEPOSIT_PAY_NOTIFY', 11, 'newlandadmin', '保证金支付通知，由客户发送至财务部门', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (9, 'CUST_LOGISTICS_NOTIFY', 11, 'newlandadmin', '客户制定物流单通知，由客户发送至销管', now(), now(), 'N');
/*
insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (10, 'REG_APPLY_NOTIFY', 1, 'newlandadministrator', '注册通知,由客户发起，发送至商务管理部进行审核', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (11, 'AGMT_APPLY_NOTIFY', 1, 'newlandadministrator', '协议提交通知,由客户发起到事业部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (12, 'AGMT_CONFIRM_NOTIFY', 1, 'newlandadministrator', '协义确认通知,事业部确认额度后发送到商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (13, 'CUST_PAY_NOTIFY', 1, 'newlandadministrator', '客户凭证上传通知,由客户发起至账务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (14, 'PAY_AUDIT_NOTIFY', 1, 'newlandadministrator', '付款审核通知,由账务部发起至商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (15, 'CUST_ORDER_NOTIFY', 1, 'newlandadministrator', '客户点单通知，由客户发送至商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (16, 'DEPOSIT_PAY_NOTIFY', 1, 'newlandadministrator', '保证金支付通知，由客户发送至财务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (17, 'CUST_LOGISTICS_NOTIFY', 1, 'newlandadministrator', '客户制定物流单通知，由客户发送至销管', now(), now(), 'N');
*/
/*
insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (1, 'REG_APPLY_NOTIFY', 42, 'ADMIN009', '注册通知,由客户发起，发送至商务管理部进行审核', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (2, 'AGMT_APPLY_NOTIFY', 42, 'ADMIN009', '协议提交通知,由客户发起到事业部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (3, 'AGMT_CONFIRM_NOTIFY', 42, 'ADMIN009', '协义确认通知,事业部确认额度后发送到商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (4, 'CUST_PAY_NOTIFY', 42, 'ADMIN009', '客户凭证上传通知,由客户发起至账务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (5, 'PAY_AUDIT_NOTIFY', 42, 'ADMIN009', '付款审核通知,由账务部发起至商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (6, 'SERIAL_CONFIRM_NOTIFY', 35, 'ADMIN002', '序号确认通知,由商户部发起至生产部门确认序列号', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (7, 'CUST_ORDER_NOTIFY', 42, 'ADMIN009', '客户点单通知，由客户发送至商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (8, 'DEPOSIT_PAY_NOTIFY', 42, 'ADMIN009', '保证金支付通知，由客户发送至财务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (9, 'CUST_LOGISTICS_NOTIFY', 43, 'ADMIN010', '客户制定物流单通知，由客户发送至销管', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (10, 'REG_APPLY_NOTIFY', 40, 'ADMIN007', '注册通知,由客户发起，发送至商务管理部进行审核', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (11, 'AGMT_APPLY_NOTIFY', 40, 'ADMIN007', '协议提交通知,由客户发起到事业部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (12, 'AGMT_CONFIRM_NOTIFY', 40, 'ADMIN007', '协义确认通知,事业部确认额度后发送到商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (13, 'CUST_PAY_NOTIFY', 40, 'ADMIN007', '客户凭证上传通知,由客户发起至账务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (14, 'PAY_AUDIT_NOTIFY', 40, 'ADMIN007', '付款审核通知,由账务部发起至商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (15, 'CUST_ORDER_NOTIFY', 40, 'ADMIN007', '客户点单通知，由客户发送至商务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (16, 'DEPOSIT_PAY_NOTIFY', 40, 'ADMIN007', '保证金支付通知，由客户发送至财务部', now(), now(), 'N');

insert into t_notify_cfg (i_notify_cfg,notify_type,i_user,user_name,memo,gen_time,upd_time,del_flag) values (17, 'CUST_LOGISTICS_NOTIFY', 40, 'ADMIN007', '客户制定物流单通知，由客户发送至销管', now(), now(), 'N');
*/