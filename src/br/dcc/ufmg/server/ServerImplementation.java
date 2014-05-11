package br.dcc.ufmg.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

import br.dcc.ufmg.client.Client;
import br.dcc.ufmg.rmi.nameserver.LocateRegistry;
import br.dcc.ufmg.rmi.nameserver.NameServer;

public class ServerImplementation implements Server {
	boolean _listening = true;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1725330208313977625L;
	ArrayList<Client> _clients;// List of Client Stubs

	public ServerImplementation() {
		_clients = new ArrayList<>();
	}

	@Override
	public void registerClient(Client client) {
		_clients.add(client);		
	}

	@Override
	public boolean sendMessage(String senderName, String message) {
		for (Client c : _clients) {
			c.notifyMe(senderName + ":" + message);
		}
		return true;
	}

	@Override
	public int getPort() {
		return 0;
	}

	@Override
	public String getAddress() {
		return "";
	}

	@Override
	public void close() {
		_listening = false;
	}

	public boolean isListening() {
		return _listening;
	}

	static public void main(String[] args) {
		// if (args.length != 1) {
		// System.err.println("Usage: java MultiServer <port number>");
		// System.exit(1);
		// }

		int myPort = 2021;
		int nsPort = 2022;

		// portNumber = Integer.parseInt(args[0]);

		Server chatServer = new ServerImplementation();

		try (ServerSocket serverSocket = new ServerSocket(myPort)) {
			System.out.println("ChatServer is running on port [" + myPort
					+ "]");
			NameServer ns = LocateRegistry.at("localhost", nsPort);
			ns.bind("Server", new ServerStub("Server", "localhost", myPort));

			while (((ServerImplementation) chatServer).isListening()) {
				new MultiServerThread(serverSocket.accept(),
						(Server) chatServer).start();
				System.out.println("Client Connected to chatServer");
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + myPort);
			System.exit(-1);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
