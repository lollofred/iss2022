package it.unibo.radarSystem22.domain.models;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.concrete.SonarConcreteObservable;
import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.IDistanceMeasured;
import it.unibo.radarSystem22.domain.interfaces.IObserver;
import it.unibo.radarSystem22.domain.mock.SonarMockObservable;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;
import it.unibo.radarSystem22.domain.interfaces.ISonarObservable;

public abstract class SonarModelObservable extends SonarModel implements ISonarObservable {
	protected IDistanceMeasured observableDistance;

	// Factory method
	public static ISonarObservable create() {
		if (DomainSystemConfig.simulation)
			return new SonarMockObservable();
		else
			return new SonarConcreteObservable();
	}

	@Override
	public IDistance getDistance() {
		return observableDistance;
	}

	@Override
	public void register(IObserver obs) {
		observableDistance.addObserver(obs);
	}

	@Override
	public void unregister(IObserver obs) {
		observableDistance.deleteObserver(obs);
	}

	@Override
	protected void updateDistance(int d) {
		observableDistance.setVal(new Distance(d));
	}
}