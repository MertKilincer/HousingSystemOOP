import java.io.FileWriter;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.*;

public class Main {
    public static void main(String[] args)  {
        String[] inputs = ReadInputFile.readFile(args[0]);
        Home home = new Home();
        inputs = Arrays.stream(inputs).filter(s -> !s.isEmpty()).toArray(String[]::new);

        try {
            if (inputs[0].startsWith("SetInitialTime")) {
                List<String> temp = Arrays.asList(inputs[0].split("\t"));
                home.setFactoryTime(temp.get(1));
                for (int i = 1; i <= inputs.length - 1; i++) {
                    String[] values = inputs[i].split("\t");
                    switch (values[0]) {

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
                            home.plugIn(values);
                            break;
                        case "PlugOut":
                            home.plugOut(values);
                            break;
                        case "Remove":
                            home.removeDevice(values);
                            break;
                        case "ChangeName":
                            home.changeName(values);
                            break;
                        case "Switch":
                            home.switchCommand(values);
                            break;
                        case "ZReport":
                            home.zReport(false);
                            break;
                        case "SetInitialTime":
                        default:
                            home.illegalCommand(values);
                            break;
                    }

                    }

                if (!(inputs[inputs.length-1].equals("ZReport"))){
                    home.zReport(true);
                }

            } else {
                    home.illegalStart(inputs[0]);
            }


        } catch (ArrayIndexOutOfBoundsException e) {
            home.illegalStart(inputs[0]);

        }catch (DateTimeException e){
            home.illegalStartTime(inputs[0]);
        }
        finally {
            try {
                FileWriter writer = new FileWriter(args[1]);
                writer.write(home.getOutput());
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}





