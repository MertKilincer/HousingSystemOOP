public class ColorLamp extends Lamp{

    public String colorCode;

    public ColorLamp(String name) {
        super(name);
    }

    public ColorLamp(String name, String status) throws Custom {
        super(name, status);
    }

    public ColorLamp(String name, String status,String val3,int brightness) throws Custom {
    try {
        if (isHexadecimal(val3)) {
            super.setStatus(status);
            super.setName(name);
            super.setBrightness(brightness);
            this.setColorCode(val3);
        } else {
            super.setBrightness(brightness);
            super.setStatus(status);
            super.setName(name);
            super.setKelvin(Integer.parseInt(val3));

        }
    }catch (NumberFormatException e){
        throw new Erroneous();
    }
    }

    public void setColorCode(String colorCode) throws Custom {
        if (isHexadecimal(colorCode)) {
            int number = Integer.parseInt(colorCode.substring(2),16);
            if (number<=0xFFFFFF&&number>=0x000000){
                this.colorCode = colorCode;
            }else{
                throw new RangeError("ERROR: Color code ","0x0-0xFFFFFF!\n");
            }
        }else{
            throw new Erroneous();
        }

    }

    public void setColor(String colorCode,int brightness) throws Custom {
        this.setColorCode(colorCode);
        super.setBrightness(brightness);
    }

    public static boolean isHexadecimal(String str) {
        if(str == null || str.isEmpty()) {
            return false; // if string is null or empty, it cannot be a hexadecimal number
        }
        String hexPattern = "^0[xX][0-9A-F]+$"; // regular expression pattern to match a hexadecimal number
        return str.matches(hexPattern);
    }

    @Override
    public String info() {
        return "Smart Color Lamp " + super.getName() + " is " + super.getStatus().toLowerCase() +
                " and its color value is " + this.getKelvin() + "K with " + this.getBrightness() + "% brightness" +
                ", and its time to switch its status is "+ TimeControl.stringFormatter(this.getSwitchTime())+".\n";
    }

    }

