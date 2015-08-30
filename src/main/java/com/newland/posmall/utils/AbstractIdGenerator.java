package com.newland.posmall.utils;



import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.utils.dao.SequenceRegisterDao;
import com.newland.posmall.utils.entity.SequenceRegister;
import com.newland.posmall.utils.formatter.Formatter;

public abstract class AbstractIdGenerator implements
		IdentifierGenerator {
	
	@Resource(name = "sequenceRegisterDao")
	protected SequenceRegisterDao sequenceRegisterDao;

	private Integer snoId = -1;

	@PostConstruct
	public void init() {
		SequenceRegister register = sequenceRegisterDao.findBySnoType(getSnoType());
		if (register == null) {
			register = new SequenceRegister();
			register.setSnoType(getSnoType());
			register.setMaxVal(0);
			sequenceRegisterDao.save(register);
		}
		snoId = register.getSnoId();
	}

	protected Integer getThenAdd(SequenceRegister register) {
		Integer seed = register.getMaxVal();
		register.setMaxVal(seed + 1);
		sequenceRegisterDao.update(register);
		return seed;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String generate() {
		sequenceRegisterDao.lock(snoId);
		SequenceRegister register = sequenceRegisterDao.findById(snoId);
		Integer seq = null;
		seq = generateSequence(register);
		if (seq > getMaxValue()) {
			seq = doWhenOutofMaxValue(register);
		}
		
		return getFormatter().format(seq);
		
		
	}

	private Integer doWhenOutofMaxValue(SequenceRegister register) {
		StrategyWhenOutofMaxValue strategy = 
			getStrategyWhenOutofMaxValue() == null?StrategyWhenOutofMaxValue.MAXVAL_TO_ZERO:getStrategyWhenOutofMaxValue();
		switch(strategy){
			case THROW_EXCEPTION:
				throw new RuntimeException("unexpected exception for the maxval of snoid:"+snoId+" out of setted maxvalue:"+getMaxValue());
			case MAXVAL_TO_ZERO:
			default:
				register.setMaxVal(0);
				return generateSequence(register);
		}
	}

	/**
	 * 定义序列号生成器在最大值溢出时的处理策略
	 * 
	 * @return
	 */
	protected abstract StrategyWhenOutofMaxValue getStrategyWhenOutofMaxValue();

	/**
	 * 算法实现，通过数据库获取的最高位<tt>SequenceRegister.maxVal</tt>，计算具体的实际流水号
	 * 
	 * @param seed
	 * @return
	 */
	protected abstract Integer generateSequence(SequenceRegister register);

	/**
	 * 定义序列号生成器的最大值
	 * 
	 * @return
	 */
	protected abstract int getMaxValue();

	/**
	 * 输出格式化器，要求是针对具体的<tt>Integer</tt>转化成<tt>String</tt>
	 * 
	 * @return
	 */
	protected abstract Formatter getFormatter();

	/**
	 * 定义流水号的类型，对应数据库中的<tt>SequenceRegister.snoType</tt>
	 * 
	 * @return
	 */
	protected abstract String getSnoType();

	
	
	/**
	 * 当最大值超过时流水号生成器策略
	 * @author lance
	 */
	public enum StrategyWhenOutofMaxValue {
		/**
		 * 抛出一个运行时异常
		 */
		THROW_EXCEPTION, 
		/**
		 * 回滚0
		 */
		MAXVAL_TO_ZERO,
	}
}
