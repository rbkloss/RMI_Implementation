package br.dcc.ufmg.rmi.proxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public abstract class Stub implements Serializable {

	String _name;

	/**
	 * 
	 */
	private static final long serialVersionUID = -9200834525936432635L;

	private Socket _socket = null;
	private ObjectInputStream _objIn = null;
	private ObjectOutputStream _objOut = null;

	final private String _address;
	final private int _port;

	protected Stub(String name, String address, int port) {
		_name = name;
		_address = address;
		_port = port;
	}

	@Override
	public void finalize() {
		try {
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
		writeOnSocket(new Object[] { methodName, params });
		System.out.println("Waiting Response from server");
		Object ans = readObjectFromSocket();
		return ans;
	}

	public Object readObjectFromSocket() {
		Object ans = null;
		if (socketReady()) {
			try {
				System.out.println(("Reading from Port: " + _socket.getPort()));
				if (_objIn == null) {
					_objIn = new ObjectInputStream(_socket.getInputStream());
				}
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

	private boolean socketReady() {
		boolean ans = true;

		if (_socket == null) {
			try {
				System.out.println("Creating Socket at [" + _address + "]"
						+ " port [" + _port + "]");
				_socket = new Socket(_address, _port);
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
				if (_objOut == null) {
					_objOut = new ObjectOutputStream(_socket.getOutputStream());
				}
				System.out.println(("Writing at Port: " + _socket.getPort()));
				_objOut.writeObject(o);
				_objOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
