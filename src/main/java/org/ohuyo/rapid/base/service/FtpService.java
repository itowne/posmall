package org.ohuyo.rapid.base.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * @author rabbit
 * 
 */
@Service("com.newland.iaf.ftpService")
public class FtpService{

	private Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 生产本地文件名称文件
	 * 
	 * @param local
	 * @return
	 */
	private String genFileName(String local) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String name = local + "_" + df.format(new Date());
		File f = new File(name);
		int i = 1;
		while (f.exists()) {
			name = local + "_" + df.format(new Date()) + "_" + i;
			f = new File(name);
			i++;
		}
		return name;
	}


	public String ftpGet(String remoteAddr, int remotePort, String remotePath,
			String remoteFileName, String localPath, String username,
			String password) throws SocketException, IOException {
		FTPClient ftp = new FTPClient();
		FTPClientConfig config = new FTPClientConfig();
		ftp.configure(config);
		try {
			ftp.connect(remoteAddr, remotePort);
			log.debug("Connected to " + remoteAddr + ":" + remotePort + ".\n"
					+ ftp.getReplyString());
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.error("FTP server refused connection.");
				throw new RuntimeException("FTP server refused connection.");
			}
			ftp.login(username, password);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				log.error("FTP server refused connection.");
				throw new RuntimeException("FTP server refused connection.");
			}
			if (!ftp.setFileType(FTP.BINARY_FILE_TYPE)) {
				log.error("set BINARY_FILE_TYPE error.");
				throw new RuntimeException("FTP server refused connection.");
			}
			//ftp.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
			ftp.enterLocalPassiveMode();
			String remote = remotePath + "/" + remoteFileName;
			String local = localPath + "/" + remoteFileName;
			String returnName = genFileName(local);
			File f = new File(returnName);
			FileOutputStream fos = FileUtils.openOutputStream(f);
			try {
				  long startTime = System.currentTimeMillis();				   
				   if (!ftp.retrieveFile(remote, fos)) {
					log.error("get " + remote + " error.");
					throw new RuntimeException("get " + remote + " error.");
				}
				log.debug("get remote file[" + remote + "], local file["
						+ local + "] .");
				long finishedTime = System.currentTimeMillis();
				log.debug("remote time :"+(finishedTime-startTime)/1000f+" sec.");
			} finally {
				IOUtils.closeQuietly(fos);
			}
			ftp.logout();
			return returnName;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}

	}

}
