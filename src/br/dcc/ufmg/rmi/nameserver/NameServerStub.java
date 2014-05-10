package br.dcc.ufmg.rmi.nameserver;

import java.io.IOException;

import br.dcc.ufmg.rmi.proxy.Stub;

public class NameServerStub extends Stub implements NameServer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7632063024427358347L;

	public NameServerStub(String name, String address, int port)
			throws ClassNotFoundException, IOException {
		super(name, address, port);
	}

	@Override
	public Stub lookup(String name) {
		Stub ans = null;
		ans = (Stub) invoke("lookup", new Object[] { name });
		return ans;
	}

	@Override
	public Stub bind(String name, Object object) {
		Stub ans = (Stub) invoke("bind", new Object[] { name, object });
		return ans;
	}

	@Override
	public int lookupPort(String name) {
		int ans = (int) invoke("lookupPort", new Object[] { name });
		return ans;
	}
}
