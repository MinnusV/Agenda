package br.com.costa.agenda.converter;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.costa.agenda.StudentsListActivity;
import br.com.costa.agenda.model.Student;

/**
 * Created by Vinicius Viana on 29/04/2017.
 */

public class StudentConverter {

    public String ConverterJson(List<Student> students){

        JSONStringer js = new JSONStringer();
        try{
            js.object().key("list").array().object().key("aluno").array();
            for(Student student : students){
                js.object();
                js.key("nome").value(student.getName());
                js.key("nota").value(student.getNote());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();
        } catch (JSONException e){
            e.printStackTrace();
        }

        return js.toString();
    }
}
