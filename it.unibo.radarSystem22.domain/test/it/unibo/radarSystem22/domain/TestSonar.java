package it.unibo.radarSystem22.domain;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.*;

import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
 

public class TestSonar {
	@Before
	public void up() {
		System.out.println("up");
	}
	
	@After
	public void down() {
		System.out.println("down");		
	}	
	
	@Test 
	public void testSonarMock() {
		DomainSystemConfig.simulation = true;
		DomainSystemConfig.testing    = false;
		DomainSystemConfig.sonarDelay = 10;	
		int delta = 1;
		
		ISonar sonar = DeviceFactory.createSonar();
		assertFalse(sonar.isActive());
		
	}
	
 
}
