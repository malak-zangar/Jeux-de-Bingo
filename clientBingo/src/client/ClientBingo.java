package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ClientBingo {
	public static void main(String[] args) throws IOException {
    	Scanner scanner = new Scanner(System.in);
    	
    	Socket socket = new Socket("127.0.0.1", 5001);
    	
    	InputStreamReader in = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(in);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
        
        boolean quit=false;

		System.out.println("\n ******* Choix du serveur *******\n");
		System.out.println("1- RPC");
        System.out.println("2- RMI");
		System.out.print(" \n-> Choisissez un serveur : ");			

		int choice=scanner.nextInt();
		while(choice<1 || choice>2){
			System.out.println("Option invalide");
			choice=scanner.nextInt();
		}
		printWriter.println(choice);

        while(!quit) {
			System.out.println("\n ******* Bienvenue au jeu de BINGO *******\n");
			System.out.println("1- Jouer BINGO");
            System.out.println("2- Consulter meilleur score");
			System.out.println("3- Quitter");
			System.out.print(" \n-> Choisissez une option: ");			
			int n = scanner.nextInt();
            printWriter.println(n);
            
			switch(n){
				case 1: 
					System.out.println("Les prédictions sont de 0 à 9\n");
					for(int i=0;i<10;i++){
							System.out.print("Votre prédiction pour la valeur "+(i+1)+":");
							int nbr = scanner.nextInt();
							while(nbr<0 || nbr>9) {
								System.out.println("Prédiction doit être comprise entre 0 et 9!!");
								System.out.print("Votre prédiction pour la valeur "+(i+1)+":");
								nbr = scanner.nextInt();
							}
							printWriter.println(nbr);
					}
                    String score = bufferedReader.readLine();
                    System.out.println("Votre score est : "+ score + "/10");
					break;
				case 2: 
				 int len = Integer.parseInt(bufferedReader.readLine());

				System.out.println(" \n Les meilleurs scores sont : ");

				for (int i=0;i<len;i++){
                     String str = bufferedReader.readLine();
                    System.out.println(str +"/10");}
					break;

				case 3: 
					System.out.println("Au revoir!");
					quit=true;
					break;
				default:
					System.out.println("Option invalide!");
					break;
			}
        
        }
		   printWriter.close();
            bufferedReader.close();
            socket.close();
		    scanner.close();
	}
}