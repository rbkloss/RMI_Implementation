package br.dcc.ufmg.rmi.nameserver;

import br.dcc.ufmg.rmi.proxy.Proxy;

public interface NameServer {

	Proxy lookup(String name);
	
	Proxy bind(String name, Object object);

	Object remoteInvokation(String className, String method,
			Object[] params);

}
