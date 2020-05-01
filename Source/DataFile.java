/*
 * Copyright (c) 2019 Halan.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public final class DataFile {

    private final LinkedList<DataLine> dataLines = new LinkedList<>();

    public static DataFile parseDataFile(String f) throws IOException {
        DataFile dataFile = new DataFile();
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = null;
        while ((line = br.readLine()) != null) {
            double[] values = Arrays.stream(line.split("\t"))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            dataFile.dataLines.add(
                    new DataLine(
                            new MyCustomVector(
                                    Arrays.copyOf(values, values.length - 1)),
                            values[values.length - 1]));
        }
        br.close();
        return dataFile;
    }

    public static DataFile parseDataFile(String file, int n, int m) throws IOException {
        DataFile dataFile = parseDataFile(file);
        assert dataFile.dataLines.size() == m;
        assert dataFile.dataLines.getFirst().getXSize() == n;
        return dataFile;
    }

    public String toString() {
        return dataLines.toString();
    }

    public LinkedList<DataLine> getDataLines() {
        return dataLines;
    }
}
