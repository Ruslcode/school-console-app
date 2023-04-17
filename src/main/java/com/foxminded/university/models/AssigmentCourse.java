package com.foxminded.university.models;

import jakarta.persistence.*;

@Entity
@Table(name = "course_assignment")
public class AssigmentCourse {
    @Id
    @GeneratedValue
    @Column(name = "assignment_id")
    private int segmentationID;
    @Column(name = "student_id")
    private int studentID;
    @Column(name = "group_id")
    private int groupID;
    @Column(name = "course_id")
    private int courseID;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getSegmentationID() {
        return segmentationID;
    }

    public void setSegmentationID(int segmentationID) {
        this.segmentationID = segmentationID;
    }

    @Override
    public String toString() {
        return "AssigmentCourse{" +
                "segmentationID=" + segmentationID +
                ", studentID=" + studentID +
                ", groupID=" + groupID +
                ", courseID=" + courseID +
                '}';
    }
}
