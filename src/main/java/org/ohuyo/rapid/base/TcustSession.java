package org.ohuyo.rapid.base;

import com.newland.posmall.bean.customer.Tcust;
import com.newland.posmall.bean.customer.TcustReg;

/**
 * 
 * @author rabbit
 *
 */
public class TcustSession extends AbstractAppSession {

	private static final long serialVersionUID = 1L;

	private Tcust tcust;

	private TcustReg tcustReg;

	@Override
	public SessionType getSessionType() {
		return SessionType.CUST;
	}

	public Tcust getTcust() {
		return tcust;
	}

	public void setTcust(Tcust tcust) {
		this.tcust = tcust;
	}

	public TcustReg getTcustReg() {
		return tcustReg;
	}

	public void setTcustReg(TcustReg tcustReg) {
		this.tcustReg = tcustReg;
	}

}
