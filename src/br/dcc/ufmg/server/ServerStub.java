package br.dcc.ufmg.server;

import java.rmi.RemoteException;

import br.dcc.ufmg.client.Client;
import br.dcc.ufmg.rmi.proxy.Stub;

public class ServerStub extends Stub implements Server {

	public ServerStub(String name, String address, int port) {
		super(name, address, port);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8504348455373968699L;

	@Override
	public void registerClient(Client client) {
		invoke("registerClient", new Object[] { client });
	}

	public boolean sendMessage(String senderName, String message) {
		boolean ans = (boolean) invoke("sendMessage", new Object[] {
				senderName, message });
		return ans;
	}

	@Override
	public void close() throws RemoteException {
		invoke("close", new Object[] {});
	}

}
