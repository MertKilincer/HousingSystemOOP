import java.time.LocalDateTime;

public interface Pluggable {

    public void PlugIn(double ampere, LocalDateTime time) throws Custom;

}
