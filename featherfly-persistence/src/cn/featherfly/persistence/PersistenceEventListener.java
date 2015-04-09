
package cn.featherfly.persistence;

import cn.featherfly.persistence.event.PersistenceExecuteBatchEvent;
import cn.featherfly.persistence.event.PersistenceExecuteEvent;
import cn.featherfly.persistence.event.PersistenceGetEvent;


/**
 * <p>
 * PersistenceListener
 * </p>
 * 
 * @author 钟冀
 */
public interface PersistenceEventListener {
	/**
	 * <p>
	 * 持久化执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforePersist(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 持久化执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterPersist(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 保存执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeSave(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 保存执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterSave(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 更新执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeUpdate(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 更新执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterUpdate(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 保存更新执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeSaveOrUpdate(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 保存更新执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterSaveOrUpdate(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 保存合并执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeSaveOrMerge(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 保存合并执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterSaveOrMerge(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 删除执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeDelete(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 删除执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterDelete(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 合并执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeMerge(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 合并执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterMerge(PersistenceExecuteEvent<E> event);
	/**
	 * <p>
	 * 获取对象前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeGet(PersistenceGetEvent<E> event);
	/**
	 * <p>
	 * 获取对象后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterGet(PersistenceGetEvent<E> event);
	/**
	 * <p>
	 * 获取对象前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeLoad(PersistenceGetEvent<E> event);
	/**
	 * <p>
	 * 获取对象后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterLoad(PersistenceGetEvent<E> event);
	/**
	 * <p>
	 * 批量持久化执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforePersistBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量持久化执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterPersistBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量保存执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeSaveBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量保存执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterSaveBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量更新执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeUpdateBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量更新执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterUpdateBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量保存或更新执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeSaveOrUpdateBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量保存或更新执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterSaveOrUpdateBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量保存或合并执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeSaveOrMergeBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量保存或合并执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterSaveOrMergeBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量删除执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeDeleteBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量删除执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterDeleteBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量合并执行前
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onBeforeMergeBatch(PersistenceExecuteBatchEvent<E> event);
	/**
	 * <p>
	 * 批量合并执行后
	 * </p>
	 * @param <E> 泛型
	 * @param event 事件对象
	 */
	<E> void onAfterMergeBatch(PersistenceExecuteBatchEvent<E> event);
}
