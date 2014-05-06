package br.dcc.ufmg.rmi.proxy

public class Proxy {

	Proxy(Handler handler)
	
	/*
	 * This method is to be called after the stubs method's.
	 * It propagate the call to the name server
	 */
	public <T> T makeRequisition(NameServer nameServer, Method method,
			ArrayList<Object> params) {
		//finds the nameserver and propagate the requisition
		//return what the name server returns as value
	}

}
