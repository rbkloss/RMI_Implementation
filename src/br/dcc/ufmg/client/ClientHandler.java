package br.dcc.ufmg.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler extends Thread {
	Client c;
	ServerSocket ss;

	ClientHandler(Client implementation, ServerSocket socket) {
		c = implementation;
		ss = socket;
	}

	public void run() {
		try (Socket s = ss.accept();
				ObjectInputStream objIn = new ObjectInputStream(
						s.getInputStream());
				ObjectOutputStream objOut = new ObjectOutputStream(
						s.getOutputStream());) {
			Object[] input;
			String inputLine;
			Method[] ms = c.getClass().getDeclaredMethods();
			while ((input = (Object[]) objIn.readObject()) != null) {
				inputLine = (String) input[0];
				System.out.println("Requisition :[" + inputLine + "]");
				System.out.println("Requisition :[" + inputLine + "]");
				Object[] params = (Object[]) input[1];
				for (Method m : ms) {
					if (m.getName().equals(inputLine)) {
						System.out.println("Executing method [" + m.getName()
								+ "]");
						Object ans = m.invoke(c, params);
						if (ans != null) {
							objOut.writeObject(ans);
						} else {
							objOut.writeObject(new Object[] {});
						}
						objOut.flush();
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
