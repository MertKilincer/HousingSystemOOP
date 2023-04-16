import java.time.LocalDateTime;

/**
 * Interface for classes consume some resources
 * when they are open can implement this interface to calculate their consumption of that resource
 */
public interface ConsumerDevice {

     void switchDevice(LocalDateTime time, String statusValue) throws Custom;

     double duration(LocalDateTime start,LocalDateTime end);

     void calculate();
}
