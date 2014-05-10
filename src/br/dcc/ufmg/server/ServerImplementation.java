package br.dcc.ufmg.server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import br.dcc.ufmg.client.ClientInt;

public class ServerImplementation implements ServerInt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9206674187728494188L;
	ArrayList<ClientInt> _clients;//List of Client Stubs

	@Override
	public void registerClient(ClientInt client) throws RemoteException {
		_clients.add(client);
	}

	@Override
	public boolean sendMessageTo(String senderName, String message)
			throws RemoteException {
		try {
			for (ClientInt c : _clients) {
				c.notifyMe(senderName + ":" + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void close() throws RemoteException {
		System.exit(0);
	}

	@Override
	public String getThisHostAddress() {
		// TODO Auto-generated method stub
		return null;
	}

}
