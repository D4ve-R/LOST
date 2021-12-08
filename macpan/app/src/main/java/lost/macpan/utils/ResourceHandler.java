/*
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.utils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    default File getFilefromFS(String fileName) {
        return new File(System.getProperty("user.home") + File.separator + "Macpan" + File.separator + fileName);
    }

    /**
     * @param is InputStream to be converted to String
     * @return converted String
     * @throws IOException an Io Exception
     */
    default String convertStreamToString(InputStream is) throws IOException {
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }

    default void initStorage(){
        Path path = Paths.get(System.getProperty("user.home") + File.separator + "Macpan");
        if(!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                Files.copy(getFileResourcesAsStream("levels/level_1.txt"), Paths.get(System.getProperty("user.home") + File.separator + "Macpan" + File.separator + "level_1.txt"));
                Files.copy(getFileResourcesAsStream("levels/level_2.txt"), Paths.get(System.getProperty("user.home") + File.separator + "Macpan" + File.separator + "level_2.txt"));
                Files.copy(getFileResourcesAsStream("levels/level_3.txt"), Paths.get(System.getProperty("user.home") + File.separator + "Macpan" + File.separator + "level_3.txt"));
                Files.copy(getFileResourcesAsStream("highscores/Highscores.txt"), Paths.get(System.getProperty("user.home") + File.separator + "Macpan" + File.separator + "Highscores.txt"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
