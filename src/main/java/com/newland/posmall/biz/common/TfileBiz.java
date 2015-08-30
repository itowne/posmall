package com.newland.posmall.biz.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.ohuyo.rapid.base.entity.Tfile;
import org.ohuyo.rapid.base.service.TfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("posmall.TfileBiz")
@Transactional
public class TfileBiz {

	@Resource
	private TfileService tfileService;

	public Tfile queryTfileById(Long id) {
		if (id == null) {
			return null;
		}
		return this.tfileService.getById(id);
	}

	public void save(Tfile file) {
		this.tfileService.save(file);
	}

	public Tfile getFileByObj(Tfile file) {
		return tfileService.getFileByObj(file);
	}

	public Tfile getTfileByFile(File file, String fileExt,String type) throws IOException {
		Tfile tfile = new Tfile();
		tfile.setExt(fileExt);
		tfile.setGenTime(new Date());
		tfile.setLength(file.length());
		tfile.setName(file.getName());
		tfile.setContentType(type);
		tfile.setLocation(file.getPath());
		FileInputStream in = new FileInputStream(file);
		tfile.setContent(IOUtils.toByteArray(in));
		tfile.setSha(Base64.encodeBase64String(DigestUtils.sha1(in)));
		return tfile;
	}
}
