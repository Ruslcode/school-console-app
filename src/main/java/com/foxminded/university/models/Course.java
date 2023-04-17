package com.foxminded.university.models;

import jakarta.persistence.*;

@Entity
@Table(name = "courses", schema = "school-console-app")
public class Course {
    @Id
    @Column(name = "course_id")
    private int index;
    @Column(name = "course_name")
    private String courseName;
    @Column(name = "course_description")
    private String courseDescription;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    @Override
    public String toString() {
        return courseName + " {" +
                "index=" + index +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + "}'";
    }
}
