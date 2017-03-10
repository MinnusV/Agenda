package br.com.costa.agenda;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StudentsInsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Carregando configurações do AppCompatActivity para o onCreate
        super.onCreate(savedInstanceState);
        //Adicionando o xml ao conteúdo da tela
        setContentView(R.layout.activity_students_insert);

        Button saveButton = (Button) findViewById(R.id.studentsInsert_buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateSave()) {
                    Toast.makeText(StudentsInsertActivity.this, "Save!", Toast.LENGTH_SHORT).show();
                }else if(!validateSave()){
                    Toast.makeText(StudentsInsertActivity.this, "Not Save!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validateSave(){
        //boolean validade = true;
        boolean validade = false;

        return validade;
    }
}
