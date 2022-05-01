package unibo.actor22.common;

import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.*;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommUtils;

/*
 * Il controller conosce SOLO I NOMI dei dispositivi 
 * (non ha riferimenti ai dispositivi-attori)
 */
public class ControllerActor extends QakActor22{
	private static final int ITERATIONS = 10;
	private static final int TIME_BETWEEN_ITERATIONS = 100;
	protected int numIter = -1;
	protected int state = -1;
	protected IApplMessage getStateRequest ;
	protected boolean on = true;
	boolean ledRequiredState;

	public ControllerActor(String name  ) {
		super(name);
		getStateRequest  = ApplData.buildRequest(name,"ask", ApplData.reqLedState, ApplData.ledName);
	}

	@Override
	protected void handleMsg(IApplMessage msg) {  
		if( msg.isReply() ) {
			elabAnswer(msg);
		}else { 
			elabCmd(msg) ;	
		}
	}

	protected void elabCmd(IApplMessage msg) {
		String msgCmd = msg.msgContent();
		ColorsOut.outappl( getName()  + " | elabCmd=" + msgCmd, ColorsOut.GREEN);
		switch( msgCmd ) {
		case ApplData.cmdActivate : {
			doControllerWork();
			break;
		}
		default:break;
		}		
	}

	protected void wrongBehavior() {
		//WARNING: Inviare un treno di messaggi VA EVITATO
		//mantiene il controllo del Thread degli attori (qaksingle)		
		for( int i=1; i<=3; i++) {
			forward( ApplData.turnOffLed );
			CommUtils.delay(500);
			forward( ApplData.turnOnLed );
			CommUtils.delay(500);		
		}
		forward( ApplData.turnOffLed );
	}
	protected void doControllerWork() {
		if( numIter==-1 ) {
			CommUtils.aboutThreads(getName()  + " |  Before doControllerWork on=" + on );
			forward( ApplData.activateSonar );
			request( ApplData.isActiveSonar );
			numIter=0;
		}
	}

	protected void elabAnswer(IApplMessage msg) {
		ColorsOut.outappl( getName()  + " | elabAnswer numIter=" + numIter + " "+ msg, ColorsOut.MAGENTA);
		if(numIter==0) {
			if(msg.msgSender().equals(ApplData.sonarName)) {
				if(msg.msgContent()=="false") {
					ColorsOut.outappl(getName() + " | sonar initialization failed ", ColorsOut.RED);
					stop();
				}
				numIter=1;
				request( ApplData.reqDistanceSonar );
				state=1;
			}else {
				ColorsOut.outappl(getName() + " | unexpected message ", ColorsOut.RED);
			}
		} else if( state == 1 ) {
			if(msg.msgSender().equals(ApplData.sonarName)) {
				IDistance dist = new Distance(msg.msgContent());
				if( dist.getVal() < DomainSystemConfig.DLIMIT) {
					ledRequiredState = true;
					forward( ApplData.turnOnLed ); //accesione
				} else {
					ledRequiredState = false;
					forward( ApplData.turnOffLed ); //spegnimento
				}
				request(getStateRequest);
				state=2;
			}else {
				ColorsOut.outappl(getName() + " | unexpected message ", ColorsOut.RED);
			}
		}else if( state == 2) {
			if(msg.msgSender().equals(ApplData.ledName)) {
				if(msg.msgContent().equals(""+ledRequiredState)) {
					state = 1;
					numIter ++;
					if (numIter > ITERATIONS) stop();
					BasicUtils.delay(TIME_BETWEEN_ITERATIONS);
					request( ApplData.reqDistanceSonar );
				}else {
					ColorsOut.outappl(getName() + " | error led state ", ColorsOut.RED);
					stop();
				}
			}else {
				ColorsOut.outappl(getName() + " | unexpected message ", ColorsOut.RED);
			}
		}
	}

	protected void stop() {
		forward( ApplData.deactivateSonar ); //spegnimento
		System.exit(0);
	}

}
