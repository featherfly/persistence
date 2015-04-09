
package cn.featherfly.persistence.query;

import java.util.List;

import cn.featherfly.common.db.builder.ConditionBuilder;
import cn.featherfly.common.db.builder.LogicBuilder;
import cn.featherfly.common.db.operator.LogicOperator;
import cn.featherfly.common.db.operator.QueryOperator;
import cn.featherfly.common.structure.page.PaginationResults;


/**
 * <p>
 * 抽象查询
 * </p>
 *
 * @param <E> 类型信息
 * @param <Q> 实际的query对象
 *
 * @author 钟冀
 */
public abstract class AbstractQuery<Q extends AbstractQuery<Q, E>, E> implements Query<Q, E>{

	/**
	 * conditionBuilder
	 */
	protected ConditionBuilder conditionBuilder;

	private LogicBuilder logicBuilder;

	/**
	 * 查询返回的最大条数（null和-1为忽略）
	 */
	private int maxResult = -1;
	/**
	 * 查询开始的条数(null和-1为忽略)
	 */
	private int firstResult;
	/*
	 * 分页模型
	 */
	private PaginationResults<E> pagination;

	/**
	 */
	public AbstractQuery() {
		conditionBuilder = new ConditionBuilder();
		conditionBuilder.setBuildWithWhere(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Q setMaxResult(int maxResult) {
		this.maxResult = maxResult;
		return (Q) this;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Q setFirstResult(int first) {
		this.firstResult = first;
		return (Q) this;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Q setPagination(PaginationResults<E> pagination) {
		this.pagination = pagination;
		return (Q) this;
	}

	/**
	 * 返回maxResult
	 * @return maxResult
	 */
	public int getMaxResult() {
		return maxResult;
	}

	/**
	 * 返回firstResult
	 * @return firstResult
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * 返回pagination
	 * @return pagination
	 */
	public PaginationResults<E> getPagination() {
		return pagination;
	}

	// ********************************************************************
	//
	// ********************************************************************

	/**
	 * <p>
	 * 添加条件
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param queryOperator 条件运算符
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q addCondition(String name, Object value, QueryOperator queryOperator
			, LogicOperator logicOperator) {
		if (logicBuilder != null) {
			if (logicOperator == LogicOperator.AND) {
				and();
			} else {
				or();
			}
		}
		logicBuilder = conditionBuilder.add(name, value, queryOperator);
		return (Q) this;
	}

	/**
	 * <p>
	 * 小于
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q lt(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.lt, logicOperator);
		return (Q) this;
	}

	/**
	 * <p>
	 * 小于等于
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q le(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.le, logicOperator);
		return (Q) this;
	}

	/**
	 * <p>
	 * 等于
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q eq(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.eq, logicOperator);
		return (Q) this;
	}
	/**
	 * <p>
	 * 不等于
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q ne(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.ne, logicOperator);
		return (Q) this;
	}

	/**
	 * <p>
	 * 大于等于
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q ge(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.ge, logicOperator);
		return (Q) this;
	}

	/**
	 * <p>
	 * 大于
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q gt(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.gt, logicOperator);
		return (Q) this;
	}


	/**
	 * <p>
	 * 开始于
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q sw(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.sw, logicOperator);
		return (Q) this;
	}

	/**
	 * <p>
	 * 包含
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q co(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.co, logicOperator);
		return (Q) this;
	}

	/**
	 * <p>
	 * 结束于
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q ew(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.ew, logicOperator);
		return (Q) this;
	}
	/**
	 * <p>
	 * 包含, in (?,?)
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q in(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.in, logicOperator);
		return (Q) this;
	}
	/**
	 * <p>
	 * 不包含, not in (?,?)
	 * </p>
	 * @param name 名称
	 * @param value 值
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q nin(String name, Object value, LogicOperator logicOperator) {
		addCondition(name, value, QueryOperator.in, logicOperator);
		return (Q) this;
	}
	/**
	 * <p>
	 * 为空，is null
	 * </p>
	 * @param name 名称
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q isn(String name, LogicOperator logicOperator) {
		addCondition(name, null, QueryOperator.isn, logicOperator);
		return (Q) this;
	}
	/**
	 * <p>
	 * 不为空，is not null
	 * </p>
	 * @param name 名称
	 * @param logicOperator 逻辑运算符
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	protected Q inn(String name, LogicOperator logicOperator) {
		addCondition(name, null, QueryOperator.inn, logicOperator);
		return (Q) this;
	}

//	/**
//	 * @return 逻辑表达式建造者
//	 * @see cn.featherfly.common.db.builder.ConditionBuilder#getParent()
//	 */
//	protected LogicBuilder getParent() {
//		return conditionBuilder.getParent();
//	}
//
//	/**
//	 * @return 逻辑表达式建造者
//	 * @see cn.featherfly.common.db.builder.ConditionBuilder#group()
//	 */
//	protected ExpressionBuilder group() {
//		return conditionBuilder.group();
//	}

	/**
	 * <p>
	 * 添加升序条件
	 * </p>
	 * @param names 排序名称
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public Q asc(String...names) {
		conditionBuilder.asc(names);
		return (Q) this;
	}

	/**
	 * <p>
	 * 添加降序条件
	 * </p>
	 * @param names 排序名称
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public Q desc(String...names) {
		conditionBuilder.desc(names);
		return (Q) this;
	}

	/**
	 * <p>
	 * 添加升序条件到最前面
	 * </p>
	 * @param names 排序名称
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public Q ascFirst(String... names) {
		conditionBuilder.ascFirst(names);
		return (Q) this;
	}

	/**
	 * <p>
	 * 清楚排序条件
	 * </p>
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public Q clearOrders() {
		conditionBuilder.clearOrders();
		return (Q) this;
	}

	/**
	 * <p>
	 * 添加降序条件到最前面
	 * </p>
	 * @param names 排序名称
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public Q descFirst(String... names) {
		conditionBuilder.descFirst(names);
		return (Q) this;
	}

	/**
	 * 返回参数
	 * @return 参数
	 */
	protected List<Object> getParams() {
		return conditionBuilder.getParams();
	}

	/**
	 * <p>
	 * 获取查询条件
	 * </p>
	 * @return 查询条件
	 */
	protected String getCondition() {
		return conditionBuilder.build();
	}

	/**
	 * <p>
	 * 获取查询别名
	 * </p>
	 * @return 查询别名
	 */
	protected abstract String getAlias();

	// ********************************************************************
	//
	// ********************************************************************

	/**
	 * <p>
	 * and
	 * </p>
	 */
	protected void and() {
		if (logicBuilder != null) {
			logicBuilder.and();
		}
	}

	/**
	 * <p>
	 * or
	 * </p>
	 */
	protected void or() {
		if (logicBuilder != null) {
			logicBuilder.or();
		}
	}
}
