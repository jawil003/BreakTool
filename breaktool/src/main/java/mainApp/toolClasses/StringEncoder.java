package mainApp.toolClasses;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StringEncoder {
    public StringEncoder() {

    }

    public static final String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}


