
package cn.featherfly.persistence.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * PersistenceExecuteBatchEvent
 * </p>
 * @param <E> 泛型
 * @author 钟冀
 */
public class PersistenceExecuteBatchEvent<E> extends AbstractPersistenceEvent<E>{
	/**
	 */
	public PersistenceExecuteBatchEvent() {
	}
	
	/**
	 * 添加事件原对象
	 * @param entity 监听器
	 */
	public void addEntity(E entity) {
		this.entitys.add(entity);
	}
	/**
	 * 添加监听器（复数）
	 * @param entitys 监听器
	 */
	public void addEntitys(@SuppressWarnings("unchecked") E...entitys) {
		addEntitys(Arrays.asList(entitys));
	}
	/**
	 * 添加监听器集合
	 * @param entitys 监听器集合
	 */
	public void addEntitys(List<E> entitys) {
		this.entitys.addAll(entitys);
	}
	/**
	 * 添加实体唯一标示
	 * @param identity 唯一标示
	 */
	public void addIdentity(Serializable identity) {
		this.identitys.add(identity);
	}
	/**
	 * 添加实体唯一标示（复数）
	 * @param identitys 唯一标示
	 */
	public void addIdentitys(Serializable...identitys) {
		addIdentitys(Arrays.asList(identitys));
	}
	/**
	 * 添加实体唯一标示集合
	 * @param identitys 唯一标示集合
	 */
	public void addIdentitys(List<Serializable> identitys) {
		this.identitys.addAll(identitys);
	}
	
	// ********************************************************************
	//	property
	// ********************************************************************
	
	private List<Serializable> identitys = new ArrayList<Serializable>();
	
	private List<E> entitys = new ArrayList<E>();
	
	/**
	 * 返回entitys
	 * @return entitys
	 */
	public List<?> getEntitys() {
		return entitys;
	}

	/**
	 * 设置entitys
	 * 
	 * @param entitys entitys
	 */
	public void setEntitys(List<E> entitys) {
		this.entitys = entitys;
	}

	/**
	 * 返回identitys
	 * @return identitys
	 */
	public List<Serializable> getIdentitys() {
		return identitys;
	}

	/**
	 * 设置identitys
	 * @param identitys identitys
	 */
	public void setIdentitys(List<Serializable> identitys) {
		this.identitys = identitys;
	}
}
