import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Public plug class extends Product abstract class and its implements Switchable interface
 * Plug class has six private field
 * @Ampere field stands for energy consumption calculation
 * @start field stands for starting of memory consumption
 * @end field stands for end time of memory consumption
 * @consumption field stands for used energy
 * @voltage constant voltage value
 * @plugged boolean value holds plug availability
 *
 *@author Mert Kılınçer
 * @see Device
 * @see ConsumerDevice
 */
public class Plug extends Device implements ConsumerDevice {

    private double ampere;
    private double consumption;
    private LocalDateTime start;
    private LocalDateTime end;
    private static final double voltage=220.0;
    private boolean plugged;

    /**
     * Constructor with name
     * @param name name of the device
     */
    public Plug(String name) {
        super(name);

    }

    /**
     * Constructor with name and status
     * @param name name of the device
     * @param status status of the device
     * @throws Custom
     */
    public Plug(String name, String status) throws Custom {
        super(name, status);
    }

    /**
     * Constructor with name ,status,ampere and time value for start
     * @param name name of the device
     * @param status status of the device
     * @param Ampere ampere value
     * @param start start time for energy consumption
     * @throws Custom
     */
    public Plug(String name, String status, double Ampere,LocalDateTime start) throws Custom {
        super(name, status);
        PlugIn(Ampere,start);//plug in an item
    }

    /**
     * Setter for ampere
     * @param ampere ampere value
     * @throws PositivityError if values is less than zero it throws an error
     */
    public void setAmpere(double ampere) throws PositivityError {
        if (ampere > 0) {
            this.ampere = ampere;
        } else {
            throw new PositivityError("Ampere");//give an error if condition satisfies
        }
    }

    public double getAmpere() {
        return this.ampere;
    }

    public double getConsumption() {
        return this.consumption;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public boolean isPlugged() {
        return this.plugged;
    }

    public void setPlugged(boolean plugged) {
        this.plugged = plugged;
    }

    /**
     * Method that switches objects
     * @param time  value can affect start and end times
     * @throws Custom
     */
    @Override
    public void nopSwitch(LocalDateTime time) throws Custom {
        if (super.getStatus().equals("Off")) {
            super.setStatus("On");
            if (this.isPlugged()) {
                this.setStart(time);
            }
        } else {
            super.setStatus("Off");
            if (this.isPlugged()) {
                this.setEnd(time);
                this.calculate();
            }
        }
    }

    /**
     * Method for plug in device to the plug object
     * @param ampere ampere value
     * @param time value can affect start and end times
     * @throws Custom if there is already a device is plugged in it throws a subclass exception
     */
    public void PlugIn(double ampere, LocalDateTime time) throws Custom {
        if (!this.isPlugged()){
            this.setAmpere(ampere);
            this.setPlugged(true);
            this.setStart(time);
    }else {
            throw new PlugInError();
        }
    }

    /**
     * Method for plug out device from the plug object
     * @param time value can affect start and end times
     * @throws Custom if there is already a device is plugged out it throws a subclass error
     */
    public void PlugOff(LocalDateTime time) throws Custom {
        if (this.isPlugged()){
            setPlugged(false);
            if (super.getStatus().equals("On")){
            this.setEnd(time);
            }
            this.calculate();//calculate the consumption
            this.ampere= Double.parseDouble(null);
        }else{
            throw new PlugOutError();
        }
    }

    /**
     *  Method that updates energy consumption of the plug object
     */
    public void calculate() {
        this.consumption = this.getConsumption() + voltage * (this.getAmpere()) * duration(this.getStart(),this.getEnd());
        //uses duration method and multiply by voltage value and ampere to update energy Consumption
        //resetting the start and end field
        this.start = null;
        this.end = null;
    }

    /**
     * Method calculate difference between date time values in the hour format
     * @param start LocalDateTime type value represent start field
     * @param end   LocalDateTime type value represent end field
     * @return double type difference between time values in hours
     */
    public double duration(LocalDateTime start, LocalDateTime end){
        if (start==null || end == null){
            return 0;
        }else {
           return  Duration.between(this.start, this.end).getSeconds() / 3600.0;
        }
    }

    /**
     * Method that switch device status to parameter
     * @param time        its may affect start and end field with time value
     * @param statusValue switch device status to these value
     * @throws Custom
     */
    public void switchDevice(LocalDateTime time, String statusValue) throws Custom {
        if (statusValue.equals("On")) {
            super.setStatus("On");
            if (this.plugged) {//check plugged field to change start field
                this.start = time;
            }
        } else if (statusValue.equals("Off")) {
            super.setStatus("Off");
            if (this.plugged) {//check plugged field to change end field
                this.end = time;
                this.calculate();
            }
        }

    }

    /**
     *  Info method create information about device object
     * @return all device values as a specific string
     */
    @Override
    public String  info() {
        return  "Smart Plug " + super.getName() + " is " + super.getStatus().toLowerCase() + " and consumed "
                + String.format("%.2f", this.consumption).replace(".",",")//format the output
                + "W so far (excluding current device), and " + "its time to switch its status is "
                + TimeControl.stringFormatter(this.getSwitchTime())+".\n";
    }
}
