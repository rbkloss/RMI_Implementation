package br.dcc.ufmg.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import br.dcc.ufmg.client.Client;

public class ServerImplementation implements Server {

	ArrayList<Client> _clients;// List of Client Stubs

	@Override
	public void registerClient(Client client) {
		_clients.add(client);
	}

	@Override
	public boolean sendMessageTo(String senderName, String message) {
		try {
			for (Client c : _clients) {
				c.notifyMe(senderName + ":" + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void close() {
		System.exit(0);
	}

	public void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java MultiServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);
		boolean listening = true;

		Server chatServer = new ServerImplementation();

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			while (listening) {
				new MultiServerThread(serverSocket.accept(), chatServer)
						.start();
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}

}
