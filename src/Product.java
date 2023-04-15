import java.time.LocalDateTime;

public abstract class Product {
    private String name;
    private String status;
    private LocalDateTime switchTime;

    private LocalDateTime lastswitchtime;

    public Product(String name) {
        this.name = name;
        this.status = "Off";
    }

    public Product(String name, String status) throws Custom {
        this.name = name;
        setStatus(status);
    }

    public Product() {
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



    public void setSwitchTime(LocalDateTime switchTime) {
        this.switchTime = switchTime;
    }

    public LocalDateTime getSwitchTime() {
        return this.switchTime;
    }
    public LocalDateTime getLastswitchtime() {
        return this.lastswitchtime;
    }

    public String getName(){
        return this.name;
    }

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

    public abstract String info();


}
