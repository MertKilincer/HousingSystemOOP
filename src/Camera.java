import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * public camera class extends Product abstract class and its implements Switchable interface
 *@author Mert Kılınçer
 * @see Device
 * @see ConsumerDevice
 */
public class Camera extends Device implements ConsumerDevice {
    /**
     * Camera class has four private field
     * 1-consumptionRate stands for memory consumption rate Megabyte per minute
     * 2-start stands for starting of memory consumption
     * 3-end stands for end time of memory consumption
     * 4-usedMemory stands for used memory space in Megabyte format
     */
    private double consumptionRate;
    private LocalDateTime start;
    private LocalDateTime end;
    private double usedMemory;

    /**
     * Constructor that calls super constructor with name parameter and defines the field consumptionRate
     * @param name name of the device
     * @param memoryConsumption consumption of the device
     * @throws Custom
     */

    public Camera(String name,double memoryConsumption) throws Custom {
        super(name);
        setConsumptionRate(memoryConsumption);
        }

    /**
     * Constructor that calls super constructor with name and status parameter and defines the field consumptionRate
     * this constructor may define start field too.
     * @param name name of the device
     * @param memoryConsumption consumption of the device
     * @param status initial status of device that can be changed between (On,Off)
     * @param time  time that device enters the home or system
     * @throws Custom
     */
    public Camera(String name, double memoryConsumption, String status,LocalDateTime time) throws Custom {
        super(name, status);
        setConsumptionRate(memoryConsumption);//call of the set function
        if (super.getStatus().equals("On")){//if status was on define start too.
            this.start=time;
        }
    }

    /**
     * Method sets consumptionRate field
     * @param consumptionRate double value that can be used for field of camera class
     * @throws Custom
     */
    public void setConsumptionRate(double consumptionRate) throws Custom {
        if (consumptionRate>0){//check for positive values
            this.consumptionRate = consumptionRate;
        }else {
            throw new PositivityError("Megabyte");//this error class is subclass of Custom
        }
    }

    /**
     * Getter method for usedMemory field
     * @return double field usedMemory
     */
    public double getUsedMemory(){return this.usedMemory;}

    /**
     * nopSwitch method that change device status to other than its current status
     * @param time  its may affect start and end field with time value
     * @throws Custom
     */
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

    /**
     * Method that switch device status to val parameter
     * @param time its may affect start and end field with time value
     * @param val switch device status to these value
     * @throws Custom
     */
    @Override
    public void switchDevice(LocalDateTime time, String val) throws Custom {
        if (val.equals("On")) {
            super.setStatus("On");
            this.start = time;
        } else if (val.equals("Off")) {
            super.setStatus("Off");
            this.end = time;
            this.calculate();
        }
    }

    /**
     *  Method that updates memory
     */
    public void calculate(){

        this.usedMemory =this.getUsedMemory() + this.consumptionRate *duration(this.start,this.end);
        //uses duration method and multiply by consumption rate and update existing memory
        //resetting the start and end field
        this.start = null;
        this.end = null;
    }

    /**
     * Method calculate difference between date time values
     * @param start LocalDateTime type value represent start field
     * @param end   LocalDateTime type value represent end field
     * @return
     */
    public double duration(LocalDateTime start, LocalDateTime end){
        if (start==null || end == null){
            return 0;
        }else {
            return  Duration.between(this.start, this.end).getSeconds()/60.0;//calculate the difference between times

        }
    }

    /** Info method create information about device
     * @return all device values as a specific string
     */
    @Override
    public String info() {
        return  "Smart Camera "+ super.getName() +
                " is " +super.getStatus().toLowerCase()+ " and used "+
                String.format("%.2f",this.getUsedMemory()).replace(".",",")//format the output
                +" MB of storage so far (excluding current status), " +
                "and its time to switch its status is " +TimeControl.stringFormatter(super.getSwitchTime())+".\n";
    }
}
