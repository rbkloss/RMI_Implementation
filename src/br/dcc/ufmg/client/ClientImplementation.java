package br.dcc.ufmg.client;

import java.security.AccessControlException;

import br.dcc.ufmg.rmi.nameserver.LocateRegistry;
import br.dcc.ufmg.rmi.nameserver.NameServer;
import br.dcc.ufmg.server.Server;

;

public class ClientImplementation implements Client {
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
		String address = args[0];
		int port = Integer.parseInt(args[1]);
		try {
			System.out.println("Client Started");
			NameServer nameServer = null;
			nameServer = LocateRegistry.at(address, port);
			System.out.println("Located Registry");
			Server serverStub = (Server) nameServer.lookup("Server");

			Client client = new ClientImplementation("cl1_");

			Client clientStub = new ClientStub();
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
