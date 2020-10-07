package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appbanhang.R;

public class LoginSession extends AppCompatActivity {
    private TextView txtsessionname,txtsessionemail;
    private Button btndangxuat;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtin);


        txtsessionemail= findViewById(R.id.idemailsession);
        txtsessionname=findViewById(R.id.idnamesession);
        btndangxuat=findViewById(R.id.btndangxuats);

        Intent intent= getIntent();
        String extraname= intent.getStringExtra("username");
        String extramail= intent.getStringExtra("email");
        //String mname=user.get(sessionManager.NAME);
        //String memail=user.get(sessionManager.EMAIL);
        txtsessionname.setText(extraname);
        txtsessionemail.setText(extramail);


        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginSession.this,MainActivity.class);
                startActivity(intent);
            }
        });




    }


}
