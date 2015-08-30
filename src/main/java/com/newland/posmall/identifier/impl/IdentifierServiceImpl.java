package com.newland.posmall.identifier.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.newland.posmall.identifier.IdentifierService;
import com.newland.posmall.utils.IdentifierGenerator;

@Service("identifierService")
public class IdentifierServiceImpl implements IdentifierService {

	@Resource(name = "agrIdGenerator")
	private IdentifierGenerator agrIdGenerator;

	@Resource (name = "custIdGenerator")
	private IdentifierGenerator custIdGenerator;

	@Resource(name = "agrIdGenerator")
	private IdentifierGenerator flowGenerator;
	
	@Resource (name = "interflowIdGenerator")
	private IdentifierGenerator interflowIdGenerator;

	@Resource(name = "monIdGenerator")
	private IdentifierGenerator monIdGenerator;

	@Resource(name = "notifyIdGenerator")
	private IdentifierGenerator notifyIdGenerator;

	@Resource(name = "orderIdGenerator")
	private IdentifierGenerator orderIdGenerator;

	@Resource(name = "payIdGenerator")
	private IdentifierGenerator payIdGenerator;

	@Resource(name = "fileIdGenerator")
	private IdentifierGenerator fileIdGenerator;

	@Resource(name="batchGenerator")
	private IdentifierGenerator BatchGenerator; 
	
	@Resource(name="contractIdGenerator")
	private IdentifierGenerator contractIdGenerator; 

	@Override
	public String genCustId() {
		return this.custIdGenerator.generate();
	}

	@Override
	public String genArgId() {
		return this.agrIdGenerator.generate();
	}

	@Override
	public String genMonthId() {
		return this.monIdGenerator.generate();
	}

	@Override
	public String genInterFlowId() {
		return this.interflowIdGenerator.generate();
	}

	@Override
	public String genOrderId() {
		return this.orderIdGenerator.generate();
	}

	@Override
	public String genPayId() {
		return this.payIdGenerator.generate();
	}

	@Override
	public String genNotifyId() {
		return this.notifyIdGenerator.generate();
	}

	@Override
	public String getFileId() {
		return this.fileIdGenerator.generate();
	}

	@Override
	public String genBatchId() {
		return this.BatchGenerator.generate();
	}
	
	@Override
	public String genContractId(){
		return this.contractIdGenerator.generate();
	}

}
