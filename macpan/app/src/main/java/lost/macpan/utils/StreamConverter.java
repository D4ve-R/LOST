package lost.macpan.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public interface StreamConverter {
    /*
        http://www.java2s.com/Code/Android/File/getResourceAsStreamLoadstheresourcefromclasspath.htm
     */

    /**
     * Wandelt einen Stream zu einem String
     *
     * @param is Inputstream
     * @return String der aus Input entstanden ist
     * @throws IOException throws an io exception when an error occurs
     */
    default String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}
