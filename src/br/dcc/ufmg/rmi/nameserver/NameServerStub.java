package br.dcc.ufmg.rmi.nameserver;

import br.dcc.ufmg.rmi.proxy.Proxy;

public class NameServerStub extends Proxy implements NameServer {
	public NameServerStub(String address, int port) {
		setAddress(address);
		setPort(port);
	}

	@Override
	public Proxy lookup(String name) {
		Object[] params = null;
		params = new Object[1];
		params[0] = name;
		Proxy ans = null;

		writeOnSocket("lookup");
		writeOnSocket(params);
		ans = (Proxy) readObjectFromSocket();

		return ans;
	}

	@Override
	public Proxy bind(String name, Object object) {

		Object[] params = null;
		params = new Object[2];
		params[0] = name;
		params[1] = object;
		Proxy ans = null;

		writeOnSocket("bind");
		writeOnSocket(params);
		ans = (Proxy) readObjectFromSocket();

		return ans;
	}

}
