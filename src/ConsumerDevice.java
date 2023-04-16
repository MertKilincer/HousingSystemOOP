import java.time.LocalDateTime;

public interface ConsumerDevice {
     void switchDevice(LocalDateTime time, String val) throws Custom;

     double duration(LocalDateTime start,LocalDateTime end);

     void calculate();
}
