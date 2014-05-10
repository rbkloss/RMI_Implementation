package br.dcc.ufmg.rmi.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

import br.dcc.ufmg.rmi.nameserver.LocateRegistry;
import br.dcc.ufmg.rmi.nameserver.NameServer;

public abstract class Stub implements Serializable {

	String _name;
	final NameServer ns;

	/**
	 * 
	 */
	private static final long serialVersionUID = -9200834525936432635L;

	private Socket _socket = null;

	private PrintWriter _out = null;

	private BufferedReader _in = null;
	private ObjectInputStream _objIn = null;

	private ObjectOutputStream _objOut = null;
	final private String _address;

	final private int _port;
	protected Stub(String name, String nsAddress, int nsPort)
			throws ClassNotFoundException, IOException {
		_name = name;
		ns = LocateRegistry.at(nsAddress, nsPort);
		_address = nsAddress;
		_port = ns.lookupPort(name);
	}

	@Override
	public void finalize() {
		try {
			_in.close();
			_out.close();
			_objIn.close();
			_objOut.close();

			_socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getAddress() {
		return _address;
	}

	public int getPort() {
		return _port;
	}

	public Object invoke(String methodName, Object params[]) {
		System.out.println("Invoking method [" + methodName + "]");
		writeOnSocket(methodName);
		writeOnSocket(params);
		System.out.println("Waiting Response from server");
		Object ans = readObjectFromSocket();
		return ans;
	}

	public Object readObjectFromSocket() {
		Object ans = null;
		if (socketReady()) {
			try {
				ans = _objIn.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ans;
	}

	public String readStringFromSocket() {
		String ans = null;
		if (socketReady()) {
			try {
				ans = _in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ans;
	}

	private boolean socketReady() {
		boolean ans = true;

		if (_socket == null) {
			try {

				System.out.println("Creating Socket at [" + _address + "]"
						+ " port [" + _port + "]");

				_socket = new Socket(_address, _port);
				_out = new PrintWriter(_socket.getOutputStream(), true);
				_in = new BufferedReader(new InputStreamReader(
						_socket.getInputStream()));

				_objIn = new ObjectInputStream(_socket.getInputStream());
				_objOut = new ObjectOutputStream(_socket.getOutputStream());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ans = false;
			}
		}
		while (!_socket.isConnected())
			;

		return ans;
	}

	@Override
	public String toString() {
		return "Name:[Proxy] [" + _address + "] [" + _port + "]";
	}

	public void writeOnSocket(Object o) {
		if (socketReady()) {
			try {
				_objOut.writeObject(o);
				_objOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void writeOnSocket(String s) {
		if (socketReady()) {
			_out.println(s);
		}
	}

}
