package cn.featherfly.persistece.jdbc;

import java.sql.ResultSet;

/**
 * <p>
 * 记录行映射接口
 * </p>
 * @param <E> 要映射的具体类
 * @author 钟冀
 */
public interface RowMapper<E> {
	/**
	 * <p>
	 * 映射记录到指定的对象
	 * </p>
	 * @param res 结果集
	 * @param rowNum 行数
	 * @return 记录映射的对象
	 */
	E mapRow(ResultSet res, int rowNum);
}