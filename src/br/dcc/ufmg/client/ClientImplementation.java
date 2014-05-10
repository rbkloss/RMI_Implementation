package br.dcc.ufmg.client;

import java.security.AccessControlException;

import br.dcc.ufmg.rmi.nameserver.LocateRegistry;
import br.dcc.ufmg.rmi.nameserver.NameServer;
import br.dcc.ufmg.rmi.proxy.Stub;
import br.dcc.ufmg.rmi.remote.RemoteHandler;
import br.dcc.ufmg.server.Server;

;

public class ClientImplementation implements Client {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6863896539159512011L;
	String _name;

	public ClientImplementation(String name) {
		_name = name;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void notifyMe(String message) {
		System.out.println("Message Received:" + message);

	}

	public static void main(String[] args) {
		String address = "localhost";
		int port = 2022;
		String endPointName = "Server";

		// String address = args[0];
		// int port = Integer.parseInt(args[1]);
		try {
			System.out.println("Client Started");
			NameServer nameServer = null;
			nameServer = LocateRegistry.at(address, port);
			System.out.println("Located Registry");
			Server serverStub = (Server) nameServer.lookup(endPointName);

			Client client = new ClientImplementation("cl1_");
			Client clientStub = new ClientStub(address, address, port);

			RemoteHandler.exportObject(client, (Stub) clientStub, endPointName,
					port);

			System.out.println("Registering Client");
			serverStub.registerClient(clientStub);
			System.out.println("Client Registered");

			serverStub.sendMessageTo(client.getName(), "lorem ipsum");
			System.out.println("Sending Message");

			serverStub.close();
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
