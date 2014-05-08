package br.dcc.ufmg.rmi.nameserver;

import java.rmi.RemoteException;

import br.dcc.ufmg.client.ClientInt;
import br.dcc.ufmg.rmi.proxy.Proxy;
import br.dcc.ufmg.server.ServerInt;

public class ServerStub extends Proxy implements ServerInt {

	protected ServerStub(NameServer nameServer) {
		super(nameServer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerClient(ClientInt client) throws RemoteException {
		this.makeRequisition(getClass().getName(), "registerClient",
				new Object[] { client });

		// XXX the client to be registered must be a stub, but as is,
		// this is not guaranteed
	}

	@Override
	public boolean sendMessageTo(String senderName, String message)
			throws RemoteException {
		Boolean ans = (Boolean) this.makeRequisition(getClass().getName(),
				"sendMessageTo", new Object[] { senderName, message });
		return ans.booleanValue();
	}

	@Override
	public void close() throws RemoteException {
		makeRequisition(getClass().getName(), "close", null);
	}

	@Override
	public int[] getThisHostAddress() {
		// TODO Auto-generated method stub
		return null;
	}

}
