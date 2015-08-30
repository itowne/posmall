package com.newland.posmall.base.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;
import com.newland.posmall.Application;
import com.newland.posmall.bean.basebusi.Tagmt;
import com.newland.posmall.bean.basebusi.TagmtDetail;
import com.newland.posmall.bean.basebusi.Tcontract;
import com.newland.posmall.bean.basebusi.Tpdt;
import com.newland.posmall.bean.basebusi.TpdtHis;
import com.newland.posmall.bean.common.TmsgTmp;
import com.newland.posmall.bean.customer.TcustReg;
import com.newland.posmall.bean.dict.MsgTmpType;
import com.newland.posmall.bean.model4page.AgmtDetail4Page;
import com.newland.posmall.biz.cust.Agmt4PageBiz;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 电子协议生成器
 * @author rabbit
 *
 */
@Service
@Transactional
public class ElecAgmtService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TmsgTmpService msgTmpService;
	
	@Autowired
	private TagmtService agmtService;
	
	@Autowired
	private TpdtService pdtService;
	
	@Autowired
	private TcustRegService tcustRegService;
	
	@Autowired
	private Agmt4PageBiz agmt4PageBiz;
	
	private static Configuration conf; 
		
	@PostConstruct
	public void init() throws Throwable{
		conf = Configuration.getDefaultConfiguration();
		conf.setObjectWrapper(new DefaultObjectWrapper());
		conf.setTemplateLoader(new FileTemplateLoader(new File(Application.getTemplatePath())));
	}
	
	public Tagmt getTagmt(Long id){
		return this.agmtService.findById(id);
	}
	
	public File generate(Tagmt agmt){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		this.renderHtml(agmt, out, getTemplateFile());
		ITextRenderer renderer = new ITextRenderer();
		ITextFontResolver resolver = renderer.getFontResolver();
		FileOutputStream fileout = null;
		try {
			resolver.addFont(this.getFont(), BaseFont.IDENTITY_H,
					BaseFont.NOT_EMBEDDED);
			File path = new File(Application.getTemplatePath());
			renderer.setDocumentFromString(out.toString(), "/posmall");
			renderer.layout();
			File file = new File(path, "protocol_" + agmt.getAgmtNo() + ".pdf");
			fileout = new FileOutputStream(file);
			renderer.createPDF(fileout);
			logger.debug("文件生成成功！");
			return file;
		} catch(Exception e){
			throw new RuntimeException("生成文件失败", e);
		}finally {
			IOUtils.closeQuietly(fileout);
		}
	}
	
	private String getFont(){
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource res = resolver.getResource("simsun.ttc");
		File file = null;
		try {
			file = res.getFile();
		} catch (IOException e) {
			logger.error("字体文件不存在", e);
			return null;
		}
		if(file.exists()) return file.getAbsolutePath();
		File path = new File(Application.getTemplatePath());
		file = new File(path, "simsun.ttc");
		if (file.exists()) return file.getAbsolutePath();
		FileOutputStream out = null;
		try {
			 out = new FileOutputStream(file);
			IOUtils.copy(res.getInputStream(), out);
			return file.getAbsolutePath();
		} catch (Exception e) {
			logger.error("生成文件流失败", e);
			return null;
		}finally{
			IOUtils.closeQuietly(out);
		}
	}
	
	private synchronized String getTemplateFile(){
		TmsgTmp msg = this.msgTmpService.queryByTypeAndCode(MsgTmpType.AGMT, "PROTOCOL");
		if (msg == null) throw new RuntimeException("协议模板未定义");
		File file = new File(Application.getTemplatePath(), "protocol_" + msg.getVersion() + ".flt");
		if (file.exists()) return file.getName();
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
			IOUtils.write(msg.getContent().getBytes(), fout);
		} catch (Exception e) {
			logger.error("写入协议模板文件失败", e);
		}finally{
			IOUtils.closeQuietly(fout);
		}
		
		return file.getName();
	}
	
	private void renderHtml(Tagmt agmt, OutputStream out, String templateFile){
		
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("agmt", agmt);
		
		List<TagmtDetail> detls = this.agmtService.findTagmtDetial(agmt);
		
		List<AgmtDetail4Page> agmtDetail4PageList = this.agmt4PageBiz.findAgmtDetail4PageList(detls);
		
		for (AgmtDetail4Page p:agmtDetail4PageList){
			
			TpdtHis tpdtHis = this.pdtService.findTpdtHisById(p.getTagmtDetail().getIpdtHis());
			if (tpdtHis == null) {
				tpdtHis = new TpdtHis();
				tpdtHis.setPdtNo("--");
			}
			
			p.setTpdtHis(tpdtHis);
		}
		
		TcustReg custReg = tcustRegService.findByIcust(agmt.getIcust());
		root.put("prodList", agmtDetail4PageList);
		root.put("custReg",custReg);
		Template temp = null;
		try {
			temp = conf.getTemplate(templateFile);
		} catch (IOException e) {
			throw new RuntimeException("查找模板错误", e);
		}
		Writer writer = new OutputStreamWriter(out);
		try {
			temp.process(root, writer);
		} catch (TemplateException e) {
			logger.error("模板生成错误", e);
			throw new RuntimeException("模板生成错误", e);
		} catch (IOException e) {
			logger.error("写数据错误", e);
			throw new RuntimeException("写数据错误", e);
		}
	}

}
