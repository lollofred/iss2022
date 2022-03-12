package it.unibo.radarSystem22.domain.mock;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class SonarMock extends SonarModel implements ISonar{
private int delta = 1;
	
	@Override
	protected void sonarSetUp() { //Inizializzo il sonar con Distanza = 90
		curVal = new Distance(90);		
		ColorsOut.out("SonarMock | sonarSetUp curVal="+curVal);
	}
	@Override
	protected void sonarProduce( ) {
		if( DomainSystemConfig.testing ) {	//produces always the same value
			updateDistance( DomainSystemConfig.testingDistance );			      
		}else { //Riduco la distanza di 1 e aggiorno il valore corrente della distanza
			int v = curVal.getVal() - delta;
			updateDistance( v );			    
			stopped = ( v <= 0 ); //Mi fermo sei il valore è <=0
		}
		BasicUtils.delay(DomainSystemConfig.sonarDelay);  //avoid fast generation
 	}	
	@Override
	protected void sonarActivate() {
		curVal = new Distance( 90 );
 		ColorsOut.out("SonarModel | activate" );
		stopped = false;
		new Thread() {
			public void run() {
				while(!stopped) {
					//Colors.out("SonarModel | call produce", Colors.GREEN);
					sonarProduce();
					//Utils.delay(RadarSystemConfig.sonarDelay);
				}
				ColorsOut.out("SonarModel | ENDS" );
		    }
		}.start();
 	}
	@Override
	protected void sonarDeactivate() {
		ColorsOut.out("SonarModel | deactivate", ColorsOut.BgYellow );
		stopped = true;
 	}
}