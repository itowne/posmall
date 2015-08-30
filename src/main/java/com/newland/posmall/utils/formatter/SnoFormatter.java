package com.newland.posmall.utils.formatter;


/**
 * 流水号格式化器，只实现格式化，未实现解析
 * <p>
 * 对于输入可能导致的过长位数，一致截取低位，这可能导致高位或符号位丢失。
 * <p>
 * 对象对实际可能需要设置的高低位段，例如:<tt>${Date,8位}+sno</tt>,单独设置了2个线程变量控制。
 * 在格式化前对线程变量进行设置，将可将设置输出的追加高低位。
 * 
 * 
 * @author lance
 * 
 */
public class SnoFormatter extends Formatter {

	private int maxLength;

	private ThreadLocal<String> prefixLocal;

	private ThreadLocal<String> suffixLocal;

	public SnoFormatter(int maxLength) {
		super("Sno_Formatter");
		this.maxLength = maxLength;

		this.prefixLocal = new ThreadLocal<String>();
		this.suffixLocal = new ThreadLocal<String>();

	}

	@Override
	public String format(Object aObject) {
		if (aObject == null)
			throw new RuntimeException("object is null!");
		int value = -1;
		if (aObject instanceof Integer) {
			value = ((Integer) aObject).intValue();
		} else if (aObject instanceof Long) {
			value = ((Long) aObject).intValue();
		} else {
			throw new RuntimeException("unsupported object type!"
					+ aObject.getClass().getName());
		}
		StringBuilder sb = new StringBuilder();
		sb.append(value);

		// if((sb.length() )> maxLength){
		// return (String) sb.subSequence((sb.length() - maxLength),
		// sb.length());
		// }

		if ((sb.length()) > maxLength) {
			StringBuilder sb1 = new StringBuilder();
			sb1.append((String) sb.subSequence((sb.length() - maxLength),
					sb.length()));
			sb = sb1;
		} else {
			while (sb.length() < maxLength) {
				sb.insert(0, "0");
			}
		}
		String prefix = prefixLocal.get();
		if (prefix != null) {
			sb.insert(0, prefix);
		}

		String suffix = suffixLocal.get();
		if (suffix != null) {
			sb.append(suffix);
		}

		return sb.toString();
	}

	public void setPrefix(String prefix) {
		prefixLocal.set(prefix);
	}

	public void setSuffix(String suffix) {
		suffixLocal.set(suffix);
	}

	@Override
	public void setPattern(String pattern) {
		throw new UnsupportedOperationException("unexpected invoke!");
	}

	@Override
	public Object unformat(String aString) {
		throw new UnsupportedOperationException("unexpected invoke!");
	}

}