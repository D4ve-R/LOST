/*
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.utils;
import java.io.InputStream;

/**
 * ResourceHandler interface to handle file opening in production mode
 */
public interface ResourceHandler {
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
}
