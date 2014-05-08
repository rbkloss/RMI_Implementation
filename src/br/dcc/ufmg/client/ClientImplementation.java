package br.dcc.ufmg.client;

import java.rmi.RemoteException;

public class ClientImplementation implements ClientInt {
	String _name;

	public ClientImplementation(String name) {
		_name = name;
	}

	@Override
	public int[] getThisHostAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() throws RemoteException {
		return _name;
	}

	@Override
	public void notifyMe(String message) throws RemoteException {
		System.out.println("Message Received:" + message);

	}

}
