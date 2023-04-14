import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String[] inputs = ReadInputFile.readFile("input4.txt");
        Home home = new Home();
        inputs = Arrays.stream(inputs).filter(s -> !s.isEmpty()).toArray(String[]::new);

        try {
            if (inputs[0].startsWith("SetInitialTime")) {
                List<String> temp = Arrays.asList(inputs[0].split("\t"));
                home.setFactoryTime(temp.get(1));
                for (int i = 1; i <= inputs.length - 1; i++) {
                    String[] values = inputs[i].split("\t");
                    switch (values[0]) {

                        case "SetInıtialTime":
                            //pass
                        case "SetTime":
                            home.setTime(values);
                            break;
                        case "SkipMinutes":
                            home.skipMinutes(values);
                            break;
                        case "Add":
                            home.add(values);
                            break;
                        case "SetSwitchTime":
                            home.setSwitchTime(values);
                            break;
                        case "Nop":
                            home.nop();
                            break;
                        case "SetKelvin":
                            home.setKelvin(values);
                            break;
                        case "SetBrightness":
                            home.setBrightness(values);
                            break;
                        case "SetColorCode":
                            home.setColorCode(values);
                            break;
                        case "SetWhite":
                            home.setWhite(values);
                            break;
                        case "SetColor":
                            home.setColor(values);
                            break;
                        case "PlugIn":
                            home.PlugIn(values);
                            break;
                        case "PlugOut":
                            home.PlugOut(values);
                            break;
                        case "Remove":
                            home.removeDevice(values);
                            break;
                        case "ChangeName":
                            home.changeName(values);
                            break;
                        case "Switch":
                            home.Switch(values[1],values[2]);
                            break;
                        case "ZReport":
                            home.ZReport();
                            break;
                        default:
                            home.illegalCommand();
                            break;
                    }

                    }
                if (!(inputs[inputs.length-1].equals("ZReport"))){
                    home.ZReport();
                }
                FileWriter writer = new FileWriter("output.txt");
                writer.write(home.getOutput());
                writer.close();


            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





