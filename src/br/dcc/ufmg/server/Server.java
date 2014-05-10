package br.dcc.ufmg.server;

import java.rmi.RemoteException;

import br.dcc.ufmg.client.Client;
import br.dcc.ufmg.rmi.remote.Remote;

public interface Server extends Remote {

	public void registerClient(Client client);

	public boolean sendMessageTo(String senderName, String message);

	public void close() throws RemoteException;

}
