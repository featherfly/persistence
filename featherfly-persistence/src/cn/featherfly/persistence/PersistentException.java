package cn.featherfly.persistence;

import java.util.Locale;

import cn.featherfly.common.exception.StandardResourceBundleException;


/**
 * <p>
 * 持久化异常
 * </p>
 *
 * @author 钟冀
 */
public class PersistentException extends StandardResourceBundleException {

	private static final long serialVersionUID = 4425897945460700161L;

	/**
	 * 
	 */
	public PersistentException() {
		super();
	}

	/**
	 * @param message
	 * @param locale
	 * @param ex
	 */
	public PersistentException(String message, Locale locale, Throwable ex) {
		super(message, locale, ex);
	}

	/**
	 * @param message
	 * @param locale
	 */
	public PersistentException(String message, Locale locale) {
		super(message, locale);
	}

	/**
	 * @param message
	 * @param argus
	 * @param locale
	 * @param ex
	 */
	public PersistentException(String message, Object[] argus, Locale locale,
			Throwable ex) {
		super(message, argus, locale, ex);
	}

	/**
	 * @param message
	 * @param argus
	 * @param locale
	 */
	public PersistentException(String message, Object[] argus, Locale locale) {
		super(message, argus, locale);
	}

	/**
	 * @param message
	 * @param argus
	 * @param ex
	 */
	public PersistentException(String message, Object[] argus, Throwable ex) {
		super(message, argus, ex);
	}

	/**
	 * @param message
	 * @param argus
	 */
	public PersistentException(String message, Object[] argus) {
		super(message, argus);
	}

	/**
	 * @param message
	 * @param ex
	 */
	public PersistentException(String message, Throwable ex) {
		super(message, ex);
	}

	/**
	 * @param message
	 */
	public PersistentException(String message) {
		super(message);
	}

	/**
	 * @param ex
	 */
	public PersistentException(Throwable ex) {
		super(ex);
	}

	
}