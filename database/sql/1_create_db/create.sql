/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2014/11/21 10:09:17                          */
/*==============================================================*/


drop table if exists hibernate_sequences;

drop table if exists t_addr;

drop table if exists t_addr_his;

drop table if exists t_agmt;

drop table if exists t_agmt_deposit;

drop table if exists t_agmt_detail;

drop table if exists t_agmt_detail_his;

drop table if exists t_cust;

drop table if exists t_cust_appver;

drop table if exists t_cust_code;

drop table if exists t_cust_credit;

drop table if exists t_cust_custrole;

drop table if exists t_cust_rate;

drop table if exists t_cust_reg;

drop table if exists t_cust_stock;

drop table if exists t_cust_stock_detail;

drop table if exists t_custauth;

drop table if exists t_custrole;

drop table if exists t_custrole_custauth;

drop table if exists t_detail_trace;

drop table if exists t_dict;

drop table if exists t_erp_maintenance;

drop table if exists t_erp_sync;

drop table if exists t_file;

drop table if exists t_holiday;

drop table if exists t_log;

drop table if exists t_logistics_com;

drop table if exists t_logistics_detail;

drop table if exists t_logistics_download;

drop table if exists t_logistics_ord;

drop table if exists t_logistics_ord_addr;

drop table if exists t_logistics_ord_trace;

drop table if exists t_logistics_tracking;

drop table if exists t_maintenance;

drop table if exists t_msg_queue;

drop table if exists t_msg_tmp;

drop table if exists t_no_seg_cfg;

drop table if exists t_no_seg_cfg_his;

drop table if exists t_notify_cfg;

drop table if exists t_ord;

drop table if exists t_ord_detail;

drop table if exists t_ord_detail_his;

drop table if exists t_ord_detail_pdt;

drop table if exists t_ord_detail_pdt_appver;

drop table if exists t_ord_download;

drop table if exists t_ord_his;

drop table if exists t_pay;

drop table if exists t_pay_detail;

drop table if exists t_pay_notify;

drop table if exists t_pdt;

drop table if exists t_pdt_his;

drop table if exists t_pdt_no;

drop table if exists t_pdt_plan_day;

drop table if exists t_pdt_plan_day_his;

drop table if exists t_pdt_plan_day_quota;

drop table if exists t_pdt_plan_month;

drop table if exists t_pdt_quota;

drop table if exists t_pdt_stock;

drop table if exists t_pdt_stock_his;

drop table if exists t_province;

drop table if exists t_renew;

drop table if exists t_renew_detail;

drop table if exists t_sno_cfg;

drop table if exists t_sys;

drop table if exists t_sys_param;

drop table if exists t_sys_sysrole;

drop table if exists t_sysauth;

drop table if exists t_sysrole;

drop table if exists t_sysrole_sysauth;

drop table if exists t_task_conf;

drop table if exists t_task_log;

drop table if exists t_user;

drop table if exists t_user_sub;

drop table if exists t_user_userrole;

drop table if exists t_userauth;

drop table if exists t_userrole;

drop table if exists t_userrole_userauth;

drop table if exists t_ware_house;

drop table if exists t_ware_house_detail;

drop table if exists t_warranty;

drop table if exists t_warranty_download;

/*==============================================================*/
/* Table: hibernate_sequences                                   */
/*==============================================================*/
create table hibernate_sequences
(
   sequence_name        varchar(255) not null,
   next_val             int not null,
   primary key (sequence_name, next_val)
);

/*==============================================================*/
/* Table: t_addr                                                */
/*==============================================================*/
create table t_addr
(
   i_addr               bigint not null,
   i_cust               int not null,
   name                 varchar(50) not null,
   province             varchar(50) not null,
   city                 varchar(50) not null,
   county               varchar(50),
   area                 varchar(50) not null,
   tel                  varchar(50),
   mobile               varchar(50),
   version              bigint not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   postal_code          varchar(20) not null,
   del_flag             char(1) not null,
   result               varchar(100) not null,
   primary key (i_addr)
);

/*==============================================================*/
/* Index: t_addr_idx1                                           */
/*==============================================================*/
create index t_addr_idx1 on t_addr
(
   name
);

/*==============================================================*/
/* Table: t_addr_his                                            */
/*==============================================================*/
create table t_addr_his
(
   i_addr_his           bigint not null,
   i_addr               bigint not null,
   i_cust               int not null,
   name                 varchar(50) not null,
   province             varchar(50) not null,
   city                 varchar(50) not null,
   county               varchar(50),
   area                 varchar(50) not null,
   tel                  varchar(50),
   mobile               varchar(50),
   ver                  bigint not null,
   gen_time             datetime not null,
   postal_code          varchar(20) not null,
   result               varchar(100) not null,
   primary key (i_addr_his)
);

/*==============================================================*/
/* Table: t_agmt                                                */
/*==============================================================*/
create table t_agmt
(
   i_agmt               bigint not null,
   agmt_no              char(14) not null,
   i_cust               bigint not null,
   deposit              decimal(20,2) not null,
   start_time           date not null,
   end_time             date not null,
   gen_time             datetime not null,
   efficient_time       datetime,
   upd_time             datetime not null,
   version              bigint not null,
   used_deposit         decimal(20,2) not null,
   remain_deposit       decimal(20,2) not null,
   del_flag             char(1) not null,
   agmt_status          varchar(50) not null,
   redundant_deposit    decimal(20,2),
   primary key (i_agmt)
);

/*==============================================================*/
/* Index: t_agmt_idx1                                           */
/*==============================================================*/
create unique index t_agmt_idx1 on t_agmt
(
   agmt_no
);

/*==============================================================*/
/* Index: t_agmt_idx2                                           */
/*==============================================================*/
create index t_agmt_idx2 on t_agmt
(
   gen_time,
   del_flag,
   agmt_status
);

/*==============================================================*/
/* Table: t_agmt_deposit                                        */
/*==============================================================*/
create table t_agmt_deposit
(
   i_agmt_deposit       bigint not null,
   i_agmt               bigint not null,
   agmt_no              char(14) not null,
   i_cust               bigint not null,
   deposit              decimal(20,2) not null,
   gen_time             datetime not null,
   ver                  bigint not null,
   used_deposit         decimal(20,2) not null,
   remain_deposit       decimal(20,2) not null,
   primary key (i_agmt_deposit)
);

/*==============================================================*/
/* Index: t_agmt_deposit_idx1                                   */
/*==============================================================*/
create index t_agmt_deposit_idx1 on t_agmt_deposit
(
   gen_time
);

/*==============================================================*/
/* Table: t_agmt_detail                                         */
/*==============================================================*/
create table t_agmt_detail
(
   i_agmt_detail        bigint not null,
   i_agmt               bigint not null,
   i_pdt                bigint not null,
   i_pdt_his            bigint not null,
   num                  int not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   agmt_detail_status   varchar(50) not null,
   version              bigint not null,
   rate                 decimal(20,2) not null,
   del_flag             char(1) not null,
   start_time           date not null,
   end_time             date not null,
   used_quota           int not null,
   remain_quota         int not null,
   primary key (i_agmt_detail)
);

/*==============================================================*/
/* Index: t_agmt_detail_idx1                                    */
/*==============================================================*/
create index t_agmt_detail_idx1 on t_agmt_detail
(
   gen_time,
   del_flag
);

/*==============================================================*/
/* Table: t_agmt_detail_his                                     */
/*==============================================================*/
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

/*==============================================================*/
/* Table: t_cust                                                */
/*==============================================================*/
create table t_cust
(
   i_cust               bigint not null,
   login_name           varchar(100) not null,
   password             char(28) not null,
   upd_time             datetime not null,
   gen_time             datetime not null,
   version              bigint not null,
   cust_status          varchar(50) not null,
   credit_level         varchar(50) not null,
   cust_code            varchar(50) not null,
   primary key (i_cust)
);

/*==============================================================*/
/* Index: t_cust_idx1                                           */
/*==============================================================*/
create unique index t_cust_idx1 on t_cust
(
   login_name
);

/*==============================================================*/
/* Index: t_cust_idx2                                           */
/*==============================================================*/
create index t_cust_idx2 on t_cust
(
   cust_status,
   credit_level,
   gen_time
);

/*==============================================================*/
/* Table: t_cust_appver                                         */
/*==============================================================*/
create table t_cust_appver
(
   i_cust_appver        bigint not null,
   cust_no              char(11) not null,
   name                 varchar(50) not null,
   i_cust               bigint not null,
   appver               varchar(255) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   del_flag             char(1) not null,
   primary key (i_cust_appver)
);

/*==============================================================*/
/* Table: t_cust_code                                           */
/*==============================================================*/
create table t_cust_code
(
   i_cust_code          bigint(20) not null,
   cust_code            varchar(50) not null,
   i_cust               bigint(20),
   cust_code_status     varchar(50) not null,
   company_name         varchar(50),
   i_user               bigint(20) not null,
   user_name            varchar(50) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint(20) not null,
   primary key (i_cust_code)
);

/*==============================================================*/
/* Index: t_cust_code_idx1                                      */
/*==============================================================*/
create unique index t_cust_code_idx1 on t_cust_code
(
   cust_code
);

/*==============================================================*/
/* Table: t_cust_credit                                         */
/*==============================================================*/
create table t_cust_credit
(
   i_cust_credit        bigint not null,
   i_cust               bigint not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   i_ord                bigint not null,
   change_reason        varchar(50) not null,
   credit_level         varchar(50) not null,
   primary key (i_cust_credit)
);

/*==============================================================*/
/* Index: t_cust_credit_idx1                                    */
/*==============================================================*/
create index t_cust_credit_idx1 on t_cust_credit
(
   gen_time
);

/*==============================================================*/
/* Table: t_cust_custrole                                       */
/*==============================================================*/
create table t_cust_custrole
(
   i_cust               bigint not null,
   i_custrole           bigint not null,
   primary key (i_cust, i_custrole)
);

/*==============================================================*/
/* Table: t_cust_rate                                           */
/*==============================================================*/
create table t_cust_rate
(
   i_cust_rate          bigint not null,
   i_cust               bigint not null,
   i_pdt                bigint not null,
   rate                 decimal(20,2) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   cust_rate_status     varchar(50) not null,
   primary key (i_cust_rate)
);

/*==============================================================*/
/* Index: t_cust_rate_idx1                                      */
/*==============================================================*/
create index t_cust_rate_idx1 on t_cust_rate
(
   gen_time
);

/*==============================================================*/
/* Table: t_cust_reg                                            */
/*==============================================================*/
create table t_cust_reg
(
   i_cust               bigint not null,
   cust_no              char(11),
   name                 varchar(50) not null,
   long_name            varchar(50) not null,
   reg_addr             varchar(50) not null,
   cust_type            varchar(50),
   contact_name         varchar(50),
   tel                  varchar(50),
   mobile               varchar(50),
   fax                  varchar(50),
   reg_date             datetime,
   ret_cap              varchar(50),
   upd_time             datetime not null,
   gen_time             datetime not null,
   version              bigint not null,
   summary              varchar(200),
   bus_lic              varchar(50),
   org_code             varchar(50),
   tax_reg              varchar(50),
   bus_lic_ifile        bigint,
   org_code_ifile       bigint,
   tax_reg_ifile        bigint,
   email                varchar(100) not null,
   corporation_name     varchar(100),
   account              varchar(100) not null,
   company_type         varchar(100),
   bank                 varchar(100) not null,
   refuse_reason        varchar(200),
   contract_no          varchar(50) not null,
   signature_date       datetime not null,
   invoice_corporation  varchar(100) not null,
   invoice_type         varchar(50) not null,
   tax_id               varchar(50) not null,
   reg_tel              varchar(50) not null,
   remarks              varchar(200),
   salesman_name        varchar(50),
   salesman_email       varchar(100),
   primary key (i_cust)
);

/*==============================================================*/
/* Index: t_cust_reg_idx1                                       */
/*==============================================================*/
create unique index t_cust_reg_idx1 on t_cust_reg
(
   cust_no
);

/*==============================================================*/
/* Index: t_cust_reg_idx2                                       */
/*==============================================================*/
create unique index t_cust_reg_idx2 on t_cust_reg
(
   name,
   cust_type,
   gen_time
);

/*==============================================================*/
/* Table: t_cust_stock                                          */
/*==============================================================*/
create table t_cust_stock
(
   i_cust_stock         bigint not null,
   i_cust               bigint not null,
   i_pdt                bigint not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   sum_ord_num_paid     bigint not null,
   sum_ord_num_4pay     bigint not null,
   sum_ord_num_cancel   bigint not null,
   sum_ord_num          bigint not null,
   sum_ord_num_sum      bigint not null,
   sum_spod_num         bigint not null,
   sum_shipment_num     bigint not null,
   primary key (i_cust_stock)
);

/*==============================================================*/
/* Index: t_cust_stock_idx1                                     */
/*==============================================================*/
create index t_cust_stock_idx1 on t_cust_stock
(
   gen_time
);

/*==============================================================*/
/* Table: t_cust_stock_detail                                   */
/*==============================================================*/
create table t_cust_stock_detail
(
   i_cust_stock_detail  bigint not null,
   i_cust               bigint not null,
   i_pdt                bigint not null,
   i_cust_stock         bigint not null,
   gen_time             datetime not null,
   num                  int not null,
   cust_stock_detail_type varchar(50) not null,
   delta_ord_num_paid   bigint not null,
   delta_ord_num_4pay   bigint not null,
   delta_ord_num_cancel bigint not null,
   delta_ord_num        bigint not null,
   delta_ord_num_sum    bigint not null,
   delta_spot_num       bigint not null,
   delta_shipment_num   bigint not null,
   sum_ord_num_paid     bigint not null,
   sum_ord_num_4pay     bigint not null,
   sum_ord_num_cancel   bigint not null,
   sum_ord_num          bigint not null,
   sum_ord_num_sum      bigint not null,
   sum_spod_num         bigint not null,
   sum_shipment_num     bigint not null,
   i_fk                 bigint not null,
   i_user               bigint not null,
   user_name            varchar(50) not null,
   primary key (i_cust_stock_detail)
);

/*==============================================================*/
/* Index: t_cust_stock_detail_idx1                              */
/*==============================================================*/
create index t_cust_stock_detail_idx1 on t_cust_stock_detail
(
   gen_time
);

/*==============================================================*/
/* Table: t_custauth                                            */
/*==============================================================*/
create table t_custauth
(
   i_custauth           bigint not null,
   name                 varchar(100) not null,
   code                 varchar(100) not null,
   url                  tinytext not null,
   parent_i_custauth    bigint,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   auth_type            varchar(50) not null,
   primary key (i_custauth)
);

/*==============================================================*/
/* Index: t_custauth_idx1                                       */
/*==============================================================*/
create index t_custauth_idx1 on t_custauth
(
   auth_type
);

/*==============================================================*/
/* Index: t_custauth_idx2                                       */
/*==============================================================*/
create index t_custauth_idx2 on t_custauth
(
   name,
   code
);

/*==============================================================*/
/* Table: t_custrole                                            */
/*==============================================================*/
create table t_custrole
(
   i_custrole           bigint not null,
   name                 varchar(100) not null,
   memo                 tinytext not null,
   upd_time             datetime not null,
   gen_time             datetime not null,
   version              bigint not null,
   del_flag             char(1) not null,
   primary key (i_custrole)
);

/*==============================================================*/
/* Index: t_custrole_idx1                                       */
/*==============================================================*/
create index t_custrole_idx1 on t_custrole
(
   name,
   del_flag,
   gen_time
);

/*==============================================================*/
/* Index: t_custrole_idx2                                       */
/*==============================================================*/
create index t_custrole_idx2 on t_custrole
(
   i_custrole,
   del_flag
);

/*==============================================================*/
/* Table: t_custrole_custauth                                   */
/*==============================================================*/
create table t_custrole_custauth
(
   i_custrole           bigint not null,
   i_custauth           bigint not null,
   primary key (i_custrole, i_custauth)
);

/*==============================================================*/
/* Table: t_detail_trace                                        */
/*==============================================================*/
create table t_detail_trace
(
   i_detail_trace       bigint not null,
   i_cust               bigint not null,
   i_agmt               bigint,
   i_ord                bigint,
   i_logistics_ord      bigint,
   agmt_no              varchar(14),
   ord_no               varchar(14),
   logistics_ord_no     varchar(14),
   amt                  decimal(20,2),
   num                  int,
   memo                 text,
   gen_time             datetime not null,
   log_type             varchar(50) not null,
   i_operator           bigint not null,
   primary key (i_detail_trace)
);

/*==============================================================*/
/* Table: t_dict                                                */
/*==============================================================*/
create table t_dict
(
   i_dict               bigint not null,
   dict_type            varchar(50) not null,
   code                 VARCHAR(50) not null,
   value                VARCHAR(50) not null,
   memo                 tinytext,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   primary key (i_dict)
);

/*==============================================================*/
/* Index: t_dict_idx1                                           */
/*==============================================================*/
create index t_dict_idx1 on t_dict
(
   dict_type
);

/*==============================================================*/
/* Table: t_erp_maintenance                                     */
/*==============================================================*/
create table t_erp_maintenance
(
   i_erp_maintenance    bigint not null,
   fh_date              varchar(24) not null,
   real_ordno           varchar(32) not null,
   inner_ordno          varchar(126) not null,
   sn                   varchar(12) not null,
   ph                   varchar(50) not null,
   pm                   varchar(256),
   i_cust               bigint not null,
   purchase_date        date,
   life_start_time      date not null,
   warranty_period      date not null,
   last_repaired_date   date,
   repair_num           integer not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   valid_status         varchar(50) not null,
   last_maintenance_id  bigint,
   primary key (i_erp_maintenance)
);

/*==============================================================*/
/* Index: t_erp_maintenance_idx1                                */
/*==============================================================*/
create unique index t_erp_maintenance_idx1 on t_erp_maintenance
(
   sn
);

/*==============================================================*/
/* Table: t_erp_sync                                            */
/*==============================================================*/
create table t_erp_sync
(
   i_erp_sync           bigint(32) not null,
   id                   bigint(32),
   ywy_code             varchar(12),
   ywy                  varchar(12),
   dept_code            varchar(12),
   deptment             varchar(64),
   db                   varchar(4),
   dh                   varchar(16),
   dj_date              varchar(20),
   khjc                 varchar(256),
   skdw                 varchar(256),
   xhrq                 varchar(24),
   qty                  bigint(11),
   cpml                 varchar(256),
   ph                   varchar(64),
   ck_code              varchar(12),
   ck_name              varchar(64),
   dddh                 varchar(24),
   hyfs                 varchar(128),
   shdz                 varchar(128),
   shdh                 varchar(126),
   shr                  varchar(128),
   ddsx                 varchar(128),
   th_date              varchar(24),
   fh_date              varchar(24),
   dh_date              varchar(24),
   qrdhs                varchar(24),
   qrdsd                varchar(24),
   ycfk                 varchar(256),
   sjtq_date            varchar(20),
   wlgs                 varchar(32),
   pm                   varchar(256),
   gg                   varchar(256),
   wldh                 varchar(32),
   xh                   varchar(10),
   khdh                 varchar(126),
   shzt                 varchar(10),
   tbzl                 varchar(40),
   fhsl                 int,
   fhxs                 int,
   sn                   blob,
   DDH                  varchar(126),
   ypos_xh              varchar(10),
   gen_time             datetime,
   upd_time             datetime,
   version              bigint,
   primary key (i_erp_sync)
);

/*==============================================================*/
/* Table: t_file                                                */
/*==============================================================*/
create table t_file
(
   i_file               bigint not null,
   uuid                 char(32) not null,
   length               int not null,
   sha                  varchar(50),
   name                 varchar(100) not null,
   ext_name             varchar(10) not null,
   location             varchar(255),
   content_type         varchar(100) not null,
   content              longblob,
   gen_time             datetime not null,
   primary key (i_file)
);

/*==============================================================*/
/* Index: t_file_idx1                                           */
/*==============================================================*/
create unique index t_file_idx1 on t_file
(
   uuid
);

/*==============================================================*/
/* Table: t_holiday                                             */
/*==============================================================*/
create table t_holiday
(
   i_holiday            bigint not null,
   year                 int not null,
   month                int not null,
   day                  int not null,
   holi_status          varchar(50) not null,
   memo                 tinytext,
   gen_time             datetime not null,
   upd_time             datetime not null,
   del_flg              CHAR(1) not null,
   version              bigint not null,
   primary key (i_holiday)
);

/*==============================================================*/
/* Index: t_holiday_idx1                                        */
/*==============================================================*/
create index t_holiday_idx1 on t_holiday
(
   year,
   month,
   day,
   holi_status
);

/*==============================================================*/
/* Table: t_log                                                 */
/*==============================================================*/
create table t_log
(
   i_log                bigint not null,
   i_fk                 bigint not null,
   log_type             varchar(50) not null,
   oper_type            varchar(50) not null,
   memo                 tinytext not null,
   pre_data             text not null,
   data                 text not null,
   gen_time             datetime not null,
   primary key (i_log)
);

/*==============================================================*/
/* Index: t_log_idx1                                            */
/*==============================================================*/
create index t_log_idx1 on t_log
(
   log_type,
   oper_type,
   gen_time
);

/*==============================================================*/
/* Index: t_log_idx2                                            */
/*==============================================================*/
create index t_log_idx2 on t_log
(
   log_type
);

/*==============================================================*/
/* Index: t_log_idx3                                            */
/*==============================================================*/
create index t_log_idx3 on t_log
(
   oper_type
);

/*==============================================================*/
/* Index: t_log_idx4                                            */
/*==============================================================*/
create index t_log_idx4 on t_log
(
   gen_time
);

/*==============================================================*/
/* Index: t_log_idx5                                            */
/*==============================================================*/
create index t_log_idx5 on t_log
(
   log_type,
   gen_time
);

/*==============================================================*/
/* Index: t_log_idx6                                            */
/*==============================================================*/
create index t_log_idx6 on t_log
(
   oper_type,
   gen_time
);

/*==============================================================*/
/* Table: t_logistics_com                                       */
/*==============================================================*/
create table t_logistics_com
(
   i_logistics_com      bigint not null,
   name                 varchar(50) not null,
   fullname             varchar(200) not null,
   aging                int not null,
   fee_flag             varchar(50) not null,
   logistics_status     varchar(50) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   price                decimal(20,2) not null,
   code                 varchar(20) not null,
   primary key (i_logistics_com)
);

/*==============================================================*/
/* Index: t_logistics_com_idx1                                  */
/*==============================================================*/
create unique index t_logistics_com_idx1 on t_logistics_com
(
   fullname
);

/*==============================================================*/
/* Table: t_logistics_detail                                    */
/*==============================================================*/
create table t_logistics_detail
(
   i_logistics_detail   bigint not null,
   i_logistics_ord      bigint not null,
   i_pdt_his            bigint not null,
   pdt_num              int not null,
   i_cust               bigint not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   i_ord_detail_pdt     bigint not null,
   primary key (i_logistics_detail)
);

/*==============================================================*/
/* Index: t_logistics_detail_idx1                               */
/*==============================================================*/
create index t_logistics_detail_idx1 on t_logistics_detail
(
   gen_time
);

/*==============================================================*/
/* Table: t_logistics_download                                  */
/*==============================================================*/
create table t_logistics_download
(
   i_logistics_download bigint(20) not null,
   batch_no             varchar(32),
   i_usr                bigint(20),
   num                  int(11),
   file_uuid            varchar(32),
   gen_time             datetime,
   upd_time             datetime,
   primary key (i_logistics_download)
);

/*==============================================================*/
/* Table: t_logistics_ord                                       */
/*==============================================================*/
create table t_logistics_ord
(
   i_logistics_ord      bigint not null,
   inner_ordno          char(14) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   num                  int,
   specify_delivery     datetime,
   expected_arrival     datetime,
   i_cust               bigint not null,
   i_user               bigint not null,
   username             varchar(50) not null,
   real_outstock_num    int,
   i_logistics_ord_trace bigint,
   temp_flag            char(1) not null,
   i_ord                bigint,
   trace_no             varchar(32),
   logistics_ord_status varchar(50) not null,
   logistics_freight    decimal(20,2),
   pay_status           varchar(50) not null,
   i_file               bigint not null,
   amt                  decimal(20,2),
   primary key (i_logistics_ord)
);

/*==============================================================*/
/* Index: t_logistics_ord_idx1                                  */
/*==============================================================*/
create unique index t_logistics_ord_idx1 on t_logistics_ord
(
   inner_ordno
);

/*==============================================================*/
/* Table: t_logistics_ord_addr                                  */
/*==============================================================*/
create table t_logistics_ord_addr
(
   i_logistics_ord_addr bigint not null,
   i_logistics_ord      bigint not null,
   i_addr_his           bigint,
   consignee_name       varchar(100),
   consignee_mobile     varchar(50),
   consignee_addr       varchar(200),
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   real_ordno           varchar(100),
   real_delivery        datetime,
   real_arrival         datetime,
   outstock_no          varchar(100),
   i_logistics_com      bigint,
   logistics_com_name   varchar(100),
   price                decimal(20,2),
   num                  int,
   real_outstock_num    int,
   i_user               bigint not null,
   username             varchar(50) not null,
   i_pdt_his            bigint not null,
   serial               int not null,
   fee_flag             varchar(50),
   real_serial          varchar(255),
   del_flag             char(1),
   pre_serial           int,
   primary key (i_logistics_ord_addr)
);

/*==============================================================*/
/* Table: t_logistics_ord_trace                                 */
/*==============================================================*/
create table t_logistics_ord_trace
(
   i_logistics_org_trace bigint not null,
   logistics_org_no     varchar(20) not null,
   i_user               bigint not null,
   user_name            varchar(50) not null,
   gen_time             datetime not null,
   logistics_num        bigint not null,
   status               varchar(50) not null,
   primary key (i_logistics_org_trace)
);

/*==============================================================*/
/* Table: t_logistics_tracking                                  */
/*==============================================================*/
create table t_logistics_tracking
(
   i_logistics_tracking bigint not null,
   i_logistics_ord      bigint not null,
   logistics_date       datetime not null,
   logistics_desc       varchar(255),
   receving_status      varchar(50) not null,
   gen_time             datetime not null,
   primary key (i_logistics_tracking)
);

/*==============================================================*/
/* Index: t_logistics_tracking_idx1                             */
/*==============================================================*/
create index t_logistics_tracking_idx1 on t_logistics_tracking
(
   gen_time
);

/*==============================================================*/
/* Table: t_maintenance                                         */
/*==============================================================*/
create table t_maintenance
(
   i_maintenance        bigint not null,
   primary key (i_maintenance)
);

/*==============================================================*/
/* Table: t_msg_queue                                           */
/*==============================================================*/
create table t_msg_queue
(
   i_queue              bigint not null,
   attachmentt          blob,
   content              blob not null,
   from_user            varchar(100),
   gen_time             datetime not null,
   message              varchar(255),
   notify_type          varchar(20) not null,
   stat                 varchar(20) not null,
   subject              varchar(100) not null,
   target               varchar(100) not null,
   to_user              varchar(100) not null,
   upd_time             datetime not null,
   primary key (i_queue)
);

/*==============================================================*/
/* Table: t_msg_tmp                                             */
/*==============================================================*/
create table t_msg_tmp
(
   i_msg_tmp            bigint not null,
   tmp_type             VARCHAR(100) not null,
   tmp_code             varchar(50) not null,
   note_name            VARCHAR(255) not null,
   content              text not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   del_flg              CHAR(1) not null,
   version              bigint not null,
   primary key (i_msg_tmp)
);

/*==============================================================*/
/* Index: t_msg_tmp_idx1                                        */
/*==============================================================*/
create index t_msg_tmp_idx1 on t_msg_tmp
(
   tmp_type
);

/*==============================================================*/
/* Table: t_no_seg_cfg                                          */
/*==============================================================*/
create table t_no_seg_cfg
(
   i_no_seg_cfg         bigint not null,
   pre                  varchar(4) not null,
   start                bigint not null,
   end                  bigint not null,
   idx                  bigint not null,
   version              bigint not null,
   primary key (i_no_seg_cfg)
);

/*==============================================================*/
/* Table: t_no_seg_cfg_his                                      */
/*==============================================================*/
create table t_no_seg_cfg_his
(
   i_no_seg_cfg_his     bigint not null,
   i_no_seg_cfg         bigint not null,
   pre                  varchar(4) not null,
   start                bigint not null,
   end                  bigint not null,
   idx                  bigint not null,
   ver                  bigint not null,
   gen_time             datetime not null,
   primary key (i_no_seg_cfg_his)
);

/*==============================================================*/
/* Table: t_notify_cfg                                          */
/*==============================================================*/
create table t_notify_cfg
(
   i_notify_cfg         bigint not null,
   notify_type          varchar(50) not null,
   i_user               bigint not null,
   user_name            varchar(100) not null,
   memo                 varchar(100) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   del_flag             char(1) not null,
   primary key (i_notify_cfg)
);

/*==============================================================*/
/* Index: t_notify_cfg_idx1                                     */
/*==============================================================*/
create index t_notify_cfg_idx1 on t_notify_cfg
(
   notify_type,
   user_name,
   gen_time
);

/*==============================================================*/
/* Table: t_ord                                                 */
/*==============================================================*/
create table t_ord
(
   i_ord                bigint not null,
   ord_no               char(14) not null,
   i_agmt               bigint not null,
   i_cust               bigint not null,
   upd_time             datetime not null,
   gen_time             datetime not null,
   amt                  decimal(20,2) not null,
   ord_status           varchar(50) not null,
   pay_status           varchar(50) not null,
   del_flag             char(1) not null,
   trace_no             varchar(32),
   lock_amt_of_deposit  decimal(20,2) not null,
   amt_of_delivery      decimal(20,2),
   remark               varchar(255),
   primary key (i_ord)
);

/*==============================================================*/
/* Index: t_ord_idx1                                            */
/*==============================================================*/
create index t_ord_idx1 on t_ord
(
   gen_time,
   ord_status,
   pay_status,
   del_flag
);

/*==============================================================*/
/* Table: t_ord_detail                                          */
/*==============================================================*/
create table t_ord_detail
(
   i_ord_detail         bigint not null,
   i_ord                bigint not null,
   i_pdt                bigint not null,
   i_pdt_his            bigint not null,
   i_cust               bigint not null,
   num                  int not null,
   price                decimal(20,2) not null,
   rate                 decimal(20,2) not null,
   amt                  decimal(20,2) not null,
   ord_detail_status    varchar(50) not null,
   version              bigint not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   used_quota           int not null,
   remain_quota         int not null,
   del_flag             char(1) not null,
   deliveryed           int not null,
   pending_num          int not null,
   produced_num         int not null,
   start_sn             varchar(12),
   end_sn               varchar(12),
   amt_of_delivery      decimal(20,2),
   primary key (i_ord_detail)
);

/*==============================================================*/
/* Index: t_ord_detail_idx1                                     */
/*==============================================================*/
create index t_ord_detail_idx1 on t_ord_detail
(
   ord_detail_status,
   del_flag
);

/*==============================================================*/
/* Table: t_ord_detail_his                                      */
/*==============================================================*/
create table t_ord_detail_his
(
   i_ord_detail_his     bigint not null,
   i_ord_his            bigint,
   i_ord_detail         bigint not null,
   i_ord                bigint not null,
   i_pdt                bigint not null,
   i_pdt_his            bigint not null,
   num                  int not null,
   price                decimal(20,2) not null,
   amt                  decimal(20,2) not null,
   ord_detail_status    varchar(50) not null,
   rate                 decimal(20,2) not null,
   i_cust               bigint not null,
   gen_time             datetime not null,
   used_quota           int not null,
   remain_quota         int not null,
   deliveryed           int not null,
   pending_num          int not null,
   download_stat        varchar(11),
   produced_num         int not null,
   start_sn             varchar(12),
   end_sn               varchar(12),
   amt_of_delivery      decimal(20,2),
   primary key (i_ord_detail_his)
);

/*==============================================================*/
/* Table: t_ord_detail_pdt                                      */
/*==============================================================*/
create table t_ord_detail_pdt
(
   i_ord_detail_pdt     bigint not null,
   year                 int not null,
   month                int not null,
   day                  int not null,
   ord_detail_pdt_type  varchar(50) not null,
   num                  int not null,
   lock_type            varchar(50) not null,
   ord_detail_pdt_status varchar(50) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   i_pdt                bigint not null,
   i_ord                bigint not null,
   produce_status       varchar(50) not null,
   deliveryed           int not null,
   pending_num          int not null,
   produced_num         int not null,
   used_quota           int not null,
   remain_quota         int not null,
   download_stat        varchar(11),
   start_sn             varchar(12),
   end_sn               varchar(12),
   primary key (i_ord_detail_pdt)
);

/*==============================================================*/
/* Index: t_ord_detail_pdt_idx1                                 */
/*==============================================================*/
create index t_ord_detail_pdt_idx1 on t_ord_detail_pdt
(
   year,
   month,
   day,
   gen_time
);

/*==============================================================*/
/* Table: t_ord_detail_pdt_appver                               */
/*==============================================================*/
create table t_ord_detail_pdt_appver
(
   i_ord_detail_pdt_appver bigint not null,
   i_ord_detail_pdt     bigint not null,
   i_cust_appver        bigint,
   appver               varchar(255) not null,
   num                  int not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   start_sn             varchar(12) not null,
   end_sn               varchar(12) not null,
   primary key (i_ord_detail_pdt_appver)
);

/*==============================================================*/
/* Table: t_ord_download                                        */
/*==============================================================*/
create table t_ord_download
(
   i_ord_download       bigint(20) not null,
   batch_no             varchar(32),
   i_usr                bigint(20),
   num                  int(11),
   file_uuid            varchar(32),
   gen_time             datetime,
   upd_time             datetime,
   count                int(32),
   primary key (i_ord_download)
);

/*==============================================================*/
/* Table: t_ord_his                                             */
/*==============================================================*/
create table t_ord_his
(
   i_ord_his            bigint not null,
   i_ord                bigint not null,
   i_agmt               bigint not null,
   i_cust               bigint,
   upd_time             datetime not null,
   gen_time             datetime not null,
   amt                  decimal(20,2) not null,
   ord_status           varchar(50) not null,
   pay_status           varchar(50) not null,
   trace_no             varchar(32),
   lock_amt_of_deposit  decimal(20,2) not null,
   amt_of_delivery      decimal(20,2),
   primary key (i_ord_his)
);

/*==============================================================*/
/* Index: t_ord_his_idx1                                        */
/*==============================================================*/
create index t_ord_his_idx1 on t_ord_his
(
   gen_time,
   ord_status
);

/*==============================================================*/
/* Table: t_pay                                                 */
/*==============================================================*/
create table t_pay
(
   i_pay                bigint not null,
   pay_status           varchar(50) not null,
   i_cust               bigint not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   amt                  decimal(20,2) not null,
   pay_no               varchar(50),
   i_file               bigint,
   remittance_no        varchar(50),
   account              varchar(50),
   bank                 varchar(50),
   pay_method           varchar(50) not null,
   remain_amt           decimal(20,2) not null,
   primary key (i_pay)
);

/*==============================================================*/
/* Index: t_pay_idx1                                            */
/*==============================================================*/
create index t_pay_idx1 on t_pay
(
   pay_status,
   gen_time,
   pay_no
);

/*==============================================================*/
/* Table: t_pay_detail                                          */
/*==============================================================*/
create table t_pay_detail
(
   i_pay_detail         bigint not null,
   i_pay                bigint not null,
   i_fk                 bigint not null,
   i_his_fk             bigint not null,
   pay_type             varchar(50) not null,
   pay_status           varchar(50) not null,
   i_cust               bigint not null,
   gen_time             datetime not null,
   version              bigint not null,
   upd_time             datetime not null,
   i_user               bigint not null,
   cust_name            varchar(100) not null,
   user_name            varchar(100) not null,
   refuse_reason        varchar(200),
   pay_amt              decimal(20,2),
   pay_method           varchar(50) not null,
   primary key (i_pay_detail)
);

/*==============================================================*/
/* Index: t_pay_detail_idx1                                     */
/*==============================================================*/
create index t_pay_detail_idx1 on t_pay_detail
(
   pay_status,
   gen_time
);

/*==============================================================*/
/* Table: t_pay_notify                                          */
/*==============================================================*/
create table t_pay_notify
(
   i_pay_notify         bigint not null,
   i_cust               bigint not null,
   i_fk                 bigint not null,
   i_his_fk             bigint not null,
   pay_type             varchar(50) not null,
   pay_notify_no        char(14) not null,
   memo                 tinytext not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   pay_notify_status    varchar(50) not null,
   del_flag             char(1) not null,
   havepay_amt          decimal(20,2) not null,
   primary key (i_pay_notify)
);

/*==============================================================*/
/* Index: t_pay_notify_idx1                                     */
/*==============================================================*/
create index t_pay_notify_idx1 on t_pay_notify
(
   pay_type,
   pay_notify_no,
   gen_time,
   pay_notify_status
);

/*==============================================================*/
/* Table: t_pdt                                                 */
/*==============================================================*/
create table t_pdt
(
   i_pdt                bigint not null,
   i_pdt_his            bigint,
   i_no_seg_cfg         bigint not null,
   pdt_no               varchar(50) not null,
   name                 varchar(100) not null,
   long_name            varchar(200) not null,
   price                decimal(20,2) not null,
   i_user               bigint not null,
   user_name            varchar(100) not null,
   memo                 varchar(255) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   del_flag             char(1) not null,
   smalllogo            bigint,
   biglogo              bigint,
   primary key (i_pdt)
);

/*==============================================================*/
/* Index: t_pdt_idx1                                            */
/*==============================================================*/
create index t_pdt_idx1 on t_pdt
(
   pdt_no,
   name,
   gen_time
);

/*==============================================================*/
/* Table: t_pdt_his                                             */
/*==============================================================*/
create table t_pdt_his
(
   i_pdt_his            bigint not null,
   i_pdt                bigint not null,
   i_no_seg_cfg         bigint not null,
   ver                  bigint not null,
   pre_i_pdt_his        bigint,
   pdt_no               varchar(50) not null,
   name                 varchar(100) not null,
   long_name            varchar(100) not null,
   price                decimal(20,2) not null,
   i_user               bigint not null,
   user_name            varchar(100) not null,
   memo                 varchar(255) not null,
   gen_time             datetime not null,
   smalllogo            bigint,
   biglogo              bigint,
   primary key (i_pdt_his)
);

/*==============================================================*/
/* Index: t_pdt_his_idx1                                        */
/*==============================================================*/
create index t_pdt_his_idx1 on t_pdt_his
(
   gen_time
);

/*==============================================================*/
/* Table: t_pdt_no                                              */
/*==============================================================*/
create table t_pdt_no
(
   i_pdt_no             bigint not null,
   pdt_no               varchar(50) not null,
   appver_type          varchar(50) not null,
   i_cust_appver        varchar(50),
   i_cust               bigint not null,
   i_ord                bigint not null,
   i_ord_detail         bigint not null,
   i_logistics_ord      bigint,
   i_logistics_ord_addr bigint,
   lock_type            varchar(50) not null,
   del_flag             char(1) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   primary key (i_pdt_no)
);

/*==============================================================*/
/* Table: t_pdt_plan_day                                        */
/*==============================================================*/
create table t_pdt_plan_day
(
   i_pdt_plan_day       bigint not null,
   i_pdt_plan_month     bigint not null,
   i_pdt                bigint not null,
   year                 int not null,
   month                int not null,
   week                 int not null,
   day                  int not null,
   weekday              int not null,
   num                  int not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   del_flag             char(1) not null,
   primary key (i_pdt_plan_day)
);

/*==============================================================*/
/* Index: t_pdt_plan_day_idx1                                   */
/*==============================================================*/
create index t_pdt_plan_day_idx1 on t_pdt_plan_day
(
   year,
   month,
   day,
   gen_time
);

/*==============================================================*/
/* Table: t_pdt_plan_day_his                                    */
/*==============================================================*/
create table t_pdt_plan_day_his
(
   i_pdt_plan_day_his   bigint not null,
   i_pdt_plan_day       bigint not null,
   i_pdt_plan_month     bigint not null,
   i_pdt                bigint not null,
   year                 int not null,
   month                int not null,
   week                 int not null,
   day                  int not null,
   weekday              int not null,
   num                  int not null,
   gen_time             datetime not null,
   ver                  bigint not null,
   primary key (i_pdt_plan_day_his)
);

/*==============================================================*/
/* Index: t_pdt_plan_day_his_idx1                               */
/*==============================================================*/
create index t_pdt_plan_day_his_idx1 on t_pdt_plan_day_his
(
   year,
   month,
   day,
   gen_time
);

/*==============================================================*/
/* Table: t_pdt_plan_day_quota                                  */
/*==============================================================*/
create table t_pdt_plan_day_quota
(
   i_pdt_plan_day_quota bigint not null,
   year                 int not null,
   month                int not null,
   week                 int not null,
   day                  int not null,
   weekday              int not null,
   num                  int not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   used_quota           int not null,
   remain_quota         int not null,
   ord_detail_pdt_type  varchar(50) not null,
   i_pdt                bigint not null,
   collection_flag      varchar(3) not null,
   primary key (i_pdt_plan_day_quota)
);

/*==============================================================*/
/* Index: t_pdt_plan_day_quota_idx1                             */
/*==============================================================*/
create index t_pdt_plan_day_quota_idx1 on t_pdt_plan_day_quota
(
   year,
   month,
   day,
   gen_time
);

/*==============================================================*/
/* Table: t_pdt_plan_month                                      */
/*==============================================================*/
create table t_pdt_plan_month
(
   i_pdt_plan_month     bigint not null,
   pdt_plan_month_no    char(11) not null,
   i_pdt                bigint not null,
   num                  int not null,
   year                 int not null,
   month                int not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   del_flag             char(1) not null,
   primary key (i_pdt_plan_month)
);

/*==============================================================*/
/* Index: t_pdt_plan_month_idx1                                 */
/*==============================================================*/
create index t_pdt_plan_month_idx1 on t_pdt_plan_month
(
   year,
   month
);

/*==============================================================*/
/* Table: t_pdt_quota                                           */
/*==============================================================*/
create table t_pdt_quota
(
   i_pdt_quota          bigint not null,
   primary key (i_pdt_quota)
);

/*==============================================================*/
/* Table: t_pdt_stock                                           */
/*==============================================================*/
create table t_pdt_stock
(
   i_pdt                bigint not null,
   num                  int not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   primary key (i_pdt)
);

/*==============================================================*/
/* Index: t_pdt_stock_idx1                                      */
/*==============================================================*/
create index t_pdt_stock_idx1 on t_pdt_stock
(
   gen_time
);

/*==============================================================*/
/* Table: t_pdt_stock_his                                       */
/*==============================================================*/
create table t_pdt_stock_his
(
   i_pdt_stock_his      bigint not null,
   i_pdt                bigint not null,
   num                  int not null,
   gen_time             datetime not null,
   i_user               bigint,
   user_name            varbinary(100),
   ver                  bigint not null,
   day                  date not null,
   primary key (i_pdt_stock_his)
);

/*==============================================================*/
/* Index: t_pdt_stock_his_idx1                                  */
/*==============================================================*/
create index t_pdt_stock_his_idx1 on t_pdt_stock_his
(
   gen_time
);

/*==============================================================*/
/* Table: t_province                                            */
/*==============================================================*/
create table t_province
(
   prov_code            varchar(6) not null,
   close_flag           tinyint(1) not null,
   name                 varchar(30) not null,
   pre_prov_code        varchar(6) not null,
   primary key (prov_code)
);

/*==============================================================*/
/* Table: t_renew                                               */
/*==============================================================*/
create table t_renew
(
   i_renew              bigint not null,
   renew_status         varchar(50) not null,
   pay_status           varchar(50) not null,
   renew_time           datetime not null,
   renew_life           int not null,
   renew_amt            decimal(20,2) not null,
   i_cust               bigint not null,
   cust_name            varchar(100) not null,
   i_user               bigint,
   user_name            varchar(100),
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   primary key (i_renew)
);

/*==============================================================*/
/* Table: t_renew_detail                                        */
/*==============================================================*/
create table t_renew_detail
(
   i_renew_detail       bigint not null,
   i_renew              bigint not null,
   i_erp_maintenance    bigint not null,
   seq_no               varchar(12) not null,
   pdt_no               varchar(100) not null,
   life_start_time      date not null,
   life_end_time        date not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   del_flag             char(1) not null,
   version              bigint not null,
   primary key (i_renew_detail)
);

/*==============================================================*/
/* Index: t_renew_detail_idx1                                   */
/*==============================================================*/
create unique index t_renew_detail_idx1 on t_renew_detail
(
   seq_no,
   i_renew
);

/*==============================================================*/
/* Table: t_sno_cfg                                             */
/*==============================================================*/
create table t_sno_cfg
(
   sno_id               int not null,
   sno_type             varchar(100) not null,
   max_val              int not null,
   primary key (sno_id)
);

/*==============================================================*/
/* Table: t_sys                                                 */
/*==============================================================*/
create table t_sys
(
   i_sys                bigint not null,
   login_name           varchar(100) not null,
   password             char(28) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   primary key (i_sys)
);

/*==============================================================*/
/* Index: t_sys_idx1                                            */
/*==============================================================*/
create unique index t_sys_idx1 on t_sys
(
   login_name
);

/*==============================================================*/
/* Table: t_sys_param                                           */
/*==============================================================*/
create table t_sys_param
(
   i_sys_param          bigint not null,
   sys_param_type       varchar(50) not null,
   name                 varchar(100) not null,
   code                 varchar(100) not null,
   value                varchar(200) not null,
   memo                 tinytext not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   start_time           varchar(50) not null,
   end_time             varchar(50) not null,
   primary key (i_sys_param)
);

/*==============================================================*/
/* Index: t_sys_param_idx1                                      */
/*==============================================================*/
create index t_sys_param_idx1 on t_sys_param
(
   sys_param_type
);

/*==============================================================*/
/* Table: t_sys_sysrole                                         */
/*==============================================================*/
create table t_sys_sysrole
(
   i_sys                bigint not null,
   i_sysrole            bigint not null,
   primary key (i_sys, i_sysrole)
);

/*==============================================================*/
/* Table: t_sysauth                                             */
/*==============================================================*/
create table t_sysauth
(
   i_sysauth            bigint not null,
   name                 varchar(100) not null,
   code                 varchar(100) not null,
   url                  tinytext not null,
   parent_i_sysauth     bigint,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   auth_type            varchar(50) not null,
   primary key (i_sysauth)
);

/*==============================================================*/
/* Index: t_sysauth_idx1                                        */
/*==============================================================*/
create index t_sysauth_idx1 on t_sysauth
(
   auth_type
);

/*==============================================================*/
/* Index: t_sysauth_idx2                                        */
/*==============================================================*/
create index t_sysauth_idx2 on t_sysauth
(
   name,
   code
);

/*==============================================================*/
/* Table: t_sysrole                                             */
/*==============================================================*/
create table t_sysrole
(
   i_sysrole            bigint not null,
   name                 varchar(100) not null,
   memo                 tinytext not null,
   upd_time             datetime not null,
   gen_time             datetime not null,
   version              bigint not null,
   del_flag             char(1) not null,
   primary key (i_sysrole)
);

/*==============================================================*/
/* Index: t_sysrole_idx1                                        */
/*==============================================================*/
create index t_sysrole_idx1 on t_sysrole
(
   del_flag,
   gen_time
);

/*==============================================================*/
/* Index: t_sysrole_idx2                                        */
/*==============================================================*/
create index t_sysrole_idx2 on t_sysrole
(
   name,
   del_flag
);

/*==============================================================*/
/* Table: t_sysrole_sysauth                                     */
/*==============================================================*/
create table t_sysrole_sysauth
(
   i_sysrole            bigint not null,
   i_sysauth            bigint not null,
   primary key (i_sysrole, i_sysauth)
);

/*==============================================================*/
/* Table: t_task_conf                                           */
/*==============================================================*/
create table t_task_conf
(
   i_task               bigint not null,
   enabled              varchar(255) not null,
   end_stat             varchar(255) not null,
   err_msg              tinytext,
   gen_time             datetime not null,
   last_date            datetime not null,
   service              varchar(255) not null,
   task_name            varchar(255) not null,
   task_no              varchar(255) not null,
   upd_time             datetime not null,
   count                int not null,
   primary key (i_task)
);

/*==============================================================*/
/* Table: t_task_log                                            */
/*==============================================================*/
create table t_task_log
(
   i_task_log           bigint not null,
   i_task               bigint not null,
   end_time             datetime not null,
   err_msg              tinytext,
   exec_type            varchar(255) not null,
   i_user               bigint,
   user_name            varchar(255),
   start_time           datetime,
   service              varchar(255) not null,
   task_name            varchar(255) not null,
   task_no              varchar(255) not null,
   status               varchar(255) not null,
   primary key (i_task_log)
);

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   i_user               bigint not null,
   login_name           varchar(100) not null,
   password             char(28) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   del_flag             char(1) not null,
   primary key (i_user)
);

/*==============================================================*/
/* Index: t_user_idx1                                           */
/*==============================================================*/
create unique index t_user_idx1 on t_user
(
   login_name
);

/*==============================================================*/
/* Index: t_user_idx2                                           */
/*==============================================================*/
create index t_user_idx2 on t_user
(
   del_flag,
   gen_time
);

/*==============================================================*/
/* Table: t_user_sub                                            */
/*==============================================================*/
create table t_user_sub
(
   i_user               bigint not null,
   last_login           datetime not null,
   name                 varchar(100) not null,
   depart               varchar(100) not null,
   upd_time             datetime not null,
   gen_time             datetime not null,
   version              bigint not null,
   email                varchar(100) not null,
   primary key (i_user)
);

/*==============================================================*/
/* Table: t_user_userrole                                       */
/*==============================================================*/
create table t_user_userrole
(
   i_user               bigint not null,
   i_userrole           bigint not null,
   primary key (i_user, i_userrole)
);

/*==============================================================*/
/* Table: t_userauth                                            */
/*==============================================================*/
create table t_userauth
(
   i_userauth           bigint not null,
   name                 varchar(100) not null,
   code                 varchar(100) not null,
   url                  tinytext not null,
   parent_i_userauth    bigint,
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   auth_type            varchar(50) not null,
   primary key (i_userauth)
);

/*==============================================================*/
/* Index: t_userauth_idx1                                       */
/*==============================================================*/
create index t_userauth_idx1 on t_userauth
(
   auth_type
);

/*==============================================================*/
/* Index: t_userauth_idx2                                       */
/*==============================================================*/
create index t_userauth_idx2 on t_userauth
(
   name,
   code
);

/*==============================================================*/
/* Table: t_userrole                                            */
/*==============================================================*/
create table t_userrole
(
   i_userrole           bigint not null,
   name                 varchar(100) not null,
   memo                 tinytext not null,
   upd_time             datetime not null,
   gen_time             datetime not null,
   version              bigint not null,
   del_flag             char(1) not null,
   primary key (i_userrole)
);

/*==============================================================*/
/* Index: t_userrole_idx1                                       */
/*==============================================================*/
create index t_userrole_idx1 on t_userrole
(
   del_flag,
   gen_time
);

/*==============================================================*/
/* Index: t_userrole_idx2                                       */
/*==============================================================*/
create index t_userrole_idx2 on t_userrole
(
   name,
   del_flag,
   gen_time
);

/*==============================================================*/
/* Table: t_userrole_userauth                                   */
/*==============================================================*/
create table t_userrole_userauth
(
   i_userrole           bigint not null,
   i_userauth           bigint not null,
   primary key (i_userrole, i_userauth)
);

/*==============================================================*/
/* Table: t_ware_house                                          */
/*==============================================================*/
create table t_ware_house
(
   i_ware_house         bigint not null,
   i_cust               bigint not null,
   i_agmt               bigint not null,
   pay_status           varchar(50) not null,
   remain_quota         int not null,
   amt                  decimal(20,2) not null,
   gen_time             datetime not null,
   upd_time             datetime not null,
   primary key (i_ware_house)
);

/*==============================================================*/
/* Table: t_ware_house_detail                                   */
/*==============================================================*/
create table t_ware_house_detail
(
   i_ware_house_detail  bigint not null,
   i_ware_house         bigint not null,
   i_cust               bigint not null,
   i_agmt               bigint not null,
   i_ord                bigint not null,
   i_ord_detail_pdt     bigint not null,
   i_pdt                bigint not null,
   date_varchar         varchar(50) not null,
   used_quota           int not null,
   remain_quota         int not null,
   amt                  decimal(20,2) not null,
   gen_time             datetime not null,
   primary key (i_ware_house_detail)
);

/*==============================================================*/
/* Table: t_warranty                                            */
/*==============================================================*/
create table t_warranty
(
   i_warranty           bigint not null,
   i_erp_maintenance    bigint not null,
   seq_no               varchar(12) not null,
   new_seq_no           varchar(12),
   pdt_no               varchar(100) not null,
   warranty_time        datetime not null,
   accept_time          datetime,
   repaired_time        datetime,
   warranty_status      varchar(50) not null,
   i_cust               bigint not null,
   cust_name            varchar(100) not null,
   warranty_person      varchar(100),
   remark               varchar(256),
   repaired_remark      varchar(256),
   trace_no             varchar(32),
   gen_time             datetime not null,
   upd_time             datetime not null,
   version              bigint not null,
   primary key (i_warranty)
);

/*==============================================================*/
/* Table: t_warranty_download                                   */
/*==============================================================*/
create table t_warranty_download
(
   i_warranty_download  bigint(20) not null,
   batch_no             varchar(32),
   i_usr                bigint(20),
   num                  int(11),
   file_uuid            varchar(32),
   gen_time             datetime,
   upd_time             datetime,
   primary key (i_warranty_download)
);

