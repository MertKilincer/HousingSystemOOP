import java.time.LocalDateTime;

/**
 * Device class is an abstract class that all of the smart devices  derived from this class
 * class holds name,status,switchTime,lastSwitchTime for usage
 */
public abstract class Device {
    private String name;
    private String status;
    private LocalDateTime switchTime;
    private LocalDateTime lastSwitchTime;

    public Device(String name) {
        this.name = name;
        this.status = "Off";
    }

    public Device(String name, String status) throws Custom {
        this.name = name;
        setStatus(status);
    }

    public Device() {
    }

    public String getStatus() {
        return this.status;
    }


    public void setStatus(String status) throws Custom {
        if (status.equals("On") || status.equals("Off")) {
            try {
                if (this.getStatus().equals(status)){
                    throw new StatusError(status);//error like if you try to switch on with status on devices
                }else {
                    this.status = status;
                }

            }catch (NullPointerException e){
                this.status=status;
            }
        }else{
            throw new Erroneous();//unvalid command
        }
    }


    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }


    public LocalDateTime getSwitchTime() {
        return this.switchTime;
    }
    public LocalDateTime getLastSwitchTime() {
        return this.lastSwitchTime;
    }


    public String getName(){
        return this.name;
    }


    public void setName(String name){ this.name=name;}

    /**
     * resetSwitchTime methods reset the existing switchTime to null after storing in lastSwitchTime
     */
    public void resetSwitchTime(){
        this.lastSwitchTime =this.getSwitchTime();
        this.switchTime = null;
    }


    public void switchDevice(String val) throws Custom {
        if (val.equals("On")) {
            this.setStatus("On");
        } else if (val.equals("Off")) {
            this.setStatus("Off");
        }
    }


    /**
     * abstract method that switching devices status if status is on to off and is off to on
     * @param time  lamp class do not use time parameter only plug and camera uses for calculate some calculations
     * @throws Custom
     */

    public abstract void nopSwitch(LocalDateTime time) throws Custom;


    /**
     *  Info method create information about device object
     * @return all device values as a specific string
     */
    public abstract String info();


}
