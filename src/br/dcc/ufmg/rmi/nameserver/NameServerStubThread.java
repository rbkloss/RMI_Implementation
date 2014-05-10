package br.dcc.ufmg.rmi.nameserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import br.dcc.ufmg.rmi.proxy.Proxy;

public class NameServerStubThread extends Thread {

	private Socket _client;
	private NameServer _ns;

	public NameServerStubThread(Socket s0, NameServer ns) {
		super("NameServerStubThread");
		_client = s0;
		_ns = ns;
	}

	public void run() {
		try (PrintWriter out = new PrintWriter(_client.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						_client.getInputStream()));
				ObjectOutputStream objOut = new ObjectOutputStream(
						_client.getOutputStream());
				ObjectInputStream objIn = new ObjectInputStream(
						_client.getInputStream());) {
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				Object[] params = (Object[]) objIn.readObject();
				if (inputLine.equals("bind")) {
					String name = params[0].toString();
					Proxy ans = _ns.bind(name, params[1]);

					objOut.writeObject(new Object[] { ans });

				} else if (inputLine.equals("lookup")) {
					String name = params[0].toString();
					Proxy ans = _ns.lookup(name);

					objOut.writeObject(new Object[] { ans });

				}

			}
			_client.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
