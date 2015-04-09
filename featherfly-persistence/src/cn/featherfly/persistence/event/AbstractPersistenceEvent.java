
package cn.featherfly.persistence.event;

import javax.sql.DataSource;

import cn.featherfly.persistence.PersistenceEvent;

/**
 * <p>
 * PersistenceGetEvent
 * </p>
 * @param <E> 泛型
 * @author 钟冀
 */
public abstract class AbstractPersistenceEvent<E> implements PersistenceEvent<E> {
	/**
	 */
	public AbstractPersistenceEvent() {
	}

	/**
	 * 数据源
	 */
	protected DataSource dataSource;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public DataSource getDataSourc() {
		return dataSource;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
} 
