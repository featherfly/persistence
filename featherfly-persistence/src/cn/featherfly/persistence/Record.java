
package cn.featherfly.persistence;

import java.io.Serializable;

/**
 * <p>
 * 持久化记录接口
 * </p>
 * @param <E> 具体类型
 * @param <ID> ID类型
 * @author 钟冀
 */
public interface Record<E extends Record<E, ?>, ID extends Serializable> {
	/**
	 * <p>
	 * 保存对象
	 * </p>
	 */
	void save();
	/**
	 * <p>
	 * 更新对象
	 * </p>
	 */
	void update();
	/**
	 * 合并，忽略传入对象的空值，将其余值更新进数据库
	 * @return 合并后的对象
	 */
	E merge();
	/**
	 * 删除对象
	 * @return 删除的对象
	 */
	E delete();
	/**
	 * 根据id查找对象
	 * @param id 唯一标识（主键）
	 * @return 指定唯一标识的对象
	 */
	E get(ID id);
	/**
	 * 根据实体对象的唯一标示加载对象
	 * @return 实体的持久化对象
	 */
	E load();
	/**
	 * 返回唯一标示
	 * @return 唯一标示
	 */
	ID getId();
}
