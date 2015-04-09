
package cn.featherfly.persistence;

import cn.featherfly.persistence.event.PersistenceExecuteBatchEvent;
import cn.featherfly.persistence.event.PersistenceExecuteEvent;
import cn.featherfly.persistence.event.PersistenceGetEvent;

/**
 * <p>
 * PersistenceAdapter
 * </p>
 * 
 * @author 钟冀
 */
public class PersistenceEventAdapter implements PersistenceEventListener{

	/**
	 */
	public PersistenceEventAdapter() {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterDelete(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterDeleteBatch(PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterGet(PersistenceGetEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterMerge(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterMergeBatch(PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterPersist(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterPersistBatch(PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterSave(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterSaveBatch(PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterSaveOrMerge(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterSaveOrMergeBatch(
			PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterSaveOrUpdate(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterSaveOrUpdateBatch(
			PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterUpdate(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterUpdateBatch(PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeDelete(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeDeleteBatch(PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeGet(PersistenceGetEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeMerge(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeMergeBatch(PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforePersist(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforePersistBatch(PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeSave(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeSaveBatch(PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeSaveOrMerge(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeSaveOrMergeBatch(
			PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeSaveOrUpdate(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeSaveOrUpdateBatch(
			PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeUpdate(PersistenceExecuteEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeUpdateBatch(PersistenceExecuteBatchEvent<E> event) {
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onAfterLoad(PersistenceGetEvent<E> event) {
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <E> void onBeforeLoad(PersistenceGetEvent<E> event) {
		
	}

}
