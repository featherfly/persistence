
package cn.featherfly.persistence.jdbc.query;

import java.util.List;

import cn.featherfly.common.db.SqlUtils;
import cn.featherfly.common.db.builder.ConditionBuildUtils;
import cn.featherfly.persistence.jdbc.JdbcPersistence;
import cn.featherfly.persistence.query.AbstractTypeQurey;

/**
 * <p>
 * hql查询
 * </p>
 *
 * @param <Q> 查询对象本身的类型
 * @param <E> 类型信息
 *
 * @author 钟冀
 */
public abstract class SqlQuery<Q extends SqlQuery<Q, E>, E> extends AbstractTypeQurey<Q, E>{
	/**
	 * @param type 类型
	 */
	public SqlQuery(Class<E> type) {
		super(type);
	}

	/**
	 * @param type 类型
	 * @param jdbcPersistence jdbcPersistence
	 */
	public SqlQuery(Class<E> type, JdbcPersistence jdbcPersistence) {
		super(type);
		this.jdbcPersistence = jdbcPersistence;
	}

	/**
	 * hibernatePersistence
	 */
	protected JdbcPersistence jdbcPersistence;

	/**
	 * 设置jdbcPersistence
	 * @param jdbcPersistence jdbcPersistence
	 */
	public void setJdbcPersistence(JdbcPersistence jdbcPersistence) {
		this.jdbcPersistence = jdbcPersistence;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public E unique() {
		return (E) jdbcPersistence.find(buildSql(), type, getParams());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<E> list() {
		if (getPagination() != null) {
			return jdbcPersistence.findList(buildSql(), type, getPagination(), getParams());
		} else {
			return jdbcPersistence.findList(buildSql(), type, getFirstResult()
					, getMaxResult(), getParams());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long count() {
		String hql = SqlUtils.convertSelectToCount(buildSql());
		return jdbcPersistence.findForLong(hql, getParams());
	}

	/**
	 * <p>
	 * 获取select XX from XX 部分的hql
	 * </p>
	 * @return 部分hql
	 */
	protected abstract StringBuilder getSelectAndFrom();
//		StringBuilder selectAndFromSql = new StringBuilder();
//		selectAndFromSql.append("select * FROM ")
//			.append(type.getSimpleName());
//		String alias = getAlias();
//		if (StringUtils.isNotBlank(alias)) {
//			selectAndFromSql.append(" as ")
//				.append(getAlias());
//		}
//		return selectAndFromSql;
//	}

	private String buildSql() {
		StringBuilder hql = getSelectAndFrom();
		ConditionBuildUtils.appendCondition(hql, getCondition().toString());
		return hql.toString();
	}
}
