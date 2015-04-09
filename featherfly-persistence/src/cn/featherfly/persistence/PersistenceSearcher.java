
package cn.featherfly.persistence;

import java.util.List;
import java.util.Map;

import cn.featherfly.common.structure.page.PaginationResults;

/**
 * <p>
 * 持久化查询接口
 * </p>
 * @author 钟冀
 */
public interface PersistenceSearcher extends Persistence{
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, final Map<String, Object> params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, final Object[] params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, final List<Object> params);
	/**
	 * 根据查询字符串查询对象列表.
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, final int start, final int limit, final Map<String, Object> params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, final int start, final int limit, final Object[] params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, final int start, final int limit, final List<Object> params);
	/**
	 * 根据查询字符串查询对象列表.
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, PaginationResults<E> pagination, final Map<String, Object> params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, PaginationResults<E> pagination, final Object[] params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, PaginationResults<E> pagination, final List<Object> params);
	/**
	 * 根据查询字符串查询对象列表.
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(final String queryStr, final int start, final int limit
			, final Map<String, Object> params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(final String queryStr, final int start, final int limit
			, final Object[] params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(final String queryStr, final int start, final int limit
			, final List<Object> params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(final String queryStr, PaginationResults<E> pagination, final Map<String, Object> params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(final String queryStr, PaginationResults<E> pagination, final Object[] params);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(final String queryStr, PaginationResults<E> pagination, final List<Object> params);
	/**
	 * 根据查询字符串查询对象列表.
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param params 参数
	 * @param start 起始位置
	 * @param limit 返回数
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, final Map<String, Object> params
			, final int start, final int limit);
	/**
	 * 根据查询字符串查询对象列表.
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param params 参数
	 * @param pagination 分页模型
	 * @return 对象列表
	 */
	<E> List<E> findList(final String queryStr, final Map<String, Object> params, PaginationResults<E> pagination);
	/**
	 * 根据查询字符串查询对象列表.
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param params 参数
	 * @param start 起始位置
	 * @param limit 返回数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(final String queryStr, final Map<String, Object> params
			, final int start, final int limit);
	/**
	 * 根据查询字符串查询对象列表
	 * @param <E> 实体类型
	 * @param queryStr 查询字符串
	 * @param params 参数
	 * @param pagination 分页模型
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(final String queryStr, final Map<String, Object> params, PaginationResults<E> pagination);

}
