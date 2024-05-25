package service;

import java.util.HashMap;
import java.util.List;
public interface BingoService {
	public int calculScore(HashMap<String, Integer> predictions);
    public List<Integer> meilleurScore();

}