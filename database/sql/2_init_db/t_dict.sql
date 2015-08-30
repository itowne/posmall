delete from t_dict;

/*================================================================*/
/*dict_type: 是否类型       Table:通用                            */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(101, 'yes_no_type', 'YES', '是','通用 -是否类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(102, 'yes_no_type', 'NO', '否','通用 -是否类型',now(),now(),1);

/*================================================================*/
/*dict_type: 有效无效状态        Table:通用                       */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(201, 'valid_status', 'INVALID','无效','通用-有效无效状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(202, 'valid_status', 'VALID','有效','通用-有效无效状态',now(),now(),1);

/*================================================================*/
/*dict_type: 支付状态        Table:通用                           */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(301, 'pay_status', 'WAIT_PAY','待付款','通用-支付状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(302, 'pay_status', 'HAVE_PAY','已支付','通用-支付状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(303, 'pay_status', 'WAIT_AUDIT','待审核','通用-支付状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(304, 'pay_status', 'PART_PAY','部分支付','通用-支付状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(305, 'pay_status', 'NO_PASS','审核不通过','通用-支付状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(306, 'pay_status', 'REVOKED','已撤销','通用-支付状态',now(),now(),1);

/*================================================================*/
/*dict_type: 支付方式        Table:通用                       */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(401, 'pay_method', 'DEPOSIT','保证金','通用-支付方式',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(402, 'pay_method', 'REMITTANCE','汇款','通用-支付方式',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(403, 'pay_method', 'REDUNDANT_DEPOSIT','协议多余保证金','通用-支付方式',now(),now(),1);


/*================================================================*/
/*dict_type: 日志类型          Table:t_log                        */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1101, 'log_type','CUST', '客户','t_log-日志类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1102, 'log_type','USER', '后台人员','t_log-日志类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1103, 'log_type','SYS', '系统人员','t_log-日志类型',now(),now(),1);

/*================================================================*/
/*dict_type: 操作类型          Table:t_log                        */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1201, 'oper_type', 'USER_MGR', '后台人员管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1202, 'oper_type', 'CUST_MGR', '客户管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1203, 'oper_type', 'SYS_MGR', '系统人员管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1204, 'oper_type', 'AGMT_MGR', '协议管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1205, 'oper_type', 'ORD_MGR', '订单管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1206, 'oper_type', 'LOGISTICS_MGR', '物流管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1207, 'oper_type', 'PAYNOTIFY_MGR', '付款通知书管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1208, 'oper_type', 'PDT_MGR', '产品信息维护管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1209, 'oper_type', 'PDTPLANMONTH_MGR', '月排产计划管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1210, 'oper_type', 'MAINTENANCE_MGR', '产品维保管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1211, 'oper_type', 'USER_LOGIN', '用户登录', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1212, 'oper_type', 'USER_LOGOUT', '用户退出', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1213, 'oper_type', 'WAREHOUSE_MGR', '仓管', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1214, 'oper_type', 'WARRANTY_MGR', '报修受理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1215, 'oper_type', 'RENEW_MGR', '续保管理', 't_log-操作类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1216, 'oper_type', 'CUSTAPPVER_MGR', '应用版本管理', 't_log-操作类型',now(),now(),1);

/*================================================================*/
/*dict_type: 文件类型          Table:t_file                       */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1301, 'file_type','BUS_LIC_FILE', '营业执照文件','t_file-文件类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1302, 'file_type','ORG_CODE_FILE', '组织机构代码证文件','t_file-文件类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1303, 'file_type','TAX_REG_FILE','税务登记证文件','t_file-文件类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1304, 'file_type','PDT_LOGO_FILE','产品图片文件','t_file-文件类型',now(),now(),1);

/*================================================================*/
/*dict_type: 节日类型          Table:t_holiday                    */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1401, 'holiday_status', 'IS_HOLIDAY','假日','t_holiday-节日类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1402, 'holiday_status', 'IS_WEEKDAY','工作日','t_holiday-节日类型',now(),now(),1);

/*================================================================*/
/*dict_type: 模板类型          Table:t_msg_tmp                    */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1501, 'msg_tmp_type','EMAIL', '邮件','t_msg_tmp-模板类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1502, 'msg_tmp_type','SMS', '短信','t_msg_tmp-模板类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1503, 'msg_tmp_type','AGMT', '合同','t_msg_tmp-模板类型',now(),now(),1);

/*================================================================*/
/*dict_type: 系统参数类型          Table:t_sys_param              */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1601, 'sys_param_type', 'EMAIL_CONF', '邮件配置','t_sys_param-系统参数类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1602, 'sys_param_type', 'SCHEDUL_CONF', '定时器配置','t_sys_param-系统参数类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1603, 'sys_param_type', 'OTHER_CONF', '其他','t_sys_param-系统参数类型',now(),now(),1);

/*================================================================*/
/*dict_type: 权限类型          Table:t_auth                       */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1701, 'auth_type','MENU', '菜单','t_auth-权限类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1702, 'auth_type','URL', '路径','t_auth-权限类型',now(),now(),1);

/*================================================================*/
/*dict_type: 客户状态          Table:t_cust                       */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1801, 'cust_status', 'REG', '已注册','t_cust-客户状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1802, 'cust_status', 'WAIT_AUDIT', '待审核','t_cust-客户状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1803, 'cust_status', 'AUDIT_ING', '审核中','t_cust-客户状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1804, 'cust_status', 'AUDIT_PASS', '审核通过','t_cust-客户状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1805, 'cust_status', 'AUDIT_NOPASS', '审核未通过','t_cust-客户状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1806, 'cust_status', 'INVALID', '无效','t_cust-客户状态',now(),now(),1);

/*================================================================*/
/*dict_type: 客户信用级别          Table:t_cust                   */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1901, 'credit_level', 'EXCELLENT','优','t_cust-客户信用级别',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1902, 'credit_level', 'GOOD','良','t_cust-客户信用级别',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1903, 'credit_level', 'MEDIUM','中','t_cust-客户信用级别',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(1904, 'credit_level', 'BAD','差','t_cust-客户信用级别',now(),now(),1);

/*================================================================*/
/*dict_type: 信用变化原因类型        Table:t_cust_credit              */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2001, 'change_reason_type', 'TIMEOUT', '超时未付款','t_cust_credit-信用变化原因',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2002, 'change_reason_type', 'RECOVER', '定期还原信用初始值','t_cust_credit-信用变化原因',now(),now(),1);

/*================================================================*/
/*dict_type: 客户类型        Table:t_cust_reg		                */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2101, 'cust_type', 'SUPPLIER','代理商','t_cust_reg-客户类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2102, 'cust_type', 'THIRD_PARTY','第三方','t_cust_reg-客户类型',now(),now(),1);

/*================================================================*/
/*dict_type: 客户库存明细类型        Table:t_cust_stock_detail    */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2201, 'cust_stock_detail_type', 'PLACE_AN_ORDER','下订单','t_cust_stock_detail-客户库存明细类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2202, 'cust_stock_detail_type', 'PRODUCE', '生产','t_cust_stock_detail-客户库存明细类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2203, 'cust_stock_detail_type', 'SHIPMENT', '出货','t_cust_stock_detail-客户库存明细类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2204, 'cust_stock_detail_type', 'PAYMENT', '付款','t_cust_stock_detail-客户库存明细类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2205, 'cust_stock_detail_type', 'CANCEL', '撤销','t_cust_stock_detail-客户库存明细类型',now(),now(),1);

/*================================================================*/
/*dict_type: 物流公司状态        Table:t_logistics_com            */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2301, 'logistics_status', 'NO_SELECT', '无效','t_logistics_com-物流公司状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2302, 'logistics_status', 'SELECT', '有效','t_logistics_com-物流公司状态',now(),now(),1);

/*================================================================*/
/*dict_type: 物流收货状态        Table:t_logistics_tracking       */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2401, 'receving_status', 'NO_TAKE_DELIVERY','未收货','t_logistics_tracking-物流收货状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2402, 'receving_status', 'TAKE_DELIVERY','已收货','t_logistics_tracking-物流收货状态',now(),now(),1);

/*================================================================*/
/*dict_type: 订单状态        Table:t_ord                          */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2501, 'ord_status', 'WAIT_AUDIT','待审核','t_ord-订单状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2502, 'ord_status', 'HAVE_AUDIT','已审核','t_ord-订单状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2503, 'ord_status', 'REVOKED','已撤销','t_ord-订单状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version)
values(2504, 'ord_status', 'STOP','已终止','t_ord-订单状态',now(),now(),1);

/*================================================================*/
/*dict_type: 协议状态        Table:t_agmt                         */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2601, 'agmt_status', 'AGMT_SUBMIT', '协议已提交','t_agmt-协议状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2602, 'agmt_status', 'AGMT_QUOTA_CONFIRM', '待付款','t_agmt-协议状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2603, 'agmt_status', 'PAY', '已付款待审核','t_agmt-协议状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2604, 'agmt_status', 'PAY_PASS', '付款成功','t_agmt-协议状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2605, 'agmt_status', 'CONFIRM', '已生效','t_agmt-协议状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2606, 'agmt_status', 'REVOKED', '已撤销','t_agmt-协议状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2607, 'agmt_status', 'SUBMIT_CHANGE', '变更申请审核中','t_agmt-协议状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2608, 'agmt_status', 'HAVE_CHANGED', '已变更','t_agmt-协议状态',now(),now(),1);

/*================================================================*/
/*dict_type: 号段状态        Table:i_pdt_no                       */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2701, 'pdt_no_status', 'PLACE_AN_ORDER', '下单','i_pdt_no-号段状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2702, 'pdt_no_status', 'PRODUCE', '已生产','i_pdt_no-号段状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2703, 'pdt_no_status', 'SHIPMENT', '已出货','i_pdt_no-号段状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2704, 'pdt_no_status', 'BLANK', '白机','i_pdt_no-号段状态',now(),now(),1);

/*================================================================*/
/*dict_type: 支付类型        Table:t_pay_notify                   */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2801, 'pay_type', 'BAIL', '保证金','t_pay_notify-支付类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2802, 'pay_type', 'ORDER', '订单','t_pay_notify-支付类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2803, 'pay_type', 'LOGISTICS', '物流','t_pay_notify-支付类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2804, 'pay_type', 'WAREHOUSE', '仓管费','t_pay_notify-支付类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2805, 'pay_type', 'RENEW_AMT', '续保费用','t_pay_notify-支付类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2806, 'pay_type', 'BAIL_SUPPLEMENT', '保证金补交','t_pay_notify-支付类型',now(),now(),1);

/*================================================================*/
/*dict_type: 公司类型        Table:t_cust_reg                     */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(2901, 'company_type', 'LTD', '股份有限公司','t_cust_reg-公司类型',now(),now(),1);

/*================================================================*/
/*dict_type: 通知类型        Table:t_notify_cfg                   */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3001, 'notify_type', 'REG_APPLY_NOTIFY', '注册通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3002, 'notify_type', 'REG_AUDIT_NOTIFY', '客户注册审核通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3003, 'notify_type', 'AGMT_APPLY_NOTIFY', '协议提交通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3004, 'notify_type', 'AGMT_CONFIRM_NOTIFY', '协义确认通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3005, 'notify_type', 'AGMT_AUDIT_NOTIFY', '协议审核结果通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3006, 'notify_type', 'PAY_NOTIFY', '付款通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3007, 'notify_type', 'CUST_PAY_NOTIFY', '客户凭证上传通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3008, 'notify_type', 'PAY_AUDIT_NOTIFY', '付款审核通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3009, 'notify_type', 'ORD_CONFIRM_NOTIFY', '订单确认通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3010, 'notify_type', 'LOGISTICS_PAY_NOTIFY', '物流扣费通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3011, 'notify_type', 'SERIAL_CONFIRM_NOTIFY', '序号确认通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3012, 'notify_type', 'CUST_ORDER_NOTIFY', '客户点单通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3013, 'notify_type', 'DEPOSIT_PAY_NOTIFY', '保证金上传付款凭证通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3014, 'notify_type', 'CUST_LOGISTICS_NOTIFY', '客户物流单通知','t_notify_cfg-通知类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3015, 'notify_type', 'AGMT_CHANGE_AUDIT_NOTIFY', '协议变更审核结果通知','t_notify_cfg-通知类型',now(),now(),1);

/*================================================================*/
/*dict_type: 协议明细状态        Table:t_agmt_detail              */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3101, 'agmt_detail_status', 'AGMT_SUBMIT', '协议已提交','t_agmt_detail-协议明细状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3102, 'agmt_detail_status', 'AGMT_QUOTA_CONFIRM', '待付款','t_agmt_detail-协议明细状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3103, 'agmt_detail_status', 'PAY', '已付款待审核','t_agmt_detail-协议明细状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3104, 'agmt_detail_status', 'PAY_PASS', '付款成功','t_agmt_detail-协议明细状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3105, 'agmt_detail_status', 'CONFIRM', '已生效','t_agmt_detail-协议明细状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3106, 'agmt_detail_status', 'REVOKED', '已撤销','t_agmt_detail-协议明细状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3107, 'agmt_status', 'SUBMIT_CHANGE', '变更申请审核中','t_agmt-协议状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3108, 'agmt_status', 'HAVE_CHANGED', '已变更','t_agmt-协议状态',now(),now(),1);

/*================================================================*/
/*dict_type: 存量类型        Table:t_ord_detail_pdt               */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3201, 'ord_detail_pdt_type', 'DAILY_OUTPUT', '日产量','t_ord_detail_pdt-存量类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3202, 'ord_detail_pdt_type', 'STOCK', '存量','t_ord_detail_pdt-存量类型',now(),now(),1);

/*================================================================*/
/*dict_type: 注册码状态        Table:t_cust_code                  */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3301, 'cust_code_status', 'USED', '已使用','t_cust_code-注册码状态 ',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3302, 'cust_code_status', 'NO_USED', '未使用','t_cust_code-注册码状态 ',now(),now(),1);

/*================================================================*/
/*dict_type: 物流单状态        Table:t_logistics_ord              */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3401, 'logistics_ord_status', 'WAIT_AUDIT', '待审核','t_logistics_ord-物流单状态 ',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3402, 'logistics_ord_status', 'HAVE_AUDIT', '已审核','t_logistics_ord-物流单状态 ',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3403, 'logistics_ord_status', 'REVOKED', '已撤销','t_logistics_ord-物流单状态 ',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3404, 'logistics_ord_status', 'HAVE_LIBRARY', '已下销货单','t_logistics_ord-物流单状态 ',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3405, 'logistics_ord_status', 'SHIPPED', '已发货','t_logistics_ord-物流单状态 ',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3406, 'logistics_ord_status', 'PARTIAL_DELIVERY', '部分送达','t_logistics_ord-物流单状态 ',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3407, 'logistics_ord_status', 'ALL_SERVICE', '全部送达','t_logistics_ord-物流单状态 ',now(),now(),1);

/*================================================================*/
/*dict_type: 发票类型       Table:t_cust_reg                  */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3501, 'invoice_type', 'TAX_SPECIAL_INVOICE', '增值税专用发票','t_cust_reg-开票类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3502, 'invoice_type', 'TAX_INVOICE', '增值税普通发票','t_cust_reg-开票类型',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3503, 'invoice_type', 'NO_INVOICE', '无需开票','t_cust_reg-开票类型',now(),now(),1);

/*================================================================*/
/*dict_type: 产品续保状态       Table:t_renew                  */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3601, 'renew_status', 'WAIT_AUDIT', '待审核','t_renew-产品续保状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3602, 'renew_status', 'AUDIT_PASS', '已生效','t_renew-产品续保状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3603, 'renew_status', 'AUDIT_NO_PASS', '审核不通过','t_renew-产品续保状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3604, 'renew_status', 'REVOKED', '已撤销','t_renew-产品续保状态',now(),now(),1);

/*================================================================*/
/*dict_type: 产品报修状态       Table:t_warranty                  */
/*================================================================*/
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3701, 'warranty_status', 'HAVE_SUBMIT', '已提交','t_warranty-产品报修状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3702, 'warranty_status', 'HAVE_ACCEPT', '已受理','t_warranty-产品报修状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3703, 'warranty_status', 'HAVE_REPAIRED', '已修复','t_warranty-产品报修状态',now(),now(),1);
insert into t_dict(i_dict, dict_type, code, value, memo,gen_time,upd_time,version) 
values(3704, 'warranty_status', 'REVOKED', '已撤销','t_warranty-产品报修状态',now(),now(),1);
