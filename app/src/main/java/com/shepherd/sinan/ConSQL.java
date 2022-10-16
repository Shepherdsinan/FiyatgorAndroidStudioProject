package com.shepherd.sinan;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConSQL {
    Connection con;
    public static Context mainActivity ;
    public static String ip ;
    public static String instance ;
    public static String port ;
    public static String db ;
    public static String un ;
    public static String password ;

    @SuppressLint("NewApi")
    public Connection conclass()  {

        DriverManager.setLoginTimeout(3);

        String ConnURL = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + ip +":"+port+";instance="+instance+";databaseName=" + db + ";user=" + un + ";password="+ password + ";";

            con = DriverManager.getConnection(ConnURL);


        } catch (Exception e) {

            Toast.makeText(mainActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
            AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
            alert.setTitle("İnfo");
            alert.setMessage("Bağlantı Başarız!Lütfen bağlantınızı kontrol edin...");
            alert.setPositiveButton("Ayarlar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(mainActivity,Sqlsettings.class);
                    mainActivity.startActivity(intent);
                }
            });
            alert.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alert.show();

        }
        return con;
    }


}
