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

    public Camera(String name, double memoryConsumption, String status,LocalDateTime time) throws Custom {
        super(name, status);
        setConsumptionRate(memoryConsumption);
        if (super.getStatus().equals("On")){
            this.start=time;
        }
    }

    public void setConsumptionRate(double consumptionRate) throws Custom {
        if (consumptionRate>0){
            this.ConsumptionRate = consumptionRate;
        }else {
            throw new PositivityError("Camera");
        }
    }
    public double getUsedMemory(){return this.usedMemory;}

    @Override
    public void nopSwitch(LocalDateTime time) throws Custom {
        if (super.getStatus().equals("Off")) {
            super.setStatus("On");
            this.start = time;
        } else {
            super.setStatus("Off");
            this.end = time;
            this.calculate();
        }
    }

    @Override
    public void switchDevice(LocalDateTime time, String val) throws Custom {
        if (val.equals("On")) {
            super.setStatus("On");
            this.start = time;
            } else if (val.equals("Off")) {
            super.setStatus("Off");

                this.end = time;
                this.calculate();


        } else {
            //CUSTOM EXCEPTİONS GETS HERE
        }

    }
    public void calculate(){
        this.usedMemory =this.getUsedMemory() + ConsumptionRate*duration(this.start,this.end);
        this.start = null;
        this.end = null;
    }
    public double duration(LocalDateTime start,LocalDateTime end){
        if (start==null || end == null){
            return 0;
        }else {
            return  Duration.between(this.start, this.end).getSeconds()/60.0;
        }
    }
    @Override
    public String info() {
        return  "Smart Camera "+ super.getName() +
                " is " +super.getStatus()+ " and used "+
                this.getUsedMemory()+" MB of storage so far (excluding current status), " +
                "and its time to switch its status is " +TimeControl.stringFormatter(super.getSwitchTime())+".\n";
    }
}
