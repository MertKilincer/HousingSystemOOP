
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;

public class Home {

    public TimeControl factoryTime;

    public Map<String, Product> itemList = new LinkedHashMap<>();

    public LinkedList<Product> deviceList = new LinkedList<>();
    public String output = new String();
    public ArrayList<LocalDateTime> switchlist = new ArrayList<>();

    public void setFactoryTime(String timeString) {
        this.factoryTime = new TimeControl(timeString);
        this.updateOutput("COMMAND: SetInitialTime\t" + timeString +
                "\nSUCCESS: Time has been set to " + timeString + "!\n");
    }

    public Map<String, Product> getItemList() {
        return this.itemList;
    }

    public LinkedList<Product> getDeviceList(){return this.deviceList;}

    public String getOutput() {
        return this.output;
    }

    private void updateOutput(String message) {
        output += message;
    }

    private LocalDateTime getCurrentTime() {
        return factoryTime.getTime();
    }

    public TimeControl getTimeControl() {
        return this.factoryTime;
    }

    public ArrayList<LocalDateTime> getSwitchlist() {
        return this.switchlist;
    }

    public void add(String[] input) {
        this.updateOutput("COMMAND: " + String.join("\t", input) + "\n");
        Product device = null;
        try {
            switch (input[1]) {
                case "SmartPlug":
                    device = getPlug(input);
                    break;
                case "SmartCamera":
                    device = getCamera(input);
                    break;
                case "SmartLamp":
                    device = getLamp(input);
                    break;
                case "SmartColorLamp":
                    device = getColorLamp(input);
                    break;
            }
            String Name = input[2];
            if (!checkDevices(Name)){
                this.getDeviceList().add(device);
            }else {
                throw new NameError();
            }

        } catch (Custom e) {
            this.updateOutput(e.getMessage());
        }
    }

    private static Plug getPlug(String[] args) throws Custom {

        if (args.length == 3) {
            return new Plug(args[2]);
        } else if (args.length == 4) {
            return new Plug(args[2], args[3]);
        } else if (args.length == 5) {
            return new Plug(args[2], args[3], Double.parseDouble(args[4]));
        } else {
            throw new Erroneous();
        }
    }

    private  Camera getCamera(String[] args) throws Custom {

        if (args.length == 4) {
            return new Camera(args[2], Double.parseDouble(args[3]));
        } else if (args.length == 5) {
            return new Camera(args[2], Double.parseDouble(args[3]), args[4],this.getCurrentTime());
        } else {
            throw new Erroneous();
        }
    }


    private static Lamp getLamp(String[] args) throws Custom {
        if (args.length == 3) {
            return new Lamp(args[2]);
        } else if (args.length == 4) {
            return new Lamp(args[2], args[3]);
        } else if (args.length == 6) {
            return new Lamp(args[2], args[3],
                    Integer.parseInt(args[4]), Integer.parseInt(args[5]));
        } else {
            throw new Erroneous();
        }
    }

    private static ColorLamp getColorLamp(String[] args) throws Custom {
        if (args.length == 3) {
            return new ColorLamp(args[2]);
        } else if (args.length == 4) {
            return new ColorLamp(args[2], args[3]);
        } else if (args.length == 6) {
            return new ColorLamp(args[2], args[3],
                    args[4], Integer.parseInt(args[5]));
        } else {
            throw new Erroneous();
        }
    }


    public void doSwitches() {
        ArrayList<LocalDateTime> temp1 = new ArrayList<>();
        for (LocalDateTime i : this.getSwitchlist()) {
            if (i.isBefore(this.getCurrentTime()) || i.isEqual(this.getCurrentTime())) {
                NopDoSwitch(i);
                temp1.add(i);
            }
        }
        for (LocalDateTime j : temp1) {
            this.getSwitchlist().remove(j);
        }
    }

    public void NopDoSwitch(LocalDateTime switchTime) {
        for (Product p : this.getDeviceList()){
            try {
                if (p.getSwitchTime().equals(switchTime)) {
                    p.nopSwitch(switchTime);
                    p.resetSwitchTime();
                }
            } catch (NullPointerException e) {
            } catch (Custom e) {
                this.updateOutput(e.getMessage());
            }
        }
    }

    public void PlugIn(String[] args) {
        this.updateOutput("COMMAND: " + String.join("\t", args) + "\n");
        try {
            String name = args[1];
            double Ampere = Double.parseDouble(args[2]);
            Product device =findDevices(name);
            Plug plug = (Plug) device;
            plug.PlugIn(Ampere, this.getCurrentTime());
            replaceProduct(name,plug);

        }catch (NullPointerException e) {
            this.updateOutput("null");
        } catch (NumberFormatException e) {
            this.updateOutput("ERROR: Erroneous command!\n");
        } catch (Custom e) {
            this.updateOutput(e.getMessage());
        } catch (ClassCastException e) {
            this.updateOutput("ERROR: This device is not a smart plug!\n");
        }
    }

    public void PlugOut(String [] args) {
        this.updateOutput("COMMAND: " + String.join("\t", args) + "\n");
        try {

            String name = args[1];
            Product device =findDevices(name);
            Plug temp = (Plug) device;
            temp.PlugOff(this.getCurrentTime());
            replaceProduct(name,temp);

        }catch (NullPointerException e) {
        } catch (Custom e) {
            this.updateOutput(e.getMessage());
        } catch (ClassCastException e) {
            this.updateOutput("ERROR: This device is not a smart plug!\n");
        }
    }

    public void Switch(String name, String status) {
        this.updateOutput("COMMAND: Switch\t" + name + "\t" + status + "\n");
        try {

                Product device = findDevices(name);
                if (device instanceof Lamp && device.getName().equals(name)) {
                    device.switchDevice(status);
                    replaceProduct(name,device);
                } else if (device.getName().equals(name)) {
                    Switchable switchableDevice = (Switchable) device;
                    switchableDevice.switchDevice(this.getCurrentTime(), status);
                    replaceProduct(name, (Product) switchableDevice);
                }


        }catch (NullPointerException e) {

        } catch (Custom e) {
            this.updateOutput(e.getMessage());
        }
    }


    public void nop() {
        this.updateOutput("COMMAND: Nop\n");
        if (!(this.getSwitchlist().size() == 0)) {
            this.getTimeControl().setTime(this.getSwitchlist().get(0));
            this.NopDoSwitch(this.getSwitchlist().get(0));
            this.getSwitchlist().remove(getSwitchlist().get(0));
        } else {
            this.updateOutput("ERROR: There is nothing to switch!\n");
        }
    }

    public void illegalCommand() {
        this.updateOutput("ERROR: Erroneous command!\n");
    }

    public void skipMinutes(String[] args) {
        this.getTimeControl().skipMinutes(args[1]);
        this.doSwitches();
        this.updateOutput("COMMAND: " + String.join("\t", args) + "\n");
    }

    public void setTime(String [] args) {
        try {
            LocalDateTime setTime = TimeControl.TimeFormatter(args[1]);
            this.getTimeControl().setTime(setTime);
            this.doSwitches();
            this.updateOutput("COMMAND: SetTime	" + TimeControl.stringFormatter(setTime) + "\n");
        }catch (DateTimeException e){
            this.updateOutput("ERROR: Time format is not correct!\n");
        }

    }

    public void setSwitchTime(String[] args) {
        this.updateOutput("COMMAND: SetSwitchTime\t" + args[1] + "\t" + args[2] + "\n");
        try {
            String name = args [1];
            String timeString = args[2];
            Product device = findDevices(name);
            LocalDateTime TimeOfSwitch = TimeControl.TimeFormatter(timeString);
            device.setSwitchTime(TimeOfSwitch);
            replaceProduct(name,device);
            if (!(this.getSwitchlist().contains(TimeOfSwitch))) {
                this.getSwitchlist().add(TimeOfSwitch);
            }
            Collections.sort(this.getSwitchlist());

        }catch (Custom e){
            this.updateOutput(e.getMessage());
        }

    }


    public void ZReport() {
        //ArrayList<Map.Entry<String, Product>> list = new ArrayList<>(this.getItemList().entrySet());
        //Comparator<Map.Entry<String, Product>> comparator = new Comparator<Map.Entry<String, Product>>() {
           // @Override
            //public int compare(Map.Entry<String, Product> e1, Map.Entry<String, Product> e2) {
               // LocalDateTime d1 = e1.getValue().getSwitchTime();
                //LocalDateTime d2 = e2.getValue().getSwitchTime();
                //if (d1 == null && d2 == null) {
                //    return 0; // both are null, equal
                //} else if (d1 == null) {
                //    return 1; // e1 is null, e2 is not null, e2 comes first
               /// } else if (d2 == null) {
                   // return -1; // e1 is not null, e2 is null, e1 comes first
                //} else {
                  //  return d1.compareTo(d2); // both are not null, compare their values
                //}////
            //}
        //};
        this.updateOutput("COMMAND: ZReport\nTime is:\t" + TimeControl.stringFormatter(this.getCurrentTime()) + "\n");
        //Collections.sort(list, Comparator.nullsLast(comparator));
        for (Product p : this.getDeviceList()) {
            this.updateOutput(p.info());
        }

    }

    public void removeDevice(String[] args) {
        this.updateOutput("COMMAND: Remove\t"+args[1]+"\n");
        try {
            if (args.length == 2) {
                String name =args[1];
                Product device =findDevices(name);
                this.updateOutput("SUCCESS: Information about removed smart device is as follows:\n"
                        +device.info());

                this.getDeviceList().remove(device);

            } else {
                throw new Erroneous();
            }
        } catch (Custom e) {
            this.updateOutput(e.getMessage());
        }
    }

    public void setKelvin(String[] args){
        this.updateOutput("COMMAND: " + String.join("\t", args) + "\n");
        try {
            String name = args[1];

            Product device = findDevices(name);
            Lamp lamp = (Lamp) device;
            lamp.setKelvin(Integer.parseInt(args[2]));
            replaceProduct(name,lamp);

        }catch (Custom e){
            this.updateOutput(e.getMessage());
        }catch (ClassCastException e){
            this.updateOutput("ERROR: This device is not a smart lamp!\n");
        }

    }
    public void setBrightness(String[] args){
        this.updateOutput("COMMAND: " + String.join("\t", args) + "\n");
        try {
            String name = args[1];
            Product device = findDevices(name);
            Lamp lamp = (Lamp) device;
            lamp.setBrightness(Integer.parseInt(args[2]));
            replaceProduct(name,lamp);

        }catch (Custom e){
            this.updateOutput(e.getMessage());
        }catch (ClassCastException e){
            this.updateOutput("ERROR: This device is not a smart lamp!\n");
        }

    }

    public void setColorCode(String[] args){
        this.updateOutput("COMMAND: " + String.join("\t", args) + "\n");
        try {
            String name = args[1];
            if (this.getItemList().containsKey(name)){
                Product device = this.getItemList().get(name);

                ColorLamp lamp = (ColorLamp) device;
                lamp.setColorCode(args[2]);
                replaceProduct(name,lamp);

            }else {
                throw new NotFound();
            }
        }catch (Custom e){
            this.updateOutput(e.getMessage());
        }catch (ClassCastException e){
            this.updateOutput("ERROR: This device is not a smart color lamp!\n");
        }

    }
    public void setWhite(String[] args){
        this.updateOutput("COMMAND: " + String.join("\t", args) + "\n");
        try {
            String name = args[1];
            if (this.getItemList().containsKey(name)){
                Product device = this.getItemList().get(name);
                Lamp lamp = (Lamp) device;
                lamp.setWhite(Integer.parseInt(args[2]),Integer.parseInt(args[3]));
                replaceProduct(name,lamp);

            }else {
                throw new NotFound();
            }
        }catch (Custom e){
            this.updateOutput(e.getMessage());
        }catch (ClassCastException e){
            this.updateOutput("ERROR: This device is not a smart lamp!\n");
        }

    }
    public void setColor(String [] args){
        this.updateOutput("COMMAND: " + String.join("\t", args) + "\n");
        try {
            String name = args[1];
            if (this.getItemList().containsKey(name)){
                Product device = this.getItemList().get(name);
                ColorLamp lamp = (ColorLamp) device;
                lamp.setColor(args[2],Integer.parseInt(args[3]));
                replaceProduct(name,lamp);

            }else {
                throw new NotFound();
            }
    }catch (Custom e){
        this.updateOutput(e.getMessage());
    }catch (ClassCastException e){
        this.updateOutput("ERROR: This device is not a smart color lamp!\n");
    }

}
    public void changeName(String [] args){
        this.updateOutput("COMMAND: " + String.join("\t", args) + "\n");
        try {
            try {
                if (!(args.length == 3)) {
                    throw new Erroneous();
                }
                String oldName = args [1];
                String newName = args [2];
                if (!(checkDevices(newName))) {

                    Product device = findDevices(oldName);
                    device.setName(newName);
                    replaceProduct(oldName,device);

                } else {
                    throw new NameError();
                }
            }catch (NullPointerException e) {
                throw new NotFound();
            }
        } catch (Custom e){
            this.updateOutput(e.getMessage());
        }
    }


    public Product findDevices(String name) throws Custom {

            for (Product p : this.getDeviceList()) {
                if (p.getName().equals(name)) {
                    return p;
                }
        }
        throw new NotFound();
    }
    public boolean checkDevices(String name){
        for (Product p : this.getDeviceList()) {
            if (p.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    public void replaceProduct(String name, Product newProduct) throws NotFound {
        int index = -1;
        for (int i = 0; i < this.getDeviceList().size(); i++) {
            Product p = this.getDeviceList().get(i);
            if (p.getName().equals(name)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            this.getDeviceList().set(index, newProduct);
        } else {
            throw new NotFound();
        }
    }



}











