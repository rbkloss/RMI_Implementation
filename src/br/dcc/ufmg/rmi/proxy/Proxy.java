package br.dcc.ufmg.rmi.proxy;

import br.dcc.ufmg.rmi.nameserver.NameServer;

public class Proxy {

	NameServer _nameServer;

	protected Proxy(NameServer nameServer) {
		_nameServer = nameServer;
	}

	/*
	 * This method is to be called after the stubs method's. It propagate the
	 * call to the name server
	 */
	public Object makeRequisition(String className, String method,
			Object[] params) {
		// finds the nameserver and propagate the requisition
		// return what the name server returns as value
		return  _nameServer.remoteInvokation(className, method, params);
	}

}
