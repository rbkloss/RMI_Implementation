package br.dcc.ufmg.rmi.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Skeleton {
	Object _hostObject;
	
	public void registerObject(Object obj){
		_hostObject = obj;
	}
	

	/**
	 * Requests the execution of a remote method.
	 * @param objectName Name of the Object to execute the request
	 * @param methodName Name of the method requested
	 * @param params Parameters of the method to be executed
	 * @return The Value returned by the requested execution
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object request(String objectName, String methodName, Object[] params)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		Class<?> classObj = _hostObject.getClass();

		Method method = classObj.getDeclaredMethod(methodName,
				objectArrayToClassArray(params));
		
		Object ans = method.invoke(_hostObject, params);
		return ans;
		
	}

	/**
	 * @param objects the Array of Objects to convert to an Array of Class<?>
	 * @return
	 */
	public static Class<?>[] objectArrayToClassArray(Object[] objects) {
		Class<?>[] classes = new Class<?>[objects.length];
		int i = 0;
		for (Object obj : objects) {
			classes[i] = obj.getClass();
		}
		return classes;
	}
}
