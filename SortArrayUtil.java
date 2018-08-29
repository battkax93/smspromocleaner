package sunny.smspromocleaner;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Wayan-MECS on 8/29/2018.
 */

public class SortArrayUtil {

    public static String[] main(String x) {
        String text = "the quick brown fox jumps fox fox over the lazy dog brown";
        String[] keys = x.split(",");
        String[] uniqueKeys;
        ArrayList<String> freqWord = new ArrayList<>();
        int count = 0;
        System.out.println(text);
        uniqueKeys = getUniqueKeys(keys);
        System.out.println("count " + Arrays.asList(uniqueKeys).toString());
        System.out.println("count " + Arrays.asList(keys).toString());

        for (String key : uniqueKeys) {
            if (null == key) break;
            for (String s : keys) {
                if (key.equals(s)) count++;
            }
            System.out.println("Count of [" + key + "] is : " + count);
            if (count > 10) freqWord.add(key);
            count = 0;
        }
        String[] cAll = new String[freqWord.size()];
        cAll = freqWord.toArray(cAll);
        System.out.println("most freq word: " + Arrays.asList(cAll).toString());
        return cAll;
    }

    private static String[] getUniqueKeys(String[] keys) {
        String[] uniqueKeys = new String[keys.length];

        uniqueKeys[0] = keys[0];
        int uniqueKeyIndex = 1;
        boolean keyAlreadyExists = false;

        for (int i = 1; i < keys.length; i++) {
            for (int j = 0; j <= uniqueKeyIndex; j++) {
                if (keys[i].equals(uniqueKeys[j])) {
                    keyAlreadyExists = true;
                }
            }

            if (!keyAlreadyExists) {
                uniqueKeys[uniqueKeyIndex] = keys[i];
                uniqueKeyIndex++;
            }
            keyAlreadyExists = false;
        }
        return uniqueKeys;
    }
}
