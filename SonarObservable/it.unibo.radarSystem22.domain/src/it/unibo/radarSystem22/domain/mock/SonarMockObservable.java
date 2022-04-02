package it.unibo.radarSystem22.domain.mock;

import it.unibo.radarSystem22.domain.DistanceMeasured;
import it.unibo.radarSystem22.domain.models.SonarModelObservable;
import it.unibo.radarSystem22.domain.utils.BasicUtils;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class SonarMockObservable extends SonarModelObservable {

	@Override
	protected void sonarSetUp() {
		observableDistance = new DistanceMeasured();
		observableDistance.setVal(curVal);
	}

	@Override
	protected void sonarProduce() {
		if (DomainSystemConfig.testing) {
			updateDistance(DomainSystemConfig.testingDistance);
			stopped = true; // one shot
		} else {
			int v = curVal.getVal() - 1;
			updateDistance(v);
			stopped = (v == 0);
			BasicUtils.delay(DomainSystemConfig.sonarDelay); // avoid fast generation
		}
	}
}