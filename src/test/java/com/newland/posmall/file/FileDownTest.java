package com.newland.posmall.file;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.newland.posmall.BeanTest;
import com.newland.posmall.base.service.TcustService;
import com.newland.posmall.bean.customer.Twarranty;
import com.newland.posmall.biz.file.FileDownload;
import com.newland.posmall.biz.file.ReportFormDownload;

public class FileDownTest extends BeanTest {

	@Resource
	private FileDownload fileDownload;
	
	@Resource
	private TcustService tcustService;
	
	@Resource
	private ReportFormDownload reportFormDownload;
	
	@Test
	public void downTest() throws Exception{
		List<Twarranty> list = new ArrayList<Twarranty>();
		Date now = new Date();
		for (int i = 0; i < 6; i++) {
			Twarranty twarranty = new Twarranty();
			Long iwarranty = 10000L+i;
			twarranty.setIwarranty(iwarranty);
			twarranty.setSeqNo("fsf"+i);
			twarranty.setPdtNo("fre"+i);
			twarranty.setWarrantyTime(now);
			twarranty.setAcceptTime(now);
			twarranty.setCustName("chenwenjing");
			twarranty.setRemark("测试");
			list.add(twarranty);
		}
		fileDownload.warrantyInfoDown(list, "7890");
	}
	@Test
	public void getSn()throws Exception{
		fileDownload.maintenanceSync("2014-10-10");
	}
	@Test
	public void testAgmtReport()throws Exception{
		String startTime = "2014-10-24";
		String finishTime = "2014-11-04";
		Date beginTime = DateUtils.parseDate(startTime,new String[]{"yyyy-MM-dd"});
		Date endTime = DateUtils.addDays(DateUtils.parseDate(finishTime,new String[]{"yyyy-MM-dd"}),1);
		reportFormDownload.agmtReport(beginTime, endTime);
	}
	public static void main(String[] args) {
		Date now = new Date();
		String genTime = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
		System.out.println(genTime);
	}

}
