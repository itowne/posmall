package com.newland.posmall.biz.cust;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.exception.BizException;
import com.newland.posmall.base.service.ProvinceService;
import com.newland.posmall.base.service.TaddrService;
import com.newland.posmall.bean.customer.Taddr;
import com.newland.posmall.bean.customer.TaddrHis;
import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.condition.TaddrCondition;

@Service
@Transactional
public class AddrBiz {
	
	@Resource
	private TaddrService addrService;
	
	@Resource
	private ProvinceService provinceService;
	
	public Taddr queryTaddrById(Long iaddr) {
		if(iaddr == null) {
			return null;
		}
		return this.addrService.findById(iaddr);
	}
	
	/**
	 * 
	 * @param page
	 * @param tcust
	 * @param name
	 * @return
	 */
	public List<Taddr> queryTaddrPageByCondition(Page page, Tcust tcust, String name) {
		if(tcust == null || tcust.getIcust() == null) {
			return null;
		}
		TaddrCondition condition = new TaddrCondition();
		condition.setIcust(tcust.getIcust());
		if(StringUtils.isNotBlank(name)) {
			condition.setName(name);
		}
		condition.setDelFlag(Boolean.FALSE);
		condition.addOrders(Order.desc("updTime"));
		return this.addrService.findPageByCondition(page, condition);
	}
	
	public void addTaddr(Tcust tcust, Taddr taddr) throws BizException {
		if(tcust == null || tcust.getIcust() == null) {
			throw new BizException(0, "用户数据异常！");
		}
		if(taddr == null) {
			throw new BizException(0, "数据为空！");
		}
		taddr.setIcust(tcust.getIcust());
		Map<String, String> map = this.provinceService.getProvinceMap();
		this.setResultOfTaddr(taddr, map);
		this.addrService.save(taddr);
		
		this.addTaddrHis(taddr);
	}
	
	public void modifyTaddr(Tcust tcust, Taddr taddr) throws BizException {
		if(tcust == null || tcust.getIcust() == null) {
			throw new BizException(0, "用户数据异常！");
		}
		if(taddr == null || taddr.getIaddr() == null) {
			throw new BizException(0, "数据为空！");
		}
		Taddr taddrDB = this.addrService.findById(taddr.getIaddr());
		if(taddrDB == null) {
			throw new BizException(0, "数据为空！");
		}
		taddrDB.setArea(taddr.getArea());
		taddrDB.setCity(taddr.getCity());
		taddrDB.setMobile(taddr.getMobile());
		taddrDB.setName(taddr.getName());
		taddrDB.setProvince(taddr.getProvince());
		taddrDB.setTel(taddr.getTel());
		taddrDB.setPostalCode(taddr.getPostalCode());
		taddrDB.setCounty(taddr.getCounty());
		taddrDB.setIcust(tcust.getIcust());
		Map<String, String> map = this.provinceService.getProvinceMap();
		this.setResultOfTaddr(taddrDB, map);
		this.addrService.update(taddrDB);
		
		this.addTaddrHis(taddrDB);
	}
	
	/**
	 * 同步添加一条收货地址历史记录
	 * @param taddr
	 */
	private void addTaddrHis(Taddr taddr) {
		if(taddr == null) {
			return;
		}
		TaddrHis taddrHis = new TaddrHis();
		taddrHis.setArea(taddr.getArea());
		taddrHis.setCity(taddr.getCity());
		taddrHis.setCounty(taddr.getCounty());
		taddrHis.setIaddr(taddr.getIaddr());
		taddrHis.setIcust(taddr.getIcust());
		taddrHis.setMobile(taddr.getMobile());
		taddrHis.setName(taddr.getName());
		taddrHis.setPostalCode(taddr.getPostalCode());
		taddrHis.setProvince(taddr.getProvince());
		taddrHis.setTel(taddr.getTel());
		taddrHis.setVer(taddr.getVersion());
		taddrHis.setResult(taddr.getResult());
		this.addrService.saveTaddrHis(taddrHis);
	}
	
	public void deleteTaddr(Long id) {
		if(id == null) {
			return;
		}
		this.addrService.delete(this.addrService.findById(id));
	}
	
	/**
	 * 将地址拼装成字符串
	 * @param taddr
	 * @param provinceMap
	 */
	private void setResultOfTaddr(Taddr taddr, Map<String, String> provinceMap) {
		if(taddr == null) {
			return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(((provinceMap.get(taddr.getProvince()) == null) ? "" : provinceMap.get(taddr.getProvince())) + " ");
		sb.append(((provinceMap.get(taddr.getCity()) == null) ? "" : provinceMap.get(taddr.getCity())) + " ");
		sb.append(((provinceMap.get(taddr.getCounty()) == null) ? "" : provinceMap.get(taddr.getCounty())) + " ");
		sb.append(((taddr.getArea() == null) ? "" : taddr.getArea()) + "  ");
		taddr.setResult(sb.toString());
	}
}
