
package cn.featherfly.persistence.query;

import java.util.List;

import cn.featherfly.common.structure.page.PaginationResults;

/**
 * <p>
 * 查询接口
 * </p>
 *
 * @param <E> 类型信息
 * @param <Q> 实际的query对象
 *
 * @author 钟冀
 */
public interface Query<Q extends Query<Q, E>, E> {
	/**
	 *
	 * <p>
	 * 返回唯一对象
	 * </p>
	 * @return 唯一对象
	 */
	E unique();

	/**
	 * <p>
	 * 返回列表
	 * </p>
	 * @return 列表
	 */
	List<E> list();

	/**
	 * <p>
	 * 统计
	 * </p>
	 * @return 总数
	 */
	long count();

//	/**
//	 * <p>
//	 * 添加升序条件
//	 * </p>
//	 * @param names 排序名称
//	 * @return this 链式调用
//	 */
//	Query<E> asc(String...names);
//	/**
//	 * <p>
//	 * 添加降序条件
//	 * </p>
//	 * @param names 排序名称
//	 * @return this 链式调用
//	 */
//	Query<E> desc(String...names);

	/**
	 * <p>
	 * 设置返回最大结果数
	 * </p>
	 * @param maxResult 最大结果集
	 * @return this 链式调用
	 */
	Q setMaxResult(int maxResult);

	/**
	 * <p>
	 * 设置第一条
	 * </p>
	 * @param firstResult 第一条
	 * @return this 链式调用
	 */
	Q setFirstResult(int firstResult);

	/**
	 * <p>
	 * 设置分页模型
	 * </p>
	 * @param pagination 分页模型
	 * @return this 链式调用
	 */
	Q setPagination(PaginationResults<E> pagination);
}
