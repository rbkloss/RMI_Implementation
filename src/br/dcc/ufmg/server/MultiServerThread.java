package br.dcc.ufmg.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import br.dcc.ufmg.rmi.proxy.Skeleton;

public class MultiServerThread extends Thread {
	private Socket socket = null;
	private Server chatServer = null;

	public MultiServerThread(Socket socket, Server chatServer) {
		super("MultiServerThread");
		this.socket = socket;
		this.chatServer = chatServer;

	}

	public void run() {

		try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				ObjectOutputStream objOut = new ObjectOutputStream(
						socket.getOutputStream());
				ObjectInputStream objIn = new ObjectInputStream(
						socket.getInputStream());) {
			String inputLine;
			Class<?> c = chatServer.getClass();
			while ((inputLine = in.readLine()) != null) {
				Object[] params = (Object[]) objIn.readObject();
				Method m = c.getDeclaredMethod(inputLine,
						Skeleton.objectArrayToClassArray(params));
				Object ans = m.invoke(chatServer, params);
				objOut.writeObject(ans);
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
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
