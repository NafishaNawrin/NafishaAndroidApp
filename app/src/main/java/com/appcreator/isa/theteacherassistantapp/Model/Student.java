package com.appcreator.isa.theteacherassistantapp.Model;

public class Student
{
    public long studentID;


    private String firstname;
    private String lastname;
    private String study;
    private String attendance;
    private String enrlomentID;

    public Student(long studentID,String enrolmentID, String firstname , String lastname, String study, String attendance)
    {
        this.studentID = studentID;
        this.enrlomentID = enrolmentID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.study = study;
        this.attendance = attendance;
    }

    public Student()
    {

    }

    public long getStudentID()
    {
        return studentID;
    }

    public void setStudentID(long studentID)
    {
        this.studentID = studentID;
    }

    public String getEnrlomentID()
    {
        return enrlomentID;
    }

    public void setEnrlomentID(String enrlomentID)
    {
        this.enrlomentID = enrlomentID;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getStudy()
    {
        return study;
    }

    public void setStudy(String study)
    {
        this.study = study;
    }

    public String getAttendance()
    {
        return attendance;
    }

    public void setAttendance(String attendance)
    {
        this.attendance = attendance;
    }

    public String toString()
    {
        return "Student ID: "+getStudentID()+ "\n" +
                "Enrolment ID: "+getEnrlomentID() + "\n" +
                "Name: "+getFirstname() + " " + getLastname() + "\n" +
                "Study: "+getStudy() + "\n" +
                "Attendance: " + getAttendance();
    }
}
