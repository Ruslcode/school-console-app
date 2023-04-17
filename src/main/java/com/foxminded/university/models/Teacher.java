package com.foxminded.university.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "teachers", schema = "school-console-app")
public class Teacher {
    @Id
    @Column(name = "teacher_id")
    private int teacherID;
    @Column(name = "teacher_name")
    private String firstName;
    @Column(name = "teacher_surname")
    private String lastName;
    @Column(name = "teacher_activity")
    private String activityDescription;

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }
}
