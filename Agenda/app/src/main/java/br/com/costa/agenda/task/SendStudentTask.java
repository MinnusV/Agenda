package br.com.costa.agenda.task;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.costa.agenda.Client.WebClient;
import br.com.costa.agenda.StudentsListActivity;
import br.com.costa.agenda.converter.StudentConverter;
import br.com.costa.agenda.dao.StudentDAO;
import br.com.costa.agenda.model.Student;

/**
 * Created by Vinicius Viana on 29/04/2017.
 */

public class SendStudentTask extends AsyncTask{
    private Context context;
    private Dialog dialog;

    public SendStudentTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPostExecute(Object o) {
        dialog.dismiss();
        String response = (String) o;
        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
        super.onPostExecute(o);
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde... ", "Enviando dados dos alunos", true, true);
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {

        StudentDAO studentDAO = new StudentDAO(context);
        List<Student> student = studentDAO.read();

        StudentConverter converter = new StudentConverter();
        String json = converter.ConverterJson(student);

        WebClient webClient = new WebClient();
        String response = webClient.post(json);

        return response;
    }
}
