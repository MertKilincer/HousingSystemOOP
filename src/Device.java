import java.time.LocalDateTime;

public abstract class Device {
    private String name;
    private String status;
    private LocalDateTime switchTime;

    private LocalDateTime lastswitchtime;

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
                    throw new StatusError(status);
                }else {
                    this.status = status;
                }

            }catch (NullPointerException e){
                this.status=status;
            }
        }else{
            throw new Erroneous();
        }
    }

    /**
     * Setter for switchTime field
     * @param switchTime
     */
    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }

    /**
     * Getter for switchTime field
     * @return
     */
    public LocalDateTime getSwitchTime() {
        return this.switchTime;
    }
    public LocalDateTime getLastswitchtime() {
        return this.lastswitchtime;
    }

    /**
     * Getter for name fied
     * @return
     */
    public String getName(){
        return this.name;
    }

    /**
     * Setter for name field
     * @param name
     */
    public void setName(String name){ this.name=name;}
    public void resetSwitchTime(){
        this.lastswitchtime =this.getSwitchTime();
        this.switchTime = null;
    }

    public void switchDevice(String val) throws Custom {
        if (val.equals("On")) {
            this.setStatus("On");
        } else if (val.equals("Off")) {
            this.setStatus("Off");
        }
    }





    public abstract void nopSwitch(LocalDateTime time) throws Custom;


    /**
     *  Info method create information about device object
     * @return all device values as a specific string
     */
    public abstract String info();


}
