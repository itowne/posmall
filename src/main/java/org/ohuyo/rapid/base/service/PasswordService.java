package org.ohuyo.rapid.base.service;

import javax.annotation.Resource;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.ohuyo.rapid.base.security.RsaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 密码服务类
 * 
 * @author rabbit
 *
 */
@Service
public class PasswordService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private RsaService rsaService;

	/**
	 * 盐值偏移量
	 */
	private int offset = 10;

	public String clearText2Base64String(String plaintext) {
		byte[] buf = plaintext.getBytes();
		return Base64.encodeBase64String(buf);
	}

	public String decrypt(byte[] data) {
		byte[] a = rsaService.decrypt(data);
		byte[] b = ArrayUtils.subarray(a, offset, a.length);
		byte[] c = DigestUtils.sha1(b);
		return Base64.encodeBase64String(c);
	}

//	public static void main(String[] args) {
//		byte[] sha = DigestUtils.sha1("123456");
//		System.out.println(Base64.encodeBase64String(sha));
//	}

	public String decrypt(String hexPlaintext) throws DecoderException {
		byte[] a = Hex.decodeHex(hexPlaintext.toCharArray());
		return decrypt(a);
	}
	
	/**
	 * 生成加密密码（默认密码：123456）
	 * @param origal
	 * @return
	 */
	public String getDefaultDecryptPassword() {
		byte[] sha = DigestUtils.sha1("123456");
		return Base64.encodeBase64String(sha);
	}
}
