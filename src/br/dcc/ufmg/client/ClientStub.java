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
	 * 
	 */
	private static final long serialVersionUID = -2818548325173650274L;

	String _address;
	/**
	 * @param nameServer
	 */
	ClientStub(NameServer nameServer) {
		super(nameServer);		
		_address = this.getThisHostAddress();
	}

	/* (non-Javadoc)
	 * @see br.dcc.ufmg.client.ClientInt#getName()
	 */
	@Override
	public String getName() throws RemoteException {
		return (String) request(this.getClass().getName(), "getName", null);
	}

	/* (non-Javadoc)
	 * @see br.dcc.ufmg.client.ClientInt#notifyMe(java.lang.String)
	 */
	@Override
	public void notifyMe(String message) throws RemoteException {
		request(getName(), "notifyMe", new Object[]{message});		
	}

	@Override
	public String getThisHostAddress() {		
		return _address;
	}

}
