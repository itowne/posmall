delete from t_sys_param ;

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000001,'OTHER_CONF','默认日产量','MRRCL','2000','OTHER_CONF',now(),now(),1,'00:00','00:00'); 

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000002,'OTHER_CONF','未提货的超期天数','WTHCQTS','60','OTHER_CONF',now(),now(),1,'00:00','00:00'); 

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000003,'OTHER_CONF','保证金比例调整','BZJBLTZ','10%','OTHER_CONF',now(),now(),1,'00:00','00:00'); 

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000004,'OTHER_CONF','超期未提货收费配置(元/台)','CQWTHSFPZ','5','OTHER_CONF',now(),now(),1,'00:00','00:00'); 

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000005,'OTHER_CONF','灌密钥服务收费配置(元/台)','GMYFWSFPZ','10','OTHER_CONF',now(),now(),1,'00:00','00:00'); 

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000006,'OTHER_CONF','协议有效期跨度（月）','XYYXQKD_Y','2','OTHER_CONF',now(),now(),1,'00:00','00:00'); 

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000007,'OTHER_CONF','协议订货总量最小值','XYDHZLZXZ','5000','OTHER_CONF',now(),now(),1,'00:00','00:00'); 

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000008,'OTHER_CONF','ERP物流接口地址','ERPURL','http://192.168.30.32/QueryForOrderAction.do','OTHER_CONF',now(),now(),1,'00:00','00:00');

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000009,'OTHER_CONF','订单支付保证金使用比例','DEPOSIT_SCALE_4_TORD','10%','OTHER_CONF',now(),now(),1,'00:00','00:00');

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000010,'OTHER_CONF','点单间隔天数(可以下几天之后的单)','SPACE_DAY_4_ORDER','5','OTHER_CONF',now(),now(),1,'00:00','00:00');

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000011,'OTHER_CONF','点单释放天数(可以下几天的单)','RELEASE_DAY_4_ORDER','10','OTHER_CONF',now(),now(),1,'00:00','00:00');

insert into t_sys_param(i_sys_param,sys_param_type,name,code,value,memo,gen_time,upd_time,version,start_time,end_time) 
values(000012,'OTHER_CONF','预约订单起始日期距下单日天数','SPACE_DAY_4_AGMT','5','OTHER_CONF',now(),now(),1,'00:00','00:00');

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000013,'EMAIL_CONF','系统邮件地址','FROM_ADDR','ypos@newlandpayment.com','EMAIL_CONF',now(), now(),1,'00:00','00:00');

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000014,'EMAIL_CONF','系统邮件登录密码','MAIL_PWD','520@payment','EMAIL_CONF',now(), now(),1,'00:00','00:00');

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000015,'OTHER_CONF','多地址物流模板文件','DDZWLTEMPSCV','template/moreAddrLogistics.csv','OTHER_CONF',now(),now(),1,'00:00','00:00');

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000016,'OTHER_CONF','协议到期提醒最大天数','EXPIRATION_REMINDE_MAX_DAY','15','OTHER_CONF',now(),now(),1,'00:00','00:00');

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000017,'OTHER_CONF','协议到期提醒最小天数','EXPIRATION_REMINDE_MIN_DAY','1','OTHER_CONF',now(),now(),1,'00:00','00:00');

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000018,'OTHER_CONF','订单平台网址','OFFICIAL_WEBSITE_ADDR','<a target=\"_blank\" href=\"https://www.yposmall.com/posmall/cust/login.do\">订单平台网址</a>','OTHER_CONF',now(),now(),1,'00:00','00:00');

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000019,'OTHER_CONF','公司邮件发件人名称','FROM_USERNAME','福建新大陆支付技术公司','OTHER_CONF',now(),now(),1,'00:00','00:00');

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000020,'OTHER_CONF','上传文件大小限制(单位:M)','UPLOAD_FILE_LIMIT_SIZE','10','OTHER_CONF',now(),now(),1,'00:00','00:00');

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000021,'OTHER_CONF','续保单价(单位：元/台/年)','RENEW_PRICE','30','OTHER_CONF',now(),now(),1,'00:00','00:00');

INSERT INTO t_sys_param ( i_sys_param, sys_param_type, name, code, value, memo, gen_time, upd_time, version, start_time, end_time ) 
VALUES(000022,'OTHER_CONF','包换周期(月)','REPLACE_PERIOD','3','OTHER_CONF',now(),now(),1,'00:00','00:00');