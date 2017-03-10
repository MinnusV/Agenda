package br.com.costa.agenda;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StudentsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Carregando configurações do AppCompatActivity para o onCreate
        super.onCreate(savedInstanceState);
        //Adicionando o xml ao conteúdo da tela
        setContentView(R.layout.activity_students_list);

        //Inserindo alunos na listview definida no xml
        String[] studends = {"Erick", "John", "Fidelis ", "Costa", "João"};
        ListView studeList = (ListView) findViewById(R.id.studentsList_listViewStudents);
        ArrayAdapter<String> studentsListViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studends);
        studeList.setAdapter(studentsListViewAdapter);
    }
}
