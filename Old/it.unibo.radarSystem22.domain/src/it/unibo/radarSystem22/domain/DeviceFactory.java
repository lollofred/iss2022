package it.unibo.radarSystem22.domain;

import it.unibo.radarSystem22.domain.concrete.RadarDisplay;
import it.unibo.radarSystem22.domain.interfaces.*;
import it.unibo.radarSystem22.domain.models.LedModel;
import it.unibo.radarSystem22.domain.models.SonarModel;

//Manage the creation of all device
public class DeviceFactory {

	public static ILed createLed() {
		return LedModel.create();
	}
	
	public static ISonar createSonar() {
		return SonarModel.create();
	}
	
	public static IRadarDisplay createRadarGui() {
		return RadarDisplay.getRadarDisplay();
	}
}
