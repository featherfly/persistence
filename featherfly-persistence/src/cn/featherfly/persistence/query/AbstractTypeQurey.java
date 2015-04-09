
package cn.featherfly.persistence.query;






/**
 * <p>
 * 抽象类型查询
 * </p>
 *
 * @param <E> 类型信息
 * @param <Q> 实际的query对象
 * @author 钟冀
 */
public abstract class AbstractTypeQurey<Q extends AbstractQuery<Q, E>, E> extends AbstractQuery<Q, E>{

	/**
	 * 查询的类型
	 */
	protected Class<E> type;

	/**
	 * @param type 类型
	 */
	public AbstractTypeQurey(Class<E> type) {
		this.type = type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getAlias() {
		return type.getSimpleName().substring(0, 1).toLowerCase()
				+ type.getSimpleName().substring(1, type.getSimpleName().length());
	}
}
