import java.time.LocalDateTime;

public interface Switchable {
     void switchDevice(LocalDateTime time, String val) throws Custom;

}
