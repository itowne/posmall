drop table if exists t_pdt_no;
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
drop table if exists t_cust_appver;
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
drop table if exists t_ord_detail_pdt_appver;
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