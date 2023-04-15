import java.time.Duration;
import java.time.LocalDateTime;

public class Plug extends Product implements Switchable {

    private double ampere;
    private double consumption;
    private LocalDateTime start;
    private LocalDateTime end;

    private boolean plugged;

    public Plug(String name) {
        super(name);

    }

    public Plug(String name, String status) throws Custom {
        super(name, status);
    }

    public void setAmpere(double ampere) throws PositivityError {
        if (ampere > 0) {
            this.ampere = ampere;
        } else {
            throw new PositivityError("Ampere");
        }
    }

    public Plug(String name, String status, double Ampere,LocalDateTime start) throws Custom {
        super(name, status);
        PlugIn(Ampere,start);
    }


    @Override
    public void nopSwitch(LocalDateTime time) throws Custom {
        if (super.getStatus().equals("Off")) {
            super.setStatus("On");
            if (this.plugged) {
                this.start = time;
            }
        } else {
            super.setStatus("Off");
            if (this.plugged) {
                this.end = time;
                this.calculate();
            }
        }
    }


    @Override
    public String  info() {
        return  "Smart Plug " + super.getName() + " is " + super.getStatus().toLowerCase() +
                " and consumed " + String.format("%.2f", this.consumption)+
                "W so far (excluding current device), and " +
                "its time to switch its status is " + TimeControl.stringFormatter(this.getSwitchTime())+".\n";
    }

    public void PlugIn(double ampere, LocalDateTime time) throws Custom {
        if (!plugged){
            setAmpere(ampere);
            plugged = true;
            this.start = time;
    }else {
            throw new PlugInError();
        }
    }

    public void PlugOff(LocalDateTime time) throws Custom {
        if (plugged){
            this.plugged = false;
            if (super.getStatus().equals("On")){
            this.end = time;
            }
            this.calculate();
            this.ampere = 0;
    }else{
            throw new PlugOutError();
        }
    }

    private void calculate() {
        this.consumption = this.consumption + 220 * (this.ampere) *
                duration(this.start,this.end);
        this.start = null;
        this.end = null;
    }
    private double duration(LocalDateTime start,LocalDateTime end){
        if (start==null || end == null){
            return 0;
        }else {
           return  Duration.between(this.start, this.end).getSeconds() / 3600.0;
        }
    }

    public void switchDevice(LocalDateTime time, String val) throws Custom {
        if (val.equals("On")) {
            super.setStatus("On");
            if (this.plugged) {
                this.start = time;
            }
        } else if (val.equals("Off")) {
            super.setStatus("Off");
            if (this.plugged) {
                this.end = time;
                this.calculate();
            }
        }

    }
}
