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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    Button btnLogin ,btnRegister;
    EditText edtusername,edtpassword;
    String username="";
    String email="";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sessionManager= new SessionManager(this);

        AnhXa();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,Thongtinkhachhang.class);
                startActivity(intent);
            }
        });
    }

    private void Login() {

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // if(response.trim().equals("2")){
                 //   Toast.makeText(getApplicationContext(),"Login thành công đây là tài khoản người dùng",Toast.LENGTH_LONG).show();
                //}
                //else if(response.trim().equals("1")){
                  //  Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    //startActivity(intent);
                    //CheckConnection.ShowToast_Short(getApplicationContext(),"Đăng nhập thành công.Chào mừng quản lý tới với cửa hàng");

               // }
               // else {
                   // Toast.makeText(getApplicationContext(),"Login that bai",Toast.LENGTH_LONG).show();

                //}

                    try {

                        JSONObject jsonObject= new JSONObject(response);
                        String success=jsonObject.getString("success");
                        JSONArray jsonArray=jsonObject.getJSONArray("login");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object= jsonArray.getJSONObject(i);

                            String username = object.getString("username").trim();
                            String email = object.getString("email").trim();
                            sessionManager.createsesstion(username,email);
                            Intent intent = new Intent(Register.this,MainActivity.class);
                            intent.putExtra("username",username);
                            intent.putExtra("email",email);

                            startActivity(intent);
                            finish();
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Đăng nhập thành công.Chào mừng quản lý tới với cửa hàng"+email);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                params.put("Username",edtusername.getText().toString().trim());
                params.put("Password",edtpassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        btnLogin=(Button) findViewById(R.id.btnlogin);
        btnRegister=(Button) findViewById(R.id.btnregister);
        edtpassword=(EditText) findViewById(R.id.edtpassword);
        edtusername=(EditText) findViewById(R.id.edtusername);
    }
}