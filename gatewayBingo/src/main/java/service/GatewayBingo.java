package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.rmi.AccessException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
  
public class GatewayBingo  {

    public static void main(String[] args) throws IOException ,MalformedURLException,NotBoundException,RemoteException {
        ServerSocket serverSocket = new ServerSocket(5001); // Écoute sur le port 5001

        System.out.println("GatewayBingo en attente de connexion...");



        try{
        while (true) {
            Socket clientSocket = serverSocket.accept(); // Accepte les connexions entrantes
            System.out.println("Client connecté.");

            // Crée un thread pour gérer la communication avec le client
            Thread thread = new Thread(new ClientHandler(clientSocket));
            thread.start();

        }} 
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        }
    

    static class ClientHandler implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter printWriter;
        private XmlRpcClient rpcClient;
        private int choice ;
        private BingoService stub;

        public ClientHandler(Socket clientSocket) throws IOException, AccessException ,MalformedURLException,NotBoundException,RemoteException {
            this.client = clientSocket;

            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            printWriter = new PrintWriter(client.getOutputStream(), true);

             choice=Integer.parseInt(in.readLine());

            if(choice==1){
            //partie rpc 
                XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
                config.setServerURL(new URL("http://127.0.0.1:5005"));
                rpcClient = new XmlRpcClient();
                rpcClient.setConfig(config);
            }
            //partie rmi 
            else {
             stub=(BingoService) Naming.lookup("rmi://127.0.0.1:5005/bingo/jeux");

        }
    }



    @Override
    public void run() {
          try {
           
            while (true) {
                String line = in.readLine();
                if (line != null) {
            	int n = Integer.parseInt(line);
                if(n==1){
	                HashMap<String, Integer> predictions = new HashMap<> (); 
	                for(int i=0;i<10;i++){
	                	int nbr=Integer.parseInt(in.readLine());
                        predictions.put("pred"+i,nbr);
                        
	                }
                    int result;
                    if(choice==1){
                  //rpc
                         result = (int) rpcClient.execute("Bingo.calculScore", new Object[]{predictions});
                    }
                    else {
                    // rmi
                         result=stub.calculScore(predictions);
                    }
                    printWriter.println(result);
                }
                if (n==2){
                Object[] result;

                    if(choice==1){
                    result = (Object[]) rpcClient.execute("Bingo.meilleurScore", new Object[]{});
                    }
                    else{
                         List<Integer> res=stub.meilleurScore();
                         result = res.toArray();
                    }
                    
                    List<Integer> bestscore = Arrays.asList(Arrays.copyOf(result, result.length, Integer[].class));
                                                            
                    printWriter.println(bestscore.size());
                        for (int i = 0; i < bestscore.size(); i++) {
                        printWriter.println(bestscore.get(i));
                    }
            }  
        
        }
                else{
                    
                }
        }
          }catch(IOException | XmlRpcException e){
        	  	System.err.println("IO exception in BingoGateway");
        	  	System.err.println(Arrays.toString(e.getStackTrace()));
            
          }
          finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
   
    }}