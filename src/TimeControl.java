

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeControl {
    public LocalDateTime Time;


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
    public void skipMinutes(String min){
        this.Time =this.Time.plusMinutes(Integer.parseInt(min));
    }

    public static LocalDateTime TimeFormatter(String timeString){
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        return LocalDateTime.parse(timeString, parser);
    }

    public static String stringFormatter(LocalDateTime time) {
        if (!(time == null)) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
            return time.format(format);
        }else {
            return  null;
        }
    }
}
