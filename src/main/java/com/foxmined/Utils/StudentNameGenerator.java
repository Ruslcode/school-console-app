package com.foxmined.Utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class StudentNameGenerator {
    private static final int STUDENTS_LIMIT = 200;
    private static String[] firstNames;
    private static String[] lastNames;

    private static String[] generateUniqueNames() {
        if (StudentNameGenerator.STUDENTS_LIMIT > firstNames.length * lastNames.length) {
            throw new IllegalArgumentException("Number of requested names is too large");
        }

        Random random = new Random();
        String[] names = new String[StudentNameGenerator.STUDENTS_LIMIT];
        boolean[] used = new boolean[firstNames.length * lastNames.length];
        int numUsed = 0;

        while (numUsed < StudentNameGenerator.STUDENTS_LIMIT) {
            int index = random.nextInt(firstNames.length * lastNames.length);
            if (!used[index]) {
                used[index] = true;
                numUsed++;
                int firstNameIndex = index / firstNames.length;
                int lastNameIndex = index % lastNames.length;
                String firstName = firstNames[firstNameIndex];
                String lastName = lastNames[lastNameIndex];
                names[numUsed - 1] = firstName + "/" + lastName;
            }
        }

        return names;
    }

    private static void setNames() {
        try {
            firstNames = FileReader.readLines(String.valueOf(Paths.get("First Names.txt").toAbsolutePath())).get(0).split(" ");
            lastNames = FileReader.readLines(String.valueOf(Paths.get("Last Names.txt").toAbsolutePath())).get(0).split(" ");
        } catch (IOException e) {
            throw new IllegalArgumentException(ExceptionConstants.NO_FILE);
        }
    }

    public static String[] getNames() {
        setNames();
        return generateUniqueNames();
    }
}

