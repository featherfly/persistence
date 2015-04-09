
package cn.featherfly.persistence;

import java.util.List;

import cn.featherfly.common.db.dialect.Dialect;

/**
 * <p>
 * 持久化接口
 * </p>
 * @author 钟冀
 */
public interface PersistenceBatch extends Persistence{
	/**
	 * 默认的批量处理数量
	 */
	int DEFAULT_BATCH_SIZE = 20;
	/**
	 * 默认的查询数据数量
	 */
	int DEFAULT_LIMIT = Dialect.DEFAULT_LIMIT;
	/**
	 * <p>
	 * 批量持久化对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体对象列表
	 */
	<E> void persistBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量保存对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体对象列表
	 */
	<E> void saveBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量更新对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体对象列表
	 */
	<E> void updateBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量保存或更新对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体对象列表
	 */
	<E> void saveOrUpdateBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量保存或合并对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体对象列表
	 */
	<E> void saveOrMergeBatch(List<E> entityList);
	/**
	 * <p>
	 * 批量删除对象
	 * </p>
	 * @param <E> 泛型
	 * @param entityList 实体对象列表
	 */
	<E> void deleteBatch(List<E> entityList);
	/**
	 * 批量合并对象
	 * @param <E> 泛型
	 * @param entityList 对象集合
	 */
	<E> void mergeBatch(List<E> entityList);
}
