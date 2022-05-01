package unibo.wenvUsage22.robotCleaner;
import unibo.actor22comm.utils.ColorsOut;

public class GuardNextTurnR {
	protected static boolean b = true; 
  	
	public static void setValue( boolean val  ) {
		b = val;
	}
 	public boolean eval( ) {
 		return b;
	}
}
