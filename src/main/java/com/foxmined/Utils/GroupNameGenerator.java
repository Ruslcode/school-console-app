package com.foxmined.Utils;

public class GroupNameGenerator {
    private static final int[] INNS = {0, 1, 2, 3, 4, 5, 6};
    private static final char[] CHARS = "ABFGQWE".toCharArray();
    private static final String[] groupNames = new String[10];

    private static String generateRandomGroupName() {
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

    private static void setGroupNames() {
        for (int i = 0; i < groupNames.length; i++) {
            groupNames[i] = generateRandomGroupName();
        }
    }

    public static String[] getGroupNames() {
        setGroupNames();
        return groupNames;
    }
}
