package service;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.webserver.WebServer;
import java.io.IOException;
import impl.BingoServiceImpl;

public class Server  {
	public static void main(String[] args) throws XmlRpcException, IOException {
		WebServer webServer = new WebServer(5005);
		XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
		PropertyHandlerMapping phm = new PropertyHandlerMapping();
		phm.addHandler("Bingo", BingoServiceImpl.class);
		xmlRpcServer.setHandlerMapping(phm);
		webServer.start();
		System.out.println("Server is running...");
	}
}