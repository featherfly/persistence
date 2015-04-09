
package cn.featherfly.persistence.event;


/**
 * <p>
 * PersistenceExecuteEvent
 * </p>
 * @param <E> 泛型
 * @author 钟冀
 */
public class PersistenceExecuteEvent<E> extends AbstractPersistenceEvent<E>{
	/**
	 */
	public PersistenceExecuteEvent() {
	}
	
	// ********************************************************************
	//	property
	// ********************************************************************
	
	private E entity;

	/**
	 * 返回entity
	 * @return entity
	 */
	public E getEntity() {
		return entity;
	}

	/**
	 * 设置entity
	 * @param entity entity
	 */
	public void setEntity(E entity) {
		this.entity = entity;
	}
	

}
