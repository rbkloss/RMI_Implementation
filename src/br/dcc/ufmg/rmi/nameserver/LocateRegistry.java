package br.dcc.ufmg.rmi.nameserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LocateRegistry {

	static LocateRegistry lr = null;
	Map<String, NameServer> nsMap = null;

	private LocateRegistry() {
		super();
		nsMap = new HashMap<String, NameServer>();
	}

	public static NameServer at(String address, int port) throws IOException,
			ClassNotFoundException {
		if (lr == null) {
			lr = new LocateRegistry();
		}
		NameServer ns = lr.nsMap.get(address + port);
		if (ns == null) {
			ns = new NameServerStub("NameServer", address, port);
			lr.nsMap.put(address + port, ns);
		}
		return ns;
	}
}
