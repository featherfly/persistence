package cn.featherfly.persistence.jdbc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import cn.featherfly.common.db.PaginationWrapper;
import cn.featherfly.common.db.SqlUtils;
import cn.featherfly.common.db.builder.ConditionBuildUtils;
import cn.featherfly.common.db.builder.ConditionBuilder;
import cn.featherfly.common.db.data.Execution;
import cn.featherfly.common.db.dialect.Dialect;
import cn.featherfly.common.lang.ArrayUtils;
import cn.featherfly.common.lang.ClassUtils;
import cn.featherfly.common.lang.LangUtils;
import cn.featherfly.common.structure.page.Pagination;
import cn.featherfly.common.structure.page.PaginationResults;
import cn.featherfly.common.structure.page.SimplePagination;
import cn.featherfly.component.sorm.SimpleORMFactory;
import cn.featherfly.persistence.PersistenceObserver;
import cn.featherfly.persistence.PersistentException;

/**
 * <p>
 * jdbc持久化实现类
 * </p>
 * 
 * @author 钟冀
 */
public class JdbcPersistenceImpl extends PersistenceObserver implements
        JdbcPersistence {

    /**
	 */
    public JdbcPersistenceImpl() {
    }

    private static final Logger LOGGER = LoggerFactory
            .getLogger(JdbcPersistenceImpl.class);

    // ********************************************************************
    // PersistenceObserver impl
    // ********************************************************************

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> E doGet(Serializable id, Class<E> type) {
        if (id != null) {
            return simpleORMFactory.getSimpleORM((Class<E>) type).get(id);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> E doLoad(E entity) {
        if (entity != null) {
            return simpleORMFactory.getSimpleORM(
                    ClassUtils.castGenericType(entity.getClass(), entity))
                    .load(entity);
        }
        return null;
    }

    /**
     * <p>
     * 删除. 具体的表名从传入对象类上的注解@Table的name属性获取 具体的条件以传入对象类属性上的标注了@Pk和@Column的name获取
     * </p>
     * 
     * @param <E>
     *            对象类型
     * @param entity
     *            对象
     */
    @Override
    protected <E> void doDelete(E entity) {
        if (entity != null) {
            simpleORMFactory.getSimpleORM(
                    ClassUtils.castGenericType(entity.getClass(), entity))
                    .delete(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doDeleteBatch(List<E> entityList) {
        if (LangUtils.isEmpty(entityList)) {
            return;
        }
        for (Object entity : entityList) {
            doDelete(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doMerge(E entity) {
        if (entity != null) {
            simpleORMFactory.getSimpleORM(
                    ClassUtils.castGenericType(entity.getClass(), entity))
                    .merge(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doMergeBatch(List<E> entityList) {
        if (LangUtils.isEmpty(entityList)) {
            return;
        }
        for (Object entity : entityList) {
            doMerge(entity);
        }
    }

    /**
     * <p>
     * 插入，如果传入对象为null，忽略 将指定对象的值插入指定的表中， 具体的表名从传入对象类上的注解@Table的name属性获取
     * </p>
     * 
     * @param <E>
     *            对象类型
     * @param entity
     *            对象
     */
    @Override
    protected <E> void doPersist(E entity) {
        if (entity != null) {
            simpleORMFactory.getSimpleORM(
                    ClassUtils.castGenericType(entity.getClass(), entity))
                    .save(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doPersistBatch(List<E> entityList) {
        if (LangUtils.isEmpty(entityList)) {
            return;
        }
        for (Object entity : entityList) {
            doPersist(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doSave(E entity) {
        if (entity != null) {
            simpleORMFactory.getSimpleORM(
                    ClassUtils.castGenericType(entity.getClass(), entity))
                    .save(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doSaveBatch(List<E> entityList) {
        if (LangUtils.isEmpty(entityList)) {
            return;
        }
        for (Object entity : entityList) {
            doSave(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doSaveOrMerge(E entity) {
        if (entity == null) {
            return;
        }
        E e = load(entity);
        if (e == null) {
            doSave(entity);
        } else {
            doMerge(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doSaveOrMergeBatch(List<E> entityList) {
        if (LangUtils.isEmpty(entityList)) {
            return;
        }
        for (Object entity : entityList) {
            doSaveOrMerge(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doSaveOrUpdate(E entity) {
        if (entity == null) {
            return;
        }
        E e = load(entity);
        if (e == null) {
            doSave(entity);
        } else {
            doUpdate(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doSaveOrUpdateBatch(List<E> entityList) {
        if (LangUtils.isEmpty(entityList)) {
            return;
        }
        for (Object entity : entityList) {
            doSaveOrUpdate(entity);
        }
    }

    /**
     * <p>
     * 更新. 具体的表名从传入对象类上的注解@Table的name属性获取 具体的条件以传入对象类属性上的标注了@PK和@Column的name获取
     * </p>
     * 
     * @param <E>
     *            对象类型
     * @param entity
     *            对象
     */
    @Override
    protected <E> void doUpdate(E entity) {
        if (entity != null) {
            simpleORMFactory.getSimpleORM(
                    ClassUtils.castGenericType(entity.getClass(), entity))
                    .update(entity);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected <E> void doUpdateBatch(List<E> entityList) {
        if (LangUtils.isEmpty(entityList)) {
            return;
        }
        for (Object entity : entityList) {
            doUpdate(entity);
        }
    }

    // ********************************************************************
    // JdbcPersistence impl
    // ********************************************************************

    /**
     * {@inheritDoc}
     */
    @Override
    public int execute(String sql, Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.update(sql, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int execute(String sql, Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.update(sql, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int execute(String sql, List<Object> params) {
        logger.debug("sql : {}", sql);
        return execute(sql, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insert(String tableName, Object object) {
        SimpleJdbcInsert jdbcInsert = getSimpleJdbcInsert(tableName);
        SqlParameterSource source = new BeanPropertySqlParameterSource(object);
        return jdbcInsert.execute(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Number insert(String tableName, Object object, String[] pkColumnNames) {
        SimpleJdbcInsert jdbcInsert = getSimpleJdbcInsert(tableName,
                pkColumnNames);
        SqlParameterSource source = new BeanPropertySqlParameterSource(object);
        return jdbcInsert.executeAndReturnKey(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Number insert(String tableName, Object object,
            List<String> pkColumnNames) {
        if (pkColumnNames == null) {
            pkColumnNames = new ArrayList<String>();
        }
        return insert(tableName, object, pkColumnNames.toArray(new String[] {}));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insert(String tableName, Map<String, Object> map) {
        SimpleJdbcInsert jdbcInsert = getSimpleJdbcInsert(tableName);
        SqlParameterSource source = new MapSqlParameterSource(map);
        return jdbcInsert.execute(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Number insert(String tableName, Map<String, Object> map,
            String[] pkColumnNames) {
        SimpleJdbcInsert jdbcInsert = getSimpleJdbcInsert(tableName,
                pkColumnNames);
        SqlParameterSource source = new MapSqlParameterSource(map);
        return jdbcInsert.executeAndReturnKey(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(String tableName, Map<String, Object> params,
            Map<String, Object> conditions) {
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tableName);        
        if (LangUtils.isNotEmpty(params)) {
            sql.append(" set "); 
            int index = 0;
            for (String key : params.keySet()) {
                if (index > 0) {
                    sql.append(", ");
                }
                sql.append(key).append(" = :").append(key);
                index++;
            }
        }
        Map<String, Object> newCondition = new HashMap<>();
        if (LangUtils.isNotEmpty(conditions)) {
            sql.append(" where ");
            int index = 0;
            for (String key : conditions.keySet()) {
                if (index > 0) {
                    sql.append(" and ");
                }
                String conditionNamedParam = ":_condition_" + key;
                sql.append(key).append(" = ").append(conditionNamedParam);
                Object value = conditions.get(key);                
                newCondition.put(conditionNamedParam, value);
                index++;                
            }
        }
        params.putAll(newCondition);
        logger.debug("sql : {}", sql);
        logger.debug("params : {}", params);
        return namedParameterJdbcTemplate.update(sql.toString(), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(String tableName, Map<String, Object> columnKeyAndValue) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete from ").append(tableName);
        if (LangUtils.isNotEmpty(columnKeyAndValue)) {
            sql.append(" where ");
            int index = 0;
            for (Entry<String, Object> entry : columnKeyAndValue.entrySet()) {
                if (index > 0) {
                    sql.append(" and ");
                }
                sql.append(entry.getKey()).append(" = :")
                        .append(entry.getKey());
                index++;
            }
        }
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.update(sql.toString(),
                columnKeyAndValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> find(String sql, Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.queryForMap(sql, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> find(String sql, Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.queryForMap(sql, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> find(String sql, List<Object> params) {
        return find(sql, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E find(Class<E> mappingType, ConditionBuilder builder) {
        builder.setDialect(dialect);
        return simpleORMFactory.getSimpleORM(mappingType).unique(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E find(String sql, Class<E> mappingType) {
        return find(sql, mappingType, new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E find(String sql, Class<E> mappingType,
            Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params,
                    getParameterizedBeanPropertyRowMapper(mappingType));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E find(String sql, Class<E> mappingType, Object[] params) {
        logger.debug("sql : {}", sql);
        try {
            return jdbcTemplate.queryForObject(sql,
                    getParameterizedBeanPropertyRowMapper(mappingType), params);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E find(String sql, Class<E> mappingType, List<Object> params) {
        return find(sql, mappingType, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E find(String sql, RowMapper<E> rowMapper) {
        return find(sql, rowMapper, new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E find(String sql, final RowMapper<E> rowMapper,
            Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params,
                    new org.springframework.jdbc.core.RowMapper<E>() {
                        @Override
                        public E mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            return rowMapper.mapRow(rs, rowNum);
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E find(String sql, final RowMapper<E> rowMapper, Object[] params) {
        logger.debug("sql : {}", sql);
        try {
            return jdbcTemplate.queryForObject(sql,
                    new org.springframework.jdbc.core.RowMapper<E>() {
                        @Override
                        public E mapRow(ResultSet rs, int rowNum)
                                throws SQLException {
                            return rowMapper.mapRow(rs, rowNum);
                        }
                    }, params);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> E find(String sql, RowMapper<E> rowMapper, List<Object> params) {
        return find(sql, rowMapper, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer countForInt(String tableName,
            ConditionBuilder conditionBuilder) {
        conditionBuilder.setBuildWithWhere(true);
        conditionBuilder.setDialect(dialect);
        String condition = conditionBuilder.build();
        StringBuilder sql = new StringBuilder("select count(*) from");
        ConditionBuildUtils.appendCondition(sql, tableName);
        ConditionBuildUtils.appendCondition(sql, condition);
        logger.debug("tableName : {}, condition : {}", tableName, condition);
        return findForInt(sql.toString(), conditionBuilder.getParams());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long countForLong(String tableName, ConditionBuilder conditionBuilder) {
        conditionBuilder.setBuildWithWhere(true);
        conditionBuilder.setDialect(dialect);
        String condition = conditionBuilder.build();
        StringBuilder sql = new StringBuilder("select count(*) from");
        ConditionBuildUtils.appendCondition(sql, tableName);
        ConditionBuildUtils.appendCondition(sql, condition);
        logger.debug("tableName : {}, condition : {}", tableName, condition);
        return findForLong(sql.toString(), conditionBuilder.getParams());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer findForInt(String sql, Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.queryForObject(sql, params,
                Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer findForInt(String sql, Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer findForInt(String sql, List<Object> params) {
        return findForInt(sql, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long findForLong(String sql, Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.queryForObject(sql, params,
                Long.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long findForLong(String sql, Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long findForLong(String sql, List<Object> params) {
        return findForLong(sql, toArray(params));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal findForBigDecimal(String sql,
            Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.queryForObject(sql, params,
                BigDecimal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal findForBigDecimal(String sql, Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.queryForObject(sql, params, BigDecimal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal findForBigDecimal(String sql, List<Object> params) {
        return findForBigDecimal(sql, toArray(params));
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String findForString(String sql,
            Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.queryForObject(sql, params,
                String.class);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String findForString(String sql, Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.queryForObject(sql, params, String.class);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String findForString(String sql, List<Object> params) {
        return findForString(sql, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> findList(String sql) {
        return findList(sql, new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> findList(String sql,
            Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> findList(String sql, Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.queryForList(sql, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> findList(String sql, List<Object> params) {
        return findList(sql, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> findList(String sql,
            Pagination pagination, Map<String, Object> params) {
        // Integer total = findForInt(SqlUtils.convertSelectToCount(sql),
        // params);
        // if (total != null) {
        // pagination.setTotal(total);
        // }
        PaginationWrapper<Map<String, Object>> wrapper = new PaginationWrapper<Map<String, Object>>(
                pagination);
        return findList(sql, wrapper.getStart(), wrapper.getLimit(), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> findList(String sql,
            Pagination pagination, Object[] params) {
        // Integer total = findForInt(SqlUtils.convertSelectToCount(sql),
        // params);
        // if (total != null) {
        // pagination.setTotal(total);
        // }
        PaginationWrapper<Map<String, Object>> wrapper = new PaginationWrapper<Map<String, Object>>(
                pagination);
        return findList(sql, wrapper.getStart(), wrapper.getLimit(), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> findList(String sql,
            Pagination pagination, List<Object> params) {
        // Integer total = findForInt(SqlUtils.convertSelectToCount(sql),
        // params);
        // if (total != null) {
        // pagination.setTotal(total);
        // }
        PaginationWrapper<Map<String, Object>> wrapper = new PaginationWrapper<Map<String, Object>>(
                pagination);
        return findList(sql, wrapper.getStart(), wrapper.getLimit(), params);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationResults<Map<String, Object>> findPage(String sql,
            Pagination pagination, Map<String, Object> params) {        
        List<Map<String, Object>> list = findList(sql, pagination, params);
        Integer total = findForInt(SqlUtils.convertSelectToCount(sql), params);
//        if (total != null) {
//            pagination.setTotal(total);
//        }
        return createPaginationResults(list, pagination, total);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationResults<Map<String, Object>> findPage(String sql,
            Pagination pagination, Object[] params) {
        Integer total = findForInt(SqlUtils.convertSelectToCount(sql), params);
//        if (total != null) {
//            pagination.setTotal(total);
//        }
        List<Map<String, Object>> list = findList(sql, pagination, params);
        return createPaginationResults(list, pagination, total);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationResults<Map<String, Object>> findPage(String sql,
            Pagination pagination, List<Object> params) {
        return findPage(sql, pagination, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> PaginationResults<E> findPage(String sql, Class<E> mappingType,
            Pagination pagination, Map<String, Object> params) {
        Integer total = findForInt(SqlUtils.convertSelectToCount(sql), params);
//        if (total != null) {
//            pagination.setTotal(total);
//        }
        List<E> list = findList(sql, mappingType, pagination, params);
        return createPaginationResults(list, pagination, total);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> PaginationResults<E> findPage(String sql, Class<E> mappingType,
            Pagination pagination, Object[] params) {
        Integer total = findForInt(SqlUtils.convertSelectToCount(sql), params);
//        if (total != null) {
//            pagination.setTotal(total);
//        }
        List<E> list = findList(sql, mappingType, pagination, params);
        return createPaginationResults(list, pagination, total);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> PaginationResults<E> findPage(String sql, Class<E> mappingType,
            Pagination pagination, List<Object> params) {
        return findPage(sql, mappingType, pagination, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> findList(String sql, int start, int limit,
            Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.queryForList(
                dialect.getParamNamedPaginationSql(sql, start, limit),
                dialect.getPaginationSqlParameter(params, start, limit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationResults<Map<String, Object>> findPage(String sql,
            int start, int limit, Map<String, Object> params) {
        checkStartAndLimit(start, limit);
        SimplePagination<Map<String, Object>> pagination = new SimplePagination<Map<String, Object>>();
        List<Map<String, Object>> results = findList(sql, start, limit, params);
        pagination.setPageResults(results);
        pagination.setPageSize(limit);
        pagination.setPageNumber((start + limit - 1) / limit);
        Integer total = findForInt(SqlUtils.convertSelectToCount(sql), params);
        pagination.setTotal(total);
        return pagination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> findList(String sql, int start, int limit,
            Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.queryForList(
                dialect.getPaginationSql(sql, start, limit),
                dialect.getPaginationSqlParameter(params, start, limit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> findList(String sql, int start, int limit,
            List<Object> params) {
        return findList(sql, start, limit, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationResults<Map<String, Object>> findPage(String sql,
            int start, int limit, Object[] params) {
        checkStartAndLimit(start, limit);
        SimplePagination<Map<String, Object>> pagination = new SimplePagination<Map<String, Object>>();
        List<Map<String, Object>> results = findList(sql, start, limit, params);
        pagination.setPageResults(results);
        pagination.setPageSize(limit);
        pagination.setPageNumber((start + limit - 1) / limit);
        Integer total = findForInt(SqlUtils.convertSelectToCount(sql), params);
        pagination.setTotal(total);
        return pagination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PaginationResults<Map<String, Object>> findPage(String sql,
            int start, int limit, List<Object> params) {
        return findPage(sql, start, limit, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(Class<E> mappingType, ConditionBuilder builder) {
        builder.setDialect(dialect);
        return simpleORMFactory.getSimpleORM(mappingType).list(builder);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public <E> PaginationResults<E> findPage(Class<E> mappingType,
            ConditionBuilder builder) {
        List<E> list = findList(mappingType, builder);
        // 查询完了以后把转换count语句的干扰去掉
        builder.clearOrders();
        builder.setDialect(dialect);
        Pagination p = builder.getPagination();
        builder.setPagination(null);
        Execution execution = simpleORMFactory.getSimpleORM(mappingType).getQueryExecution(builder);
        Integer total = findForInt(SqlUtils.convertSelectToCount(execution.getSql()), execution.getParams());
        return createPaginationResults(list, p, total);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, Class<E> mappingType) {
        return findList(sql, mappingType, new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, Class<E> mappingType,
            Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.query(sql, params,
                getParameterizedBeanPropertyRowMapper(mappingType));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, Class<E> mappingType,
            Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.query(sql,
                getParameterizedBeanPropertyRowMapper(mappingType), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, Class<E> mappingType,
            List<Object> params) {
        return findList(sql, mappingType, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, Class<E> mappingType,
            Pagination pagination, Map<String, Object> params) {
        // Integer total = findForInt(SqlUtils.convertSelectToCount(sql),
        // params);
        // if (total != null) {
        // pagination.setTotal(total);
        // }
        PaginationWrapper<E> wrapper = new PaginationWrapper<E>(pagination);
        return findList(sql, mappingType, wrapper.getStart(),
                wrapper.getLimit(), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, Class<E> mappingType,
            Pagination pagination, Object[] params) {
        // Integer total = findForInt(SqlUtils.convertSelectToCount(sql),
        // params);
        // if (total != null) {
        // pagination.setTotal(total);
        // }
        PaginationWrapper<E> wrapper = new PaginationWrapper<E>(pagination);
        return findList(sql, mappingType, wrapper.getStart(),
                wrapper.getLimit(), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, Class<E> mappingType,
            Pagination pagination, List<Object> params) {
        // Integer total = findForInt(SqlUtils.convertSelectToCount(sql),
        // params);
        // if (total != null) {
        // pagination.setTotal(total);
        // }
        PaginationWrapper<E> wrapper = new PaginationWrapper<E>(pagination);
        return findList(sql, mappingType, wrapper.getStart(),
                wrapper.getLimit(), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, Class<E> mappingType, int start,
            int limit, Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.query(
                dialect.getParamNamedPaginationSql(sql, start, limit),
                dialect.getPaginationSqlParameter(params, start, limit),
                getParameterizedBeanPropertyRowMapper(mappingType));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> PaginationResults<E> findPage(String sql, Class<E> mappingType,
            int start, int limit, Map<String, Object> params) {
        checkStartAndLimit(start, limit);
        SimplePagination<E> pagination = new SimplePagination<E>();
        List<E> results = findList(sql, mappingType, start, limit, params);
        pagination.setPageResults(results);
        pagination.setPageSize(limit);
        pagination.setPageNumber((start + limit - 1) / limit);
        Integer total = findForInt(SqlUtils.convertSelectToCount(sql), params);
        pagination.setTotal(total);
        return pagination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, Class<E> mappingType, int start,
            int limit, Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.query(dialect.getPaginationSql(sql, start, limit),
                getParameterizedBeanPropertyRowMapper(mappingType),
                dialect.getPaginationSqlParameter(params, start, limit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, Class<E> mappingType, int start,
            int limit, List<Object> params) {
        return findList(sql, mappingType, start, limit, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> PaginationResults<E> findPage(String sql, Class<E> mappingType,
            int start, int limit, Object[] params) {
        checkStartAndLimit(start, limit);
        SimplePagination<E> pagination = new SimplePagination<E>();
        List<E> results = findList(sql, mappingType, start, limit, params);
        pagination.setPageResults(results);
        pagination.setPageSize(limit);
        pagination.setPageNumber((start + limit - 1) / limit);
        Integer total = findForInt(SqlUtils.convertSelectToCount(sql), params);
        pagination.setTotal(total);
        return pagination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> PaginationResults<E> findPage(String sql, Class<E> mappingType,
            int start, int limit, List<Object> params) {
        return findPage(sql, mappingType, start, limit, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, RowMapper<E> rowMapper) {
        return findList(sql, rowMapper, new Object[] {});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, final RowMapper<E> rowMapper,
            Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.query(sql, params,
                new org.springframework.jdbc.core.RowMapper<E>() {
                    @Override
                    public E mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        return rowMapper.mapRow(rs, rowNum);
                    }
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, final RowMapper<E> rowMapper,
            Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.query(sql,
                new org.springframework.jdbc.core.RowMapper<E>() {
                    @Override
                    public E mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        return rowMapper.mapRow(rs, rowNum);
                    }
                }, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, RowMapper<E> rowMapper,
            List<Object> params) {
        return findList(sql, rowMapper, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, RowMapper<E> rowMapper,
            Pagination pagination, Map<String, Object> params) {
        // Integer total = findForInt(SqlUtils.convertSelectToCount(sql),
        // params);
        // if (total != null) {
        // pagination.setTotal(total);
        // }
        PaginationWrapper<E> wrapper = new PaginationWrapper<E>(pagination);
        return findList(sql, rowMapper, wrapper.getStart(), wrapper.getLimit(),
                params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, RowMapper<E> rowMapper,
            Pagination pagination, Object[] params) {
        // Integer total = findForInt(SqlUtils.convertSelectToCount(sql),
        // params);
        // if (total != null) {
        // pagination.setTotal(total);
        // }
        PaginationWrapper<E> wrapper = new PaginationWrapper<E>(pagination);
        return findList(sql, rowMapper, wrapper.getStart(), wrapper.getLimit(),
                params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, RowMapper<E> rowMapper,
            Pagination pagination, List<Object> params) {
        // Integer total = findForInt(SqlUtils.convertSelectToCount(sql),
        // params);
        // if (total != null) {
        // pagination.setTotal(total);
        // }
        PaginationWrapper<E> wrapper = new PaginationWrapper<E>(pagination);
        return findList(sql, rowMapper, wrapper.getStart(), wrapper.getLimit(),
                params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, final RowMapper<E> rowMapper,
            int start, int limit, Map<String, Object> params) {
        logger.debug("sql : {}", sql);
        return namedParameterJdbcTemplate.query(
                dialect.getParamNamedPaginationSql(sql, start, limit),
                dialect.getPaginationSqlParameter(params, start, limit),
                new org.springframework.jdbc.core.RowMapper<E>() {
                    @Override
                    public E mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        return rowMapper.mapRow(rs, rowNum);
                    }
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> PaginationResults<E> findPage(String sql,
            RowMapper<E> rowMapper, int start, int limit,
            Map<String, Object> params) {
        checkStartAndLimit(start, limit);
        SimplePagination<E> pagination = new SimplePagination<E>();
        List<E> results = findList(sql, rowMapper, start, limit, params);
        pagination.setPageResults(results);
        pagination.setPageSize(limit);
        pagination.setPageNumber((start + limit - 1) / limit);
        Integer total = findForInt(SqlUtils.convertSelectToCount(sql), params);
        pagination.setTotal(total);
        return pagination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, final RowMapper<E> rowMapper,
            int start, int limit, Object[] params) {
        logger.debug("sql : {}", sql);
        return jdbcTemplate.query(dialect.getPaginationSql(sql, start, limit),
                new org.springframework.jdbc.core.RowMapper<E>() {
                    @Override
                    public E mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        return rowMapper.mapRow(rs, rowNum);
                    }
                }, dialect.getPaginationSqlParameter(params, start, limit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> List<E> findList(String sql, RowMapper<E> rowMapper, int start,
            int limit, List<Object> params) {
        return findList(sql, rowMapper, start, limit, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> PaginationResults<E> findPage(String sql,
            RowMapper<E> rowMapper, int start, int limit, Object[] params) {
        checkStartAndLimit(start, limit);
        SimplePagination<E> pagination = new SimplePagination<E>();
        List<E> results = findList(sql, rowMapper, start, limit, params);
        pagination.setPageResults(results);
        pagination.setPageSize(limit);
        pagination.setPageNumber((start + limit - 1) / limit);
        Integer total = findForInt(SqlUtils.convertSelectToCount(sql), params);
        pagination.setTotal(total);
        return pagination;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> PaginationResults<E> findPage(String sql,
            RowMapper<E> rowMapper, int start, int limit, List<Object> params) {
        return findPage(sql, rowMapper, start, limit, toArray(params));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <E> Serializable getIdentity(E entity) {
        return simpleORMFactory.getSimpleORM(
                ClassUtils.castGenericType(entity.getClass(), entity))
                .getIdentity(entity);
    }

    // ********************************************************************
    // private method
    // ********************************************************************

    // private <E> ParameterizedBeanPropertyRowMapper<E>
    // getParameterizedBeanPropertyRowMapper(Class<E> mappingType) {
    // ParameterizedBeanPropertyRowMapper<E> rowMapper = new
    // ParameterizedBeanPropertyRowMapper<E>();
    // rowMapper.setMappedClass(mappingType);
    // return rowMapper;
    // }

    private <E> org.springframework.jdbc.core.RowMapper<E> getParameterizedBeanPropertyRowMapper(
            Class<E> mappingType) {
        final BeanPropertyRowMapper<E> mapper = new BeanPropertyRowMapper<>(
                mappingType);
        return new org.springframework.jdbc.core.RowMapper<E>() {
            @Override
            public E mapRow(ResultSet rs, int rowNum) throws SQLException {
                return mapper.mapRow(rs, rowNum);
            }
        };
    }

    // 获取指定表名称的insert
    private SimpleJdbcInsert getSimpleJdbcInsert(String tableName) {
        SimpleJdbcInsert jdbcInsert = simpleJdbcInserts.get(tableName);
        if (jdbcInsert == null) {
            jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName(tableName);
            simpleJdbcInserts.put(tableName, jdbcInsert);
        }
        return jdbcInsert;
    }

    // 获取指定表名称主键列名的insert
    private SimpleJdbcInsert getSimpleJdbcInsert(String tableName,
            String... pkColumnNames) {
        SimpleJdbcInsert jdbcInsert = getSimpleJdbcInsert(tableName);
        if (LangUtils.isEmpty(jdbcInsert.getGeneratedKeyNames())) {
            logger.debug("表{}设置主键列{}", tableName,
                    ArrayUtils.toString(pkColumnNames));
            jdbcInsert.usingGeneratedKeyColumns(pkColumnNames);
        } else {
            logger.debug("表{}已经设置了主键列{}，新的设置被忽略", tableName,
                    ArrayUtils.toString(pkColumnNames));
        }
        return jdbcInsert;
    }

    // 设置起始和上限
    private void checkStartAndLimit(int start, int limit) {
        if (start <= 0) {
            LOGGER.error("start参数必须大于0");
            throw new PersistentException("start参数必须大于0");
        }
        if (limit <= 0) {
            LOGGER.error("start参数必须大于0");
            throw new PersistentException("limit参数必须大于0");
        }
    }

    // ********************************************************************
    // property
    // ********************************************************************

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private DataSource dataSource;

    private Dialect dialect;

    private SimpleORMFactory simpleORMFactory;

    private Map<String, SimpleJdbcInsert> simpleJdbcInserts = new HashMap<String, SimpleJdbcInsert>(
            0);

    /**
     * @param dialect
     *            设置dialect
     */
    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    /**
     * @return 返回dialect
     */
    public Dialect getDialect() {
        return dialect;
    }

    /**
     * @return 返回jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource
     *            设置dataSource
     */
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        if (simpleORMFactory == null) {
            simpleORMFactory = new SimpleORMFactory(this.dataSource);
        }
        this.simpleORMFactory.setDataSource(dataSource);
    }

    /**
     * 设置simpleORMFactory
     * 
     * @param simpleORMFactory
     *            simpleORMFactory
     */
    public void setSimpleORMFactory(SimpleORMFactory simpleORMFactory) {
        this.simpleORMFactory = simpleORMFactory;
        if (dataSource != null) {
            simpleORMFactory.setDataSource(dataSource);
        }
    }

    /**
     * 返回simpleORMFactory
     * 
     * @return simpleORMFactory
     */
    public SimpleORMFactory getSimpleORMFactory() {
        return simpleORMFactory;
    }
    
    private <E> PaginationResults<E> createPaginationResults(Iterable<E> results, Pagination pagination, Integer total) {
        SimplePagination<E> paginationResult = new SimplePagination<E>();
        paginationResult.setPageResults(results);
        paginationResult.setPageSize(pagination.getPageSize());
        paginationResult.setPageNumber(pagination.getPageNumber());
        paginationResult.setTotal(total);
        return paginationResult;
    }
    
    private Object[] toArray(List<Object> params) {
        if (params == null) {
            params = new ArrayList<Object>();
        }
        return params.toArray();
    }
}