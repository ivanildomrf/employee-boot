package br.com.imrf.employee.framework;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

public class CheckerUtil {

	public static boolean isDifferent(String string1, String string2) {
		return !StringUtils.equalsIgnoreCase(string1, string2);
	}

	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	public static boolean isNull(Object object) {
		return object == null;
	}

	public static boolean isEmpty(Object object) {
		return StringUtils.isEmpty((String) object);
	}

	public static boolean isNullOrEmpty(Object object) {
		return isNull(object) || isEmpty(object);
	}

	public static boolean isNotEmpty(Object object) {
		return !isEmpty(object);
	}

	public static boolean isNotNullAndNotEmpty(Object object) {
		return isNotNull(object) && isNotEmpty(object);
	}

	public static boolean colecaoNaoEstaNulaNemVazio(Collection<?> objects) {
		return isNotNull(objects) && isCollectionNotEmpty(objects);
	}

	public static boolean isCollectionEmpty(Collection<?> objects) {
		return objects.isEmpty();
	}

	public static boolean isCollectionNotEmpty(Collection<?> objects) {
		return !isCollectionEmpty(objects);
	}
	
	public static boolean isCollectionNotNullNotEmpty(Collection<?> objects) {
		return objects != null && !isCollectionEmpty(objects);
	}	

}
