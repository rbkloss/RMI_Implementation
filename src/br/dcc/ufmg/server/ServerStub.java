package br.dcc.ufmg.server;

import java.rmi.RemoteException;

import br.dcc.ufmg.client.ClientInt;
import br.dcc.ufmg.rmi.nameserver.NameServer;
import br.dcc.ufmg.rmi.proxy.Proxy;

public class ServerStub extends Proxy implements ServerInt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8504348455373968699L;

	protected ServerStub(NameServer nameServer) {
		super(nameServer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerClient(ClientInt client) throws RemoteException {
		this.request(getClass().getName(), "registerClient",
				new Object[] { client });

		// XXX the client to be registered must be a stub, but as is,
		// this is not guaranteed
	}

	@Override
	public boolean sendMessageTo(String senderName, String message)
			throws RemoteException {
		Boolean ans = (Boolean) this.request(getClass().getName(),
				"sendMessageTo", new Object[] { senderName, message });
		return ans.booleanValue();
	}

	@Override
	public void close() throws RemoteException {
		request(getClass().getName(), "close", null);
	}

	@Override
	public String getThisHostAddress() {
		// TODO Auto-generated method stub
		return null;
	}

}
