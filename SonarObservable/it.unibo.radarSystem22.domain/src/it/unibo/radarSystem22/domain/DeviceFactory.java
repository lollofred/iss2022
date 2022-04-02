package it.unibo.radarSystem22.domain;

import it.unibo.radarSystem22.domain.concrete.SonarConcreteObservable;
import it.unibo.radarSystem22.domain.interfaces.ILed;
import it.unibo.radarSystem22.domain.interfaces.ISonar;
import it.unibo.radarSystem22.domain.interfaces.ISonarObservable;
import it.unibo.radarSystem22.domain.mock.SonarMockObservable;
import it.unibo.radarSystem22.domain.models.LedModel;
import it.unibo.radarSystem22.domain.models.SonarModel;
import it.unibo.radarSystem22.domain.utils.ColorsOut;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class DeviceFactory {

	public static ILed createLed() {
		ColorsOut.out("MIAO", ColorsOut.BgGreen);
		return LedModel.create();
	}

	public static ISonar createSonar() {
		return SonarModel.create();
	}

	public static ISonar createSonar(boolean observable) {
		if (observable)
			return createSonarObservable();
		else
			return createSonar();
	}

	public static ISonarObservable createSonarObservable() {
		if (DomainSystemConfig.simulation) {
			return new SonarMockObservable();
		} else {
			return new SonarConcreteObservable();
		}
	}

}
