package br.com.costa.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.costa.agenda.dao.StudentDAO;
import br.com.costa.agenda.model.Student;

public class StudentsListActivity extends AppCompatActivity {

    ListView studentListView;

    @Override
    protected void onResume() {
        super.onResume();
        buildStudentsList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        studentListView = (ListView) findViewById(R.id.studentsList_listViewStudents);

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Student student = (Student) studentListView.getItemAtPosition(position);
                Intent intentStudentInsert = new Intent(StudentsListActivity.this, StudentsInsertActivity.class);
                intentStudentInsert.putExtra("student", student);
                startActivity(intentStudentInsert);
            }
        });

        Button newButton = (Button) findViewById(R.id.studentsInsert_buttonNew);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStudentInsert = new Intent(StudentsListActivity.this, StudentsInsertActivity.class);
                startActivity(intentStudentInsert);
            }
        });

        registerForContextMenu(studentListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deleteMenuItem = menu.add("Delete");

        deleteMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Student student = (Student) studentListView.getItemAtPosition(adapterMenuInfo.position);

                StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);
                studentDAO.delete(student.getId());
                studentDAO.close();

                buildStudentsList();

                Toast.makeText(StudentsListActivity.this, "Aluno "  + student.getName() + " removido!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void buildStudentsList() {

        StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);
        List<Student> studentList = studentDAO.read();
        studentDAO.close();

        ArrayAdapter<Student> studentsListViewAdapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, studentList);
        studentListView.setAdapter(studentsListViewAdapter);
    }

}
