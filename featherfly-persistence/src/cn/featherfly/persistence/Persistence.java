
package cn.featherfly.persistence;

import java.io.Serializable;

import javax.sql.DataSource;

/**
 * <p>
 * 持久化接口
 * </p>
 * @author 钟冀
 */
public interface Persistence {
	/**
	 * <p>
	 * 持久化对象
	 * </p>
	 * @param <E> 对象类型
	 * @param entity 实体对象
	 */
	<E> void persist(E entity);
	/**
	 * <p>
	 * 保存对象
	 * </p>
	 * @param <E> 对象类型
	 * @param entity 实体对象
	 */
	<E> void save(E entity);
	/**
	 * <p>
	 * 更新对象
	 * </p>
	 * @param <E> 对象类型
	 * @param entity 实体对象
	 */
	<E> void update(E entity);
	/**
	 * <p>
	 * 保存或更新对象
	 * </p>
	 * @param <E> 对象类型
	 * @param entity 实体对象
	 */
	<E> void saveOrUpdate(E entity);
	/**
	 * <p>
	 * 保存或合并对象
	 * </p>
	 * @param <E> 对象类型
	 * @param entity 实体对象
	 */
	<E> void saveOrMerge(E entity);
	/**
	 * 删除对象
	 *
	 * @param <E> 对象类型
	 * @param entity 实体对象
	 */
	<E> void delete(E entity);
	/**
	 * 合并，忽略传入对象的空值，将其余值更新进数据库
	 * @param <E> 对象类型
	 * @param entity
	 *            操作对象
	 */
	<E> void merge(E entity);
	/**
	 * 根据id查找对象
	 * @param <E> 对象类型
	 * @param id 唯一标识（主键）
	 * @param type 对象类
	 * @return 指定唯一标识的对象
	 */
	<E> E get(Serializable id, Class<E> type);
	/**
	 * 根据实体对象的唯一标示加载对象
	 * @param <E> 实体类型
	 * @param entity 实体对象
	 * @return 传入实体的持久化对象
	 */
	<E> E load(E entity);
	/**
	 * 返回传入实体的唯一标示值
	 * @param <E> 实体类型
	 * @param entity 实体对象
	 * @return 传入实体的唯一标示值
	 */
	<E> Serializable getIdentity(E entity);
	/**
	 * <p>
	 * 返回数据源
	 * </p>
	 * @return 数据源
	 */
	DataSource getDataSource();
}
