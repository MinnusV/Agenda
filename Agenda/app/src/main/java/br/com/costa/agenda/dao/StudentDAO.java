package br.com.costa.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.costa.agenda.model.Student;

/**
 * Created by ErickJohnFidelisCost on 15/03/2017.
 */

public class StudentDAO extends SQLiteOpenHelper{

    public StudentDAO(Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableStudents =
                "CREATE TABLE Students (" +
                    "id INTEGER PRIMARY KEY,"+
                    "name TEXT NOT NULL,"+
                    "address TEXT NOT NULL,"+
                    "email TEXT NOT NULL,"+
                    "number TEXT NOT NULL,"+
                    "site TEXT NOT NULL,"+
                    "note REAL NOT NULL)";

        db.execSQL(sqlCreateTableStudents);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlUpdateTableStudents =
                "DROP TABLE IF EXISTS Students";

        db.execSQL(sqlUpdateTableStudents);
        onCreate(db);
    }

    public void create(Student student) {

        SQLiteDatabase database = getWritableDatabase();

        ContentValues studentValues = getContentValues(student);

        database.insert("Students", null, studentValues);
    }

    public List<Student> read() {

        SQLiteDatabase database = getReadableDatabase();
        String sqlReadStudents =
                "SELECT * FROM Students";

        Cursor cursorReadStudents = database.rawQuery(sqlReadStudents, null);

        List<Student> alunos = new ArrayList<Student>();
        while (cursorReadStudents.moveToNext()){

            Student student = new Student();
            student.setId(cursorReadStudents.getLong(cursorReadStudents.getColumnIndex("id")));
            student.setName(cursorReadStudents.getString(cursorReadStudents.getColumnIndex("name")));
            student.setAddress(cursorReadStudents.getString(cursorReadStudents.getColumnIndex("address")));
            student.setEmail(cursorReadStudents.getString(cursorReadStudents.getColumnIndex("email")));
            student.setNumber(cursorReadStudents.getString(cursorReadStudents.getColumnIndex("number")));
            student.setSite(cursorReadStudents.getString(cursorReadStudents.getColumnIndex("site")));
            student.setNote(cursorReadStudents.getDouble(cursorReadStudents.getColumnIndex("note")));

            alunos.add(student);
        }

        cursorReadStudents.close();

        return alunos;
    }

    /*public String readSite(Student student){

        SQLiteDatabase database = getReadableDatabase();
        String sqlReadSiteStudents =
                "SELECT * FFROM Students WHERE id = " + student.getId();

        Cursor cursorReadSiteStudents = database.rawQuery(sqlReadSiteStudents, null);
        String emailStudent = "";

        while(cursorReadSiteStudents.moveToNext()){
            Student students = new Student();
            students.setEmail(cursorReadSiteStudents.getString(cursorReadSiteStudents.getColumnIndex("site")));

            emailStudent = students.getEmail();
        }

        cursorReadSiteStudents.close();

        return emailStudent;
    }*/

    public void delete(Long id) {

        SQLiteDatabase database = getWritableDatabase();
        String[] params = {id.toString()};
        database.delete("Students", "id = ?", params);
    }

    public void update(Student student) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues studentValues = getContentValues(student);
        String[] params = {student.getId().toString()};
        database.update("Students", studentValues, "id = ?", params);

    }

    @NonNull
    private ContentValues getContentValues(Student student) {
        ContentValues studentValues = new ContentValues();
        studentValues.put("name", student.getName());
        studentValues.put("address", student.getAddress());
        studentValues.put("email", student.getEmail());
        studentValues.put("number", student.getNumber());
        studentValues.put("site", student.getSite());
        studentValues.put("note", student.getNote());
        return studentValues;
    }
}
