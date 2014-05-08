package br.dcc.ufmg.rmi.nameserver;

import java.rmi.RemoteException;
import java.util.ArrayList;

import br.dcc.ufmg.client.ClientInt;
import br.dcc.ufmg.server.ServerInt;

public class ServerImplementation implements ServerInt {
	ArrayList<ClientInt> _clients;

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
	public int[] getThisHostAddress() {
		// TODO Auto-generated method stub
		return null;
	}

}
