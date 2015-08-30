package org.ohuyo.rapid.base;

import org.ohuyo.rapid.base.entity.Tsys;

/**
 * 
 * @author rabbit
 *
 */
public class TsysSession extends AbstractAppSession {

	private static final long serialVersionUID = 1L;
	private Tsys tsys;

	@Override
	public SessionType getSessionType() {
		return SessionType.SYS;
	}

	public Tsys getTsys() {
		return tsys;
	}

	public void setTsys(Tsys tsys) {
		this.tsys = tsys;
	}

}
