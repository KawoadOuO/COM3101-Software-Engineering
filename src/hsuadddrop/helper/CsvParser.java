package hsuadddrop.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvParser {

    private final String delimiter;
    private String missingValue = null;

    public CsvParser() {
        this.delimiter = ",";
    }

    public CsvParser(String delimiter) {
        this.delimiter = delimiter;
    }

    public List<Map<String, String>> parse(File file) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Map<String, Integer> columnIndices = null;
            String currentTable = null;
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.trim().isEmpty()) {
                    continue;
                }
                if (line.startsWith("Table:")) {
                    // Parse table header row
                    currentTable = line.substring(6).trim();
                    String headerRow = reader.readLine().trim();
                    String[] columnNames = headerRow.split(delimiter);
                    columnIndices = new HashMap<>();
                    for (int j = 0; j < columnNames.length; j++) {
                        columnIndices.put(columnNames[j].trim(), j);
                    }
                } else if (columnIndices != null) {
                    // Parse data row
                    String[] row = line.split(delimiter);
                    Map<String, String> rowData = new HashMap<>();
                    for (Map.Entry<String, Integer> entry : columnIndices.entrySet()) {
                        int columnIndex = entry.getValue();
                        String value;
                        if (columnIndex >= row.length) {
                            value = missingValue; // Replace empty value with missing value
                        } else {
                            value = row[columnIndex].trim();
                        }
                        String columnName = entry.getKey();
                        if (!(value == null)) {
                            if (value.equals("NULL")) {
                                value = null; // Replace "NULL" with null
                            }
                        }
                        rowData.put(columnName, value);
                    }
                    rowData.put("table", currentTable); // Add table name to data
                    data.add(rowData);
                }
            }
        }
        return data;
    }
}
