alter table t_warranty add new_seq_no varchar(12);

alter table t_erp_maintenance add valid_status varchar(50) not null;
update t_erp_maintenance set valid_status = 'VALID' where valid_status is NULL;
update t_erp_maintenance set valid_status = 'VALID' where valid_status = '';

alter table t_erp_maintenance add last_maintenance_id bigint;

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000022,'OTHER_CONF','包换周期(月)','REPLACE_PERIOD','3','OTHER_CONF',now(),now(),1,'00:00','00:00');