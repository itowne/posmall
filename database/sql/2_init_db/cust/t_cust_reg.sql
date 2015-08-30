delete from t_cust_reg;

insert into t_cust_reg(i_cust,cust_no,name,long_name,upd_time,gen_time,version,email,account,bank,contract_no,signature_date,invoice_corporation,invoice_type,tax_id,reg_tel,reg_addr) values 
(11,'10011','cust','cust11',now(),now(),1,'ypos@newlandpayment.com','1011','中国银行', "CN10011", now(), '新大陆', 'TAX_SPECIAL_INVOICE', 'xxx', '80000', '福州');