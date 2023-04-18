package com.foxminded.university.utils;

public class CoursesArrayBuilder {

    public String[] getCourses() {
        CoursesEnum[] courses = CoursesEnum.values();
        String[] courseStringArray = new String[courses.length];
        for (int i = 0; i < courses.length; i++) {
            courseStringArray[i] = courses[i].name();
        }
        return courseStringArray;
    }
}
