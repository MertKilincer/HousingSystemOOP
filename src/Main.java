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
            /**
             * if first command is not initial time it must give error
             */
            if (inputs[0].startsWith("SetInitialTime")) {
                List<String> temp = Arrays.asList(inputs[0].split("\t"));
                home.setHomeTime(temp.get(1));
                for (int i = 1; i <= inputs.length - 1; i++) {
                    String[] values = inputs[i].split("\t");
                    /**
                     * switch case for which command will be called
                     */
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
                            /**
                             * boolean value is prevent that if is not last report it call zReport as a command
                             */
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
                    home.illegalStart(inputs[0]);//different method than set initial time
            }


        } catch (ArrayIndexOutOfBoundsException e) {
            home.illegalStart(inputs[0]);//error for missing date

        }catch (DateTimeException e){
            home.illegalStartTime(inputs[0]);//error for wrong date format
        }
        finally {
            try {
                /**
                 * Creating file  and output can be retrieved by calling
                 * Home object via getOutput method and write to output to file
                 */
                FileWriter writer = new FileWriter(args[1]);
                writer.write(home.getOutput());
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}





