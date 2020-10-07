package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.ultil.CheckConnection;
import com.example.appbanhang.ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class Thongtinkhachhang extends AppCompatActivity {
    EditText edtusername,edtemail,edtsdt,edtpassword,edtdiachi,edtconfimpass;
    Button btnxacnhan,btntrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        addViews();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext()))
        {
            EvenButton();
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }
    }
    private void EvenButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final String ten=edtusername.getText().toString().trim();
               final String c_password=edtconfimpass.getText().toString().trim();
               final String sdt=edtsdt.getText().toString().trim();
               final String email=edtemail.getText().toString().trim();
               final String password=edtpassword.getText().toString().trim();
                final String diachi=edtdiachi.getText().toString().trim();
                if(ten.length()>0&& sdt.length()>0 && email.length()>0 && diachi.length()>0 && c_password.length()>0&& password.equals(c_password)){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest  stringRequest= new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            if(madonhang.trim().equals("1")) {


                                CheckConnection.ShowToast_Short(getApplicationContext(), "Đăng ký tài khoản thành công");
                                Intent intent = new Intent(getApplicationContext(), Register.class);
                                startActivity(intent);
                                CheckConnection.ShowToast_Short(getApplicationContext(), "Mời đăng nhập");
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Login that bai",Toast.LENGTH_LONG).show();

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            CheckConnection.ShowToast_Short(getApplicationContext(),error.toString());
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String ,String> hashMap= new HashMap<String,String>();
                            hashMap.put("Username",ten);
                            hashMap.put("Password",password);
                            hashMap.put("diachi",diachi);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);

                }
                else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra thông tin");

                }

            }
        });
    }

    private void addViews() {
        edtusername= (EditText) findViewById(R.id.edittextusername);
        edtemail= (EditText) findViewById(R.id.edittextemail);
        edtsdt= (EditText) findViewById(R.id.editsdt);
        edtdiachi= (EditText) findViewById(R.id.editdiachi);
        edtpassword= (EditText) findViewById(R.id.edittextpassword);
        btntrove= (Button) findViewById(R.id.buttontrove);
        btnxacnhan= (Button) findViewById(R.id.buttonxacnhan);
        edtconfimpass=(EditText) findViewById(R.id.c_password);

    }
}