package com.appcreator.isa.theteacherassistantapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.appcreator.isa.theteacherassistantapp.Model.Student;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class StudentOperations
{
    public static final String LOGTAG = "STD_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            StudentDatabaseHandler.COLUMN_SID,
            StudentDatabaseHandler.COLUMN_EID,
            StudentDatabaseHandler.COLUMN_FIRST_NAME,
            StudentDatabaseHandler.COLUMN_LAST_NAME,
            StudentDatabaseHandler.COLUMN_STUDY,
            StudentDatabaseHandler.COLUMN_ATTENDANCE

    };

    public StudentOperations(Context context)
    {
        dbhandler = new StudentDatabaseHandler(context);
    }

    public void open()
    {
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();
    }
    public void close()
    {
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();
    }



    public Student addStudent(Student Student)
    {
        ContentValues values  = new ContentValues();
        values.put(StudentDatabaseHandler.COLUMN_EID, Student.getEnrlomentID());
        values.put(StudentDatabaseHandler.COLUMN_FIRST_NAME,Student.getFirstname());
        values.put(StudentDatabaseHandler.COLUMN_LAST_NAME,Student.getLastname());
        values.put(StudentDatabaseHandler.COLUMN_STUDY, Student.getStudy());
        values.put(StudentDatabaseHandler.COLUMN_ATTENDANCE, Student.getAttendance());
        long insertSID = database.insert(StudentDatabaseHandler.TABLE_STUDENTS,null,values);
        Student.setStudentID(insertSID);
        return Student;
    }

    // Getting single Student
    public Student getStudent(long id)
    {
        Cursor cursor = database.query(StudentDatabaseHandler.TABLE_STUDENTS,allColumns,StudentDatabaseHandler.COLUMN_SID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null && cursor.moveToFirst())
            cursor.moveToFirst();

        Student e = new Student(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
        // return Student
        return e;
    }

    public List<Student> getAllStudents()
    {
        Cursor cursor = database.query(StudentDatabaseHandler.TABLE_STUDENTS,allColumns,null,null,null, null, null);

        List<Student> students = new ArrayList<>();
        if(cursor.getCount() > 0 && cursor != null)
        {
            while(cursor.moveToNext())
            {
                Student student = new Student();
                student.setStudentID(cursor.getLong(cursor.getColumnIndex(StudentDatabaseHandler.COLUMN_SID)));
                student.setEnrlomentID(cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.COLUMN_EID)));
                student.setFirstname(cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.COLUMN_FIRST_NAME)));
                student.setLastname(cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.COLUMN_LAST_NAME)));
                student.setStudy(cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.COLUMN_STUDY)));
                student.setAttendance(cursor.getString(cursor.getColumnIndex(StudentDatabaseHandler.COLUMN_ATTENDANCE)));
                students.add(student);
            }
        }
        // return All Students
        return students;
    }





    // Updating Student
    public int updateStudent(Student student)
    {
        ContentValues values = new ContentValues();
        values.put(StudentDatabaseHandler.COLUMN_EID, student.getEnrlomentID());
        values.put(StudentDatabaseHandler.COLUMN_FIRST_NAME, student.getFirstname());
        values.put(StudentDatabaseHandler.COLUMN_LAST_NAME, student.getLastname());
        values.put(StudentDatabaseHandler.COLUMN_STUDY, student.getStudy());
        values.put(StudentDatabaseHandler.COLUMN_ATTENDANCE, student.getAttendance());

        // updating row
        return database.update(StudentDatabaseHandler.TABLE_STUDENTS, values,
                StudentDatabaseHandler.COLUMN_SID + "=?",new String[] { String.valueOf(student.getStudentID())});
    }



    // Deleting Student
    public void removeStudent(Student student)
    {
        database.delete(StudentDatabaseHandler.TABLE_STUDENTS, StudentDatabaseHandler.COLUMN_SID + "=" + student.getStudentID(), null);
    }


}
