package com.newland.posmall.utils.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.utils.dao.SequenceRegisterDao;
import com.newland.posmall.utils.entity.SequenceRegister;

@Repository("sequenceRegisterDao")
@Transactional(readOnly = false)
public class SequenceRegisterDaoImpl extends BaseDaoImpl<SequenceRegister> implements SequenceRegisterDao{



	@SuppressWarnings("unchecked")
	@Override
	public SequenceRegister findBySnoType(String snoType) {
		List<SequenceRegister>  l = this.getHibernateDaoEx().find("FROM " 
				+ SequenceRegister.class.getName() +
				" where sno_type = ?", new Object[]{snoType});
		if(l == null || l.size()<=0)
			return null;
		
		return l.get(0); 
	}

	@Override
	public void lock(Integer snoId) {
		this.getHibernateDaoEx().executeUpdate("update " 
				+ SequenceRegister.class.getName() +
				" r set r.maxVal = r.maxVal where r.snoId = ?",snoId);
	}

	@Override
	public void save(SequenceRegister register) {
		this.getSession().save(register);
	}

	@Override
	public void update(SequenceRegister register) {
		this.getSession().update(register);
		
	}

	@Override
	public SequenceRegister findById(Integer snoId) {
		
		return (SequenceRegister)this.getHibernateDaoEx().getFirst("from " + SequenceRegister.class.getName() + 
				" seq where seq.snoId = ?", snoId);
	}




	
	

}
