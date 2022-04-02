package it.unibo.radarSystem22.domain.utils;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
 
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
 

public class DomainSystemConfig {
    public static  boolean simulation    = true;
     public static  boolean ledGui        = false;
    public static  boolean webCam        = false;

    public static int sonarDelay          =  100;     
    public static int sonarDistanceMax    =  150;     
    public static boolean sonarObservable =  false;     
    public static int DLIMIT              =  15;     
    public static int testingDistance     =  DLIMIT - 2;     

    public static boolean tracing         = false;    
    public static boolean testing         = false;            
 
    public static void setTheConfiguration(  ){
        setTheConfiguration("./././././././DomainSystemConfig.json"); 
    }

    public static void setTheConfiguration( String resourceName ) {
        //Nella distribuzione resourceName è in una dir che include la bin  
        FileInputStream fis = null;
        try {
            //ColorsOut.out("%%% setTheConfiguration from file:" + resourceName);
            if(  fis == null ) {
                  fis = new FileInputStream(new File(resourceName));
            }
            Reader reader = new InputStreamReader(fis);
            JSONTokener tokener = new JSONTokener(reader);            
            JSONObject object   = new JSONObject(tokener);

            simulation          = object.getBoolean("simulation"); 
            sonarDistanceMax = object.getInt("sonarDistanceMax");    
            sonarDelay       = object.getInt("sonarDelay");    
            DLIMIT           = object.getInt("DLIMIT");    
            testing          = object.getBoolean("testing");
            tracing          = object.getBoolean("tracing");


            //webCam           = object.getBoolean("webCam");

            //sonarObservable  = object.getBoolean("sonarObservable");    


        } catch (FileNotFoundException e) {
             ColorsOut.outerr("setTheConfiguration ERROR " + e.getMessage() );
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
    }    

}