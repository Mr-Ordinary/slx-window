package slx.window.xml;

import java.util.HashMap;
import java.util.Map;

public class GrammarLibrary {
    public static final Map<String,String> library = new HashMap<String,String>();


    static {
        library.put(".$message",".messageDialog");
        library.put("exit()","main.close()");
    }
}
