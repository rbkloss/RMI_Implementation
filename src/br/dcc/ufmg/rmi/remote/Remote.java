package br.dcc.ufmg.rmi.remote;

/**
 * @author ricardo
 *
 */
public interface Remote {
	
	/**
	 * @return the Adress of the Machine this Instance is running
	 */
	int[] getThisHostAddress();

}
