package it.unibo.radarSystem22.domain;

import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.models.LedModel;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.utils.ColorsOut;

public class DeviceFactory {

	public static ILed createLed() {
		ColorsOut.out("MIAO", ColorsOut.BgGreen);
		return LedModel.create();
	}

	public static ISonar createSonar() {
		return SonarModel.create();
	}

}
