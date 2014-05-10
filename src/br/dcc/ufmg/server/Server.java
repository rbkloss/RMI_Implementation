package br.dcc.ufmg.server;

import java.rmi.RemoteException;

import br.dcc.ufmg.client.Client;

public interface Server {

	public void registerClient(Client client);

	public boolean sendMessageTo(String senderName, String message);

	public void close() throws RemoteException;

}
