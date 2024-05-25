package service;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import impl.BingoServiceImpl;

public class ServerRMI  {
	public static void main(String[] args) throws RemoteException, MalformedURLException {

		LocateRegistry.createRegistry(5005);
		BingoServiceImpl bingo=new BingoServiceImpl();

		Naming.rebind("rmi://127.0.0.1:5005/bingo/jeux",bingo);
		System.out.println("Le serveur RMI est à l'écoute .. ");

	}
}