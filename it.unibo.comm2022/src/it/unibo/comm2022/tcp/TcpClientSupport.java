package it.unibo.comm2022.tcp;
import java.net.Socket;

import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.comm2022.utils.ColorsOut;


public class TcpClientSupport {
//	private Socket socket;
	//Provo conn per n tentativi
	public static Interaction2021 connect(String host, int port, int nattempts  ) throws Exception {
//		TcpClient tcpc = new TcpClient(host,   port,   nattempts);
//		return new TcpConnection( tcpc.getSocket() );
		 
		for( int i=1; i<=nattempts; i++ ) {
			try {
				Socket socket         =  new Socket( host, port ); //Creo Socket con Server
 				Interaction2021 conn  =  new TcpConnection( socket ); //Uso Socket passandola a TCPConnection, rasppresenta filo di comunicazione con server 
				return conn;
			}catch(Exception e) {
				ColorsOut.out("TcpClient | Another attempt to connect with host:" + host + " port=" + port);
				Thread.sleep(500);
			}
		}//for
		throw new Exception("TcpClient | Unable to connect to host:" + host);
		 
 	}
 
}
