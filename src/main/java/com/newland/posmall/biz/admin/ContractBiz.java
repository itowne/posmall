package com.newland.posmall.biz.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.service.TcontractService;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.bean.basebusi.Tcontract;
import com.newland.posmall.bean.basebusi.condition.TcontractCondition;

@Service
@Transactional
public class ContractBiz {

	@Resource
	private TcontractService tcontractService;
	
	@Resource
	private TcustService tcustService;
	
	public Map<String, String> queryTcustCodeMap(){
		return tcustService.queryTcustCodeMap();
	}
	

	public List<Tcontract> queryAllTcontract(Tcontract tcontract,Page page) {
		
		TcontractCondition tcontractCondition = new TcontractCondition();
		tcontractCondition.setDelFlag(Boolean.FALSE);
		tcontractCondition.setCustCode(tcontract.getCustCode());
		tcontractCondition.setContractNo(tcontract.getContractNo());
		tcontractCondition.setContractName(tcontract.getContractName());
		tcontractCondition.addOrders(Order.desc("updTime"));
		List<Tcontract> tcontractList = this.tcontractService.queryTcontract(tcontractCondition,page);
		return tcontractList;
	}
	
	public Tcontract queryTcontractByIcontract(Long icontract) {
		return this.tcontractService.find(icontract);
	}
	
	public void addTcontract(Tcontract tcontract){
		this.tcontractService.save(tcontract);
	}
	
	public void modifyTcontract(Tcontract tcontract){
		
		Tcontract tcontractNew = tcontractService.find(tcontract.getIcontract());
		
		tcontractNew.setIcust(tcontract.getIcust());
		tcontractNew.setCustCode(tcontract.getCustCode());
		tcontractNew.setContractNo(tcontract.getContractNo());
		tcontractNew.setContent(tcontract.getContent());
		tcontractNew.setContractName(tcontract.getContractName());
		this.tcontractService.update(tcontractNew);
	}
	
	public void removeTcontract(Long id){
		Tcontract tcontract = this.tcontractService.find(id);
		tcontract.setDelFlag(Boolean.TRUE);
		this.tcontractService.update(tcontract);
	}
	
	public Boolean validateContractUniq(String name, Long id ){
		Boolean result = true;
		Tcontract tcontract = tcontractService.queryByName(name);
		if(id == null){
			if(tcontract != null){
				result = false;
			}
		}else{
			if(tcontract != null ){
				Tcontract t =  tcontractService.find(id);
				if(t != null && t.getContractName().equals(name)){
					result = true;
				}else{
					result = false;
				}
			}else{
				result = true;
			}
		}
		return result;
	}
	
}
