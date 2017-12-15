package shut_the_box_analysis.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Writer {

    public static void write(String filename, String content) {
        write(formatFolder(""), filename, content);
    }

    public static void write(String subFolder, String filename, String content) {
        final String folderName = formatFolder(subFolder);
        File folder = new File(folderName);
        if(folder.exists()) {
            writeContent(formatFilename(subFolder, filename), content);
        } else if (folder.mkdir()) {
            writeContent(formatFilename(subFolder, filename), content);
        } else {
            throw new RuntimeException("Unable to write folder" + folder);
        }
    }

    private static String formatFolder(String subFolder) {
        return "output/" + subFolder + "/";
    }

    private static String formatFilename(String folder, String filename) {
        return folder + filename + "_" + LocalDateTime.now() + ".csv";
    }

    private static void writeContent(String filename, String content) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.print(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
