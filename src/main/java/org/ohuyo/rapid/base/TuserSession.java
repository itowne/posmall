package org.ohuyo.rapid.base;

import java.util.ArrayList;
import java.util.List;

import org.ohuyo.rapid.base.entity.Tuser;
import org.ohuyo.rapid.base.entity.Tuserauth;

import com.newland.posmall.bean.backmanage.TuserSub;

/**
 * 
 * @author rabbit
 *
 */
public class TuserSession extends AbstractAppSession {

	private static final long serialVersionUID = 1L;
	private Tuser tuser;
	private TuserSub tuserSub;

	@Override
	public SessionType getSessionType() {
		return SessionType.USER;
	}

	public Tuser getTuser() {
		return tuser;
	}

	public void setTuser(Tuser tuser) {
		this.tuser = tuser;
	}

	public TuserSub getTuserSub() {
		return tuserSub;
	}

	public void setTuserSub(TuserSub tuserSub) {
		this.tuserSub = tuserSub;
	}

}
