/*==============================================================*/
/* 报表相关sql                                            */
/*==============================================================*/
insert into t_userauth values (12100, '汇总报表', 'REPORT', '/admin/reportForm/reportFormList.do', null,now(),now(), '1', 'MENU');
insert into t_userrole_userauth values (1,12100);