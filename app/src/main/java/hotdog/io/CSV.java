package hotdog.io;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.checkerframework.checker.units.qual.A;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class CSV {

    enum CSVHeaders {
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
        try {
            records = CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).build().parse(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<String[]> contents = new ArrayList<>();

        for (CSVRecord record : records) {
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
