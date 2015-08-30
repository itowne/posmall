package com.newland.posmall.biz.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.ohuyo.rapid.schedule.ResultData;
import org.ohuyo.rapid.schedule.Task;
import org.ohuyo.rapid.schedule.TaskContext;
import org.ohuyo.rapid.schedule.TaskType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.newland.posmall.biz.file.FileDownload;

@Service
public class MaintenanceInfoSyncTask extends Task {
	
	@Autowired
	FileDownload fileDownload;

	public static final Logger logger = LoggerFactory.getLogger(MaintenanceInfoSyncTask.class);
	
	protected MaintenanceInfoSyncTask() {
		super("维保信息同步定时器", TaskType.REPEAT);
	}

	@Override
	protected void runTask(Date lastDate, TaskContext ctx, ResultData data) throws Throwable {
		try{
			String genTime = null;
			if(StringUtils.isEmpty(ctx.getManualDate())){
				Date now = new Date();
				genTime =	new SimpleDateFormat("yyyy-MM-dd").format(now);
			}else{
				genTime = ctx.getManualDate();
			}
			
			this.fileDownload.maintenanceSync(genTime);
		}catch(Exception e){
			data.ERROR("维保信息插入失败。", e);
			return ;
		}
		data.SUCCESS("维保信息插入成功");
	}

}
