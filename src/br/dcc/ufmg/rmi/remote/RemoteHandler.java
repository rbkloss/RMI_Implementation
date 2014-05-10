//package br.dcc.ufmg.rmi.remote;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.PrintWriter;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.net.Socket;
//
//import br.dcc.ufmg.rmi.nameserver.LocateRegistry;
//import br.dcc.ufmg.rmi.nameserver.NameServer;
//import br.dcc.ufmg.rmi.proxy.Stub;
//
//public class RemoteHandler extends Thread {
//
//	final Object _o;
//	final Stub _stub;
//	String _address;
//
//	final int _port;
//
//	RemoteHandler(Object o, Stub stub, int port) {
//		_o = o;
//		_stub = stub;
//		_port = port;
//		_address = "localhost";
//	}
//
//	public static void exportObject(Object o, Stub stub, String serverName,
//			int port) {
//		new RemoteHandler(o, stub, port).start();
//	}
//
//	@Override
//	public void run() {
//		Method m = null;
//		Object[] params = null;
//		String inputLine = null;
//		NameServer ns = null;
//		ns = LocateRegistry.at("localhost", port)
//		int serverPort = ns.lookupPort(_serverName);
//
//		try (Socket socket = new Socket(_address, serverPort);
//				PrintWriter out = new PrintWriter(socket.getOutputStream(),
//						true);
//				BufferedReader in = new BufferedReader(new InputStreamReader(
//						socket.getInputStream()));
//				ObjectOutputStream objOut = new ObjectOutputStream(
//						socket.getOutputStream());
//				ObjectInputStream objIn = new ObjectInputStream(
//						socket.getInputStream());) {
//
//			Class<?> c = _o.getClass();
//			System.out.println("Waiting for Response");
//			while ((inputLine = in.readLine()) != null) {
//				System.out.println("Requisition message is :[" + inputLine
//						+ "]");
//				System.out.println("Waiting for params");
//				params = (Object[]) objIn.readObject();
//				Method[] ms = c.getDeclaredMethods();
//				Object ans = null;
//				for (Method method : ms) {
//					m = method;
//					if (m.getName().equalsIgnoreCase(inputLine)) {
//						System.out.println("Invoking method [" + m.getName()
//								+ "]");
//						ans = m.invoke(_o, params);
//					}
//				}
//				if (ans == null) {
//					objOut.writeObject(new Object[] {});
//				} else {
//					objOut.writeObject(ans);
//				}
//				objOut.flush();
//				System.out.println("Waiting for Response");
//			}
//			socket.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//}
