package com.appcreator.isa.theteacherassistantapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StudentDatabaseHandler extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_SID = "studentID";
    public static final String COLUMN_EID = "enrlomentID";
    public static final String COLUMN_FIRST_NAME = "firstname";
    public static final String COLUMN_LAST_NAME = "lastname";
    public static final String COLUMN_STUDY= "study";
    public static final String COLUMN_ATTENDANCE= "attendance";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_STUDENTS + " (" +
                    COLUMN_SID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EID + " TEXT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_STUDY + " TEXT, " +
                    COLUMN_ATTENDANCE + " TEXT " +

                    ")";


    public StudentDatabaseHandler(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_STUDENTS);
        db.execSQL(TABLE_CREATE);
    }
}
