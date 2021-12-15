/*
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.utils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
     * pathToDataDirectory String of the Directory in that all the Data (that is altered by the user or the program) is saved.
     */
    String pathToDataDirectory = System.getProperty("user.home") + File.separator + "LOST";

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

    default File getFileFromFS(String filename){
        return new File(pathToDataDirectory + File.separator + filename);
    }

    /**
     * @author Sebastian
     * @param pFilename name of file
     * @param pFileContent content to be written in the file
     *
     */
    default void writeStringToFile(String pFilename, String pFileContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToDataDirectory + File.separator + pFilename, StandardCharsets.UTF_8))) {
            writer.write(pFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Sebastian
     * @param pFilename name of file
     * @return String the content of the file
     *
     */
    default String readStringFromFile(String pFilename) throws IOException {
        try {
            return Files.readString(Paths.get(pathToDataDirectory + File.separator + pFilename), StandardCharsets.UTF_8);
        }
        catch ( IOException e){
            throw e;
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

    /**
     * copies files from jar to filesystem
     */
    default void initStorage(){
        Path path = Paths.get(pathToDataDirectory);
        if(!Files.exists(path)) {
            try {
                Files.createDirectory(path);
                Files.copy(getFileResourcesAsStream("levels/level_1.txt"), Paths.get(pathToDataDirectory + File.separator + "level_1.txt"));
                Files.copy(getFileResourcesAsStream("levels/level_2.txt"), Paths.get(pathToDataDirectory + File.separator + "level_2.txt"));
                Files.copy(getFileResourcesAsStream("levels/level_3.txt"), Paths.get(pathToDataDirectory + File.separator + "level_3.txt"));
                Files.copy(getFileResourcesAsStream("highscores/Highscores.txt"), Paths.get(pathToDataDirectory + File.separator + "Highscores.txt"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
