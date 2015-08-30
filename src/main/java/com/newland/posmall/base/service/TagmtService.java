package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.base.dao.TagmtDao;
import com.newland.posmall.base.dao.TagmtDepositDao;
import com.newland.posmall.base.dao.TagmtDetailDao;
import com.newland.posmall.base.dao.TagmtDetailHisDao;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDeposit;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.TagmtDetailHis;
import com.newland.posmall.bean.basebusi.condition.TagmtCondition;
import com.newland.posmall.bean.dict.AgmtDetailStatus;
import com.newland.posmall.bean.dict.AgmtStatus;
import com.newland.posmall.identifier.IdentifierService;

/**
 * 客户签订协议
 * @author zhouym
 *
 */
@Service
public class TagmtService {
	
	@Resource
	private TagmtDao tagmtDao;
	
	@Resource
	private TagmtDetailDao tagmtDetailDao;
	
	@Resource
	private TagmtDepositDao tagmtDepositDao;
	
	@Resource
	private TagmtDetailHisDao tagmtDetailHisDao;
	
	@Resource(name = "identifierService")
	private IdentifierService identifierService; 
	
	public Tagmt findTagmtById(Long id){
		return this.tagmtDao.get(id);
	}
	
	public TagmtDetail findTagmtDetailById(Long id){
		return this.tagmtDetailDao.get(id);
	}
	
	public TagmtDeposit findTagmtDepositById(Long id) {
		return this.tagmtDepositDao.get(id);
	}
	
	public TagmtDeposit findTagmtDepositByTagmtId(Long iagmt) {
		return this.tagmtDepositDao.findByiagmt(iagmt);
	}
	
	public void addTagmt(Tagmt tagmt) {
		Date date = new Date();
		tagmt.setAgmtNo(identifierService.genArgId());
		tagmt.setGenTime(date);
		tagmt.setUpdTime(date);
		tagmt.setDelFlag(Boolean.FALSE);
		this.tagmtDao.save(tagmt);
	}
	
	/**
	 * 修改 协议 的 保证金变化(已用和剩余)
	 * @param tagmt
	 */
	public void modifyTamgtDeposit(Tagmt tagmt){
		tagmt.setUpdTime(new Date());
		this.tagmtDao.update(tagmt);
		
		//添加 保证金历史表
		TagmtDeposit tagmtDeposit = new TagmtDeposit();
		tagmtDeposit.setIagmt(tagmt.getIagmt());
		tagmtDeposit.setAgmtNo(tagmt.getAgmtNo());
		tagmtDeposit.setDeposit(tagmt.getDeposit());
		tagmtDeposit.setGenTime(new Date());
		tagmtDeposit.setIcust(tagmt.getIcust());
		tagmtDeposit.setRemainDeposit(tagmt.getRemainDeposit());
		tagmtDeposit.setUsedDeposit(tagmt.getUsedDeposit());
		tagmtDeposit.setVer(tagmt.getVersion());
		tagmtDepositDao.save(tagmtDeposit);
	}
	
	
	/**
	 * 确认额度， 更新额度 保证金
	 */
	public void confirmQuota(Tagmt tagmt, List<TagmtDetail> list){
			TagmtDetail tagmtDetail = null;
		 if(list != null && list.size() > 0){
			 //更新 协议明细
			 for(TagmtDetail t : list){
				 tagmtDetail = tagmtDetailDao.get(t.getIagmtDetail());
				 tagmtDetail.setNum(t.getNum());
				 tagmtDetail.setRate(t.getRate());
				 tagmtDetail.setRemainQuota(t.getNum());
				 if(t.getNum() == 0){
					 //如果确认为0, 则删除该详细
					 tagmtDetail.setDelFlag(Boolean.TRUE);
				 }
				 tagmtDetailDao.update(tagmtDetail);
			 }
			 //添加 协议保证金历史表
			 TagmtDeposit tagmtDeposit = new TagmtDeposit();
			 tagmtDeposit.setIagmt(tagmt.getIagmt());
			 tagmtDeposit.setAgmtNo(tagmt.getAgmtNo());
			 tagmtDeposit.setDeposit(tagmt.getDeposit());
			 tagmtDeposit.setGenTime(new Date());
			 tagmtDeposit.setIcust(tagmt.getIcust());
			 tagmtDeposit.setRemainDeposit(tagmt.getRemainDeposit());
			 tagmtDeposit.setUsedDeposit(tagmt.getUsedDeposit());
			 tagmtDeposit.setVer(tagmt.getVersion());
			 tagmtDepositDao.save(tagmtDeposit);
		 }
		
	}
	
	
	public void addTagmtDetail(TagmtDetail tagmtDetail) {
		Date date = new Date();
		tagmtDetail.setGenTime(date);
		tagmtDetail.setUpdTime(date);
		tagmtDetail.setDelFlag(Boolean.FALSE);
		this.tagmtDetailDao.save(tagmtDetail);
	}
	
	public void addTagmtDetailHis(TagmtDetailHis tagmtDetailHis) {
		tagmtDetailHis.setGenTime(new Date());
		tagmtDetailHis.setDelFlag(Boolean.FALSE);
		this.tagmtDetailHisDao.save(tagmtDetailHis);
	}
	
	public void addTagmtDeposit(TagmtDeposit tagmtDeposit) {
		tagmtDeposit.setGenTime(new Date());
		this.tagmtDepositDao.save(tagmtDeposit);
	}
	
	public List<Tagmt> findAllTagmt() {
		return this.tagmtDao.find();
	}
	
	public List<Tagmt> findTagmtBySelect(Tagmt tagmt) {
		return this.tagmtDao.findSelect(tagmt);
	}
	
	public List<TagmtDetail> findTagmtDetailBySelect(TagmtDetail tagmtDetail) {
		tagmtDetail.setDelFlag(Boolean.FALSE);
		return this.tagmtDetailDao.find(tagmtDetail);
	}
	
	public List<Tagmt> findTagmtPageByCondition(Page page, TagmtCondition conditon) {
		return this.tagmtDao.findTagmtPageByCondition(page, conditon);
	}
	
	public List<Tagmt> findTagmtListByCondition(TagmtCondition conditon) {
		return this.tagmtDao.findTagmtListByCondition(conditon);
	}
	
	public Tagmt findById(Long id){
		return this.tagmtDao.get(id);		
	}
	
	/**
	 * 根据协议id查产品list
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public List findPdtByIagmt(Long id) {		
		return this.tagmtDao.findPdtListByIagmt(id);		
	}
	
	/**
	 * 查询协议变更前产品明细
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findPdtHisByIagmt(Long iagmt) {
		return this.tagmtDao.findPdtHisByIagmt(iagmt);
	}

	/**
	 * 条件查询
	 * @param startTime
	 * @param endTime
	 * @param agmtStatus
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String startTime, String endTime, String agmtStatus, String icusts) {

		return this.tagmtDao.findListByInfo(page, startTime, endTime, agmtStatus, icusts);
	}
	
	
	/**
	 * 审核协议
	 * @param tagmt
	 * @param agmtStatus
	 */
	public void checkTagmt(Tagmt tagmt,AgmtStatus agmtStatus){		
		tagmt.setUpdTime(new Date());
		tagmt.setAgmtStatus(agmtStatus);
		
		this.tagmtDao.update(tagmt);		
	}
	
	/**
	 * 协议明细更新
	 * @param tagmt
	 * @param agmtStatus
	 */
	public void checkTagmtDetail(TagmtDetail tagmtDetail,AgmtDetailStatus agmtDetailStatus){		
		TagmtDetail tagmtDetailNew = this.tagmtDetailDao.get(tagmtDetail.getIagmtDetail());		
		tagmtDetailNew.setUpdTime(new Date());
		tagmtDetailNew.setAgmtDetailStatus(agmtDetailStatus);
		
		this.tagmtDetailDao.update(tagmtDetailNew);		
	}
	
	/**
	 * 根据协议主表ID查询 明细信息
	 * @param iagmt
	 * @return
	 */
	public List<TagmtDetail> findTagmtDetial(Tagmt agmt) {
		return this.tagmtDetailDao.findByIagmt(agmt.getIagmt());
	}
	
	public List<TagmtDetailHis> findTagmtDetailHis(TagmtDetailHis condition) {
		return this.tagmtDetailHisDao.find(condition);
	}
	
	/**
	 * 撤销协议
	 * @param tpdt
	 */
	public void delete(Tagmt agmt){
		agmt.setUpdTime(new Date());
//		agmt.setDelFlag(Boolean.TRUE);
		agmt.setAgmtStatus(AgmtStatus.REVOKED);
		this.tagmtDao.update(agmt);
	}
	
	public void deleteTagmtDetail(TagmtDetail tagmtDetail){
		tagmtDetail.setUpdTime(new Date());
		tagmtDetail.setDelFlag(Boolean.TRUE);
		this.tagmtDetailDao.update(tagmtDetail);
	}
	
	public void deleteTagmtDetailHis(TagmtDetailHis tagmtDetailHis){
		tagmtDetailHis.setDelFlag(Boolean.TRUE);
		this.tagmtDetailHisDao.update(tagmtDetailHis);
	}
	
	public void modifyTagmtDetail(TagmtDetail tagmtDetail){
		tagmtDetail.setUpdTime(new Date());
		this.tagmtDetailDao.update(tagmtDetail);
	}
	
	public void modifyTagmt(Tagmt tagmt) {
		tagmt.setUpdTime(new Date());
		this.tagmtDao.update(tagmt);
	}
	/**
	 * 查询协议下产品的 出货数量与剩余数量
	 * @param iagmt
	 * @param ipdt
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object findPdtLogisticsByIagmt(Long iagmt, Long ipdt){
		List list = this.tagmtDao.findPdtLogisticsByIagmt(iagmt, ipdt);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	/**
	 * 查询协议下的 物流费用
	 * @param iagmt
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Object findLogisticsAmtByTagmt(Long iagmt){
		List list = this.tagmtDao.findLogisticsAmtByTagmt(iagmt);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
	
	@SuppressWarnings("rawtypes")
	public List findLogisticsListByTagmt(Page page, Long iagmt){
		return this.tagmtDao.findLogisticsListByTagmt(page, iagmt);
	}
	
	@SuppressWarnings("rawtypes")
	public List findCustStockFeeCount(int intervalDay, String manualDate){
		return this.tagmtDao.findCustStockFeeCount(intervalDay, manualDate);
	}
	
}
