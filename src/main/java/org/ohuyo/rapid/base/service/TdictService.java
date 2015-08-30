package org.ohuyo.rapid.base.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.TdictDao;
import org.ohuyo.rapid.base.entity.Tdict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("posmall.tdictService")
@Transactional
public class TdictService {

	@Resource
	private TdictDao tdictDao;

	public List<Tdict> list(String dictType) {
		return tdictDao.list(dictType);
	}

	public Map<String, String> dictMap(String dictType) {
		Map<String, String> map = new LinkedHashMap<String, String>();

		List<Tdict> list = list(dictType);
		for (Tdict dict : list) {
			map.put(dict.getCode(), dict.getValue());
		}
		return map;
	}

}
