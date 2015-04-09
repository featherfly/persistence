
package cn.featherfly.persistence.event;

import java.io.Serializable;

/**
 * <p>
 * PersistenceGetEvent
 * </p>
 * @param <E> 泛型
 * @author 钟冀
 */
public class PersistenceGetEvent<E> extends PersistenceExecuteEvent<E>{
	/**
	 */
	public PersistenceGetEvent() {
	}
	
	private Serializable identity;
	
	private Class<E> type;
	
	/**
	 * 返回identity
	 * @return identity
	 */
	public Serializable getIdentity() {
		return identity;
	}

	/**
	 * 设置identity
	 * @param identity identity
	 */
	public void setIdentity(Serializable identity) {
		this.identity = identity;
	}

	/**
	 * 返回type
	 * @return type
	 */
	public Class<E> getType() {
		return type;
	}

	/**
	 * 设置type
	 * @param type type
	 */
	public void setType(Class<E> type) {
		this.type = type;
	}
}
