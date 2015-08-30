/***********************t_dict*************************/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2607, 'agmt_status', 'SUBMIT_CHANGE', '变更申请审核中','t_agmt-协议状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2608, 'agmt_status', 'HAVE_CHANGED', '已变更','t_agmt-协议状态',now(),now(),1);

insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2806, 'pay_type', 'BAIL_SUPPLEMENT', '保证金补交','t_pay_notify-支付类型',now(),now(),1);

insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3015, 'notify_type', 'AGMT_CHANGE_AUDIT_NOTIFY', '协议变更审核结果通知','t_notify_cfg-通知类型',now(),now(),1);

insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3107, 'agmt_status', 'SUBMIT_CHANGE', '变更申请审核中','t_agmt-协议状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3108, 'agmt_status', 'HAVE_CHANGED', '已变更','t_agmt-协议状态',now(),now(),1);


insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(403, 'pay_method', 'REDUNDANT_DEPOSIT','协议多余保证金','通用-支付方式',now(),now(),1);



/***********************create******************************/
/*==============================================================*/
/* Table: t_agmt_detail_his                                     */
/*==============================================================*/
drop table if exists t_agmt_detail_his;


create table t_agmt_detail_his
(
   i_agmt_detail_his    bigint not null,
   i_agmt_detail        bigint not null,
   i_agmt               bigint not null,
   i_pdt                bigint not null,
   i_pdt_his            bigint not null,
   num                  int not null,
   gen_time             datetime not null,
   agmt_detail_status   varchar(50) not null,
   ver                  bigint not null,
   rate                 decimal(20,2) not null,
   del_flag             char(1) not null,
   start_time           date not null,
   end_time             date not null,
   used_quota           int not null,
   remain_quota         int not null,
   primary key (i_agmt_detail_his)
);

alter table t_agmt add redundant_deposit decimal(20,2);




/**********************t_msg_tmp****************************/
insert into t_msg_tmp values (16, 'EMAIL', 'AGMT_CHANGE_AUDIT_NOTIFY', '协议变更审核结果通知，由商务部发起到客户邮箱', '<strong>尊敬的 {0}：</strong><br /><br />&nbsp; 您的协议{1}变更申请已审核通过。<br />&nbsp; 现在您可以登陆“{2}”继续采购订单，感谢您对福建新大陆支付技术有限公司的支持。<br /><br />发送人：{3}', now(), now(), 'N', 0);