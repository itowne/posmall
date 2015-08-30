package com.newland.posmall.biz.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ohuyo.rapid.schedule.ResultData;
import org.ohuyo.rapid.schedule.Task;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.TaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.posmall.biz.file.FileDownload;
import com.newland.posmall.biz.schedule.service.HttpDownService;
import com.newland.posmall.biz.schedule.service.LogisticSyncService;

@Service
public class LogisticSyncTask extends Task {

	@Autowired
	LogisticSyncService syncService;
	
	@Autowired
	HttpDownService httpDownService;
	
	@Autowired
	FileDownload fileDownload;
	
	public static final Logger logger = LoggerFactory.getLogger(LogisticSyncTask.class);

	protected LogisticSyncTask() {
		super("物流文件同步定时器", TaskType.REPEAT);
	}

	@Override
	protected void runTask(Date lastDate, TaskContext ctx, ResultData data) throws Throwable {
		this.syncService.queryLogisticInfo(lastDate, new Date(), "", "0", "0");
		FileThread thread = new FileThread(this.httpDownService);
		thread.run();
		thread.join();
		if (thread.isSuccess()){
			try{
				this.fileDownload.insertSalesOrd(thread.getFileName());
			}catch(Exception e){
				data.ERROR("插入失败。文件名：[" + thread.getFileName() + "]", e);
				return ;
			}
			data.SUCCESS("下载成功。文件名：[" + thread.getFileName() + "]");
		}else{
			data.ERROR("下载失败。文件名：[" + thread.getFileName() + "]", thread.getExp());
			return ;
		}
	}

	private static class FileThread extends Thread {

		HttpDownService httpDownService;

		boolean success = false;

		String fileName;
		
		Throwable exp;
		

		public FileThread(HttpDownService service) {
			this.httpDownService = service;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			fileName = sdf.format(new Date()) + ".txt";
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				try {
					this.httpDownService.download(fileName);
					success = true;
				} catch (Exception e) {
					logger.warn("获取文件失败", e);
					success = false;
					this.exp = e;
				} 
				if (success){
					break;
				}
				try {
					sleep(3 * 60 * 1000);
				} catch (InterruptedException e) {
				}
			}
		}

		public boolean isSuccess() {
			return success;
		}

		public String getFileName() {
			return fileName;
		}


		public Throwable getExp() {
			return exp;
		}

	}

}
