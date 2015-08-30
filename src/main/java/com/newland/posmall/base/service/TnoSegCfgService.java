package com.newland.posmall.base.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newland.posmall.base.dao.TnoSegCfgDao;
import com.newland.posmall.base.dao.TnoSegCfgHisDao;
import com.newland.posmall.bean.basebusi.TnoSegCfg;
import com.newland.posmall.bean.basebusi.TnoSegCfgHis;

@Service
@Transactional
public class TnoSegCfgService {
	
	/**
	 * 产品号段总长度：12位
	 */
	public static int SEQ_LENGTH = 12;
	
	public static int PRE_LENGTH = 4;
	
	public static int VALUE_LENGTH = 8;

	@Resource
	private TnoSegCfgDao tnoSegCfgDao;
	
	@Resource
	private TnoSegCfgHisDao tnoSegCfgHisDao;
	
	public List<TnoSegCfg> findAll(){
		
		return this.tnoSegCfgDao.find();
	}

	public void save(TnoSegCfg tnoSegCfg) {
		this.tnoSegCfgDao.save(tnoSegCfg);
		//添加历史记录
		this.saveHis(tnoSegCfg);
	}
	
	public void update(TnoSegCfg tnoSegCfg){
		this.tnoSegCfgDao.update(tnoSegCfg);
		//添加历史记录
		this.saveHis(tnoSegCfg);
	}
	
	private void saveHis(TnoSegCfg tnoSegCfg) {
		TnoSegCfgHis his = new TnoSegCfgHis();
		his.setEnd(tnoSegCfg.getEnd());
		his.setGenTime(new Date());
		his.setIdx(tnoSegCfg.getIdx());
		his.setInoSegCfg(tnoSegCfg.getInoSegCfg());
		his.setPre(tnoSegCfg.getPre());
		his.setStart(tnoSegCfg.getStart());
		his.setVer(tnoSegCfg.getVersion());
		this.tnoSegCfgHisDao.save(his);
	}
	
	public TnoSegCfg get(Long id){
		return this.tnoSegCfgDao.get(id);
	}

	public List<TnoSegCfg> findTnoSegCfg(TnoSegCfg tnoSegCfg){
		return this.tnoSegCfgDao.findTnoSegCfg(tnoSegCfg);
	}
	
	/**
	 * 根据数量锁定号段库
	 * @param count
	 * @param ipdt
	 * @return
	 */
	public PdtNoSeq getSeq(int count, Long inoSegCfg){
		TnoSegCfg cfg = this.tnoSegCfgDao.lock(count, inoSegCfg);
		if (cfg == null) throw new RuntimeException("锁定失败");
		Long idx = cfg.getIdx();
		PdtNoSeq seq = new PdtNoSeq();
		seq.setStart(idx + 1);
		seq.setPre(cfg.getPre());
		seq.setEnd(idx + count);
		seq.setLength(getPreLengt(cfg));
		seq.setStartSeq(cfg.getPre() + String.format("%1$0" + (SEQ_LENGTH - seq.getLength()) + "d", idx + 1));
		seq.setEndSeq(cfg.getPre() + String.format("%1$0" + (SEQ_LENGTH - seq.getLength()) + "d", idx + count));
		return seq;
	}
	
	
	
	private int getPreLengt(TnoSegCfg cfg) {
		if (StringUtils.isBlank(cfg.getPre())) return 0;
		return cfg.getPre().length();
	}



	public static class PdtNoSeq implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private long start;
		
		private long end;
		
		private String pre;
		
		private String startSeq;
		
		private String endSeq;
		
		private long idx = 0;
		
		private int length;
		
		int getLength(){
			return length;
		}
		
		void setLength(int length){
			this.length = length;
		}

		public String getStartSeq() {
			return startSeq;
		}

		void setStartSeq(String startSeq) {
			this.startSeq = startSeq;
		}

		public String getEndSeq() {
			return endSeq;
		}

		void setEndSeq(String endSeq) {
			this.endSeq = endSeq;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((endSeq == null) ? 0 : endSeq.hashCode());
			result = prime * result
					+ ((startSeq == null) ? 0 : startSeq.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PdtNoSeq other = (PdtNoSeq) obj;
			if (endSeq == null) {
				if (other.endSeq != null)
					return false;
			} else if (!endSeq.equals(other.endSeq))
				return false;
			if (startSeq == null) {
				if (other.startSeq != null)
					return false;
			} else if (!startSeq.equals(other.startSeq))
				return false;
			return true;
		}

		long getIdx() {
			return idx;
		}

		void setIdx(long idx) {
			this.idx = idx;
		}

		void setStart(long start) {
			this.start = start;
		}
		
		public synchronized void setNum(int count){
			if ((idx + start + count) > end + 1) throw new RuntimeException("号段已用完！[起始：" + start + ", 结束:" + end + ", 索引：" + idx + "]" );
			this.startSeq = pre + String.format("%1$0" + (SEQ_LENGTH - length) + "d", start + idx);
			this.endSeq = pre + String.format("%1$0" + (SEQ_LENGTH - length) + "d", start + count + idx - 1);
			idx = idx + count;
		}

		void setEnd(long end) {
			this.end = end;
		}

		String getPre() {
			return pre;
		}

		void setPre(String pre) {
			this.pre = pre;
		}		
		
	}
	
	public static void main(String[] args){
		PdtNoSeq seq = new PdtNoSeq();
		seq.setEnd(2000);
		seq.setStart(1001);
		seq.setPre("NL");
		for (int i = 0 ; i < 10 ; i ++){
			seq.setNum(100);
			System.err.println(seq.getStartSeq() + " - " + seq.getEndSeq());
		}
		seq.setNum(1);
	}
}
