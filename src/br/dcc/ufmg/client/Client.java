package br.dcc.ufmg.client;

import java.io.Serializable;
import java.rmi.RemoteException;

import br.dcc.ufmg.rmi.remote.Remote;

public interface Client extends Serializable, Remote {

	/**
	 * 
	 * @return The name of the instance of this class.
	 * @throws RemoteException
	 */
	String getName();

	/**
	 * Method for callback. This method is called by the server to notify this
	 * Client that there is a new message for it.
	 * 
	 * @param message
	 *            the new message it received
	 * @throws RemoteException
	 */
	void notifyMe(String message);
}
