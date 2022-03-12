package it.unibo.radarSystem22.domain.models;
 
import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.concrete.SonarConcrete;
import it.unibo.radarSystem22.domain.interfaces.*;
import it.unibo.radarSystem22.domain.mock.SonarMock;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;



public abstract class SonarModel  implements ISonar{  
protected  IDistance curVal = new Distance(90);	 
protected boolean stopped   = true;
 	
/**
 	*  CreateSonar -> Mock or Concrete 
**/
	public static ISonar create() {
		if( DomainSystemConfig.simulation )  return createSonarMock();
		else  return createSonarConcrete();		
	}

	public static ISonar createSonarMock() {
		ColorsOut.out("createSonarMock", ColorsOut.BLUE);
		return new SonarMock();
	}	
	public static ISonar createSonarConcrete() {
		ColorsOut.out("createSonarConcrete", ColorsOut.BLUE);
		return new SonarConcrete();
	}	
	
	protected SonarModel() {//hidden costructor, to force setup
		ColorsOut.out("SonarModel | calling (specialized) sonarSetUp ", ColorsOut.BLUE );
		sonarSetUp();   
	}
 	
	/**
 	*  Override_ISonar 
 **/
 	@Override
	public void activate() {
 		sonarActivate();
	}
	@Override
	public void deactivate() {
		sonarDeactivate();
	}	
 	@Override
	public boolean isActive() {
		//ColorsOut.out("SonarModel | isActive "+ (! stopped), ColorsOut.GREEN);
		return ! stopped;  //Se non è fermo -> è attivo
	}
	@Override
	public IDistance getDistance() {
		return curVal;
	}


/**
 	*  Handle Distance
**/
	protected void updateDistance(int d) {
		curVal = new Distance(d);
		ColorsOut.out("SonarModel | updateDistance "+ d, ColorsOut.BLUE);
	}	

/**
 	*  Handle SetUp and Produce of Sonar
**/
	protected abstract void sonarSetUp();
 	protected abstract void sonarProduce();
 	protected abstract void sonarActivate();
 	protected abstract void sonarDeactivate();


}

  

