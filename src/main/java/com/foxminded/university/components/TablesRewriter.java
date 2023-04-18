package com.foxminded.university.components;

import com.foxminded.university.exceptions.ExceptionConstants;
import com.foxminded.university.utils.FileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class TablesRewriter {
    private final String PATH_TO_CREATING_STATEMENT = String.valueOf(Paths.get("Create.txt").toAbsolutePath());
    private final String PATH_TO_DROPPING_STATEMENT = String.valueOf(Paths.get("Drop.txt").toAbsolutePath());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTablespace() {
        try {
            createTableAction();
        } catch (Exception exception) {
            dropTableAction();
            createTableAction();
        }
    }

    private void dropTableAction() {
        try {
            List<String> tableDropStatement = new ArrayList<>();
            tableDropStatement = FileReader.readLines(PATH_TO_DROPPING_STATEMENT);
            for (String query : tableDropStatement) {
                jdbcTemplate.update(query);
            }
        } catch (IOException e) {
            throw new RuntimeException(ExceptionConstants.NO_FILE);
        }
    }

    private void createTableAction() {
        try {
            List<String> tableDropStatement = new ArrayList<>();
            tableDropStatement = FileReader.readLines(PATH_TO_CREATING_STATEMENT);
            for (String query : tableDropStatement) {
                jdbcTemplate.update(query);
            }
        } catch (IOException e) {
            throw new RuntimeException(ExceptionConstants.NO_FILE);
        }
    }
}
