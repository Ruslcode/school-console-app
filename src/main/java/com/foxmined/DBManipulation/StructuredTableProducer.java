package com.foxmined.DBManipulation;

import com.foxmined.Utils.ExceptionConstants;
import com.foxmined.Utils.FileReader;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class StructuredTableProducer {
    private static final String PATH_TO_CREATING_STATEMENT = String.valueOf(Paths.get("SQL creating tables statement.txt").toAbsolutePath());
    private static final String PATH_TO_DROPPING_STATEMENT = String.valueOf(Paths.get("SQL droping tables statement.txt").toAbsolutePath());

    private static final Connection CONNECTION;

    static {

        try {
            CONNECTION = ConnectionFactory.getInstance().getConnection();
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.NO_CONNECTION);
        }

    }

    public void createCleanTablespace() {
        try {
            createTableAction();
        } catch (SQLException e) {
            try {
                dropTableAction();
            } catch (SQLException | IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                createTableAction();
            } catch (SQLException | IOException ex) {
                throw new RuntimeException(ex);
            }

        } catch (IOException e) {
            throw new IllegalArgumentException(ExceptionConstants.NO_FILE);
        }
    }

    private void dropTableAction() throws SQLException, IOException {
        List<String> tableDropStatement = FileReader.readLines(PATH_TO_DROPPING_STATEMENT);
        Statement statementDroping = CONNECTION.createStatement();
        for (String s : tableDropStatement) {
            statementDroping.executeUpdate(s);
        }
    }

    private static void createTableAction() throws SQLException, IOException {
        List<String> tableDropStatement = FileReader.readLines(PATH_TO_CREATING_STATEMENT);
        Statement statementCreating = CONNECTION.createStatement();
        for (String s : tableDropStatement) {
            statementCreating.executeUpdate(s);
        }
    }
}
