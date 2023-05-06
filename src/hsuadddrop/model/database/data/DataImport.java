package hsuadddrop.model.database.data;

import hsuadddrop.helper.CsvParser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class DataImport {
    private final Connection connection;

    public DataImport(Connection connection) {
        this.connection = connection;
    }

    public void importData() {
        // Create file picker
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv");
        fileChooser.setFileFilter(filter);
        String projectPath = System.getProperty("user.dir");
        fileChooser.setCurrentDirectory(new File(projectPath));
        int result = fileChooser.showOpenDialog(null);

        // Import data from file
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            importDataFromFile(file);
        }
    }

    private void importDataFromFile(File file) {
        int totalTables = 0;
        int totalRowsAffected = 0;
        Map<String, List<String>> sqlStatements = new HashMap<>();
        Set<String> tableNames = new HashSet<>();
        try {
            // Parse CSV data into a list of rows
            CsvParser parser = new CsvParser();
            List<Map<String, String>> rows = parser.parse(file);

            // Import data into database
            for (Map<String, String> row : rows) {
                String tableName = row.get("table");
                row.remove("table");
                String columns = String.join(", ", row.keySet());
                // ("valud1", "value2", "value3")
                String values = row.values().stream()
                        .map(value -> String.format("'%s'", value))
                        .collect(Collectors.joining(", "));
                String sql = String.format("(%s)", values);

                // Add SQL statement to list for this table
                if (!sqlStatements.containsKey(tableName)) {
                    sqlStatements.put(tableName, new ArrayList<>());
                    tableNames.add(tableName);
                    sqlStatements.get(tableName).add(String.format("(%s)", columns));
                    totalTables++;
                }
                sqlStatements.get(tableName).add(sql);
            }

            // Create popup to confirm import
            int option = showSqlConfirmPopup(getAllSqlStatements(sqlStatements));
            if (option == JOptionPane.YES_OPTION) {
                // Create popup to confirm SQL statements
                option = showImportConfirmPopup(totalTables, rows.size());
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        // Disable auto-commit to improve performance
                        connection.setAutoCommit(false);

                        // Prepare batch of SQL statements
                        Statement stmt = connection.createStatement();
                        for (String tableName : sqlStatements.keySet()) {
                            String sql = getValuesList(sqlStatements, tableName);
                            System.out.println("sql = " + sql);
                            stmt.addBatch(sql);
                        }

                        // Execute batch of SQL statements and get number of rows affected
                        int[] rowsAffected = stmt.executeBatch();
                        connection.commit();

                        // Update total rows affected
                        for (int count : rowsAffected) {
                            totalRowsAffected += count;
                        }

                        // show summary popup
                        String message = String.format("Imported %d rows into %d tables", totalRowsAffected, totalTables);
                        JOptionPane.showMessageDialog(null, message, "Import complete", JOptionPane.INFORMATION_MESSAGE);

                    } catch (SQLException e) {
                        e.printStackTrace(); 
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int showImportConfirmPopup(int totalTables, int size) {
        String message = String.format("Import data from %d tables, for a total of %d rows?", totalTables, size);
        int option = JOptionPane.showConfirmDialog(null, message, "Confirm import", JOptionPane.YES_NO_OPTION);
        return option;
    }

    private int showSqlConfirmPopup(List<String> sqlStatements) {
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(true);
        for (String sqlStatement : sqlStatements) {
            textArea.append(sqlStatement + "\n");
        }
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(800, 600));
        int option = JOptionPane.showConfirmDialog(null, scrollPane, "Confirm SQL statements", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            String[] lines = textArea.getText().split("\\n");
            sqlStatements.clear();
            Collections.addAll(sqlStatements, lines);
        }
        return option;
    }

    private List<String> getAllSqlStatements(Map<String, List<String>> sqlStatements) {
        List<String> allSqlStatements = new ArrayList<>();
        for (String tableName : sqlStatements.keySet()) {
            String sql = getValuesList(sqlStatements, tableName);
            allSqlStatements.add(sql);
        }
        return allSqlStatements;
    }

    private String getValuesList(Map<String, List<String>> sqlStatements, String tableName) {
        List<String> valuesList = sqlStatements.get(tableName);
        String[] columns = valuesList.get(0).substring(1, valuesList.get(0).length() - 1).split(", ");
        String values = String.join(", ", valuesList.subList(1, valuesList.size()));
        String columnList = String.join(", ", columns);

        // format the values in rows
        values = values.replaceAll("\\),", "\\),\n");
        return String.format("INSERT INTO %s (%s) VALUES%n %s;", tableName, columnList, values);
    }
}