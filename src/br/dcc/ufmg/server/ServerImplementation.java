package br.dcc.ufmg.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import br.dcc.ufmg.client.Client;
import br.dcc.ufmg.rmi.nameserver.LocateRegistry;
import br.dcc.ufmg.rmi.nameserver.NameServer;

public class ServerImplementation implements Server {

	ArrayList<Client> _clients;// List of Client Stubs

	public ServerImplementation() {
		_clients = new ArrayList<>();
	}

	@Override
	public void registerClient(Client client) {
		_clients.add(client);
		client.notifyMe("Connected");
	}

	@Override
	public boolean sendMessageTo(String senderName, String message) {
		for (Client c : _clients) {
			c.notifyMe(senderName + ":" + message);
		}
		return true;
	}

	@Override
	public void close() {
		System.exit(0);
	}

	static public void main(String[] args) {
		// if (args.length != 1) {
		// System.err.println("Usage: java MultiServer <port number>");
		// System.exit(1);
		// }

		int portNumber = 2021;
		int nsPort = 2022;
		// portNumber = Integer.parseInt(args[0]);
		boolean listening = true;

		Server chatServer = new ServerImplementation();

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			System.out.println("ChatServer is running on port [" + portNumber
					+ "]");
			NameServer ns = LocateRegistry.at("localhost", nsPort);
			ns.bind("Server", new ServerStub("Server", "localhost", portNumber));
			while (listening) {
				new MultiServerThread(serverSocket.accept(), chatServer)
						.start();
				System.out.println("Client Connected to chatServer");
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
