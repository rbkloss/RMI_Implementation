package br.dcc.ufmg.client;

import java.rmi.RemoteException;

public interface Client {

	/**
	 * 
	 * @return The name of the instance of this class.
	 * @throws RemoteException
	 */
	String getName() throws RemoteException;

	/**
	 * Method for callback. This method is called by the server to notify this
	 * Client that there is a new message for it.
	 * 
	 * @param message
	 *            the new message it received
	 * @throws RemoteException
	 */
	void notifyMe(String message) throws RemoteException;
}
