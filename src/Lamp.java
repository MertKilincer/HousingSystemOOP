import java.time.LocalDateTime;

/**
 * Lamp class is the subclass of the Device class.
 * <li>it has two extra fields.</li>
 * <li>Lamp accepts kelvin value  for kelvin field and default value is 4000</li>
 * <li>Lamp accepts brightness value for brightness field and default value is 100</li>
 *
 */
public class Lamp extends Device {
    /**
     *  it is in range of between 2000 and 6500
     */

    private int kelvin;
    /**
     *  it is in range of between 0 and 100
     */
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
        checkBrightness(brightness);
        this.brightness = brightness;
    }

    public void setKelvin(int kelvin) throws Custom{
        checkKelvin(kelvin);
        this.kelvin=kelvin;
    }

    /**
     * Checks the range of the brightness value
     * @param brightness
     * @throws Custom RangeError subclass of the custom is thrown.
     */
    public static void checkBrightness(int brightness) throws RangeError{
        if (brightness<0 ||brightness>100){
            throw new RangeError("ERROR: Brightness ", "0%-100%!\n");
        }
    }
    /**
     * Checks the range of the kelvin value
     * @param kelvin
     * @throws Custom RangeError subclass of the custom is thrown.
     */
    public static void checkKelvin(int kelvin) throws RangeError {
        if (kelvin<2000 || kelvin>6500){
            throw new RangeError("ERROR: Kelvin value ","2000K-6500K!\n");
        }
    }


    public void setWhite(int kelvin,int brightness) throws Custom {
        checkKelvin(kelvin);
        checkBrightness(brightness);
        setKelvin(kelvin);
        setBrightness(brightness);
    }

    public int getKelvin() {
        return kelvin;
    }

    public int getBrightness() {
        return brightness;
    }

    /**
     * Change the device current status to the different one
     * @param time
     * @throws Custom
     */
    @Override
    public void nopSwitch(LocalDateTime time) throws Custom {
        if (super.getStatus().equals("Off")) {
            super.setStatus("On");
        }else {
            super.setStatus("Off");
            }
        }

    /**
     * @return Information of the device returns as a string
     */
    @Override
    public String info() {
        return "Smart Lamp " + super.getName() + " is " + super.getStatus().toLowerCase() +
                " and its kelvin value is " + this.getKelvin() + "K with " + this.getBrightness() + "% brightness" +
                ", and its time to switch its status is "+ TimeControl.stringFormatter(this.getSwitchTime())+".\n";
    }
}
