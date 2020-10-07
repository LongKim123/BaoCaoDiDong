package com.example.appbanhang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;

import java.util.HashMap;

public class Session extends AppCompatActivity {
    private TextView txtsessionname,txtsessionemail;
    private Button btndangxuat;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
        sessionManager = new SessionManager(this);
       // sessionManager.checkLogin();
        txtsessionemail= findViewById(R.id.idemailsession);
        txtsessionname=findViewById(R.id.idnamesession);
        btndangxuat=findViewById(R.id.btndangxuats);
        HashMap<String,String> user=sessionManager.getUserDetail();
        String mname=user.get(sessionManager.NAME);
        String memail=user.get(sessionManager.EMAIL);



        txtsessionname.setText(mname);
        txtsessionemail.setText(memail);


        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
            }
        });
    }
}