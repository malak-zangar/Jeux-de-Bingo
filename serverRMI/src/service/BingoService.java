package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import java.util.HashMap;
import java.util.List;
public interface BingoService extends Remote {
	public int calculScore(HashMap<String, Integer> predictions) throws RemoteException;
    public List<Integer> meilleurScore() throws RemoteException;

}