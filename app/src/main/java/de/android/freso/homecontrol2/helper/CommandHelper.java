package de.android.freso.homecontrol2.helper;

import android.util.Log;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import de.android.freso.homecontrol2.commands.Command;
import de.android.freso.homecontrol2.commands.Variable;
import de.android.freso.homecontrol2.modules.AT;
import de.android.freso.homecontrol2.modules.FhemModul;

/**
 * Created by Patrick on 14.01.2015.
 */
public class CommandHelper {

    public static void parse(HashMap<String, FhemModul> map) {

        Object[] set = map.keySet().toArray();

        for(int i=0; i < set.length; i++) {
            FhemModul device = map.get(set[i].toString());
            switch (device.getType()) {

                //****************************************************
                // Definition für die Module
                //****************************************************
                case "at":
                    AT atDevice = (AT) device;
                    String command = atDevice.getCommand();
                    ArrayList<Command> commandList = parse(command);
                    atDevice.setCommandList(commandList);
                    break;
                //****************************************************
            }
        }

    }

    public static ArrayList<Command> parse(String command) {
        ArrayList<Command> commandList = new ArrayList<Command>();
        char[] chars = command.toCharArray();
        if(chars[0] == 123 && chars[chars.length-1] == 125) {
            Log.i("CmdHelper", "Command OK");
            Log.i("CmdHelper", command.substring(1, command.length()-2));
            String cmd = command.substring(1, command.length()-2);
            Variable firstCmd = checkVariable(cmd.split(";;")[0]);
            if(firstCmd != null && firstCmd.getName().equals("hc2")) {
                Log.i("CmdHelper", "Variable OK: " + firstCmd.getName());
            } else {
                Log.i("CmdHelper", "Variable NICHT OK: " + firstCmd.getName());
            }
        } else {
            Log.i("CmdHelper", "Command NICHT OK");
            return null;
        }
        return commandList;

    }


    private static Command check(String cmdLine) {


        return null;
    }

    private static Variable checkVariable(String cmdLine) {


        return null;
    }

    private static Command checkFhem(String cmdLine) {


        return null;
    }

}
