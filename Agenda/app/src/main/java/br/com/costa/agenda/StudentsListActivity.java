package br.com.costa.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class StudentsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        String[] studends = {"Erick", "John", "Fidelis ", "Costa", "João", "Erick", "John", "Fidelis ", "Costa", "João"};
        ListView studeList = (ListView) findViewById(R.id.studentsList_listViewStudents);
        ArrayAdapter<String> studentsListViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studends);
        studeList.setAdapter(studentsListViewAdapter);

        Button newButton = (Button) findViewById(R.id.studentsInsert_buttonNew);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStudenInsert = new Intent(StudentsListActivity.this, StudentsInsertActivity.class);
                startActivity(intentStudenInsert);
            }
        });

    }
}
