package com.foxminded.university.utils;

public class GroupNameGenerator {
    private final int[] INNS = {0, 1, 2, 3, 4, 5, 6};
    private final char[] CHARS = "ABFGQWE".toCharArray();
    private final String[] groupNames = new String[10];

    public  String[] getGroupNames() {
        setGroupNames();
        return groupNames;
    }

    private String generateRandomGroupName() {
        StringBuilder groupNameBuilder = new StringBuilder();
        int firstLettersLimit = 2;
        int groupNameLength = 5;
        int randomIndex;

        for (int i = 0; i < groupNameLength; i++) {
            randomIndex = (int) (CHARS.length * Math.random());
            if (i < firstLettersLimit) {
                groupNameBuilder.append(CHARS[randomIndex]);
            } else if (i == firstLettersLimit) {
                groupNameBuilder.append("-");
            } else {
                groupNameBuilder.append(INNS[randomIndex]);
            }
        }
        return groupNameBuilder.toString();
    }

    private void setGroupNames() {
        for (int i = 0; i < groupNames.length; i++) {
            groupNames[i] = generateRandomGroupName();
        }
    }
}
