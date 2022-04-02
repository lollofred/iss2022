package it.unibo.radarSystem22.domain;

import java.util.Observer;

import it.unibo.radarSystem22.domain.interfaces.IDistance;
import it.unibo.radarSystem22.domain.interfaces.IDistanceMeasured;

public class DistanceMeasured extends java.util.Observable implements IDistanceMeasured {
	private IDistance d;

	public DistanceMeasured() {
	}

	@Override
	public void setVal(IDistance v) {
		d = v;
		setChanged(); 
		notifyObservers(d);
	}

	@Override
	public IDistance getDistance() {
		return d;
	}

	@Override
	public int getVal() {
		return d.getVal();
	}

	@Override
	public String toString() {
		return "" + getVal();
	}

	@Override
	public void addObserver(Observer obs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteObserver(Observer obs) {
		// TODO Auto-generated method stub

	}
}