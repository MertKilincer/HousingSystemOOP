import java.time.Duration;
import java.time.LocalDateTime;

public class Camera extends Product implements Switchable{

    private double ConsumptionRate;
    public LocalDateTime start;
    public LocalDateTime end;
    public double usedMemory;

    public Camera(String name,double memoryConsumption) throws Custom {
        super(name);
        setConsumptionRate(memoryConsumption);
        }

    public Camera(String name, double memoryConsumption, String status) throws Custom {
        super(name, status);
        setConsumptionRate(memoryConsumption);
    }

    public void setConsumptionRate(double consumptionRate) throws Custom {
        if (consumptionRate>0){
            this.ConsumptionRate = consumptionRate;
        }else {
            throw new PositivityError("Camera");
        }
    }

    @Override
    public void nopSwitch(LocalDateTime time) throws Custom {
        if (super.getStatus().equals("Off")) {
            super.setStatus("On");
            start = time;
        } else {
            super.setStatus("Off");
            end = time;
            this.calculate();
        }
    }

    @Override
    public void switchDevice(LocalDateTime time, String val) throws Custom {
        if (super.getStatus().equals("Off") && val.equals("On")) {
            super.setStatus("On");
            this.start = time;
            } else if (super.getStatus().equals("On") && val.equals("Off")) {
            super.setStatus("Off");

                this.end = time;
                this.calculate();


        } else {
            //CUSTOM EXCEPTİONS GETS HERE
        }

    }
    public void calculate(){
        usedMemory += ConsumptionRate*(Duration.between(start, end).getSeconds()) / 3600;
        this.start = null;
        this.end = null;
    }
    @Override
    public String info() {
        return  "Smart Camera "+ super.getName() +
                " is " +super.getStatus()+ " and used "+
                this.usedMemory+" MB of storage so far (excluding current status), " +
                "and its time to switch its status is " +TimeControl.stringFormatter(super.getSwitchTime())+".\n";
    }
}
