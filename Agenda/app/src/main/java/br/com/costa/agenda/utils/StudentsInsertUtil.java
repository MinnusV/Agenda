package br.com.costa.agenda.utils;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RatingBar;

import br.com.costa.agenda.R;
import br.com.costa.agenda.model.Student;

/**
 * Created by ErickJohnFidelisCost on 14/03/2017.
 */

public class StudentsInsertUtil{

    public StudentsInsertUtil(){

    }

    public Student buildStudentForInsert(AppCompatActivity appCompatActivity) throws Exception {

        EditText textName = (EditText) appCompatActivity.findViewById(R.id.studentsInsert_editTextName);
        if (textName.getText().toString() == "") {
            throw new Exception();
        }

        EditText textAddres = (EditText) appCompatActivity.findViewById(R.id.studentsInsert_editTextAddres);
        if (textAddres.getText().toString() == "") {
            throw new Exception();
        }

        EditText textEmail = (EditText) appCompatActivity.findViewById(R.id.studentsInsert_editTextEmail);
        if (textEmail.getText().toString() == "") {
            throw new Exception();
        }

        EditText textNumber = (EditText) appCompatActivity.findViewById(R.id.studentsInsert_editTextNumber);
        if (textNumber.getText().toString() == "") {
            throw new Exception();
        }

        EditText textSite = (EditText) appCompatActivity.findViewById(R.id.studentsInsert_editTextSite);
        if (textSite.getText().toString() == "") {
            throw new Exception();
        }

        RatingBar ratingNote = (RatingBar) appCompatActivity.findViewById(R.id.studentInsert_ratingBarNote);
        if (ratingNote.getRating() == 0) {
            throw new Exception();
        }

        String name = textName.getText().toString();

        String address = textAddres.getText().toString();

        String email = textEmail.getText().toString();

        String number = textNumber.getText().toString();

        String site = textSite.getText().toString();

        Float note = ratingNote.getRating();

        Student student = new Student();
        student.setName(name);
        student.setAddress(address);
        student.setEmail(email);
        student.setNumber(number);
        student.setSite(site);
        student.setNote(note);

        return student;
    }

}
