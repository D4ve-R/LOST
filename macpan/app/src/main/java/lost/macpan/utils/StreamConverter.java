package lost.macpan.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public interface StreamConverter {

    /**
     * Wandelt einen Stream zu einem String
     *
     * @param is Inputstream
     * @return String der aus Input entstanden ist
     * @throws IOException throws an io exception when an error occurs
     */
    default String convertStreamToString(InputStream is) throws IOException {
        return new String(is.readAllBytes(), StandardCharsets.UTF_8);
    }
}
