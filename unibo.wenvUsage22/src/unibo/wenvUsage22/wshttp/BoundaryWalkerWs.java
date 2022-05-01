/*
ClientUsingWs.java
*/
package unibo.wenvUsage22.wshttp;
import unibo.actor22comm.interfaces.IObserver;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;
import unibo.actor22comm.ws.*;
import unibo.wenvUsage22.common.ApplData;
import java.util.Observable;

public class BoundaryWalkerWs implements IObserver { 
	private  final String localHostName    = "localhost"; //"localhost"; 192.168.1.7
	private  final int port                = 8090;
	private  final String WsURL          = "ws://"+localHostName+":"+port+"/api/move";
 
	private Interaction2021 conn;
	
	private String answer = "";
	private int ForwardDuration = 500;
	private int LeftDuration = 500;
	

	protected void doJob() throws Exception {
		int DelayDuration = ForwardDuration+LeftDuration;
  		conn = WsConnection.create("localhost:8091" ); //INTERROMPIBILE usando WebGui
  		((WsConnection)conn).addObserver(this);
  		boolean obstacle = false;
  		for( int i=1; i<=4; i++ ) {
	  		while( ! obstacle ) {
	  			conn.forward( ApplData.moveForward(ForwardDuration) );
	  			ColorsOut.outappl(i + " answer= " + answer, ColorsOut.BLACK  );
	  			obstacle = answer.contains("collision");
	  			CommUtils.delay(DelayDuration);
	  		}
	  		obstacle = false;
	  		conn.forward( ApplData.turnLeft(LeftDuration) );
  			CommUtils.delay(DelayDuration);
  		}
 
		ColorsOut.outappl("answer= " + answer, ColorsOut.BLACK  );
 	}
 
 /*
MAIN
 */
	public static void main(String[] args) throws Exception   {
		CommUtils.aboutThreads("Before start - ");
 		new BoundaryWalkerWs().doJob();
		CommUtils.aboutThreads("At end - ");
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		ColorsOut.out("BoundaryWalkerWs update/2 receives:" + arg1);	
		answer = arg1.toString();
	}

	@Override
	public void update(String value) {
		ColorsOut.out("BoundaryWalkerWs update receives:" + value);	
	}
 }
