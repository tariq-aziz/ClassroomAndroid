package com.aziz.tariq.classroom;


public class Classroom {
    private String courseCode;
    private String profName;
    private String classroomPassword;

    public Classroom(String courseCode, String profName, String classroomPassword){
        this.courseCode = courseCode;
        this.profName = profName;
        this.classroomPassword = classroomPassword;
    }
    public Classroom(){

    }

    public String getCourseCode(){
        return courseCode;
    }
    public String getProfName(){
        return profName;
    }
    public String getClassroomPassword(){
        return classroomPassword;
    }
}
