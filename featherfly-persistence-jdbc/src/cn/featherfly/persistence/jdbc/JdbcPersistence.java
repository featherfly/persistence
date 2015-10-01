package cn.featherfly.persistence.jdbc;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.featherfly.common.db.builder.ConditionBuilder;
import cn.featherfly.common.structure.page.Pagination;
import cn.featherfly.common.structure.page.PaginationResults;
import cn.featherfly.persistence.PersistenceBatch;



/**
 * <p>
 * jdbc持久化实现接口.
 * 说明：<br/>
 * 1. 凡是参数以MAP形式传递的，其SQL语句必须是命名参数形式（update user set username = :username）<br/>
 * 2. 凡是参数以数组形式传递的，其SQL语句必须是问号占位形式（update user set username = ?） <br/>
 * </p>
 * @author 钟冀
 */
public interface JdbcPersistence extends PersistenceBatch{
	/**
	 * <p>
	 * 执行指定SQL语句.
	 * 只支持命名参数查询（update user set username = :username）
	 * </p>
	 * @param sql sql语句
	 * @param params 参数
	 * @return 影响的行数
	 */
	int execute(String sql, Map<String, Object> params);
	/**
	 * <p>
	 * 执行指定SQL语句.
	 * 只支持问号占位形式
	 * </p>
	 * @param sql sql语句
	 * @param params 参数
	 * @return 影响的行数
	 */
	int execute(String sql, Object[] params);
	/**
	 * <p>
	 * 执行指定SQL语句.
	 * 只支持问号占位形式
	 * </p>
	 * @param sql sql语句
	 * @param params 参数
	 * @return 影响的行数
	 */
	int execute(String sql, List<Object> params);
//	/**
//	 * <p>
//	 * 插入.
//	 * 将指定对象的值插入指定的表中，
//	 * 具体的表名从传入对象类上的注解@Table（javax.persistence.Table）的name属性获取
//	 * </p>
//	 * @param object 值对象
//	 * @return 影响的行数
//	 */
//	int insert(Object object);
//	/**
//	 * <p>
//	 * 插入并返回自动生成的主键值.
//	 * 将指定对象的值插入指定的表中，
//	 * 具体的表名从传入对象类上的注解@Table（javax.persistence.Table）的name属性获取
//	 * </p>
//	 * @param object 值对象
//	 * @param pkColumnName 主键列名
//	 * @return 自动生成的主键
//	 */
//	Number insert(Object object, String pkColumnName);
	/**
	 * <p>
	 * 插入.
	 * 将指定对象的值插入指定的表中
	 * </p>
	 * @param tableName 数据库表名称
	 * @param object 值对象
	 * @return 影响的行数
	 */
	int insert(String tableName, Object object);
	/**
	 * <p>
	 * 插入并返回自动生成的主键值.
	 * 将指定对象的值插入指定的表中，
	 * 具体的表名从传入对象类上的注解@Table的name属性获取
	 * </p>
	 * @param tableName 数据库表名称
	 * @param object 值对象
	 * @param pkColumnNames 主键列名
	 * @return 自动生成的主键
	 */
	Number insert(String tableName, Object object, String[] pkColumnNames);
	/**
	 * <p>
	 * 插入并返回自动生成的主键值.
	 * 将指定对象的值插入指定的表中，
	 * 具体的表名从传入对象类上的注解@Table的name属性获取
	 * </p>
	 * @param tableName 数据库表名称
	 * @param object 值对象
	 * @param pkColumnNames 主键列名
	 * @return 自动生成的主键
	 */
	Number insert(String tableName, Object object, List<String> pkColumnNames);
	/**
	 * <p>
	 * 插入.
	 * 将指定map中的值插入指定的表中
	 * </p>
	 * @param tableName 数据库表名称
	 * @param map 存放值的map
	 * @return 影响的行数
	 */
	int insert(String tableName, Map<String, Object> map);
	/**
	 * <p>
	 * 插入并返回自动生成的主键值.
	 * 将指定map中的值插入指定的表中.
	 * </p>
	 * @param tableName 数据库表名称
	 * @param map 存放值的map
	 * @param pkColumnNames 主键列名
	 * @return 自动生成的主键
	 */
	Number insert(String tableName, Map<String, Object> map, String[] pkColumnNames);
	/**
	 * <p>
	 * 更新.
	 * </p>
	 * @param tableName 数据库表名称
	 * @param params 设置的值（SQL SET 后面的内容）
	 * @param conditions 条件（SQL WHERE 后面的内容）
	 * @return 影响的行数
	 */
	int update(String tableName, Map<String, Object> params, Map<String, Object> conditions);
	/**
	 * <p>
	 * 删除.
	 * </p>
	 * @param tableName 数据库表名称
	 * @param columnKeyAndValue 列名和值，多个参数以and连接
	 * @return 影响的行数
	 */
	int delete(String tableName, Map<String, Object> columnKeyAndValue);
	/**
	 * <p>
	 * 查询唯一数据.
	 * 使用MAP封装.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 封装了返回数据的MAP
	 */
	Map<String, Object> find(String sql, Map<String, Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * 使用MAP封装.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 封装了返回数据的MAP
	 */
	Map<String, Object> find(String sql, Object[] params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * 使用MAP封装.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 封装了返回数据的MAP
	 */
	Map<String, Object> find(String sql, List<Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param <E> 对象类型
	 * @param mappingType 记录映射类
	 * @param builder 条件构造器
	 * @return 对象
	 */
	<E> E find(Class<E> mappingType, ConditionBuilder builder);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @return 对象
	 */
	<E> E find(String sql, Class<E> mappingType);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param params 参数
	 * @return 对象
	 */
	<E> E find(String sql, Class<E> mappingType, Map<String, Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param params 参数
	 * @return 对象
	 */
	<E> E find(String sql, Class<E> mappingType, Object[] params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param params 参数
	 * @return 对象
	 */
	<E> E find(String sql, Class<E> mappingType, List<Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 记录映射类
	 * @return 对象列表
	 */
	<E> E find(String sql, RowMapper<E> rowMapper);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 记录映射类
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> E find(String sql, RowMapper<E> rowMapper, Map<String, Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 记录映射类
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> E find(String sql, RowMapper<E> rowMapper, Object[] params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 记录映射类
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> E find(String sql, RowMapper<E> rowMapper, List<Object> params);
	/**
	 * <p>
	 * count查询语句的快捷方式
	 * </p>
	 * @param tableName 需要统计的表名
	 * @param conditionBuilder 条件构造器
	 * @return 统计数
	 */
	Integer countForInt(String tableName, ConditionBuilder conditionBuilder);
	/**
	 * <p>
	 * count查询语句的快捷方式
	 * </p>
	 * @param tableName 需要统计的表名
	 * @param conditionBuilder 条件构造器
	 * @return 统计数
	 */
	Long countForLong(String tableName, ConditionBuilder conditionBuilder);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	Integer findForInt(String sql, Map<String, Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	Integer findForInt(String sql, Object[] params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	Integer findForInt(String sql, List<Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	Long findForLong(String sql, Map<String, Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	Long findForLong(String sql, Object[] params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	Long findForLong(String sql, List<Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	BigDecimal findForBigDecimal(String sql, Map<String, Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	BigDecimal findForBigDecimal(String sql, Object[] params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	BigDecimal findForBigDecimal(String sql, List<Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	String findForString(String sql, Map<String, Object> params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	String findForString(String sql, Object[] params);
	/**
	 * <p>
	 * 查询唯一数据.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return 对象列表
	 */
	String findForString(String sql, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @return MAP列表
	 */
	List<Map<String, Object>> findList(String sql);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return MAP列表
	 */
	List<Map<String, Object>> findList(String sql, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return MAP列表
	 */
	List<Map<String, Object>> findList(String sql, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param params 参数
	 * @return MAP列表
	 */
	List<Map<String, Object>> findList(String sql, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return MAP列表
	 */
	List<Map<String, Object>> findList(String sql, Pagination pagination
			, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return MAP列表
	 */
	List<Map<String, Object>> findList(String sql, Pagination pagination, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return MAP列表
	 */
	List<Map<String, Object>> findList(String sql, Pagination pagination, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return  分页对象
	 */
	PaginationResults<Map<String, Object>> findPage(String sql, Pagination pagination
	        , Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return  分页对象
	 */
	PaginationResults<Map<String, Object>> findPage(String sql, Pagination pagination, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return  分页对象
	 */
	PaginationResults<Map<String, Object>> findPage(String sql, Pagination pagination, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return MAP列表
	 */
	List<Map<String, Object>> findList(String sql, int start, int limit, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return MAP列表
	 */
	List<Map<String, Object>> findList(String sql, int start, int limit, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return MAP列表
	 */
	List<Map<String, Object>> findList(String sql, int start, int limit, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	PaginationResults<Map<String, Object>> findPage(String sql, int start, int limit, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	PaginationResults<Map<String, Object>> findPage(String sql, int start, int limit, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的MAP.
	 * </p>
	 * @param sql 查询sql
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	PaginationResults<Map<String, Object>> findPage(String sql, int start, int limit, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param mappingType 记录映射类
	 * @param builder 条件构造器
	 * @return 对象列表
	 */
	<E> List<E> findList(Class<E> mappingType, ConditionBuilder builder);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param mappingType 记录映射类
	 * @param builder 条件构造器
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(Class<E> mappingType, ConditionBuilder builder);
//	/**
//	 * <p>
//	 * 查询列表.
//	 * 列表内放的是记录映射类的实例对象.
//	 * </p>
//	 * @param <E> 对象类型
//	 * @param mappingType 记录映射类
//	 * @param builder 条件构造器
//	 * @param start 起始位置
//	 * @param limit 返回数
//	 * @return 对象列表
//	 */
//	<E> List<E> findList(Class<E> mappingType, ConditionBuilder builder, int start, int limit);	
//	/**
//	 * <p>
//	 * 查询列表.
//	 * 列表内放的是记录映射类的实例对象.
//	 * </p>
//	 * @param <E> 对象类型
//	 * @param mappingType 记录映射类
//	 * @param builder 条件构造器
//	 * @param pagination 分页模型
//	 * @return 对象列表
//	 */
//	<E> List<E> findList(Class<E> mappingType, ConditionBuilder builder, Pagination pagination);	
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, Class<E> mappingType);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, Class<E> mappingType, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, Class<E> mappingType, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, Class<E> mappingType, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, Class<E> mappingType, Pagination pagination, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, Class<E> mappingType, Pagination pagination, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, Class<E> mappingType, Pagination pagination, List<Object> params);
	/**
     * <p>
     * 查询列表.
     * 列表内放的是记录映射类的实例对象.
     * </p>
     * @param <E> 对象类型
     * @param sql 查询sql
     * @param mappingType 记录映射类
     * @param pagination 分页模型
     * @param params 参数
     * @return 分页对象
     */
    <E> PaginationResults<E> findPage(String sql, Class<E> mappingType, Pagination pagination, Map<String, Object> params);
    /**
     * <p>
     * 查询列表.
     * 列表内放的是记录映射类的实例对象.
     * </p>
     * @param <E> 对象类型
     * @param sql 查询sql
     * @param mappingType 记录映射类
     * @param pagination 分页模型
     * @param params 参数
     * @return 分页对象
     */
    <E> PaginationResults<E> findPage(String sql, Class<E> mappingType, Pagination pagination, Object[] params);
    /**
     * <p>
     * 查询列表.
     * 列表内放的是记录映射类的实例对象.
     * </p>
     * @param <E> 对象类型
     * @param sql 查询sql
     * @param mappingType 记录映射类
     * @param pagination 分页模型
     * @param params 参数
     * @return 分页对象
     */
    <E> PaginationResults<E> findPage(String sql, Class<E> mappingType, Pagination pagination, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, Class<E> mappingType, int start, int limit, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, Class<E> mappingType, int start, int limit, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, Class<E> mappingType, int start, int limit, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(String sql, Class<E> mappingType, int start, int limit, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(String sql, Class<E> mappingType, int start, int limit, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param mappingType 记录映射类
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(String sql, Class<E> mappingType, int start, int limit, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, RowMapper<E> rowMapper);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, RowMapper<E> rowMapper, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, RowMapper<E> rowMapper, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, RowMapper<E> rowMapper, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, RowMapper<E> rowMapper,
			Pagination pagination, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, RowMapper<E> rowMapper,
			Pagination pagination, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param pagination 分页模型
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, RowMapper<E> rowMapper,
			Pagination pagination, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, RowMapper<E> rowMapper,
			int start, int limit, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, RowMapper<E> rowMapper,
			int start, int limit, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 对象列表
	 */
	<E> List<E> findList(String sql, RowMapper<E> rowMapper,
			int start, int limit, List<Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(String sql, RowMapper<E> rowMapper,
			int start, int limit, Map<String, Object> params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(String sql, RowMapper<E> rowMapper,
			int start, int limit, Object[] params);
	/**
	 * <p>
	 * 查询列表.
	 * 列表内放的是记录映射类的实例对象.
	 * </p>
	 * @param <E> 对象类型
	 * @param sql 查询sql
	 * @param rowMapper 自定义映射接口
	 * @param start 起始位置
	 * @param limit 返回数
	 * @param params 参数
	 * @return 分页对象
	 */
	<E> PaginationResults<E> findPage(String sql, RowMapper<E> rowMapper,
			int start, int limit, List<Object> params);
}