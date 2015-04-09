
package cn.featherfly.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.featherfly.common.lang.ClassUtils;
import cn.featherfly.persistence.event.PersistenceExecuteBatchEvent;
import cn.featherfly.persistence.event.PersistenceExecuteEvent;
import cn.featherfly.persistence.event.PersistenceGetEvent;

/**
 * <p>
 * 持久化观察者
 * </p>
 * @author 钟冀
 */
public abstract class PersistenceObserver implements PersistenceBatch {
	
	/**
	 * 日志
	 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void persist(E entity) {
		PersistenceExecuteEvent<E> event = new PersistenceExecuteEvent<E>();
		event.setDataSource(getDataSource());
		event.setEntity(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforePersist, listener: {}" + listener.getClass().getName());
			listener.onBeforePersist(event);
		}
		doPersist(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterPersist, listener: {}" + listener.getClass().getName());
			listener.onAfterPersist(event);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void save(E entity) {
		PersistenceExecuteEvent<E> event = new PersistenceExecuteEvent<E>();
		event.setDataSource(getDataSource());
		event.setEntity(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeSave, listener: {}" + listener.getClass().getName());
			listener.onBeforeSave(event);
		}
		doSave(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterSave, listener: {}" + listener.getClass().getName());
			listener.onAfterSave(event);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void update(E entity) {
		PersistenceExecuteEvent<E> event = new PersistenceExecuteEvent<E>();
		event.setDataSource(getDataSource());
		event.setEntity(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeUpdate, listener: {}" + listener.getClass().getName());
			listener.onBeforeUpdate(event);
		}
		doUpdate(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterUpdate, listener: {}" + listener.getClass().getName());
			listener.onAfterUpdate(event);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void saveOrUpdate(E entity) {
		PersistenceExecuteEvent<E> event = new PersistenceExecuteEvent<E>();
		event.setDataSource(getDataSource());
		event.setEntity(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeSaveOrUpdate, listener: {}" + listener.getClass().getName());
			listener.onBeforeSaveOrUpdate(event);
		}
		doSaveOrUpdate(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterSaveOrUpdate, listener: {}" + listener.getClass().getName());
			listener.onAfterSaveOrUpdate(event);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void saveOrMerge(E entity) {
		PersistenceExecuteEvent<E> event = new PersistenceExecuteEvent<E>();
		event.setDataSource(getDataSource());
		event.setEntity(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeSaveOrMerge, listener: {}" + listener.getClass().getName());
			listener.onBeforeSaveOrMerge(event);
		}
		doSaveOrMerge(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterSaveOrMerge, listener: {}" + listener.getClass().getName());
			listener.onAfterSaveOrMerge(event);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void delete(E entity) {
		PersistenceExecuteEvent<E> event = new PersistenceExecuteEvent<E>();
		event.setDataSource(getDataSource());
		event.setEntity(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeDelete, listener: {}" + listener.getClass().getName());
			listener.onBeforeDelete(event);
		}
		doDelete(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterDelete, listener: {}" + listener.getClass().getName());
			listener.onAfterDelete(event);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void merge(E entity) {
		PersistenceExecuteEvent<E> event = new PersistenceExecuteEvent<E>();
		event.setDataSource(getDataSource());
		event.setEntity(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeMerge, listener: {}" + listener.getClass().getName());
			listener.onBeforeMerge(event);
		}
		doMerge(entity);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterMerge, listener: {}" + listener.getClass().getName());
			listener.onAfterMerge(event);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> E get(Serializable id, Class<E> type) {
		PersistenceGetEvent<E> event = new PersistenceGetEvent<E>();
		event.setDataSource(getDataSource());
		event.setIdentity(id);
		event.setType(type);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeGet, listener: {}" + listener.getClass().getName());
			listener.onBeforeGet(event);
		}
		E e = doGet(id, type);
		event.setEntity(e);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterGet, listener: {}" + listener.getClass().getName());
			listener.onAfterGet(event);
		}
		return e;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> E load(E entity) {
		PersistenceGetEvent<E> event = new PersistenceGetEvent<E>();
		event.setDataSource(getDataSource());
		event.setIdentity(getIdentity(entity));
		event.setEntity(entity);
		if (entity != null) {
			event.setType(ClassUtils.castGenericType(entity.getClass(), entity));
		}
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeLoad, listener: {}" + listener.getClass().getName());
			listener.onBeforeLoad(event);
		}
		E e = doLoad(entity);
		event.setEntity(e);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterLoad, listener: {}" + listener.getClass().getName());
			listener.onAfterLoad(event);
		}
		return e;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void deleteBatch(List<E> entityList) {
		PersistenceExecuteBatchEvent<E> event = new PersistenceExecuteBatchEvent<E>();
		event.setDataSource(getDataSource());
		event.addEntitys(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeDeleteBatch, listener: {}" + listener.getClass().getName());
			listener.onBeforeDeleteBatch(event);
		}
		doDeleteBatch(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterDeleteBatch, listener: {}" + listener.getClass().getName());
			listener.onAfterDeleteBatch(event);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void mergeBatch(List<E> entityList) {
		PersistenceExecuteBatchEvent<E> event = new PersistenceExecuteBatchEvent<E>();
		event.setDataSource(getDataSource());
		event.addEntitys(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeMergeBatch, listener: {}" + listener.getClass().getName());
			listener.onBeforeMergeBatch(event);
		}
		doMergeBatch(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterMergeBatch, listener: {}" + listener.getClass().getName());
			listener.onAfterMergeBatch(event);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void persistBatch(List<E> entityList) {
		PersistenceExecuteBatchEvent<E> event = new PersistenceExecuteBatchEvent<E>();
		event.setDataSource(getDataSource());
		event.addEntitys(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforePersistBatch, listener: {}" + listener.getClass().getName());
			listener.onBeforePersistBatch(event);
		}
		doPersistBatch(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterPersistBatch, listener: {}" + listener.getClass().getName());
			listener.onAfterPersistBatch(event);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void saveBatch(List<E> entityList) {
		PersistenceExecuteBatchEvent<E> event = new PersistenceExecuteBatchEvent<E>();
		event.setDataSource(getDataSource());
		event.addEntitys(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeSaveBatch, listener: {}" + listener.getClass().getName());
			listener.onBeforeSaveBatch(event);
		}
		doSaveBatch(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterSaveBatch, listener: {}" + listener.getClass().getName());
			listener.onAfterSaveBatch(event);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void saveOrMergeBatch(List<E> entityList) {
		PersistenceExecuteBatchEvent<E> event = new PersistenceExecuteBatchEvent<E>();
		event.setDataSource(getDataSource());
		event.addEntitys(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeSaveOrMergeBatch, listener: {}" + listener.getClass().getName());
			listener.onBeforeSaveOrMergeBatch(event);
		}
		doSaveOrMergeBatch(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterSaveOrMergeBatch, listener: {}" + listener.getClass().getName());
			listener.onAfterSaveOrMergeBatch(event);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void saveOrUpdateBatch(List<E> entityList) {
		PersistenceExecuteBatchEvent<E> event = new PersistenceExecuteBatchEvent<E>();
		event.setDataSource(getDataSource());
		event.addEntitys(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeSaveOrUpdateBatch, listener: {}" + listener.getClass().getName());
			listener.onBeforeSaveOrUpdateBatch(event);
		}
		doSaveOrUpdateBatch(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterSaveOrUpdateBatch, listener: {}" + listener.getClass().getName());
			listener.onAfterSaveOrUpdateBatch(event);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void updateBatch(List<E> entityList) {
		PersistenceExecuteBatchEvent<E> event = new PersistenceExecuteBatchEvent<E>();
		event.setDataSource(getDataSource());
		event.addEntitys(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onBeforeUpdateBatch, listener: {}" + listener.getClass().getName());
			listener.onBeforeUpdateBatch(event);
		}
		doUpdateBatch(entityList);
		for (PersistenceEventListener listener : listeners) {
			logger.debug("onAfterUpdateBatch, listener: {}" + listener.getClass().getName());
			listener.onAfterUpdateBatch(event);
		}
	}
	
	// ********************************************************************
	//	abstract method
	// ********************************************************************
	
	/**
	 * <p>
	 * 持久化对象
	 * </p>
	 * @param <E> 泛型
	 * @param entity 实体
	 */
	protected abstract <E> void doPersist(E entity);
	/**
	 * <p>
	 * 保存对象
	 * </p>
	 * @param <E> 泛型
	 * @param entity 实体
	 */
	protected abstract <E> void doSave(E entity);
	/**
	 * <p>
	 * 更新对象
	 * </p>
	 * @param <E> 泛型
	 * @param entity 实体
	 */
	protected abstract <E> void doUpdate(E entity);
	/**
	 * <p>
	 * 保存或更新对象
	 * </p>
	 * @param <E> 泛型
	 * @param entity 实体
	 */
	protected abstract <E> void doSaveOrUpdate(E entity);
	/**
	 * <p>
	 * 保存或合并对象
	 * </p>
	 * @param <E> 泛型
	 * @param entity 实体
	 */
	protected abstract <E> void doSaveOrMerge(E entity);
	/**
	 * <p>
	 * 删除对象
	 * </p>
	 * @param <E> 泛型
	 * @param entity 实体
	 */
	protected abstract <E> void doDelete(E entity);
	/**
	 * <p>
	 * 合并对象
	 * </p>
	 * @param <E> 泛型
	 * @param entity 实体
	 */
	protected abstract <E> void doMerge(E entity);
	/**
	 * <p>
	 * 根据id查找对象
	 * </p>
	 * @param <E> 对象类型
	 * @param id 唯一标识（主键）
	 * @param type 对象类
	 * @return 指定唯一标识的对象
	 */
	protected abstract <E> E doGet(Serializable id, Class<E> type);
	/**
	 * 根据实体对象的唯一标示加载对象
	 * @param <E> 实体类型
	 * @param entity 实体对象
	 * @return 传入实体的持久化对象
	 */
	protected abstract <E> E doLoad(E entity);
	/**
	 * <p>
	 * 批量持久化对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体集合
	 */
	protected abstract <E> void doPersistBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量保存对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体集合
	 */
	protected abstract <E> void doSaveBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量更新对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体集合
	 */
	protected abstract <E> void doUpdateBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量保存或更新对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体集合
	 */
	protected abstract <E> void doSaveOrUpdateBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量保存或合并对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体集合
	 */
	protected abstract <E> void doSaveOrMergeBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量删除对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体集合
	 */
	protected abstract <E> void doDeleteBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量合并对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体集合
	 */
	protected abstract <E> void doMergeBatch(List<E> entityList);

	// ********************************************************************
	//	property
	// ********************************************************************
	
	private List<PersistenceEventListener> listeners = new ArrayList<PersistenceEventListener>();
	/**
	 * 添加监听器
	 * @param listener 监听器
	 */
	public void addListener(PersistenceEventListener listener) {
		this.listeners.add(listener);
	}
	/**
	 * 添加监听器（复数）
	 * @param listeners 监听器
	 */
	public void addListeners(PersistenceEventListener...listeners) {
		addListeners(Arrays.asList(listeners));
	}
	/**
	 * 添加监听器集合
	 * @param listeners 监听器集合
	 */
	public void addListeners(List<PersistenceEventListener> listeners) {
		this.listeners.addAll(listeners);
	}
	/**
	 * 移除监听器
	 * @param listener 监听器
	 */
	public void removeListener(PersistenceEventListener listener) {
		this.listeners.remove(listener);
	}
	/**
	 * 清楚所有监听器
	 */
	public void clearListener() {
		this.listeners.clear();
	}
	/**
	 * <p>
	 * 返回所有事件
	 * </p>
	 * @return 所有事件
	 */
	public List<PersistenceEventListener> getListeners() {
		return new ArrayList<PersistenceEventListener>(this.listeners);
	}
	/**
	 * 设置事件
	 * @param listeners 事件集合
	 */
	public void setListeners(List<PersistenceEventListener> listeners) {
		this.listeners = listeners;
	}
}
