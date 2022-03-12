package it.unibo.radarSystem22.domain;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.*;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;


public class TestLed {
	
	@Before
	public void up() {
		System.out.println("up");
	}
	
	@After
	public void down() {
		System.out.println("down");		
	}	
	
	@Test 
	public void testLedMockOn() {
		
		DomainSystemConfig.setTheConfiguration();
		System.out.println("testLedMockOn");
		//DomainSystemConfig.simulation = true; 
		
		//ILed led = new LedMock(); //Quando DeviceFactory ancora non esiste //DeviceFactory.createLed();
		ILed led = DeviceFactory.createLed();
		
		led.turnOn();
		assertTrue( led.getState() );
	}	
	
	@Test 
	public void testLedMockOff() {
		
		System.out.println("testLedMockOff");
		//DomainSystemConfig.simulation = true; 
		
		//ILed led = new LedMock(); //Quando DeviceFactory ancora non esiste //DeviceFactory.createLed();
		ILed led = DeviceFactory.createLed();
		
		led.turnOff();
		assertFalse( led.getState() );
	}	
	
	@Test 
	public void testLedConcreteOn() {
		
		System.out.println("testLedConcreteOn");
		//DomainSystemConfig.simulation = false; 
		
		//ILed led = new LedConcrete(); //Quando DeviceFactory ancora non esiste //DeviceFactory.createLed();
		ILed led = DeviceFactory.createLed();
		
 		led.turnOn();
		assertTrue(  led.getState() );
			
	}		
		
	@Test 
	public void testLedConcreteOff() {
		
		System.out.println("testLedConcreteOff");
		//DomainSystemConfig.simulation = false; 
		
		//ILed led = new LedConcrete(); //Quando DeviceFactory ancora non esiste //DeviceFactory.createLed();
		ILed led = DeviceFactory.createLed();

 		led.turnOff();
		assertFalse( led.getState() );		
	}	
	

}
