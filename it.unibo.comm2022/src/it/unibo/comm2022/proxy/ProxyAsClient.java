package it.unibo.comm2022.proxy;
 

import it.unibo.comm2022.ProtocolType;
import it.unibo.comm2022.interfaces.Interaction2021;
import it.unibo.comm2022.utils.ColorsOut;
import it.unibo.comm2022.tcp.TcpClientSupport;
 
public class ProxyAsClient {
private Interaction2021 conn; 
protected String name ;		//could be a uri
protected ProtocolType protocol ;

/*
 * Realizza la connessione di tipo Interaction2021 (concetto astratto)
 * in modo diverso, a seconda del protocollo indicato (tecnologia specifica)
 */
 
	public ProxyAsClient( String name, String host, String entry, ProtocolType protocol ) { //Proxy che pu? servire x tutti i protocolli
		try {
			ColorsOut.out(name+"  | CREATING entry= "+entry+" protocol=" + protocol, ColorsOut.BLUE );
			this.name     = name;
			this.protocol = protocol;			 
			setConnection(host,  entry,  protocol); //stabilire conn con host passando entry e protocollo, per lavorare con alcuni protocolli non devo dare porta ma topic (stringa) -> es. MQTT 
			ColorsOut.out(name+"  | CREATED entry= "+entry+" conn=" + conn, ColorsOut.BLUE );
		} catch (Exception e) {
			ColorsOut.outerr( name+"  |  ERROR " + e.getMessage());		}
	}
	
 	
	protected void setConnection( String host, String entry, ProtocolType protocol  ) throws Exception {
		switch( protocol ) {
			case tcp : {
				int port = Integer.parseInt(entry);
				//conn = new TcpConnection( new Socket( host, port ) ) ; //non fa attempts
				conn = TcpClientSupport.connect(host,  port, 10); //10 = num of attempts //Setta connessione
				ColorsOut.out(name + " |  setConnection "  + conn, ColorsOut.BLUE );		
				break;
			}
			case coap : {
 				//conn = new CoapConnection( host,  entry );
				break;
			}
			case mqtt : {
				//La connessione col Broker viene stabilita in fase di configurazione
				//La entry ? quella definita per ricevere risposte;
				//ColorsOut.out(name+"  | ProxyAsClient connect MQTT entry=" + entry );
				//conn = MqttConnection.getSupport();					
 				break;
			}	
			default :{
				ColorsOut.outerr(name + " | Protocol unknown");
			}
		}
	}
  	//stabilito conn invia forward indipedentemente da tutto
	public void sendCommandOnConnection( String cmd )  {
 		//ColorsOut.out( name+"  | sendCommandOnConnection " + cmd + " conn=" + conn, ColorsOut.BLUE );
		try {
			conn.forward(cmd);
		} catch (Exception e) {
			ColorsOut.outerr( name+"  | sendCommandOnConnection ERROR=" + e.getMessage()  );
		}
	}
	//Passando richiesta , questa deve essere bloccante fintanto che non ho risposta la quale poi restituisco
	public String sendRequestOnConnection( String request )  {
 		ColorsOut.out( name+"  | sendRequestOnConnection request=" + request + " conn=" + conn );
		try {
			String answer = conn.request(request); //BLOCCANTE
			ColorsOut.out( name+"  | sendRequestOnConnection-answer=" + answer  );
			return  answer  ;
			//return CommUtils.getContent( answer );
 		
		} catch (Exception e) {
			ColorsOut.outerr( name+"  | sendRequestOnConnection ERROR=" + e.getMessage()  );
			return null;
		}
 	}	
	public Interaction2021 getConn() {
		return conn;
	}
	
	public void close() {
		try {
			conn.close();
			ColorsOut.out(name + " |  CLOSED " + conn  );
		} catch (Exception e) {
			ColorsOut.outerr( name+"  | sendRequestOnConnection ERROR=" + e.getMessage()  );		}
	}
}
