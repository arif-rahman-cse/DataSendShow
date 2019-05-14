package com.example.sharedprefarance;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    EditText firstName, lastName, email, password;
    Button submitButton, showButton;
    TextView textView, textViewMessage;
    int i;
    List<ModelClass> list;

    //Database Connection Information
    private  static final  String DB_URL = "jdbc:mysql://192.168.1.49/insert_test";
    private  static final  String USER = "super";
    private  static final  String PASS = "super";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstName = findViewById(R.id.firstNameId);
        lastName = findViewById(R.id.lastNameId);
        email = findViewById(R.id.emailId);
        password = findViewById(R.id.passwordId);

        submitButton = findViewById(R.id.submitButtonId);
        showButton = findViewById(R.id.showButtonId);

        textView = findViewById(R.id.view);
        textViewMessage = findViewById(R.id.message_show);

        list = new ArrayList<>();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Send objSend = new Send();
                objSend.execute("");

            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrieve objSend2 = new Retrieve();
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                objSend2.execute("");
            }
        });
    }

    private class Send extends AsyncTask<String, String, String>
    {
        //Input Data from EditText Field
        String msg = "";
        String nameFirst = firstName.getText().toString();
        String nameLast = lastName.getText().toString();
        String newEmail = email.getText().toString();
        String newPassword = password.getText().toString();

        @Override
        protected void onPreExecute() {
            textViewMessage.setText("Please Wait Data is inserting....");
        }

        @Override
        protected String doInBackground(String... strings) {
            try{

                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);

                if(connection == null)
                {
                    msg = "Connection Null!";
                }
                else
                {
                    String query = "INSERT INTO user_info (first_name,last_name,email,password) VALUES ('"+nameFirst+"','"+nameLast+"','"+newEmail+"','"+newPassword+"')";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);
                    msg = "Data Insert Successfully!";

                }

            }catch (Exception ex){

                msg = "Try to Connect but failed!!";

            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            textViewMessage.setText(msg);
        }
    }


   class Retrieve extends AsyncTask<String, String, String> {

       String msg = "";
       String nameFirst, nameLast, newEmail,newPassword;

       @Override
       protected String doInBackground(String... strings) {

           try{

               Class.forName("com.mysql.jdbc.Driver");
               Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);

               if(connection == null)
               {
                   msg = "Connection Null!";
               }
               else
               {
                   String query = "SELECT * FROM user_info";
                   Statement statement = connection.createStatement();
                   ResultSet resultSet = statement.executeQuery(query);

                   while (resultSet.next()){
                       nameFirst = resultSet.getString(1);
                       nameLast = resultSet.getString(2);
                       newEmail = resultSet.getString(3);
                       newPassword = resultSet.getString(4);

                       ModelClass modelClass = new ModelClass(nameFirst,nameLast,newEmail);

                       list.add(modelClass);

                   }

                   msg = "Data Getting From Database Successfully !";

               }

           }catch (Exception ex){

               msg = "Try  Get From Database failed!!";

           }

           return msg;
       }

       @Override
       protected void onPostExecute(String s) {

           textView.setText("");

           for (i=0;i<list.size();i++){

               ModelClass modelClass=list.get(i);
               textView.append(modelClass.getFirstName()+" "+modelClass.getLastName()+ " \n"+modelClass.getEmail()+"\n");
           }

           textViewMessage.setText(msg);
       }
   }


}
