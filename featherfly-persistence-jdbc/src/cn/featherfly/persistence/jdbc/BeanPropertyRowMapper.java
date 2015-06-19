
package cn.featherfly.persistence.jdbc;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cn.featherfly.common.bean.BeanDescriptor;
import cn.featherfly.common.bean.BeanProperty;
import cn.featherfly.common.db.JdbcException;
import cn.featherfly.common.db.JdbcUtils;

/**
 * <p>
 * BeanPropertyRowMapper
 * 类的说明放这里
 * </p>
 * 
 * @author 钟冀
 */
public class BeanPropertyRowMapper<T> implements RowMapper<T> {
	/** Logger available to subclasses */
	protected final Log logger = LogFactory.getLog(getClass());

	/** The class we are mapping to */
	private Class<T> mappedClass;

	/** Whether we're strictly validating */
	private boolean checkFullyPopulated = false;

	/** Whether we're defaulting primitives when mapping a null value */
	private boolean primitivesDefaultedForNullValue = false;

	/** Map of the fields we provide mapping for */
	private Map<String, PropertyDescriptor> mappedFields;

	/** Set of bean properties we provide mapping for */
	private Set<String> mappedProperties;


	/**
	 * Create a new BeanPropertyRowMapper, accepting unpopulated properties
	 * in the target bean.
	 * <p>Consider using the {@link #newInstance} factory method instead,
	 * which allows for specifying the mapped type once only.
	 * @param mappedClass the class that each row should be mapped to
	 */
	public BeanPropertyRowMapper(Class<T> mappedClass) {
		initialize(mappedClass);
	}

	/**
	 * Create a new BeanPropertyRowMapper.
	 * @param mappedClass the class that each row should be mapped to
	 * @param checkFullyPopulated whether we're strictly validating that
	 * all bean properties have been mapped from corresponding database fields
	 */
	public BeanPropertyRowMapper(Class<T> mappedClass, boolean checkFullyPopulated) {
		initialize(mappedClass);
		this.checkFullyPopulated = checkFullyPopulated;
	}

	/**
	 * Initialize the mapping metadata for the given class.
	 * @param mappedClass the mapped class.
	 */
	protected void initialize(Class<T> mappedClass) {
		this.mappedClass = mappedClass;
		this.mappedFields = new HashMap<String, PropertyDescriptor>();
		this.mappedProperties = new HashSet<String>();
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
		for (PropertyDescriptor pd : pds) {
			if (pd.getWriteMethod() != null) {
				this.mappedFields.put(pd.getName().toLowerCase(), pd);
				String underscoredName = underscoreName(pd.getName());
				if (!pd.getName().toLowerCase().equals(underscoredName)) {
					this.mappedFields.put(underscoredName, pd);
				}
				this.mappedProperties.add(pd.getName());
			}
		}
	}

	/**
	 * Convert a name in camelCase to an underscored name in lower case.
	 * Any upper case letters are converted to lower case with a preceding underscore.
	 * @param name the string containing original name
	 * @return the converted name
	 */
	private String underscoreName(String name) {
		if (!StringUtils.hasLength(name)) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		result.append(name.substring(0, 1).toLowerCase());
		for (int i = 1; i < name.length(); i++) {
			String s = name.substring(i, i + 1);
			String slc = s.toLowerCase();
			if (!s.equals(slc)) {
				result.append("_").append(slc);
			}
			else {
				result.append(s);
			}
		}
		return result.toString();
	}

	/**
	 * Get the class that we are mapping to.
	 */
	public final Class<T> getMappedClass() {
		return this.mappedClass;
	}

	/**
	 * Set whether we're strictly validating that all bean properties have been
	 * mapped from corresponding database fields.
	 * <p>Default is {@code false}, accepting unpopulated properties in the
	 * target bean.
	 */
	public void setCheckFullyPopulated(boolean checkFullyPopulated) {
		this.checkFullyPopulated = checkFullyPopulated;
	}

	/**
	 * Return whether we're strictly validating that all bean properties have been
	 * mapped from corresponding database fields.
	 */
	public boolean isCheckFullyPopulated() {
		return this.checkFullyPopulated;
	}

	/**
	 * Set whether we're defaulting Java primitives in the case of mapping a null value
	 * from corresponding database fields.
	 * <p>Default is {@code false}, throwing an exception when nulls are mapped to Java primitives.
	 */
	public void setPrimitivesDefaultedForNullValue(boolean primitivesDefaultedForNullValue) {
		this.primitivesDefaultedForNullValue = primitivesDefaultedForNullValue;
	}

	/**
	 * Return whether we're defaulting Java primitives in the case of mapping a null value
	 * from corresponding database fields.
	 */
	public boolean isPrimitivesDefaultedForNullValue() {
		return primitivesDefaultedForNullValue;
	}


	/**
	 * Extract the values for all columns in the current row.
	 * <p>Utilizes public setters and result set metadata.
	 * @see java.sql.ResultSetMetaData
	 */
	@Override
	public T mapRow(ResultSet rs, int rowNumber) {
		Assert.state(this.mappedClass != null, "Mapped class was not specified");
		T mappedObject = BeanUtils.instantiate(this.mappedClass);
		BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(mappedObject);
		initBeanWrapper(bw);

		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			Set<String> populatedProperties = (isCheckFullyPopulated() ? new HashSet<String>() : null);
	
			for (int index = 1; index <= columnCount; index++) {
				String column = JdbcUtils.lookupColumnName(rsmd, index);
	//			PropertyDescriptor pd = this.mappedFields.get(column.replaceAll(" ", "").toLowerCase());
				
				boolean nestedProperty = false;
				String columnName = column.replaceAll(" ", "").toLowerCase();
				if (column.contains(".")) {
					nestedProperty = true;
					columnName = cn.featherfly.common.lang.StringUtils.substringBefore(columnName, ".");
				}
	//			PropertyDescriptor pd = this.mappedFields.get(column.replaceAll(" ", "").toLowerCase());
				PropertyDescriptor pd = this.mappedFields.get(columnName);
				if (pd != null) {
					try {
						Object value = null;
						try {
							// TODO bw.setPropertyValue支持带嵌套设值，所以只需要多加入一个判断就行了，就是前面column包含.就只判断.前面部分
							if (nestedProperty) {
								// 嵌套设值，所以直接使用SQL查询出来列的别名
//								bw.setPropertyValue(column.replaceAll(" ", ""), value);
//								cn.featherfly.common.bean.BeanUtils.setProperty(mappedObject, column.replaceAll(" ", ""), value);
								BeanDescriptor<T> bd = BeanDescriptor.getBeanDescriptor(mappedClass);
								BeanProperty bp = bd.getChildBeanProperty(column.replaceAll(" ", ""));
								if (bp != null) {
									value = JdbcUtils.getResultSetValue(rs, index, bp.getType());
									bd.setProperty(mappedObject, column.replaceAll(" ", ""), value);
								}
							} else {
								if (logger.isDebugEnabled() && rowNumber == 0) {
									logger.debug("Mapping column '" + column + "' to property '" +
											pd.getName() + "' of type " + pd.getPropertyType());
								}
								value = getColumnValue(rs, index, pd);
								bw.setPropertyValue(pd.getName(), value);
							}
						}
						catch (TypeMismatchException e) {
							if (value == null && primitivesDefaultedForNullValue) {
								logger.debug("Intercepted TypeMismatchException for row " + rowNumber +
										" and column '" + column + "' with value " + value +
										" when setting property '" + pd.getName() + "' of type " + pd.getPropertyType() +
										" on object: " + mappedObject);
							}
							else {
								throw e;
							}
						}
						if (populatedProperties != null) {
							populatedProperties.add(pd.getName());
						}
					}
					catch (NotWritablePropertyException ex) {
						throw new DataRetrievalFailureException(
								"Unable to map column " + column + " to property " + pd.getName(), ex);
					}
				}
			}
	
			if (populatedProperties != null && !populatedProperties.equals(this.mappedProperties)) {
				throw new InvalidDataAccessApiUsageException("Given ResultSet does not contain all fields " +
						"necessary to populate object of class [" + this.mappedClass + "]: " + this.mappedProperties);
			}

		} catch (SQLException e) {
			throw new JdbcException(e);
		}
		return mappedObject;
	}

	/**
	 * Initialize the given BeanWrapper to be used for row mapping.
	 * To be called for each row.
	 * <p>The default implementation is empty. Can be overridden in subclasses.
	 * @param bw the BeanWrapper to initialize
	 */
	protected void initBeanWrapper(BeanWrapper bw) {
	}

	/**
	 * Retrieve a JDBC object value for the specified column.
	 * <p>The default implementation calls
	 * {@link JdbcUtils#getResultSetValue(java.sql.ResultSet, int, Class)}.
	 * Subclasses may override this to check specific value types upfront,
	 * or to post-process values return from {@code getResultSetValue}.
	 * @param rs is the ResultSet holding the data
	 * @param index is the column index
	 * @param pd the bean property that each result object is expected to match
	 * (or {@code null} if none specified)
	 * @return the Object value
	 * @throws SQLException in case of extraction failure
	 * @see org.springframework.jdbc.support.JdbcUtils#getResultSetValue(java.sql.ResultSet, int, Class)
	 */
	protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
		return JdbcUtils.getResultSetValue(rs, index, pd.getPropertyType());
	}
}
