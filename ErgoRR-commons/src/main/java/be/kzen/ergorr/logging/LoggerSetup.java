package be.kzen.ergorr.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yaman Ustuntas
 */
public class LoggerSetup {

    private static Logger log;

    public static void setup() {

        if (log == null) {
            System.out.println("Setting up logger");
            try {
               /* Handler fileHandler = new FileHandler(System.getProperty("user.home") + "/ergorr.log", 4096, 3, true);
                fileHandler.setFormatter(new LogFormatter());
                fileHandler.setLevel(Level.FINEST);*/
                
                Handler consHandler = new ConsoleHandler();
                consHandler.setFormatter(new LogFormatter());
                consHandler.setLevel(Level.FINEST);
                
                log = Logger.getLogger("be.kzen.ergorr");
                log.addHandler(consHandler);
               // log.addHandler(fileHandler);
                log.setLevel(Level.FINEST);
            } catch (Exception ex) {
                System.out.println("Error setting up Logger");
                ex.printStackTrace();
            }
        }
    }
    
    public static void setLevel(Level newLevel) {

           System.out.println("Setting new level logger");
            try {
               /* Handler fileHandler = new FileHandler(System.getProperty("user.home") + "/ergorr.log", 4096, 3, true);
                fileHandler.setFormatter(new LogFormatter());
                fileHandler.setLevel(newLevel);*/
                
                Handler consHandler = new ConsoleHandler();
                consHandler.setFormatter(new LogFormatter());
                consHandler.setLevel(newLevel);
                
                log = Logger.getLogger("be.kzen.ergorr");
                log.addHandler(consHandler);
                //log.addHandler(fileHandler);
                log.setLevel(newLevel);
            } catch (Exception ex) {
                System.out.println("Error setting new Level Logger");
                ex.printStackTrace();
            }

        
    }
    
    public static String getLevel() {
          if(log == null)
              setup();
          return log.getLevel().toString();

    }
    
    
   
    
}
