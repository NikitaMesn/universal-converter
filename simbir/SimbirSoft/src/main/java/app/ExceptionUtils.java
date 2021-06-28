package app;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class ExceptionUtils {

    public static void writeExceptionToFile(Exception e) {

        try (FileWriter writer = new FileWriter("exceptions.txt", true)) {
            writer.write(String.format("Error: %s \nDatetime: %s " +
                            "\n ********************************************************************\n",
                    e.getMessage(), LocalDateTime.now().toString().substring(0, 19)));
        } catch (IOException ioException) {
            throw new RuntimeException("Could not write Exception to file", ioException);
        }
    }
}
