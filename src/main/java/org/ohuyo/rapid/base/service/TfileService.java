package org.ohuyo.rapid.base.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.ohuyo.rapid.base.dao.TfileDao;
import org.ohuyo.rapid.base.entity.Tfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 文件服务
 * 
 * @author rabbit
 *
 */
@Service
@Transactional
public class TfileService {

	@Resource
	private TfileDao tfileDao;

	public void save(Tfile tfile) {
		UUID uuid = UUID.randomUUID();
		String s = uuid.toString();
		s = s.replace("-", "");
		tfile.setUuid(s);
		tfile.setGenTime(new Date());
		tfileDao.save(tfile);
	}

	public Tfile get(String uuid) {
		return tfileDao.get(uuid);
	}
	
	public Tfile getById(Long id) {
		return tfileDao.getById(id);
	}

	public List<Tfile> getAll() {
		return this.tfileDao.getAll();
	}
	
	public Tfile getFileByObj(Tfile file){
		return this.tfileDao.getFileByObj(file);
	}
}
