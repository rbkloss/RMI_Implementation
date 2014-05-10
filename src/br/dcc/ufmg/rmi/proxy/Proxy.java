package br.dcc.ufmg.rmi.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Proxy {

	private Socket _socket = null;

	private PrintWriter _out = null;
	private BufferedReader _in = null;

	private ObjectInputStream _objIn = null;
	private ObjectOutputStream _objOut = null;

	private String _address;
	private int _port;

	public void setAddress(String address) {
		_address = address;
	}

	public String getAddress() {
		return _address;
	}

	public int getPort() {
		return _port;
	}

	public void setPort(int port) {
		_port = port;
	}

	private boolean socketReady() {
		boolean ans = true;
		if (_socket == null) {
			try {
				_socket = new Socket(_address, _port);
				_out = new PrintWriter(_socket.getOutputStream());
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
		return ans;
	}

	public void writeOnSocket(String s) {
		if (socketReady()) {
			_out.println(s);
		}
	}

	public void writeOnSocket(Object o) {
		if (socketReady()) {
			try {
				_objOut.writeObject(o);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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

}
