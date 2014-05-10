package br.dcc.ufmg.client;

import java.io.IOException;

import br.dcc.ufmg.rmi.proxy.Stub;

/**
 * @author ricardo
 *
 */
public class ClientStub extends Stub implements Client {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3422147872694455058L;

	/**
	 * @param nameServer
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	ClientStub(String name, String nsAddress, int nsPort)
			throws ClassNotFoundException, IOException {
		super(name, nsAddress, nsPort);
	}

	@Override
	public String getName() {
		String ans = (String) invoke("getName", new Object[] {});
		return ans;
	}

	@Override
	public void notifyMe(String message) {
		invoke("notifyMe", new Object[] { message });
	}
}
