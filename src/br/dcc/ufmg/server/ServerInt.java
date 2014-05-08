package br.dcc.ufmg.server;

import java.rmi.RemoteException;

import br.dcc.ufmg.client.ClientInt;
import br.dcc.ufmg.rmi.remote.Remote;

public interface ServerInt extends Remote{
	
	public void registerClient(ClientInt client) throws RemoteException;

	public boolean sendMessageTo(String senderName, String message)
			throws RemoteException;

	public void close() throws RemoteException;


}
