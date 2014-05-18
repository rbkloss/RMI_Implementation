package br.dcc.ufmg.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class MultiServerThread extends Thread {
	private Socket socket = null;
	private Server chatServer = null;

	public MultiServerThread(Socket socket, Server chatServer) {
		super("MultiServerThread");
		this.socket = socket;
		this.chatServer = chatServer;

	}

	public void run() {
		Method m = null;
		Object[] params = null;
		String inputLine = null;
		try (ObjectOutputStream objOut = new ObjectOutputStream(
				socket.getOutputStream());
				ObjectInputStream objIn = new ObjectInputStream(
						socket.getInputStream());) {
			Object[] input;
			Method[] ms = chatServer.getClass().getDeclaredMethods();
			System.out.println("Waiting for Response");
			while ((input = (Object[]) objIn.readObject()) != null) {
				inputLine = (String) input[0];
				System.out.println("Requisition message is :[" + inputLine
						+ "]");
				System.out.println("Waiting for params");
				params = (Object[]) input[1];
				Object ans = null;
				for (Method method : ms) {
					m = method;
					if (m.getName().equalsIgnoreCase(inputLine)) {
						System.out.println("Executing method [" + m.getName()
								+ "]");
						ans = m.invoke(chatServer, params);
						if (ans == null) {
							objOut.writeObject(new Object[] {});
						} else {
							objOut.writeObject(ans);
						}
					}
				}
				objOut.flush();
				System.out.println("Waiting for Response");
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
