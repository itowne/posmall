package org.ohuyo.rapid.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.newland.posmall.Application;

public class CsvFileTranslator {

	private String delimiter;
	
	Logger logger = LoggerFactory.getLogger(getClass());

	public CsvFileTranslator(String delimiter) {
		this.delimiter = delimiter;
	}

	public File fromObject(List<?> list, File file, String charset) {
		if (CollectionUtils.isEmpty(list))
			throw new RuntimeException("列表为空");
		OutputStreamWriter fos = null;
		try {
			 fos = new OutputStreamWriter(new FileOutputStream(file), StringUtils.isBlank(charset)?"UTF-8":charset);
			CsvWriter writer = new CsvWriter(fos, delimiter.charAt(0));
			int idx = 0;
			for (Object obj : list) {
				ObjectAware reader = new ObjectAware(obj);
				Map<String, Object> map = reader.getFieldMap();
				List<String> strs = new ArrayList<String>();
				if (idx == 0){
					strs = reader.getTitles();
					idx++;
					if (CollectionUtils.isEmpty(strs) == false){
						writer.writeRecord(strs.toArray(new String[map.size()]));
						strs = new ArrayList<String>();
					}
				}
				for (Object str : map.values()) {
					strs.add((String) str);
				}
				writer.writeRecord(strs.toArray(new String[map.size()]));
				writer.flush();
			}
			writer.finalize();
		} catch (Exception e) {
			throw new RuntimeException("写入文件失败", e);
		}finally{
			IOUtils.closeQuietly(fos);
		}

		return file;
	}

	public File fromObject(List<?> list) {
		File path = new File(Application.getTemplatePath());
		try {
			File file = File.createTempFile("file", ".tmp", path);
			return this.fromObject(list, file,"gbk");
		} catch (IOException e) {
			throw new RuntimeException("生成临时文件失败", e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> fromFile(Reader reader, Class<?> clazz, boolean hasTitle) {
		CsvReader dr = new CsvReader(reader, delimiter.charAt(0));
		dr.setSafetySwitch(false);
		List<T> objs = new ArrayList<T>();
		try {
			int idx = 0;
			while (dr.readRecord()) {
				if (hasTitle && idx == 0) {
					idx++;
					continue;
				}
				ObjectAware or = new ObjectAware(clazz, dr);
				objs.add((T) or.getTarget());
			}
		} catch (IOException e) {
			throw new RuntimeException("生成文件失败", e);
		}finally{
			dr.finalize();
		}
		return objs;
	}
}
