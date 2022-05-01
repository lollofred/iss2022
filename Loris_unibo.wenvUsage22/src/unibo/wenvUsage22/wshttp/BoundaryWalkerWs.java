/*
ClientUsingHttp.java
*/
package unibo.wenvUsage22.wshttp;
import unibo.actor22comm.interfaces.IObserver;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;
import unibo.actor22comm.ws.WsConnection;

import java.util.Observable;

import unibo.actor22comm.http.*;
import unibo.wenvUsage22.common.ApplData;

public class BoundaryWalkerWs implements IObserver {
	private Interaction2021 conn;
	
	protected void doJob() throws Exception {
		conn = WsConnection.create("localhost:8091" );
		((WsConnection)conn).addObserver(this);
		conn.forward( ApplData.moveForward(500) ); 
 	}

	@Override
	public void update(Observable arg0, Object arg1) {
		react((String)arg1);
	}

	@Override
	public void update(String answer) {
		//react(answer);
	}
	
	private void react(String answer) {
		ColorsOut.outappl("answer= " + answer, ColorsOut.BLACK  );
		try {
	  		if(answer.contains("collision")) conn.forward( ApplData.turnLeft(300) );
	  		else conn.forward( ApplData.moveForward(500) );
		}catch(Exception e) {
  			ColorsOut.outappl("Exception= ", ColorsOut.RED  );
  			System.exit(1);
  		}
	}
	
	 /*
	MAIN
	 */
	public static void main(String[] args) throws Exception   {
		CommUtils.aboutThreads("Before start - ");
 		new BoundaryWalkerWs().doJob();
		CommUtils.aboutThreads("At end - ");
	}
	
 }
