package br.dcc.ufmg.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.security.AccessControlException;

import br.dcc.ufmg.rmi.nameserver.LocateRegistry;
import br.dcc.ufmg.rmi.nameserver.NameServer;
import br.dcc.ufmg.server.Server;

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
		int nsPort = 2022;
		int myPort = 2020;
		String endPointName = "Server";

		// String address = args[0];
		// int port = Integer.parseInt(args[1]);
		try (ServerSocket serverSocket = new ServerSocket(myPort)) {
			System.out.println("Client Started");
			NameServer nameServer = null;
			nameServer = LocateRegistry.at(address, nsPort);
			System.out.println("Located Registry");
			Server serverStub = (Server) nameServer.lookup(endPointName);

			Client client = new ClientImplementation("cl1_");
			Client clientStub = new ClientStub("Client", address, myPort);

			ClientHandler handler = new ClientHandler(client, serverSocket);

			System.out.println("Registering Client");
			serverStub.registerClient(clientStub);
			System.out.println("Client Registered");
			handler.start();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String input;
			while ((input = in.readLine()) != null) {
				if (input.equalsIgnoreCase("Bye")) {
					break;
				} else {
					String message = in.readLine();
					serverStub.sendMessage(client.getName(), message + 1);
					System.out.println("Sending Message");
				}
			}

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
