package br.dcc.ufmg.rmi.nameserver;

public interface NameServer {

	Remote lookup(String name);
	Remote bind(String name, Remote object);
	
}
