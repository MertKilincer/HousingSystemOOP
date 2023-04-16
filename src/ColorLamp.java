public class ColorLamp extends Lamp {

    private String colorCode;

    private Boolean ColorMode;

    public ColorLamp(String name) {
        super(name);
        this.ColorMode=false;
    }

    public ColorLamp(String name, String status) throws Custom {
        super(name, status);
        this.ColorMode=false;
    }

    public ColorLamp(String name, String status, String val3, int brightness) throws Custom {
        try {
            if (isHexadecimal(val3)) {
                super.setName(name);
                super.setStatus(status);
                super.setBrightness(brightness);
                this.setColorCode(val3);
                this.ColorMode = true;
            } else {
                super.setBrightness(brightness);
                super.setStatus(status);
                super.setName(name);
                super.setKelvin(Integer.parseInt(val3));
                this.ColorMode = false;
            }
        } catch (NumberFormatException e) {
            throw new Erroneous();
        }
    }

    private boolean getColorMode() {
        return this.ColorMode;
    }

    private String getColorCode() {
        return this.colorCode;
    }

    public void setColorCode(String colorCode) throws Custom {
        checkColorCode(colorCode);
        colorAssigner(colorCode);
    }
    public void colorAssigner(String colorCode){
            this.colorCode = colorCode;
            this.ColorMode = true;
        }

    private static void checkColorCode(String colorCode) throws Custom{
        if (isHexadecimal(colorCode)) {
            int number = Integer.parseInt(colorCode.substring(2), 16);
            if (!(number <= 0xFFFFFF && number >= 0x000000)) {
                throw new RangeError("ERROR: Color code value ", "0x0-0xFFFFFF!\n");
            }
        }else {
            throw new Erroneous();
        }
    }

    private static boolean isHexadecimal(String str) {
        if (str == null || str.isEmpty()) {
            return false; // if string is null or empty, it cannot be a hexadecimal number
        }
        String hexPattern = "^0[xX][0-9A-F]+$"; // regular expression pattern to match a hexadecimal number
        return str.matches(hexPattern);
    }

    public void setColor(String colorCode, int brightness) throws Custom {
        checkColorCode(colorCode);
        checkBrigtness(brightness);
        colorAssigner(colorCode);
        super.setBrightness(brightness);
        this.ColorMode = true;
    }

    @Override
    public void setWhite(int kelvin, int brightness) throws Custom {
        super.setWhite(kelvin, brightness);
        this.ColorMode = false;
    }


    @Override
    public String info() {
        if (this.getColorMode()) {
            return "Smart Color Lamp " + super.getName() + " is " + super.getStatus().toLowerCase() +
                    " and its color value is " + this.getColorCode() + " with " + this.getBrightness() + "% brightness" +
                    ", and its time to switch its status is " + TimeControl.stringFormatter(this.getSwitchTime()) + ".\n";
        }else {
            return "Smart Color Lamp " + super.getName() + " is " + super.getStatus().toLowerCase() +
                    " and its color value is " + this.getKelvin() + "K with " + this.getBrightness() + "% brightness" +
                    ", and its time to switch its status is " + TimeControl.stringFormatter(this.getSwitchTime()) + ".\n";
        }

    }
}

