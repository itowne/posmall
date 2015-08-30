package org.ohuyo.rapid.base.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.base.entity.Tfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.newland.posmall.Application;

@Service
@Transactional
public class FileUpDownService {
	@Resource
	TfileService fileService;
	
	public static final String UPLOAD_FILE_LIST = "filelist";

	private String ctx = Application.getTemplatePath();

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public void download(File file, HttpServletResponse response) throws Exception{
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String fileName = file.getName();
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
			response.setHeader("Content-Length",
					String.valueOf(file.length()));
			response.setContentType("application/octet-stream;charset=utf-8");
			bos = new BufferedOutputStream(response.getOutputStream());
			IOUtils.copy(bis, bos);
		} catch (Exception e) {
			logger.error("文件下载失败", e);
			throw e;
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(bos);
		}
		return;
	}

	public void download(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		String fileid = request.getParameter("fileid");
		if (fileid == null) {
			response.sendError(500, "fileid未设置");
			return;
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		Tfile file = this.fileService.get(fileid);
		if (file == null) {
			response.sendError(404, "文件不存在");
			return;
		}
		String fileName = file.getName();// 从数据库中读取文件名称
		String downLoadPath = ctx + "/" + file.getUuid();
		try {

			response.setHeader("Content-disposition", "attachment; filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
			response.setHeader("Content-Length",
					String.valueOf(file.getLength()));
			response.setContentType(file.getContentType() + ";charset=utf-8");
			bos = new BufferedOutputStream(response.getOutputStream());
			if ("0".equals(file.getLocation())){
				bis = new BufferedInputStream(new FileInputStream(downLoadPath));
				IOUtils.copy(bis, bos);
			}else{
				IOUtils.write(file.getContent(), bos);
			}
		} catch (Exception e) {
			logger.error("文件下载失败", e);
			throw e;
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(bos);
		}
		return;
	}
	/**
	 * 上传文件服务
	 * @param request 请求对象
	 * @param response 响应对象
	 * @param limitExts 限制后缀，可以为空
	 * @param limitContentType 限制文件类型，可以为空
	 * @param limitLength 限制上传文件大小，-1为不限制
	 * @return
	 * @throws Exception
	 */
	public Map<String, Tfile> upload(HttpServletRequest request, HttpServletResponse response,String[] limitExts, String[] limitContentType, long limitLength)
			throws Exception {
		if (request instanceof MultipartHttpServletRequest) {
			return getFileWithSpringRequest(request, limitExts, limitContentType, limitLength);
		} else {
			return getFileWithServlet(request, limitExts, limitContentType, limitLength);
		}
	}

	private Map<String, Tfile> getFileWithSpringRequest(HttpServletRequest request, String[] limitExts, String[] limitContentType, long limitLength) 
			throws Exception{
		try {
			cleanSession(request);
			String location = (String)request.getParameter("location");
			if (StringUtils.isBlank(location)) location = (String)request.getAttribute("location");
			if (StringUtils.isBlank(location)) location = "0";
			request.setCharacterEncoding("utf-8"); // 设置编码
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转型为MultipartHttpRequest：
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				// 获得文件：
				Iterator<String> iter = multipartRequest.getFileNames();
				Map<String, Tfile> files = new HashMap<String, Tfile>();
				while (iter.hasNext()) {
					MultipartFile file = multipartRequest.getFile(iter.next());
					String fieldname = file.getName();
					// 获得文件名：
					String filename = file.getOriginalFilename();
					if (StringUtils.isBlank(filename)) continue;
					Tfile tfile = new Tfile();
					tfile.setContentType(file.getContentType());
					if(limitContentType != null){
						boolean allow = false;
						for (String ct: limitContentType){
							if (tfile.getContentType().equalsIgnoreCase(ct)){
								allow = true;
							}
						}
						if (allow == false) {
							logger.error("不允许的文件类型:" + tfile.getContentType());
							throw new RuntimeException("上传文件类型有误");
						}
					}
					tfile.setName(filename);
					tfile.setExt(getFileExt(filename));
					if (limitExts != null){
						boolean allow = false;
						for (String ext: limitExts){
							if (ext.equalsIgnoreCase(tfile.getExt())){
								allow = true;
							}
						}
						if (allow == false) {
							logger.error("不允许的后缀名" + tfile.getExt());
							throw new RuntimeException("上传文件类型有误");
						}
					}
					tfile.setGenTime(new Date());
					tfile.setLength(file.getSize());
					if (limitLength != -1){
						if (tfile.getLength() > limitLength){
							BigDecimal limit = new BigDecimal(limitLength).divide(new BigDecimal(1024*1024), 1, RoundingMode.HALF_UP);
							throw new RuntimeException("超过文件大小限制：" + limit + "M");
						}
					}
					tfile.setLocation(location);
					if ("1".equals(location)){
						tfile.setContent(IOUtils.toByteArray(file.getInputStream())); 
					}else{
						tfile.setContent(new byte[]{0});
					}
					this.fileService.save(tfile);
					// 写入文件
					if ("0".equals(location)){
						File localPath = new File(ctx);
						if (localPath.exists() == false)
							localPath.mkdirs();
						File localFile = new File(localPath, tfile.getUuid());
						file.transferTo(localFile);
					}
					files.put(fieldname, tfile);
					
				}
				this.setSession(files, request);
				return files;
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
		return null;
	}
	
	private void cleanSession(HttpServletRequest request) {
		HttpSession sess = request.getSession();
		if (sess == null) return;
		sess.removeAttribute(UPLOAD_FILE_LIST);
		
	}
	
	@SuppressWarnings("unchecked")
	public static Tfile getTfileFromSession(String formFieldName, HttpServletRequest request){
		HttpSession session = request.getSession();
		if (session == null) return null;
		Map<String, Tfile> map = (Map<String, Tfile>)session.getAttribute(UPLOAD_FILE_LIST);
		if (CollectionUtils.isEmpty(map)) return null;
		return map.get(formFieldName);
	}

	private void setSession(Map<String, Tfile> files, HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute(UPLOAD_FILE_LIST, files);
	}

	private Map<String, Tfile> getFileWithServlet(HttpServletRequest request, String[] limitExts, String limitContentType[], long limitLength)
			throws UnsupportedEncodingException {
		cleanSession(request);
		if (ServletFileUpload.isMultipartContent(request) == false) {
			logger.info("不是文件上传请求");
			return null;
		}
		String location = (String)request.getParameter("location");
		if (StringUtils.isBlank(location)) location = (String)request.getAttribute("location");
		if (StringUtils.isBlank(location)) location = "0";
		request.setCharacterEncoding("utf-8"); // 设置编码
		// 获得磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 获取文件需要上传到的路径
		File path = new File(ctx);
		if (path.exists() == false) {
			path.mkdirs();
		}
		factory.setRepository(path);
		// 设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		OutputStream out = null;
		InputStream in = null;
		try {
			// 可以上传多个文件
			List<FileItem> list = (List<FileItem>) upload.parseRequest(request);
			Map<String, Tfile> files = new HashMap<String, Tfile>();
			for (FileItem item : list) {
				// 获取表单的属性名字
				String name = item.getFieldName();
				// 如果获取的 表单信息是普通的 文本 信息
				if (item.isFormField()) {
					// 获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
					String value = item.getString();
					request.setAttribute(name, value);
				}
				// 对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
				else {
					String value = item.getName();
					if (StringUtils.isBlank(value)) continue;
					int start = value.lastIndexOf("\\");
					String filename = value.substring(start + 1);
					Tfile file = new Tfile();
					file.setContentType(item.getContentType());
					if(limitContentType != null){
						boolean allow = false;
						for (String ct: limitContentType){
							if (file.getContentType().equalsIgnoreCase(ct)){
								allow = true;
							}
						}
						if (allow == false) throw new RuntimeException("不允许的文件类型" + file.getContentType());
					}
					file.setName(filename);
					file.setExt(getFileExt(filename));
					if (limitExts != null){
						boolean allow = false;
						for (String ext: limitExts){
							if (ext.equalsIgnoreCase(file.getExt())){
								allow = true;
							}
						}
						if (allow == false) throw new RuntimeException("不允许的后缀名" + file.getExt());
					}
					file.setGenTime(new Date());
					file.setLength(item.getSize());
					if (limitLength != -1){
						if (file.getLength() > limitLength){
							throw new RuntimeException("超过最大文件限制[limit:" + limitLength + "]");
						}
					}
					if ("1".equals(location)){
						file.setContent(IOUtils.toByteArray(in));
					}else{
						file.setContent(new byte[]{0});
					}
					file.setLocation(location);
					this.fileService.save(file);
					if ("0".equals(location)){
						request.setAttribute(name, filename);
						out = new FileOutputStream(new File(path, file.getUuid()));
						in = item.getInputStream();
						IOUtils.copy(in, out);
					}
					files.put(name, file);
				}
				this.setSession(files, request);
				return files;
			}

		} catch (FileUploadException e) {
			logger.error("上传文件失败", e);
		} catch (Exception e) {
			logger.error("上传文件失败", e);
		} finally {
			/*
			 * IOUtils.closeQuietly(in); IOUtils.closeQuietly(out);
			 */
		}
		return null;
	}

	private String getFileExt(String filename) {
		if (StringUtils.isBlank(filename))
			throw new RuntimeException("文件名为空");
		int idx = filename.lastIndexOf(".");
		if (idx <= 0) return "";
		return filename.substring(idx + 1);
	}

}
