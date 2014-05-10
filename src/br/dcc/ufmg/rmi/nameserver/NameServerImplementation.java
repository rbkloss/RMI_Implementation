package br.dcc.ufmg.rmi.nameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

import br.dcc.ufmg.rmi.proxy.Stub;

public class NameServerImplementation implements NameServer {

	Map<String, Stub> _stubs;
	Map<String, Integer> _ClientPortMap;

	{
		_stubs = new HashMap<String, Stub>();
		_ClientPortMap = new HashMap<String, Integer>();
	}

	@Override
	public Stub lookup(String name) {
		Stub p = _stubs.get(name);

		return p;
	}

	@Override
	public Stub bind(String name, Object object) {
		_stubs.put(name, (Stub) object);
		return _stubs.get(name);
	}

	public static void main(String args[]) {

		// if (args.length != 1) {
		// System.err.println("Usage: java NameServer <port number>");
		// System.exit(1);
		// }

		int portNumber = 2022;
		// portNumber = Integer.parseInt(args[0]);
		boolean listening = true;
		NameServer ns = new NameServerImplementation();

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			System.out.println("NameServer is running on port [" + portNumber
					+ "]");
			while (listening) {
				new NameServerStubThread(serverSocket.accept(), ns).start();
				System.out.println("Client Connected to NameServer");
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}

	}

	@Override
	public int lookupPort(String name) {
		int ans = _ClientPortMap.get(name);
		return ans;
	}
}