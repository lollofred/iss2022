/*
ClientUsingHttp.java
*/
package unibo.wenvUsage22.wshttp;
import unibo.actor22comm.interfaces.IObserver;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;
import unibo.actor22comm.ws.WsConnection;
import unibo.wenvUsage22.wshttp.vacuumCleaner.Turn;

import java.util.Observable;

import unibo.actor22comm.http.*;
import unibo.wenvUsage22.common.ApplData;

public class vacuumCleaner implements IObserver {
	private Interaction2021 conn;
	public enum Turn {
		LEFT,
		RIGHT
	}
	public enum MoveState {
		MOVING_STRAIGHT,
		FIRST_TURN_DONE,
		MOVE_AFTER_FIRST_TURN_DONE
	}
	private MoveState moveState;
	private Turn nextTurn;
	private boolean lastTurnCollision;
	
	protected void doJob() throws Exception {
		conn = WsConnection.create("localhost:8091" );
		((WsConnection)conn).addObserver(this);
		conn.forward( ApplData.moveForward(500) ); 
		moveState = MoveState.MOVING_STRAIGHT;
		nextTurn = Turn.LEFT;
		lastTurnCollision = false;
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
	  		if(answer.contains("collision") && moveState.equals(MoveState.MOVING_STRAIGHT)) {
	  			conn.forward( nextTurn.equals(Turn.RIGHT) ? ApplData.turnRight(300) : ApplData.turnLeft(300) );
	  			moveState = MoveState.FIRST_TURN_DONE;
	  		}else{
	  			if(moveState.equals(MoveState.FIRST_TURN_DONE)){
	  				conn.forward( ApplData.moveForward(500));
	  				moveState = MoveState.MOVE_AFTER_FIRST_TURN_DONE;
	  			}else if(moveState.equals(MoveState.MOVE_AFTER_FIRST_TURN_DONE)) {
	  				conn.forward( nextTurn.equals(Turn.RIGHT) ? ApplData.turnRight(300) : ApplData.turnLeft(300) );
	  				moveState = MoveState.MOVING_STRAIGHT;
	  				if(answer.contains("collision") && lastTurnCollision) {
	  					//System.out.println("Last time we also had a collision");
	  				}else {
	  					nextTurn = nextTurn.equals(Turn.RIGHT) ? Turn.LEFT : Turn.RIGHT;
	  				}
	  				lastTurnCollision = answer.contains("collision");
	  			}else {
	  				conn.forward( ApplData.moveForward(500));
	  			}
	  		}
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
 		new vacuumCleaner().doJob();
		CommUtils.aboutThreads("At end - ");
	}
	
 }
