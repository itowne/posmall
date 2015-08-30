package com.newland.posmall.base.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.dao.ProvinceDao;
import com.newland.posmall.bean.common.Province;

@Service
@Transactional
public class ProvinceService {

	@Resource
	private ProvinceDao provinceDao;

	@Cacheable(value = "provinceCache", key = "'provinceMap'")
	public Map<String, String> getProvinceMap() {
		List<Province> list = this.provinceDao.queryAll();
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (list != null && list.size() >= 1 && list.get(0) != null) {
			for (Province pro : list) {
				map.put(pro.getProvCode(), pro.getName());
			}
		}
		return map;
	}

	public Map<String, String> getProvince() {
		return getProvince("000000");
	}

	public Map<String, String> getProvince(String code) {
		List<Province> list = this.provinceDao.findByPreProvCode(code);
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (list != null && list.size() >= 1 && list.get(0) != null) {
			//map.put("1", "全国");
			for (Province pro : list) {
				map.put(pro.getProvCode(), pro.getName());
			}
		}
		return map;
	}

	public List<Province> getProvince2(String provCode) {
		return this.provinceDao.findByPreProvCode(provCode);
	}


	public List<Province> getProvinceAndCityName(String provCode) {
		List<Province> provinceList = new ArrayList<Province>();
		Province city = provinceDao.queryByCityCode(provCode);
		provinceList.add(city);
		while (city != null
				&& !StringUtils.equals("000000", city.getPreProvCode())) {
			city = provinceDao.queryByCityCode(city.getPreProvCode());
			if(city!=null){
				provinceList.add(city);
			}
		}
		return provinceList;
	}

	public Province queryByProvCode(String provCode) {
		return provinceDao.queryByCityCode(provCode);
	}
	
	public List<Province> queryAllProvince() {
		return this.provinceDao.queryAll();
	}
}
