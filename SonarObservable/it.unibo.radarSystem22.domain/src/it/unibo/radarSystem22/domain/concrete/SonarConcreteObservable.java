
package it.unibo.radarSystem22.domain.concrete;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import it.unibo.radarSystem22.domain.Distance;
import it.unibo.radarSystem22.domain.DistanceMeasured;
import it.unibo.radarSystem22.domain.models.SonarModelObservable;
import it.unibo.radarSystem22.domain.utils.DomainSystemConfig;

public class SonarConcreteObservable extends SonarModelObservable {
	private BufferedReader reader;
	private int lastSonarVal = 0;
	private Process p = null;

	@Override
	protected void sonarSetUp() {
		observableDistance = new DistanceMeasured();
		observableDistance.setVal(new Distance(lastSonarVal));
	}

	@Override
	public void activate() {
		if (p == null) {
			try {
				p = Runtime.getRuntime().exec("sudo ./SonarAlone");
				reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			} catch (Exception e) {
			}
		}
		super.activate();
	}

	@Override
	protected void sonarProduce() {
		try {
			String data = reader.readLine();
			if (data == null)
				return;
			int v = Integer.parseInt(data);
			// Eliminiamo dati del tipo 3430
			if (lastSonarVal != v && v < DomainSystemConfig.sonarDistanceMax) {
				lastSonarVal = v;
				updateDistance(v);
			}
		} catch (Exception e) {
		}
	}

	@Override
	public void deactivate() {
		if (p != null) {
			p.destroy();
			p = null;
		}
		super.deactivate();
	}
}