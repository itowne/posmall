package com.newland.posmall.identifier.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.utils.HiLoIdGenerator;
import com.newland.posmall.utils.IdentifierGenerator;
import com.newland.posmall.utils.formatter.Formatter;
import com.newland.posmall.utils.formatter.SnoFormatter;

/**
 * 系统流水号生成器
 * 
 * @author lance
 */
@Component("orderIdGenerator")
public class OrderIdGenerator extends HiLoIdGenerator implements
		IdentifierGenerator {

	private static final String IDGEN_NAME = "ORDER_ID";

	private static final int MAXLENGTH = 4;

	private SnoFormatter snoFormatter;

	protected int maxValue = -1;
	
	private static final String PREFIX = "DD";

	public OrderIdGenerator() {
		this(MAXLENGTH);
	}

	protected OrderIdGenerator(int maxLength) {
		maxValue = (int) Math.pow(10, maxLength) - 1;
		if (maxLength <= 0 || this.maxValue <= 0)
			throw new RuntimeException("failed to init idgen of "
					+ getSnoType() + " for maxLength:" + maxLength
					+ ",maxValue:" + maxValue);

		snoFormatter = new SnoFormatter(maxLength);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String generate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		snoFormatter.setPrefix(sdf.format(date));
		return PREFIX + super.generate();
	}

	@Override
	protected Formatter getFormatter() {
		return snoFormatter;
	}

	@Override
	public String getSnoType() {
		return IDGEN_NAME;
	}

	@Override
	public int getMaxValue() {
		return maxValue;
	}

	@Override
	protected StrategyWhenOutofMaxValue getStrategyWhenOutofMaxValue() {
		return StrategyWhenOutofMaxValue.MAXVAL_TO_ZERO;
	}

}
