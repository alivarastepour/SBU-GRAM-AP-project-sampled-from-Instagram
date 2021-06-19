package Model;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


/**
 <h1>CurrentDateTime</h1>
 <p>this class retrieves current time from client's device and uses it in log reports</p>
 @author javatpoint
 @link https://www.javatpoint.com/java-get-current-date
 @version 1.0
 @since 5/27/2021
 @since 6/3/1400
 */
public class CurrentDateTime {

    /**
     *
     * @return current time
     */
    public static String time() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
    public static String time1() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}  