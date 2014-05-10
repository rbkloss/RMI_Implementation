package br.dcc.ufmg.rmi.proxy;

public class Skeleton {

	/**
	 * @param objects
	 *            the Array of Objects to convert to an Array of Class<?>
	 * @return
	 */
	private static Class<?>[] objectArrayToClassArray(Object[] objects) {
		Class<?>[] classes = new Class<?>[objects.length];
		int i = 0;
		for (Object obj : objects) {
			classes[i] = obj.getClass();
		}
		return classes;
	}
}
