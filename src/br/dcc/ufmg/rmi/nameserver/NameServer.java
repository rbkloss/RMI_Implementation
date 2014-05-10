package br.dcc.ufmg.rmi.nameserver;

import br.dcc.ufmg.rmi.proxy.Stub;
import br.dcc.ufmg.rmi.remote.Remote;

public interface NameServer extends Remote {
	public Stub lookup(String name);

	public int lookupPort(String name);

	public Stub bind(String name, Object object);

}

/*
 * public static Registry More ...getRegistry(String host, int port,
 * RMIClientSocketFactory csf) throws RemoteException { Registry registry =
 * null;
 * 
 * if (port <= 0) port = Registry.REGISTRY_PORT;
 * 
 * if (host == null || host.length() == 0) { // If host is blank (as returned by
 * "file:" URL in 1.0.2 used in // java.rmi.Naming), try to convert to real
 * local host name so // that the RegistryImpl's checkAccess will not fail. try
 * { host = java.net.InetAddress.getLocalHost().getHostAddress(); } catch
 * (Exception e) { // If that failed, at least try "" (localhost) anyway... host
 * = ""; } }
 * 
 * /* Create a proxy for the registry with the given host, port, and client
 * socket factory. If the supplied client socket factory is null, then the ref
 * type is a UnicastRef, otherwise the ref type is a UnicastRef2. If the
 * property java.rmi.server.ignoreStubClasses is true, then the proxy returned
 * is an instance of a dynamic proxy class that implements the Registry
 * interface; otherwise the proxy returned is an instance of the pregenerated
 * stub class for RegistryImpl.
 */
/*
 * LiveRef liveRef = new LiveRef(new ObjID(ObjID.REGISTRY_ID), new
 * TCPEndpoint(host, port, csf, null), false); RemoteRef ref = (csf == null) ?
 * new UnicastRef(liveRef) : new UnicastRef2(liveRef);
 * 
 * return (Registry) Util.createProxy(RegistryImpl.class, ref, false); }
 */