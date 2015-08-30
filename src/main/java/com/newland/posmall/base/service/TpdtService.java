package com.newland.posmall.base.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.ohuyo.commons.query.criterion.Page;
import org.springframework.stereotype.Service;

import com.newland.posmall.base.dao.TpdtDao;
import com.newland.posmall.base.dao.TpdtHisDao;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.basebusi.condition.TpdtCondition;

@Service
public class TpdtService {

	@Resource
	private TpdtDao tpdtDao;

	@Resource
	private TpdtHisDao tpdtHisDao;

	public void save(Tpdt tpdt) {
		Date date = new Date();
		tpdt.setGenTime(date);
		tpdt.setUpdTime(date);
		tpdt.setDelFlag(Boolean.FALSE);
		this.tpdtDao.save(tpdt);

		TpdtHis tpdtHis = new TpdtHis();
		tpdtHis.setIpdt(tpdt.getIpdt());
		tpdtHis.setPdtNo(tpdt.getPdtNo());
		tpdtHis.setIuser(tpdt.getIuser());
		tpdtHis.setName(tpdt.getName());
		tpdtHis.setLongName(tpdt.getLongName());
		tpdtHis.setPreIpdtHis(tpdt.getIpdtHis());
		tpdtHis.setMemo(tpdt.getMemo());
		tpdtHis.setPrice(tpdt.getPrice());
		tpdtHis.setUserName(tpdt.getUserName());
		tpdtHis.setGenTime(date);
		tpdtHis.setVer((tpdt.getVersion() == null) ? 0
				: (tpdt.getVersion() + 1));
		tpdtHis.setInoSegCfg(tpdt.getInoSegCfg());
		this.tpdtHisDao.save(tpdtHis);

		tpdt.setIpdtHis(tpdtHis.getIpdtHis());
		this.tpdtDao.update(tpdt);

	}

	public void update(Tpdt tpdtNew) {
		Date date = new Date();

		tpdtNew.setUpdTime(date);
		this.tpdtDao.update(tpdtNew);

		TpdtHis tpdtHis = new TpdtHis();
		tpdtHis.setIpdt(tpdtNew.getIpdt());
		tpdtHis.setIuser(tpdtNew.getIuser());
		tpdtHis.setPdtNo(tpdtNew.getPdtNo());
		tpdtHis.setName(tpdtNew.getName());
		tpdtHis.setLongName(tpdtNew.getLongName());
		tpdtHis.setPreIpdtHis(tpdtNew.getIpdtHis());
		tpdtHis.setMemo(tpdtNew.getMemo());
		tpdtHis.setPrice(tpdtNew.getPrice());
		tpdtHis.setUserName(tpdtNew.getUserName());
		tpdtHis.setGenTime(date);
		tpdtHis.setVer((tpdtNew.getVersion() == null) ? 0 : (tpdtNew
				.getVersion() + 1));
		tpdtHis.setInoSegCfg(tpdtNew.getInoSegCfg());
		this.tpdtHisDao.save(tpdtHis);

		tpdtNew.setIpdtHis(tpdtHis.getIpdtHis());
		this.tpdtDao.update(tpdtNew);

	}

	public void delete(Tpdt tpdt) {
		tpdt.setUpdTime(new Date());
		tpdt.setDelFlag(Boolean.TRUE);
		this.tpdtDao.update(tpdt);
	}

	public Tpdt find(Long id) {
		return this.tpdtDao.get(id);

	}

	public TpdtHis findTpdtHisById(Long id) {
		return this.tpdtHisDao.get(id);

	}

	/**
	 * 查询 产品历史列表
	 * 
	 * @return
	 */
	public List<TpdtHis> findTpdtHis(Long id) {
		return this.tpdtHisDao.findByIpdt(id);
	}

	public List<Tpdt> findBySelect(Tpdt tpdt) {
		return this.tpdtDao.findSelect(tpdt);
	}

	public List<TpdtHis> findTpdtHisBySelect(TpdtHis tpdtHis) {
		return this.tpdtHisDao.findSelect(tpdtHis);
	}

	public List<Tpdt> queryTpdt(TpdtCondition tpdtCondition, Page page) {
		return this.tpdtDao.findCondition(tpdtCondition, page);
	}

	/**
	 * 所有产品(包括已删除产品)
	 */
	public Map<String, String> queryTpdtMapAll() {
		Map<String, String> map = null;
		Tpdt tpdt = new Tpdt();
//		tpdt.setDelFlag(Boolean.FALSE);
		List<Tpdt> tpdtList = findBySelect(tpdt);
		if (tpdtList != null) {
			map = new TreeMap<String, String>();
			for (Tpdt t : tpdtList) {
				map.put("" + t.getIpdt(), t.getName());
			}
		}
		return map;
	}
	
	/**
	 * 有效
	 * @return
	 */
	public Map<String, String> queryTpdtMap() {
		Map<String, String> map = null;
		Tpdt tpdt = new Tpdt();
		tpdt.setDelFlag(Boolean.FALSE);
		List<Tpdt> tpdtList = findBySelect(tpdt);
		if (tpdtList != null) {
			map = new TreeMap<String, String>();
			for (Tpdt t : tpdtList) {
				map.put("" + t.getIpdt(), t.getName());
			}
		}
		return map;
	}

	public Map<String, String> queryTpdtHisMap() {
		Map<String, String> map = null;
		TpdtHis his = new TpdtHis();
		List<TpdtHis> list = findTpdtHisBySelect(his);
		if (list != null) {
			map = new TreeMap<String, String>();
			for (TpdtHis t : list) {
				map.put("" + t.getIpdt(), t.getName());
			}
		}
		return map;
	}

	/**
	 * 所有 有效状态的产品
	 * 
	 * @return
	 */
	public List<Tpdt> findAll() {
		Tpdt tpdt = new Tpdt();
		tpdt.setDelFlag(Boolean.FALSE);
		return this.tpdtDao.findSelect(tpdt);
	}

	public Tpdt queryObjByIpdt(Tpdt pdt) {
		return tpdtDao.queryObjByIpdt(pdt);
	}

}
