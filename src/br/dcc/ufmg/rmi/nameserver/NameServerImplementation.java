package br.dcc.ufmg.rmi.nameserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import br.dcc.ufmg.rmi.proxy.Proxy;

public class NameServerImplementation implements NameServer {

	Map<String, Proxy> _stubs;
	Map<String, Socket> _clientsSockets;
	{
		_stubs = new HashMap<String, Proxy>();
		_clientsSockets = new HashMap<String, Socket>();
	}

	@Override
	public Proxy lookup(String name) {
		Proxy p = _stubs.get(name);

		return p;
	}

	@Override
	public Proxy bind(String name, Object object) {
		_stubs.put(name, (Proxy) object);
		return _stubs.get(name);
	}

	public static void main(String args[]) {

		if (args.length != 1) {
			System.err.println("Usage: java NameServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);
		boolean listening = true;
		NameServer ns = new NameServerImplementation();

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			while (listening) {
				new NameServerStubThread(serverSocket.accept(), ns).start();
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}

	}
}