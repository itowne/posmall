/**
 * 表单校验
 */
(function($){
	if ($.validator) {
		$.validator.addMethod("float", function(value, element) {
			return this.optional(element) || /^\d+(\.\d+)?$/.test(value);
		}, "请输入合法的数字");
	}

})(jQuery);