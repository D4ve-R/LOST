/*
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.utils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * ResourceHandler interface to handle file opening in production mode
 */
public interface ResourceHandler {
    /**
     * @param filename name of file
     * @return File content as InputStream
     */
    default InputStream getFileResourcesAsStream(String filename){
        ClassLoader cl = getClass().getClassLoader();
        InputStream in = cl.getResourceAsStream(filename);
        if(in == null){
            throw new IllegalArgumentException("File not found: " + filename);
        }
        else{
            return in;
        }
    }

    /**
     * @param is InputStream to be converted to String
     * @return converted String
     * @throws IOException an Io Exception
     */
    default String convertStreamToString(InputStream is) throws IOException {
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }
}
