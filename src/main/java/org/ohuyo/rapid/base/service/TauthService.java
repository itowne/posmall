package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.dao.TauthDao;
import org.ohuyo.rapid.base.dao.TuserroleDao;
import org.ohuyo.rapid.base.dist.AuthType;
import org.ohuyo.rapid.base.entity.Tauth;
import org.ohuyo.rapid.base.entity.Tuserrole;
import org.springframework.stereotype.Service;

/**
 * 
 * @author rabbit
 *
 */
@Service
public class TauthService {

	@Resource
	private TauthDao tauthDao;

	@Resource
	private TuserroleDao troleDao;

	public void save(Tauth tauth) {
		Date date = new Date();
		tauth.setGenTime(date);
		tauth.setUpdTime(date);
		this.tauthDao.save(tauth);
	}

	public void update(Tauth tauth) {
		Date date = new Date();
		Tauth tauthNew = tauthDao.get(tauth.getIauth());
		tauthNew.setUpdTime(date);
		this.tauthDao.update(tauthNew);
	}

	public List<Tauth> find() {
		List<Tauth> auths = this.tauthDao.find();
		return auths;
	}



	public void xx(List<Tauth> auths, Map<String, Tauth> urlMap,
			Map<String, Tauth> codeMap, Map<String, Tauth> menuMap) {
		for (Tauth tauth : auths) {
			if (urlMap != null) {
				String url = tauth.getUrl();
				if (StringUtils.isNotBlank(url)) {
					url = url.trim();
					if (!StringUtils.equals(url, "#")) {
						urlMap.put(url, tauth);
					}
				}
			}
			if (codeMap != null) {
				String code = tauth.getCode();
				codeMap.put(code, tauth);
			}
			if (menuMap != null) {
				if (AuthType.MENU == tauth.getAuthType()) {
//					menuMap.put(code, tauth);
				}
			}
		}
	}

	public void findByIuser(Long iuser) {
		List<Tuserrole> roles = troleDao.findByIuser(iuser);
		List<Tauth> list = tauthDao.findByTroles(roles);

	}

	public Tauth find(Long id) {
		return this.tauthDao.get(id);
	}

}
