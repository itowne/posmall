package com.newland.posmall.base.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.commons.query.criterion.Page;
import org.ohuyo.rapid.base.dao.BaseDao;
import org.springframework.stereotype.Repository;

import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.condition.TagmtCondition;

@Repository
public class TagmtDao {
	
	@Resource
	private BaseDao baseDao;
	
	public List<Tagmt> find(){
		return baseDao.getHibernateDaoEx().find(Tagmt.class);
	}
	
	public Tagmt get(Long id){
		return (Tagmt) baseDao.getSession().get(Tagmt.class, id);
	}
	
	public void save(Tagmt tagmt){
		baseDao.getSession().save(tagmt);
	}
	
	public void update(Tagmt tagmt){
		baseDao.getSession().update(tagmt);
	}
	
	public List<Tagmt> findSelect(Tagmt tagmt){		
		return baseDao.getHibernateDaoEx().findByExample(tagmt);
		
	}
	/**
	 * 根据协议id查产品list
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findPdtListByIagmt(Long id){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT a.i_agmt,b.name,b.price,a.num,a.rate,a.i_agmt_detail, (a.rate*b.price) as rateprice , a.used_quota,  a.remain_quota , a.used_quota as logisticsU,  a.remain_quota as logisticsR , b.i_pdt FROM t_agmt_detail a join t_pdt_his b on a.i_pdt_his=b.i_pdt_his where a.del_flag='N' ");
		if ( id != null) {
			sb.append("  and a.i_agmt = ? ");
			params.add( id );
		}
		return this.baseDao.getHibernateDaoEx().findBySql( sb.toString(),params.toArray());
	}
	
	/**
	 * 查询协议变更前产品明细
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List findPdtHisByIagmt(Long iagmt){
		if ( iagmt == null) return null;
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT a.i_agmt, b.name, b.price, a.num, a.rate, a.i_agmt_detail_his, "
				+ "FORMAT(a.rate*b.price, 2) as rateprice, a.used_quota, a.remain_quota, "
				+ "a.used_quota as logisticsU, a.remain_quota as logisticsR, b.i_pdt "
				+ "FROM t_agmt_detail_his a join t_pdt_his b on a.i_pdt_his=b.i_pdt_his "
				+ "where a.del_flag='N' and a.i_agmt = ? ");
		params.add( iagmt );
		return this.baseDao.getHibernateDaoEx().findBySql( sb.toString(), params.toArray());
	}
	
	@SuppressWarnings("rawtypes")
	public List findPdtLogisticsByIagmt(Long iagmt, Long ipdt){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select c.i_pdt, sum(b.used_quota), sum(b.remain_quota) from t_ord a join t_ord_detail b on a.i_ord = b.i_ord join t_pdt_his c on b.i_pdt_his = c.i_pdt_his where a.ord_status != 'REVOKED'  and   b.del_flag = 'N' and b.ord_detail_status = 'VALID'");
		if(iagmt != null){
			sb.append("  and a.i_agmt = ? ");
			params.add(iagmt);
		}
		if(ipdt != null){
			sb.append("  and c.i_pdt = ? ");
			params.add(ipdt);
		}
		sb.append(" group by  a.i_agmt, c.i_pdt ");
		return this.baseDao.getHibernateDaoEx().findBySql( sb.toString(),params.toArray());
	}
	
	
	@SuppressWarnings("rawtypes")
	public List findListByInfo(Page page, String startTime, String endTime, String agmtStatus, String icusts){
		List<Object> params = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder("select a.i_agmt,a.agmt_no,a.start_time,a.end_time,a.deposit,a.used_deposit,a.remain_deposit,a.gen_time,a.efficient_time,a.agmt_status ,a.i_cust ,b.pdts, b.sumnum , b.sumamt  as sumamt, a.agmt_no as logisticsFlag FROM  t_agmt a join (select a.i_agmt, group_CONCAT(a.i_pdt) as pdts , sum(a.num) sumnum , sum(a.num * b.price *a.rate) as sumamt from t_agmt_detail a join t_pdt_his b on a.i_pdt_his = b.i_pdt_his  where a.del_flag = 'N'  group by a.i_agmt) b on a.i_agmt = b.i_agmt where a.del_flag='N' ");
		if (StringUtils.isNotBlank(startTime)) {
			sb.append(" and a.end_time >= ?");
			params.add(startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			sb.append(" and a.start_time <= ?");
			params.add(endTime);
		}
		if ( StringUtils.isNotBlank(agmtStatus) ) {
			sb.append(" and a.agmt_status = ?"  );
			params.add(agmtStatus);
		}
		if ( StringUtils.isNotBlank(icusts) ) {
			sb.append(" and a.i_cust in ("+icusts+")"  );
		}
		
		sb.append(" order by a.i_agmt desc ");
		return this.baseDao.getHibernateDaoEx().findBySql(page, sb.toString(),
				params.toArray());
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Tagmt> findTagmtPageByCondition(Page page, TagmtCondition condition){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition, page);
	}

	@SuppressWarnings("unchecked")
	public List<Tagmt> findTagmtListByCondition(TagmtCondition condition){
		return baseDao.getHibernateDaoEx().findByCriteriaEx(condition);
	}
	
	@SuppressWarnings("rawtypes")
	public List findLogisticsAmtByTagmt(Long iagmt){
		StringBuffer sb = new StringBuffer("select a.agmt_no , sum(c.logistics_freight) , sum(d.havepay_amt) from t_agmt a  join  t_ord  b on  a.i_agmt = b.i_agmt join t_logistics_ord c on  b.i_ord = c.i_ord "
             +"join t_pay_notify d on  c.i_logistics_ord = d.i_fk  " );
		sb.append(" where d.pay_type = 'LOGISTICS' and  d.pay_notify_status != 'REVOKED' and  c.logistics_ord_status != 'REVOKED' and  b.ord_status != 'REVOKED' and  d.pay_notify_status != 'REVOKED' ");
        sb.append(" and a.i_agmt = ?");
		sb.append(" group by a.agmt_no , a.i_agmt");
		
		return this.baseDao.getHibernateDaoEx().findBySql( sb.toString(), iagmt);
	}
	
	@SuppressWarnings("rawtypes")
	public List findLogisticsListByTagmt(Page page, Long iagmt){
		StringBuffer sb = new StringBuffer("select d.pay_notify_no,  a.agmt_no , b.ord_no, c.inner_ordno , c.pay_status , c.logistics_freight , d.havepay_amt from t_agmt a  join  t_ord  b on  a.i_agmt = b.i_agmt join t_logistics_ord c on  b.i_ord = c.i_ord ");
        sb.append(" join t_pay_notify d on  c.i_logistics_ord = d.i_fk  ");
        sb.append(" where d.pay_type = 'LOGISTICS' and  d.pay_notify_status != 'REVOKED' and  c.logistics_ord_status != 'REVOKED' and  b.ord_status != 'REVOKED' and  d.pay_notify_status != 'REVOKED'");
        sb.append(" and a.i_agmt = ?");
        return this.baseDao.getHibernateDaoEx().findBySql(page, sb.toString(), iagmt);
	}
	
	@SuppressWarnings("rawtypes")
	public List findCustStockFeeCount(int intervalDay, String manualDate){
		StringBuffer sb = new StringBuffer("select i_cust, i_agmt, a.i_ord , b.i_ord_detail_pdt , b.i_pdt , DATE_FORMAT(CONCAT(b.year,'-',b.month,'-',b.day), '%Y-%m-%d')  , b.used_quota , b.remain_quota  from t_ord a join t_ord_detail_pdt b on a.i_ord = b.i_ord");
		sb.append(" where a.ord_status != 'REVOKED' and a.ord_status != 'STOP' and b.lock_type = 'YES' ");
		sb.append(" and date_add(DATE_FORMAT(CONCAT(year,'-',month,'-',day), '%Y-%m-%d') , interval "+intervalDay+" day) < DATE_FORMAT(");
		if(StringUtils.isEmpty(manualDate)){
			sb.append(" now(), '%Y-%m-%d')  and b.remain_quota != 0 ");
		}else{
			sb.append("'").append(manualDate).append("', '%Y-%m-%d')  and b.remain_quota != 0 ");
		}
		sb.append(" order by i_cust , i_agmt , i_ord , i_ord_detail_pdt");
		return this.baseDao.getHibernateDaoEx().findBySql(sb.toString());
	}
}
