package it.unibo.comm2022.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import it.unibo.comm2022.interfaces.IApplMsgHandler;
import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.comm2022.utils.ColorsOut;
import it.unibo.comm2022.utils.CommSystemConfig;
  
public class TcpServer extends Thread{
private ServerSocket serversock;
protected IApplMsgHandler userDefHandler;
protected String name;
protected boolean stopped = true;

//Deve permettere + connessioni con + client (ognuna con connessione separata) 
//userDefHandler oggetto che contiene logica applicativa
 	public TcpServer( String name, int port,  IApplMsgHandler userDefHandler   ) {
		super(name);
	      try {
	  		this.userDefHandler   = userDefHandler;
	  		ColorsOut.out(getName() + " | costructor port=" + port, ColorsOut.BLUE  );  
			this.name             = getName();
		    serversock            = new ServerSocket( port ); //Apre Server Socket per fare duale del client
		    serversock.setSoTimeout(CommSystemConfig.serverTimeOut); //Timeout per evitare eventuali problemi
	     }catch (Exception e) { 
	    	 ColorsOut.outerr(getName() + " | costruct ERROR: " + e.getMessage());
	     }
	}
	
	@Override
	public void run() { //Aspetta richiesta connessione e deve essere capace di gestirne N, ogni volta che mando messaggio delego ad application hadler e poi mi metto in attesa di una nuova connessione
	      try {
		  	ColorsOut.out(getName() + " | STARTING ... ", ColorsOut.BLUE  );
			while( ! stopped ) {
				//Accept a connection				 
				//ColorsOut.out(getName() + " | waits on server port=" + port + " serversock=" + serversock );	 
		 		Socket sock          = serversock.accept();	
				ColorsOut.out(getName() + " | accepted connection  ", ColorsOut.BLUE   );  
		 		Interaction2021 conn = new TcpConnection(sock); 
		 		//Create a message handler on the connection
		 		new TcpApplMessageHandler( userDefHandler, conn );		//Sarà hadler che passando handler e conn gestisce messaggi	 		
			}//while
		  }catch (Exception e) {  //Scatta quando la deactive esegue: serversock.close();
			  ColorsOut.out(getName() + " | probably socket closed: " + e.getMessage(), ColorsOut.GREEN);		 
		  }
	}
	
	public void activate() { //Chiama metodo run
		if( stopped ) {
			stopped = false;
			this.start();
		}//else already activated
	}
 
	public void deactivate() { //Stop attività e chiude connessione
		try {
			ColorsOut.out(getName()+" |  DEACTIVATE serversock=" +  serversock);
			stopped = true;
            serversock.close();
		} catch (Exception e) {
			ColorsOut.outerr(getName() + " | deactivate ERROR: " + e.getMessage());	 
		}
	}
 
}
