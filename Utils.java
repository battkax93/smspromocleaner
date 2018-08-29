package sunny.smspromocleaner;

import java.util.ArrayList;
import java.util.Arrays;

public class Utils {

    public static String arrayListToString(ArrayList x){
        String[] y = new String[x.size()];
        y = (String[]) x.toArray(y);
        return Arrays.asList(y).toString();
    }

    public static String arrayToString (String[] s){
        return Arrays.asList(s).toString();
    }
}
