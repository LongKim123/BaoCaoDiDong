package com.example.appbanhang.activity;

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
import com.example.appbanhang.ultil.Server;

import java.util.HashMap;
import java.util.Map;

public class xacnhanthongtin extends AppCompatActivity {
    Button btnxacnhanmua;
    EditText editusername,edtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xacnhanthongtin);
        Anhxa();
        btnxacnhanmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Xacnhanmua();
            }
        });

    }

    private void Xacnhanmua() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("1")){
                    Toast.makeText(getApplicationContext(),"Xác nhận mua hàng",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Login that bai",Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error:",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params=new HashMap<>();
                params.put("Username",editusername.getText().toString().trim());
                params.put("Password",edtpassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


    private void Anhxa() {
        btnxacnhanmua=(Button) findViewById(R.id.btnxacnhanlogin);
        edtpassword=(EditText)findViewById(R.id.edtxacnhanpassword);
        editusername=(EditText)findViewById(R.id.edtxacnhanusername);

    }
}