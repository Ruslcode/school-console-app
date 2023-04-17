package com.foxminded.university.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "university_groups", schema = "school-console-app")
public class Group {
    @Id
    @Column(name = "group_id")
    private int groupID;
    @Column(name = "group_name")
    private String groupName;
    private int studentNumber;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Override
    public String toString() {
        return "Group - " + groupName + ", students: " + studentNumber;
    }
}
