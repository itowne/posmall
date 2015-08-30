package com.newland.posmall.utils;

import com.newland.posmall.utils.entity.SequenceRegister;

/**
 * 基于高低位算法的流水号生成器
 * <p>
 * 
 * @author lance
 */
public abstract class HiLoIdGenerator extends AbstractIdGenerator {

	private final int maxLo = 100;
	private  int hi;
	private  int lo;

	public HiLoIdGenerator() {
		lo = maxLo + 1;
	}

	@Override
	protected synchronized Integer generateSequence(SequenceRegister register) {
		if (lo >= maxLo) {
			lo = 0;
			hi = (getThenAdd(register) * maxLo) + 1;
		}
		return hi + lo++;
	}

}
