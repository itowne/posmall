package org.ohuyo.rapid.file;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ohuyo.commons.format.BeanCodecContext;
import org.ohuyo.commons.format.cache.BeanClassInfo;
import org.ohuyo.commons.format.codec.BeanCodecFactory;
import org.ohuyo.commons.format.codec.SimpleBeanCodecFactory;
import org.ohuyo.commons.format.codec.impl.ClassCodec;
import org.springframework.security.util.FieldUtils;
import org.springframework.util.CollectionUtils;

public class ObjectAware {
	
	static final BeanCodecFactory codecFactory = new SimpleBeanCodecFactory();
	static final BeanCodecContext ctx = new BeanCodecContext(codecFactory);
	
	private Class<?> target;
	
	Map<String, Object> map;
	
	List<String> titles;
	
	private ClassCodec codec;
	
	private CsvReader reader;
	
	public ObjectAware(Class<?> clazz){
		this(clazz, null);
	}
	
	public ObjectAware(Object obj){
		this(obj, null);
	}
	
	public ObjectAware(Object obj, CsvReader reader){
		this.target = obj.getClass();
		this.reader = reader;
		BeanClassInfo info = ctx.getBeanClassInfo(obj);
		codec = info.getCodec();
		try {
			map = codec.encode(obj);
		} catch (Exception e){
			throw new RuntimeException("生成对象信息失败", e);
		}
	}
	
	public ObjectAware(Class<?> clazz, CsvReader reader){
		this.target = clazz;
		this.reader = reader;
		BeanClassInfo info = ctx.getBeanClassInfo(target);
		codec = info.getCodec();
		try {
			map = codec.encode(clazz.newInstance());
		} catch (Exception e){
			throw new RuntimeException("生成对象信息失败", e);
		}
	}
	
	public Map<String, Object> getFieldMap(){
		return map;
	}

	
	public Object getTarget(){
		if (this.reader == null) return null;
		int i = 0; 
		Set<String> keys = map.keySet();
		for (String key: keys){
			try {
				map.put(key, this.reader.get(i));
			} catch (IOException e) {
				throw new RuntimeException("生成对象失败", e);
			}
			i++;
		}
		return this.codec.decode(map);
	}

	public List<String> getTitles() {
		if (CollectionUtils.isEmpty(map)) return null;
		if (titles == null){
			titles = new ArrayList<String>();
		}
		for (Map.Entry<String, Object> entry: map.entrySet()){
			String fieldName = entry.getKey();
			Field field = FieldUtils.getField(target, fieldName);
			if (field == null) throw new RuntimeException("属性找不到:[" + fieldName + "]");
			Title title = field.getAnnotation(Title.class);
			if (title == null) {
				titles.add(fieldName);
			}else{
				titles.add(title.value());
			}
		}
		return titles;
	}

}
