package br.com.costa.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.costa.agenda.adapter.StudentAdapter;
import br.com.costa.agenda.dao.StudentDAO;
import br.com.costa.agenda.model.Student;

public class StudentsListActivity extends AppCompatActivity {

    ListView studentListView;
    Student student;

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
        AdapterView.AdapterContextMenuInfo adapterMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        student = (Student) studentListView.getItemAtPosition(adapterMenuInfo.position);
        final StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);


        MenuItem deleteMenuItem = menu.add("Delete");
        MenuItem visitarSiteMenuItem = menu.add("Visitar site");
        MenuItem enviarMensagemMenuItem = menu.add("Enviar SMS");
        MenuItem localizacaoMenuItem = menu.add("Localização");
        MenuItem ligacaoMenuItem = menu.add("Efetuar ligação");

        buildVisitarSite(student, visitarSiteMenuItem);

        buildEnviarMensagem(student, enviarMensagemMenuItem);

        buildLocalizacao(student, localizacaoMenuItem);

        buildLigacaoTelefonica(student, ligacaoMenuItem);

        buildRemove(student, studentDAO, deleteMenuItem);
    }

    private void buildLigacaoTelefonica(final Student student, final MenuItem ligacaoMenuItem) {
        ligacaoMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ActivityCompat.requestPermissions(StudentsListActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);

                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 123){
            if(ActivityCompat.checkSelfPermission(StudentsListActivity.this, Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED){

                Intent ligacaoTelefonica = new Intent(Intent.ACTION_CALL);
                ligacaoTelefonica.setData(Uri.parse("tel:" + student.getNumber()));
                startActivity(ligacaoTelefonica);
            }
        }
    }

    private void buildRemove(final Student student, final StudentDAO studentDAO, MenuItem deleteMenuItem) {
        deleteMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                studentDAO.delete(student.getId());
                studentDAO.close();

                buildStudentsList();

                Toast.makeText(StudentsListActivity.this, "Aluno "  + student.getName() + " removido!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void buildLocalizacao(Student student, MenuItem localizacaoMenuItem) {
        Intent localizacao = new Intent(Intent.ACTION_VIEW);
        localizacao.setData(Uri.parse("geo:0,0?q=" + student.getAddress()));
        localizacaoMenuItem.setIntent(localizacao);
    }

    private void buildEnviarMensagem(Student student, MenuItem enviarMensagemMenuItem) {
        Intent enviarMensagem = new Intent(Intent.ACTION_VIEW);
        enviarMensagem.setData(Uri.parse("sms:" + student.getNumber()));
        enviarMensagemMenuItem.setIntent(enviarMensagem);
    }

    private void buildVisitarSite(Student student, MenuItem visitarSiteMenuItem) {
        Intent visitarSite = new Intent(Intent.ACTION_VIEW);
        String site = student.getSite();
        if(!site.startsWith("http://")){
            visitarSite.setData(Uri.parse("http://"+ student.getSite()));
        }else{
            visitarSite.setData(Uri.parse(student.getEmail()));
        }
        visitarSiteMenuItem.setIntent(visitarSite);
    }

    private void buildStudentsList() {

        StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);
        List<Student> studentList = studentDAO.read();
        studentDAO.close();

        StudentAdapter studentsListViewAdapter = new StudentAdapter(this, studentList);
        studentListView.setAdapter(studentsListViewAdapter);
    }

}
