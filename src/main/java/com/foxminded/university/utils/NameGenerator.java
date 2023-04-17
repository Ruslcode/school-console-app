package com.foxminded.university.utils;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import com.foxminded.university.exceptions.ExceptionConstants;


public class NameGenerator {
    private int nameLimit;
    private String[] firstNames;
    private String[] lastNames;

    public NameGenerator(int nameLimit) {
        this.nameLimit = nameLimit;
    }

    public String[] getNames() {
        setNames();
        return generateUniqueNames();
    }

    private String[] generateUniqueNames() {
        if (nameLimit > firstNames.length * lastNames.length) {
            throw new IllegalArgumentException("Number of requested names is too large");
        }

        Random random = new Random();
        String[] names = new String[nameLimit];
        boolean[] used = new boolean[firstNames.length * lastNames.length];
        int numUsed = 0;

        while (numUsed < nameLimit) {
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

    private void setNames() {
        try {
            firstNames = FileReader.readLines(String.valueOf(Paths.get("First Names.txt").toAbsolutePath())).get(0).split(" ");
            lastNames = FileReader.readLines(String.valueOf(Paths.get("Last Names.txt").toAbsolutePath())).get(0).split(" ");
        } catch (IOException e) {
            throw new IllegalArgumentException(ExceptionConstants.NO_FILE);
        }
    }
}

