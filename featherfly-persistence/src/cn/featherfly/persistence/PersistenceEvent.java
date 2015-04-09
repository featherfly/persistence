
package cn.featherfly.persistence;

import javax.sql.DataSource;


/**
 * <p>
 * PersistenceEvent
 * </p>
 * @param <E> 泛型
 * @author 钟冀
 */
public interface PersistenceEvent<E> {
	/**
	 * <p>
	 * 设置数据源
	 * </p>
	 * @param dataSource 设置数据源
	 */
	void setDataSource(DataSource dataSource);
	/**
	 * <p>
	 * 返回数据源
	 * </p>
	 * @return 数据源
	 */
	DataSource getDataSourc();
}
