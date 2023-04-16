

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Mert Kılınçer
 * @Operation TimeControl class that about system time controls
 * @see java.time.LocalDateTime
 * @see java.time.format.DateTimeFormatter
 */

public class TimeControl {

    public LocalDateTime Time;

    /**
     * Constructor of the time control class that uses initial time of the system to start time of the system
     * @param timeString string type input of time
     */
    public TimeControl(String timeString) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        this.Time = LocalDateTime.parse(timeString, parser);
    }

    public LocalDateTime getTime() {
        return Time;
    }


    public void setTime(LocalDateTime time) {
        this.Time = time;
    }

    /**
     * Method that skip time in minutes
     * @param minuteString string type input of minute
     * @throws Custom
     */
    public void skipMinutes(String minuteString) throws Custom {
        int minutes =Integer.parseInt(minuteString);
        if (minutes<0){
            throw new Custom("ERROR: Time cannot be reversed!\n");
        } else if (minutes==0) {
            throw new Custom( "ERROR: There is nothing to skip!\n");
        }
        this.Time =this.Time.plusMinutes(minutes);
    }

    /**
     * Formatter method
     * @param timeString string type input of time
     * @return LocalDateTime
     * @see java.time.LocalDateTime
     */
    public static LocalDateTime TimeFormatter(String timeString){
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        return LocalDateTime.parse(timeString, parser);
    }

    /**
     * Formatter method
     * @param time LocalDateTime value that will be converted to string
     * @return DateTime in string format
     */
    public static String stringFormatter(LocalDateTime time) {
        if (!(time == null)) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            return time.format(format);
        }else {
            return  null;
        }
    }
}
