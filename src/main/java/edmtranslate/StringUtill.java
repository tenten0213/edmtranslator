package edmtranslate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tenten0213
 */

public class StringUtill {


    public static String camelToSnake(String targetStr) {
        String convertedStr = targetStr
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .replaceAll("([a-z])([A-Z])", "$1_$2");
        return convertedStr.toUpperCase();
    }

}
