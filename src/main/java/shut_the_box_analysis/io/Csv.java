package shut_the_box_analysis.io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import shut_the_box_analysis.dag.states.CostType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Csv {

    private final CostType costType;
    private CSVPrinter csvPrinter;
    private final String baseOutputFolder = "output/";

    public Csv(String subFolder, String filename, CostType costType) {
        this.costType = costType;
        createFolder(baseOutputFolder);
        String folderName = createFolder(formatFolder(subFolder));
        Path outPath = Paths.get(formatFilename(folderName, filename));
        try {
            csvPrinter = new CSVPrinter(Files.newBufferedWriter(outPath), CSVFormat.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createFolder(String folderName) {
        File folder = new File(folderName);
        if (! folder.exists()) {
            if (! folder.mkdir()) throw new RuntimeException("Unable to create folder " + folder);;
        }
        return folderName;
    }

    private String formatFolder(String subFolder) {
        if (subFolder.equals("")) {
            return baseOutputFolder;
        } else {
            return baseOutputFolder + subFolder + "/";
        }
    }

    private String formatFilename(String folder, String filename) {
        return folder + filename + "_" + costType.toString() + ".csv";
    }

    public void add(Object... values) {
        List<String> formatedObject = new LinkedList<>();
        for (Object value : values) {
            if (value instanceof Integer) {
                formatedObject.add(format((Integer) value));
            } else if (value instanceof Double) {
                formatedObject.add(format((Double) value));
            } else {
                formatedObject.add(value.toString());
            }
        }
        try {
            csvPrinter.printRecord(formatedObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String format(Integer i) {
        return String.format("%d", i);
    }

    private String format(Double d) {
        return String.format(Locale.US, "%.2f", d);
    }

    public void write() {
        try {
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
