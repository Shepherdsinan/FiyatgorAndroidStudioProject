package com.shepherd.sinan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class Sqlsettings extends AppCompatActivity {

    EditText serverip,instance,port,databasename,user,password;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlsettings);


        serverip =      findViewById(R.id.editTextServerip);
        instance =      findViewById(R.id.editTextInstance);
        port =          findViewById(R.id.editTextPort);
        databasename =  findViewById(R.id.editTextDatabasename);
        user =          findViewById(R.id.editTextUser);
        password =      findViewById(R.id.editTextPassword);
        sharedPreferences = this.getSharedPreferences("com.shepherd.sinan", Context.MODE_PRIVATE);
        String dataserverip = sharedPreferences.getString("dataserverip",null);
        String datainstance = sharedPreferences.getString("datainstance",null);
        String dataport = sharedPreferences.getString("dataport",null);
        String datadatabasename = sharedPreferences.getString("datadatabasename",null);
        String datauser = sharedPreferences.getString("datauser",null);
        String datapassword = sharedPreferences.getString("datapassword",null);
        if (dataserverip !=null)
        {
            serverip.setText(dataserverip);
            instance.setText(datainstance);
            port.setText(dataport);
            databasename.setText(datadatabasename);
            user.setText(datauser);
            password.setText(datapassword);
        }
    }

    public void btnsave (View view){

        sharedPreferences.edit().putString("dataserverip", serverip.getText().toString()).apply();
        sharedPreferences.edit().putString("datainstance", instance.getText().toString()).apply();
        sharedPreferences.edit().putString("dataport", port.getText().toString()).apply();
        sharedPreferences.edit().putString("datadatabasename", databasename.getText().toString()).apply();
        sharedPreferences.edit().putString("datauser", user.getText().toString()).apply();
        sharedPreferences.edit().putString("datapassword", password.getText().toString()).apply();
        Toast.makeText(getApplicationContext(),"Ayarlar Kaydedildi :)",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        finish();
        startActivity(intent);
    }

    public void btnCheck (View view){
        Connection conn = null;
        String ConnURL = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + serverip.getText() +":"+port.getText()+";instance="+instance.getText()+";databaseName=" + databasename.getText() + ";user=" + user.getText() + ";password="+ password.getText() + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (Exception e) {

            Toast.makeText(this, "Bağlantı Başarız!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (conn!=null){
            Toast.makeText(this, "Bağlantı Başarıyla Sonuçlandı!", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Ayarları Kaydetmeyi Unutmayın!!!", Toast.LENGTH_SHORT).show();
        }
    }
}