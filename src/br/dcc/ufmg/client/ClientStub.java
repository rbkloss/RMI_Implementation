package br.dcc.ufmg.client;

import java.rmi.RemoteException;

import br.dcc.ufmg.rmi.proxy.Proxy;

/**
 * @author ricardo
 *
 */
public class ClientStub extends Proxy implements Client {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3422147872694455058L;

	/**
	 * @param nameServer
	 */
	ClientStub() {
		super();
	}

	@Override
	public String getName() throws RemoteException {
		writeOnSocket("getName");
		String ans = readObjectFromSocket().toString();
		return ans;
	}

	@Override
	public void notifyMe(String message) throws RemoteException {
		writeOnSocket("notifyMe");
	}
}
