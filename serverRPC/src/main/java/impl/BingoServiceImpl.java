package impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import service.BingoService;

public class BingoServiceImpl implements BingoService{
	private HashMap<Integer, Integer> urne = new HashMap<Integer,Integer>();
    private static List<Integer> bestScores = new ArrayList<>();

	@Override
	public int calculScore(HashMap<String, Integer> predictions) {
		int score = 0;
        Random random = new Random();
        System.out.println(predictions);
		
        for (int i=0; i<10;i++) {
        	int rand = random.nextInt(10);
        	while (urne.containsKey(rand)) {
            	rand = random.nextInt(10);
        	}
        	urne.put(rand,rand);
            System.out.println("Valeur de l'urne : "+rand+" / Prédiction :"+predictions.get("pred"+i));
            if (rand == predictions.get("pred"+i) ) {
                score++;
            }}
   
        if (!bestScores.contains(score)) {
            bestScores.add(score);
            Collections.sort(bestScores, Collections.reverseOrder()); // Trie les scores en ordre décroissant
            if (bestScores.size() > 3) {
                bestScores = bestScores.subList(0, 3); // Garde seulement les 3 meilleurs scores
            }
        }

        return  score ;
	}

	@Override
    public List<Integer> meilleurScore() {
        System.out.println("bestScores:" + bestScores);
        return bestScores;
    }
    
}