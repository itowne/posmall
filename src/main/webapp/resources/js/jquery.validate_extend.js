
$(function() {
	var validator = {
		rule : ["money","deposit_rate","fax"],
		msg : [
			{ "money" : "金额精确到小数点后两位" },
			{ "deposit_rate" : "折扣率小等1精确到小数点后两位" },
			{ "fax" : "传真格式有误"}
		],
	    money : function(value, element){
			return this.optional(element) ||/^([1-9][0-9]{1,18}|[0-9]{1})(\.\d{1,2})?$/.test(value);
		},
		deposit_rate : function(value, element){
			return this.optional(element) ||/(^[0]+(\.[0-9]{0,2})?$)|(^[1]+(\.[0]{0,2})?$)/.test(value);
		},
		fax : function(value, element) {
			return this.optional(element) || /^(\d{3,4}-)?\d{7,8}$/.test(value);
		}
			
	},
	addMethod = function( validator ){
		var rule;
		for(var i = 0, l = validator.rule.length; i < l; i++) {
			rule = validator.rule[i];
			$.validator.addMethod( rule,validator[rule],validator.msg[i][rule]);
		}
	};
	addMethod(validator);
});