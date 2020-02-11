package com.example.rifal.tokopi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Tentang extends AppCompatActivity {
    String info;
    String info2;
        private Context context;

        //Data-Data yang Akan dimasukan Pada ListView
        ListView listView;
        String[]ttg = new String[]{
                "Profil Toko", "Bantuan","Versi"
        };

        //ArrayList digunakan Untuk data tentang kami
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tentang);
            info = getApplicationContext() .getResources().getString(R.string.info);
            info2 = getApplicationContext() .getResources().getString(R.string.info2);

            listView = (ListView) findViewById(R.id.list_tentang);

            ArrayAdapter<String> item = new ArrayAdapter<String>
                    (this, android.R.layout.simple_list_item_1,ttg);
            listView.setAdapter(item);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

                    if(position==0) {
                        startActivity(ProfilToko.newIntent(Tentang.this));
                    }else if(position == 1 ){
                        startActivity(Bantuan.newIntent(Tentang.this));
                    }else if(position == 2 ) {
                        AlertDialog.Builder about = new AlertDialog.Builder(
                                Tentang.this);
                        about.setMessage(info2).setCancelable(false).setPositiveButton(
                                "OK", new AlertDialog.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = about.create();
                        dialog.setTitle("Versi 1.0");
                        dialog.show();
                        TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                        textView.setTextSize(15);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        startActivity(intent);
                    }

                }
            });
        }
    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,Tentang.class);
        return intent;
    }
    }
