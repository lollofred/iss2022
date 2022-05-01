package unibo.wenvUsage22.robotCleaner; 
import unibo.actor22comm.utils.ColorsOut;

public class GuardEndOfWork {
	protected static boolean b = false; 
  	
	public static void setValue( boolean val  ) {
		b = val;
	}
 	public boolean eval( ) {
 		return b;
	}
}
