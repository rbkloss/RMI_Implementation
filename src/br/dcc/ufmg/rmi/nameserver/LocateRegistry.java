package br.dcc.ufmg.rmi.nameserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class LocateRegistry {
	static Socket _s;

	public static NameServer at(String address, int port) throws IOException,
			ClassNotFoundException {
		boolean fail = false;
		try {
			_s = new Socket(address, port);
		} catch (IOException e) {
			e.printStackTrace();
			fail = true;
		} finally {
			if (fail) {
				return null;
			}

		}
		PrintWriter pw = new PrintWriter(_s.getOutputStream(), true);
		pw.println("new");

		InputStream is = _s.getInputStream();
		ObjectInputStream ois = new ObjectInputStream(is);
		NameServer ns = (NameServer) ois.readObject();
		ois.close();
		is.close();
		return ns;
	}

	public static void close() throws IOException {
		_s.close();
	}

}
