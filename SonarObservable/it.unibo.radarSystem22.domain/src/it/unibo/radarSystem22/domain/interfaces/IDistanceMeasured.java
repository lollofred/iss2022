package it.unibo.radarSystem22.domain.interfaces;

import java.util.Observer;

public interface IDistanceMeasured extends IDistance {
	public void setVal(IDistance d);

	public IDistance getDistance();
	
	public void addObserver(Observer obs); // implemented by Observable

	public void deleteObserver(Observer obs);// implemented by Observable
}