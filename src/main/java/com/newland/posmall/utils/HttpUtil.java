package com.newland.posmall.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * HTTP通讯工具类
 * 
 * @author ShiZhenning
 * @since 2011-04-21
 */
public class HttpUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(HttpUtil.class);

	/**
	 * 自定义私有类：绕开HTTPS证书校验
	 */
	private static class EasyTrustManager implements X509TrustManager {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		}
	}

	/**
	 * 判断一个URL地址是否可连通
	 * 
	 * @param httpUrl
	 *            http url地址
	 * @return true表示可连通
	 */
	public static boolean isConnectable(String httpUrl) {
		HttpURLConnection conn = null;
		try {
			logger.info("尝试连接:" + httpUrl);
			URL url = new java.net.URL(httpUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(15000);
			// 对于HTTPS的网址，需要绕开证书验证
			if (conn instanceof HttpsURLConnection) {
				SSLContext context = SSLContext.getInstance("TLS");
				context.init(null,
						new TrustManager[] { new EasyTrustManager() }, null);
				HttpsURLConnection httpsConn = (HttpsURLConnection) conn;
				httpsConn.setSSLSocketFactory(context.getSocketFactory());
				httpsConn
						.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			}
			// 建立TCP连接以确定其连通性
			conn.connect();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return false;
		} finally {
			logger.info("释放连接:" + httpUrl);
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return true;
	}

	/**
	 * 向一个url地址post信息
	 * 
	 * @param httpUrl
	 *            URL地址
	 * @param content
	 *            提交信息内容
	 * @return HTTP响应码
	 * @throws Exception
	 */
	public static int post(String httpUrl,String content) throws Exception {
		int statusCode = 0;

		HttpClient hc = new DefaultHttpClient();
		// 连接超时设为10秒，连接成功后等待返回的超时设为15秒
		hc.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		hc.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setHeader("Connection", "close");
		setProxy(hc);
		try {
			setSSLParam(hc);

			StringEntity stringEntity = new StringEntity(content, HTTP.UTF_8);
			stringEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(stringEntity);// 将参数传入post方法中

			logger.debug("post_url:" + httpUrl);
			logger.info("post_content:" + content);
			HttpResponse response = hc.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			logger.debug("post_result:" + statusCode);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				EntityUtils.consume(entity);
			}
		} catch (Exception e) {
			httpPost.abort();
			logger.error("发送商户信息失败:" + statusCode + e.getMessage());
			throw e;
		} finally {
			hc.getConnectionManager().shutdown();
		}
		return statusCode;
	}

	/****
	 * 提交HTTP请求至服务端，并返回成功的响应结果，如果返回的状态码>400 统一抛出APPBIZEXCEPTION
	 * 
	 * @param httpUrl
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String submitRequest(String httpUrl,String content,
			String encoding) throws Exception {

		int statusCode = 0;

		HttpClient hc = new DefaultHttpClient();
		// 开发模式使用代理
		setProxy(hc);

		// 连接超时设为10秒，连接成功后等待返回的超时设为15秒
		hc.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		hc.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setHeader("Connection", "close");
		String result = "";
		try {
			setSSLParam(hc);

			StringEntity stringEntity = new StringEntity(content, encoding);
			stringEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(stringEntity);// 将参数传入post方法中
			logger.debug("post_url:" + httpUrl);
			logger.info("post_content:" + content);
			HttpResponse response = hc.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			logger.debug("post_result:" + statusCode);
			if (statusCode >= 400 || statusCode == 0) {
				throw new Exception(String.format("网络异常,状态码：[%s]",
						Integer.valueOf(statusCode)));
			}
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, encoding);
		} catch (Exception e) {
			httpPost.abort();
			logger.error(e.getMessage(), e);
			throw e;

		} finally {
			hc.getConnectionManager().shutdown();
		}
		logger.info("收到响应内容:" + result);
		return result;
	}

	/**
	 * 提交HTTP请求至服务端，并返回成功的响应结果，如果返回的状态码>400
	 * 
	 * @param httpUrl
	 * @param nameValueList
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String submitRequestList(String httpUrl, 
			List<NameValuePair> nameValueList, String encoding) throws Exception {

		int statusCode = 0;

		HttpClient hc = new DefaultHttpClient();
		//使用代理
		setProxy(hc);

		// 连接超时设为10秒，连接成功后等待返回的超时设为15秒
		hc.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		hc.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
		HttpPost httpPost = new HttpPost(httpUrl);
		httpPost.setHeader("Connection", "close");
		String result = "";
		try {
			setSSLParam(hc);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					nameValueList, encoding);
			httpPost.setEntity(entity);// 将参数传入post方法中
			logger.debug("post_url:" + httpUrl);
			logger.info("post_content:" + nameValueList);
			HttpResponse response = hc.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			logger.debug("post_result:" + statusCode);
			if (statusCode >= 400 || statusCode == 0) {
				throw new Exception("网络繁忙");
			}
			HttpEntity resultEntity = response.getEntity();
			result = EntityUtils.toString(resultEntity, encoding);
		} catch (Exception e) {
			httpPost.abort();
			throw e;
		} finally {
			hc.getConnectionManager().shutdown();
		}
		logger.debug("收到响应内容:" + result);
		return result;

	}

	private static void setProxy(HttpClient hc) {
		String proxy = System.getProperty("Proxy");
		try{
			if (StringUtils.isNotBlank(proxy)) {
				/**
				 * 使用代理链接
				 */
				int index = proxy.indexOf(":");
				int port = 80;
				String ip = null;
				if (index == -1){
					ip = proxy.substring(0, proxy.length() - 1);
				}else{
					ip = proxy.substring(0, index);
					port = Integer.parseInt(proxy.substring(index + 1));
				}
				HttpHost Proxy = new HttpHost(ip, port);
				hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, Proxy);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 从httpUrl获取文件，并下载到savaPath中,写入用writeEncoding编码
	 * 
	 * @param httpUrl
	 *            例如http://localhost:8080/chinaPay_Batch/
	 *            808080211388210_20131119_183402.txt
	 * @param content
	 *            参数 没有参数传空串
	 * @param savePath
	 *            文件将保存的路径
	 * @param fileName
	 *            文件将保存的名称
	 * @param writeEncoding
	 *            写入本地文件使用的编码
	 * @return 写入的本地文件
	 * @author hechen
	 * @throws Exception
	 */
	public static File getFileFromUrl(String httpUrl,String savePath, String fileName, String writeEncoding)
			throws Exception {
		File file = null;
		int statusCode = 0;
		HttpClient hc = new DefaultHttpClient();
		// 开发模式使用代理
		setProxy(hc);
		// 连接超时设为10秒，连接成功后等待返回的超时设为15秒
		hc.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		hc.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
		HttpGet httpGet = new HttpGet(httpUrl);
		httpGet.setHeader("Connection", "close");

		try {
			setSSLParam(hc);
			logger.debug("post_url:" + httpUrl);
			HttpResponse response = hc.execute(httpGet);
			statusCode = response.getStatusLine().getStatusCode();
			logger.debug("post_result:" + statusCode);
			if (statusCode >= 400 || statusCode == 0) {
				throw new Exception(String.format("网络繁忙！原因:网络异常,状态码：[%s]",
						Integer.valueOf(statusCode)));
			}
			
			String temp = getFileName(httpUrl, response);
			if (StringUtils.isBlank(temp) == false) fileName = temp;
			HttpEntity entity = response.getEntity();
			long l = entity.getContentLength();
			/**
			 * 写文件准备
			 */
			file = new File(savePath, fileName);
			InputStream in = entity.getContent();

			OutputStream outputStream = new FileOutputStream(file);
			IOUtils.copy(in, outputStream);
			outputStream.flush();
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(in);
			

			if (entity != null) {
				EntityUtils.consume(entity);
			}

		} catch (Exception e) {
			httpGet.abort();
			logger.error("post_error:" + statusCode);
			logger.error(e.getMessage(),e);
			throw e;
		} finally {
			hc.getConnectionManager().shutdown();
		}
		return file;
	}

	

	private static String getFileName(String httpUrl, HttpResponse response) {
		Header header = response.getFirstHeader("Content-Disposition");
		String filename = "";
		if (header != null){
			HeaderElement[] elements = header.getElements();
			if (elements != null){
				for (HeaderElement el: elements){
					filename = el.getParameterByName("filename").getValue();
					if (StringUtils.isNotBlank(filename)) {
						int idx = filename.indexOf(";");
						if (idx >= 0){
							filename = filename.substring(0, idx);
						}
						return filename;
					}
				}
			}
		}
		return null;
	}

	private static HttpClient getHttpClient() {
		HttpClient hc = new DefaultHttpClient();
		// 连接超时设为10秒，连接成功后等待返回的超时设为15秒
		hc.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		hc.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
		return hc;
	}
	
	public static String uploadFile(String url, File localFile) throws Exception{
		//新建HttpClient
		HttpClient httpclient = getHttpClient();
		//设置SSL参数
		setSSLParam(httpclient);
		//设置代理
		setProxy(httpclient);
        //请求处理页面  
        HttpPost httppost = new HttpPost(url);
        //创建待处理的文件  
        FileBody file = new FileBody(localFile);  
        //创建待处理的表单域内容文本  
        StringBody descript = new StringBody(localFile.getName());  
          //对请求的表单域进行填充  
        MultipartEntity reqEntity = new MultipartEntity();  
        reqEntity.addPart("file", file); 
        reqEntity.addPart("descript", descript);
        
        //设置请求  
        httppost.setEntity(reqEntity);  
        //执行  
        HttpResponse response = httpclient.execute(httppost);  

        if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){  
            HttpEntity entity = response.getEntity();  
            //显示内容  
            if (entity != null) {  
                String content = EntityUtils.toString(entity);  
                EntityUtils.consume(entity);
                return content;
            }else{
            	return "";
            }
        }
        throw new Exception("上传失败");
	}
	
	public static File downloadFile(String url, String localFile) throws Exception{
		File file = null;
		int statusCode = 0;
		HttpClient hc = new DefaultHttpClient();
		// 开发模式使用代理
		setProxy(hc);
		// 连接超时设为10秒，连接成功后等待返回的超时设为15秒
		hc.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		hc.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Connection", "close");

		try {
			setSSLParam(hc);
			logger.debug("post_url:" + url);
			HttpResponse response = hc.execute(httpPost);
			statusCode = response.getStatusLine().getStatusCode();
			logger.info("post_result:" + statusCode);
			if (statusCode >= 400 || statusCode == 0) {
				throw new Exception(String.format("网络繁忙！原因:网络异常,状态码：[%s]",
						Integer.valueOf(statusCode)));
			}
			HttpEntity entity = response.getEntity();
			
			/**
			 * 写文件准备
			 */
			file = new File(localFile);
			InputStream in = entity.getContent();

			OutputStream outputStream = new FileOutputStream(file);
			int length = 0;
			byte[] bytes = new byte[2048];
			while ((length = in.read(bytes)) > 0) {
				outputStream.write(bytes, 0, length);
			}
			in.close();
			outputStream.flush();
			outputStream.close();
			//显示内容  
            EntityUtils.consume(entity);
            return file;

		} catch (Exception e) {
			httpPost.abort();
			logger.error("post_error:" + statusCode,e);
			throw e;
		} finally {
			hc.getConnectionManager().shutdown();
		}
		
	}

	private static void setSSLParam(HttpClient httpclient)
			throws NoSuchAlgorithmException, KeyManagementException {
		Scheme http = new Scheme("http", 80,
				PlainSocketFactory.getSocketFactory());
		httpclient.getConnectionManager().getSchemeRegistry().register(http);
		// HTTPS应绕开证书验证
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new TrustManager[] { new EasyTrustManager() },
				null);
		SSLSocketFactory factory = new SSLSocketFactory(context,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme https = new Scheme("https", 443, factory);
		httpclient.getConnectionManager().getSchemeRegistry().register(https);
	}

}
