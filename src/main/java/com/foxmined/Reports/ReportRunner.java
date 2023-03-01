package com.foxmined.Reports;

import com.foxmined.DBManipulation.StructuredTableProducer;
import com.foxmined.DBManipulation.DataGenerator;
import com.foxmined.Utils.ConsoleMenu;
import com.foxmined.Utils.ExceptionConstants;

import java.sql.SQLException;

public class ReportRunner {
    private static final StructuredTableProducer structuredTableProducer = new StructuredTableProducer();
    private static final DataGenerator dataGenerator = new DataGenerator();
    private static final ConsoleMenu CONSOLE_MENU = new ConsoleMenu();

    public static void main(String[] args) {

        structuredTableProducer.createCleanTablespace();
        dataGenerator.insertData();

        try {
            CONSOLE_MENU.run();
        } catch (SQLException e) {
            throw new IllegalArgumentException(ExceptionConstants.EMPTY_ARGUMENT);
        }
    }
}
