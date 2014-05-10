package br.dcc.ufmg.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.security.AccessControlException;

import br.dcc.ufmg.rmi.nameserver.NameServer;
import br.dcc.ufmg.server.ServerInt;
import br.dcc.ufmg.rmi.nameserver.LocateRegistry;

;

public class ClientImplementation implements ClientInt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6830495669897766499L;

	String _name;

	public ClientImplementation(String name) {
		_name = name;
	}

	@Override
	public String getThisHostAddress() throws UnknownHostException {
		return (Inet4Address.getLocalHost().getHostAddress());
	}

	@Override
	public String getName() throws RemoteException {
		return _name;
	}

	@Override
	public void notifyMe(String message) throws RemoteException {
		System.out.println("Message Received:" + message);

	}

	public static void main(String[] args) {
		String address = args[0];
		int port = 1099;
		port = Integer.parseInt(args[1]);
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			System.out.println("Client Started");
			NameServer nameServer = null;
			nameServer = LocateRegistry.at(address, port);
			System.out.println("Located Registry");
			ServerInt serverStub = (ServerInt) nameServer.lookup("Server");

			ClientInt client = new ClientImplementation("cl1_");

			ClientInt clientStub = new ClientStub(nameServer);
			nameServer.bind(args[0], clientStub);
			System.out.println("Exported Object");
			// Bind the remote object's stub in the registry

			serverStub.registerClient(client);
			serverStub.sendMessageTo(client.getName(), "lorem ipsum");

			System.exit(0);
		} catch (AccessControlException ex) {
			System.err.println("Server exception: " + ex.toString());
			System.err.println("Permission: " + ex.getPermission());
			ex.printStackTrace();
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
