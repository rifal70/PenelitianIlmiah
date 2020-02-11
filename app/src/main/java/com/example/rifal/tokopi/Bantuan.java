package com.example.rifal.tokopi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Bantuan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);
    }
    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,Bantuan.class);
        return intent;
    }
}
