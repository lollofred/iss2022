package unibo.actor22.local;
 
import it.unibo.kactor.IApplMessage;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import unibo.actor22.*;
import unibo.actor22.common.ApplData;
import unibo.actor22.common.ControllerActor;
import unibo.actor22.common.LedActor;
import unibo.actor22.common.SonarActor;
import unibo.actor22comm.utils.ColorsOut;
import unibo.actor22comm.utils.CommSystemConfig;
import unibo.actor22comm.utils.CommUtils;
 

/*
 * Sistema che usa led e controller come attori locali
 */
 
public class RSActor22onRasp {
   

	public void doJob() {
		ColorsOut.outappl(this.getClass().getName() +" | Start", ColorsOut.BLUE);
		configure();
		CommUtils.aboutThreads("Before execute - ");
		//CommUtils.waitTheUser();
		execute();
		terminate();
	}
	

	protected void configure() {
		DomainSystemConfig.simulation   = false;
		DomainSystemConfig.ledGui       = false;
		DomainSystemConfig.tracing      = true;
		CommSystemConfig.tracing        = false;
		DomainSystemConfig.DLIMIT       = 30;
		DomainSystemConfig.sonarDistanceMax = 400;

		new LedActor(ApplData.ledName);
		new SonarActor(ApplData.sonarName);
		new ControllerActor( ApplData.controllerName );
		
		//Creo altri Led per verificare che il numero di thread non aumenta
//		for( int i=1; i<=3; i++) {
//			new LedActor(ApplData.ledName+"_"+i);
//			Qak22Util.sendAMsg(ApplData.turnOnLed, ApplData.ledName+"_"+i  );
//			BasicUtils.delay(500);
//			Qak22Util.sendAMsg(ApplData.turnOffLed, ApplData.ledName+"_"+i  );
//		}
  	}
	
	protected void execute() {
  		Qak22Util.sendAMsg( ApplData.activateCrtl );
	} 

	public void terminate() {
		CommUtils.aboutThreads("Before exit - ");
		CommUtils.delay(30000);
		System.exit(0);
	}


	public static void main( String[] args) {
		CommUtils.aboutThreads("Before start - ");
		new RSActor22onRasp().doJob();
		CommUtils.aboutThreads("Before end - ");
	}

 

}

/*
 * Threads:
 */
