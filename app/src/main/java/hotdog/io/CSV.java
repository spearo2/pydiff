package hotdog.io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.checkerframework.checker.units.qual.A;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSV {

    private enum CSVHeaders {
        RepoName, CPC, PC, FilePath
    }

    public ArrayList<String[]> readContents(String csvPath){

        Reader in = null;
        try {
            in = new FileReader(csvPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Iterable<CSVRecord> records = null;
        CSVFormat.Builder builder = CSVFormat.Builder.create();
        try {
            records = builder.setHeader(CSVHeaders.class).build().parse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String[]> contents = new ArrayList<>();

        boolean isHeader = true;
        for (CSVRecord record : records) {
            if (isHeader) { isHeader = false; continue; }
            String RepoName = record.get(CSVHeaders.RepoName);
            String CPC = record.get(CSVHeaders.CPC);
            String PC = record.get(CSVHeaders.PC);
            String FilePath = record.get(CSVHeaders.FilePath);
            String[] data = new String[]{RepoName, CPC, PC, FilePath};
            contents.add(data);
        }

        return contents;
    }
}
