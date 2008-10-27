package be.kzen.ergorr.logging;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
                Handler fileHandler = new FileHandler(System.getProperty("user.home") + "/ergorr.log", 4096, 3, true);
                fileHandler.setFormatter(new LogFormatter());
                fileHandler.setLevel(Level.FINEST);
                
                Handler consHandler = new ConsoleHandler();
                consHandler.setFormatter(new LogFormatter());
                consHandler.setLevel(Level.FINEST);
                
                log = Logger.getLogger("be.kzen.ergorr");
                log.addHandler(consHandler);
                log.addHandler(fileHandler);
                log.setLevel(Level.FINEST);
            } catch (IOException ex) {
                System.out.println("Error setting up Logger");
                ex.printStackTrace();
            }
        }
    }
}
