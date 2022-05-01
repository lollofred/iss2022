package unibo.wenvUsage22.actors.demo_robot;

import it.unibo.kactor.IApplMessage;
import unibo.actor22.QakActor22FsmAnnot;
import unibo.actor22.annotations.State;
import unibo.actor22.annotations.Transition;
import unibo.actor22comm.SystemData;
import unibo.actor22comm.interfaces.Interaction2021;
import unibo.actor22comm.ws.WsConnection;
import unibo.wenvUsage22.annot.walker.WsConnWEnvObserver;
import unibo.wenvUsage22.common.VRobotMoves;

public class RobotCleanerDemoFsm extends QakActor22FsmAnnot  {
	private Interaction2021 conn;	
 	private int ncorner  = 0;

 	public RobotCleanerDemoFsm(String name) {
		super(name);
 	}

	@State( name = "robotStart", initial=true)
	@Transition( state = "robotMoving") //transizione spontanea alla prima mossa in avanti
	protected void robotStart( IApplMessage msg ) {
		outInfo(""+msg + " connecting (blocking all the actors ) ... ");	
 		conn = WsConnection.create("localhost:8091" ); 	 
 		outInfo("connected "+conn);	
   		((WsConnection)conn).addObserver( new WsConnWEnvObserver(getName()) );
  		//VRobotMoves.step(getName(),conn);
	}

 	@State( name = "robotMoving" )
 	@Transition( state = "robotMoving" ,  msgId = SystemData.endMoveOkId)
  	@Transition( state = "turnedL1" , msgId = SystemData.endMoveKoId , guard=GuardNextTurnL.class)
 	@Transition( state = "turnedR1" , msgId = SystemData.endMoveKoId , guard=GuardNextTurnR.class)
	protected void robotMoving( IApplMessage msg ) {
		VRobotMoves.step(getName(),conn);
 	}

 	@State( name = "turnedL1" )
 	@Transition( state = "wallStep" ,  msgId = SystemData.endMoveOkId , guard=GuardContinueWork.class)
 	@Transition( state = "endWork" ,  msgId = SystemData.endMoveOkId , guard=GuardEndOfWork.class)
	protected void turnedL1( IApplMessage msg ) {
 		VRobotMoves.turnLeft(getName(), conn);
 	}

 	@State( name = "turnedR1" )
 	@Transition( state = "wallStep" ,  msgId = SystemData.endMoveOkId , guard=GuardContinueWork.class)
 	@Transition( state = "endWork" ,  msgId = SystemData.endMoveOkId , guard=GuardEndOfWork.class)
	protected void turnedR1( IApplMessage msg ) {
 		VRobotMoves.turnRight(getName(), conn);
 	}

 	@State( name = "wallStep" )
 	@Transition( state = "turnedL2" , msgId = SystemData.endMoveOkId , guard=GuardNextTurnL.class)
 	@Transition( state = "turnedR2" , msgId = SystemData.endMoveOkId , guard=GuardNextTurnR.class)
 	@Transition( state = "turnedL2" , msgId = SystemData.endMoveKoId , guard=GuardNextTurnL.class)
 	@Transition( state = "turnedR2" , msgId = SystemData.endMoveKoId , guard=GuardNextTurnR.class)
	protected void wallStep( IApplMessage msg ) {
		//outInfo("ncorner="+ ncorner + " " + msg);
		VRobotMoves.step(getName(), conn);
 	}

 	@State( name = "turnedL2" )
 	@Transition( state = "robotMoving" ,  msgId = SystemData.endMoveOkId )
	protected void turnedL2( IApplMessage msg ) {
 		VRobotMoves.turnLeft(getName(), conn);
 		GuardNextTurnL.setValue(false);
 		GuardNextTurnR.setValue(true);
 		//register wether the previous wall step was complete or not. If not this is the last column we have to go through
		GuardEndOfWork.setValue(msg.msgId().equals(SystemData.endMoveKoId));
		GuardContinueWork.setValue(!msg.msgId().equals(SystemData.endMoveKoId));
 	}

 	@State( name = "turnedR2" )
 	@Transition( state = "robotMoving" ,  msgId = SystemData.endMoveOkId )
	protected void turnedR2( IApplMessage msg ) {
 		VRobotMoves.turnRight(getName(), conn);
 		GuardNextTurnL.setValue(true);
 		GuardNextTurnR.setValue(false);
 		//register wether the previous wall step was complete or not. If not this is the last column we have to go through
		GuardEndOfWork.setValue(msg.msgId().equals(SystemData.endMoveKoId));
		GuardContinueWork.setValue(!msg.msgId().equals(SystemData.endMoveKoId));
 	}

 	@State( name = "endWork" )
 	protected void endWork( IApplMessage msg ) {
 		//VRobotMoves.turnLeft(getName(), conn);
		outInfo("BYE" );	
 		System.exit(0);
 	}


}