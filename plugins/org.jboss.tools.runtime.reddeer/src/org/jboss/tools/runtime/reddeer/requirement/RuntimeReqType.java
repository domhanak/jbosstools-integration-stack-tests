package org.jboss.tools.runtime.reddeer.requirement;

import org.jboss.tools.runtime.reddeer.RuntimeBase;
import org.jboss.tools.runtime.reddeer.impl.RuntimeDrools;
import org.jboss.tools.runtime.reddeer.impl.RuntimeJbpm;

/**
 * 
 * @author apodhrad
 * 
 */
public enum RuntimeReqType {

	ANY(null), DROOLS(RuntimeDrools.class), JBPM(RuntimeJbpm.class);

	private Class<?> clazz;

	private RuntimeReqType(Class<?> clazz) {
		this.clazz = clazz;
	}

	public boolean matches(RuntimeBase runtimeFamily) {
		if (runtimeFamily == null) {
			return false;
		}
		if (clazz == null) {
			return true;
		}
		return clazz.equals(runtimeFamily.getClass());
	}
}
