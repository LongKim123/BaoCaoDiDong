package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    EditText edttenkhachhang,edtemail,edtsdt;
    Button btnxacnhan,btntrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinkhachhang);
        AnhXa();
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
               final String ten=edttenkhachhang.getText().toString().trim();
               final String sdt=edtsdt.getText().toString().trim();
               final String email=edtemail.getText().toString().trim();
                if(ten.length()>0&& sdt.length()>0 && email.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest  stringRequest= new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            MainActivity.manggiohang.clear();
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Xin Chúc Mừng Bạn Đã Đặt Hàng Thành Công");
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Xin Mời Tiếp Tục Chon Và Mua Hàng");
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
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);

                }
                else{
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");

                }

            }
        });
    }

    private void AnhXa() {
        edttenkhachhang= (EditText) findViewById(R.id.edittexttenkhachhang);
        edtemail= (EditText) findViewById(R.id.edittextemail);
        edtsdt= (EditText) findViewById(R.id.edittextsdt);
        btntrove= (Button) findViewById(R.id.buttontrove);
        btnxacnhan= (Button) findViewById(R.id.buttonxacnhan);
    }
}