package br.dcc.ufmg.server;

import br.dcc.ufmg.client.Client;
import br.dcc.ufmg.rmi.proxy.Proxy;

public class ServerStub extends Proxy implements Server {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5008663406604601710L;

	@Override
	public void registerClient(Client client) {
		writeOnSocket("registerClient");
	}

	@Override
	public boolean sendMessageTo(String senderName, String message) {
		writeOnSocket("sendMessage");
		Object[] params = new Object[] { senderName, message };
		writeOnSocket(params);
		boolean ans = ((Boolean) readObjectFromSocket()).booleanValue();

		return ans;
	}

	@Override
	public void close() {
		writeOnSocket("close");
	}

}
