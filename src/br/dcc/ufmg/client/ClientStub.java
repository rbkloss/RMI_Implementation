package br.dcc.ufmg.client;

import java.rmi.RemoteException;

import br.dcc.ufmg.rmi.nameserver.NameServer;
import br.dcc.ufmg.rmi.proxy.Proxy;

/**
 * @author ricardo
 *
 */
public class ClientStub extends Proxy implements ClientInt {
	
	/**
	 * @param nameServer
	 */
	ClientStub(NameServer nameServer, String id, String hostAddress) {
		super(nameServer);
	}

	/* (non-Javadoc)
	 * @see br.dcc.ufmg.client.ClientInt#getName()
	 */
	@Override
	public String getName() throws RemoteException {
		return (String) makeRequisition(this.getClass().getName(), "getName", null);
	}

	/* (non-Javadoc)
	 * @see br.dcc.ufmg.client.ClientInt#notifyMe(java.lang.String)
	 */
	@Override
	public void notifyMe(String message) throws RemoteException {
		this.makeRequisition(getName(), "notifyMe", new Object[]{message});		
	}

	@Override
	public int[] getThisHostAddress() {
		// TODO Auto-generated method stub
		return null;
	}

}
