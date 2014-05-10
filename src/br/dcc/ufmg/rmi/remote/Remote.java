package br.dcc.ufmg.rmi.remote;

import java.io.Serializable;
import java.net.UnknownHostException;

/**
 * @author ricardo
 *
 */
public interface Remote extends Serializable {
	
	/**
	 * @return the Adress of the Machine this Instance is running
	 * @throws UnknownHostException 
	 */
	String getThisHostAddress() throws UnknownHostException;

}
