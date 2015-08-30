package com.newland.posmall.utils.dao;

import com.newland.posmall.utils.entity.SequenceRegister;



public interface SequenceRegisterDao {
	
	public 	void lock(Integer snoId);
	
	public SequenceRegister findBySnoType(String snoType);

	public void save(SequenceRegister register);

	public void update(SequenceRegister register);

	public SequenceRegister findById(Integer snoId);
	
	
}
