function passwordSalt(length) {
	var ret = "";
	for (var i = 0; i < length; i++) {
		ret = ret + Math.floor(Math.random() * 10);
	}
	return ret;
}

function passwordEncrypt(a) {
	var e = '010001';
	var n = '008DAD38E3D567F17A6BD252CBE1642EB72C3A27087A061447892C1ACF4069D6F282465289F97890E475E3360CEF920BFEDE701783320DA2FA6DAE58744CD144646E9CA95F3C02E7CDE67B62E74B68015875DB7F7DDBF96060C3E081BB62CA2B229EAFAC0328AED4BC29BD95032FECAD856C700169EDC36C4653418DFBF0F66585';
	var random_num = '';
	var rsa = new RSAKey();
	rsa.setPublic(n, e);
	a = passwordSalt(10) + a;
	return rsa.encrypt(a);
}
