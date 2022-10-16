package com.shepherd.sinan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {

    Connection connection;

    EditText edittextbarkod;
    TextView txturunadi,txturunfiyati,txtdolar,txteuro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txturunadi = (TextView) findViewById(R.id.textView2);
        txturunfiyati = (TextView) findViewById(R.id.textView);
        txtdolar = (TextView) findViewById(R.id.textViewdolar);
        txteuro = (TextView) findViewById(R.id.textVieweuro);
        edittextbarkod = (EditText) findViewById(R.id.search);
        edittextbarkod.setOnKeyListener(this::onKey);

        ConSQL.mainActivity = MainActivity.this;

        SharedPreferences sharedPreferences = getSharedPreferences("com.shepherd.sinan", MODE_PRIVATE);
        String dtip = sharedPreferences.getString("dataserverip",null);
        ConSQL.ip = dtip;
        ConSQL.instance = sharedPreferences.getString("datainstance",null);
        ConSQL.port = sharedPreferences.getString("dataport",null);
        ConSQL.db = sharedPreferences.getString("datadatabasename",null);
        ConSQL.un = sharedPreferences.getString("datauser",null);
        ConSQL.password = sharedPreferences.getString("datapassword",null);

        if (dtip !=null)
        {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this,Sqlsettings.class);
        startActivity(intent);
        return true;
        }

     boolean onKey(View v, int keyCode, KeyEvent event) {

        // Listen to "Enter" key press
        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER))
        {

            ConSQL c = new ConSQL();
            connection = c.conclass();

            if (connection !=null)
            {

                try {

                    String query = "Select * from wwwFIYAT_GOR WHERE BARKOD='" + edittextbarkod.getText() + "'";
                    Statement st = connection.createStatement();
                    ResultSet rs = st.executeQuery(query);

                        while (rs.next()) {
                            txturunadi.setText(rs.getString(2));
                            txturunfiyati.setText(rs.getString(3) + " ₺");
                            txtdolar.setText(rs.getString(5) + " $");
                            txteuro.setText(rs.getString(6) + " €");
                        }
                    connection.close();
                }
                catch(Exception ex)
                    {
                        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            }

            edittextbarkod.getText().clear();
            return true;
        }

        return false;

    }

}