import java.time.LocalDateTime;

public class Lamp extends Device {

    private int kelvin;
    private int brightness;

    public Lamp(String name) {

        super(name);
        brightness=100;
        kelvin=4000;
    }

    public Lamp(String name, String status) throws Custom {
        super(name, status);
        brightness=100;
        kelvin=4000;
    }
    public Lamp (String name, String status,int kelvin,int brightness) throws Custom {
        super(name, status);
        this.setKelvin(kelvin);
        this.setBrightness(brightness);
    }

    public Lamp() {
        super();
    }


    public void setBrightness(int brightness) throws Custom {
        checkBrigtness(brightness);
        this.brightness = brightness;
        }


    public static void checkBrigtness(int brightness) throws Custom{
        if (brightness<0 ||brightness>100){
            throw new RangeError("ERROR: Brightness ", "0%-100%!\n");
        }
    }

    public void setKelvin(int kelvin) throws Custom{
        if (kelvin>=2000 && kelvin<=6500){
            this.kelvin=kelvin;
        }else{
            throw new RangeError("ERROR: Kelvin value ","2000K-6500K!\n");
        }
    }
    public void setWhite(int kelvin,int brightness) throws Custom {
        setKelvin(kelvin);
        setBrightness(brightness);
    }

    public int getKelvin() {
        return kelvin;
    }

    public int getBrightness() {
        return brightness;
    }

    @Override
    public void nopSwitch(LocalDateTime time) throws Custom {
        if (super.getStatus().equals("Off")) {
            super.setStatus("On");
        }else {
            super.setStatus("Off");
            }
        }

    @Override
    public String info() {
        return "Smart Lamp " + super.getName() + " is " + super.getStatus().toLowerCase() +
                " and its kelvin value is " + this.getKelvin() + "K with " + this.getBrightness() + "% brightness" +
                ", and its time to switch its status is "+ TimeControl.stringFormatter(this.getSwitchTime())+".\n";
    }
}
