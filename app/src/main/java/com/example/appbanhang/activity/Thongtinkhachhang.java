package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                            Log.d("madonhang",madonhang);
                            if(Integer.parseInt(madonhang)>0){
                                RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                                StringRequest request= new StringRequest(Request.Method.POST,Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")){
                                            MainActivity.manggiohang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban Đã Đặt Hàng Thành Công");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Xin Mời Tiếp Tục Chon Và Mua Hàng");
                                        }
                                        else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Giỏ Hàng Đã Bi Lỗi");

                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i=0;i<MainActivity.manggiohang.size();i++){
                                            JSONObject jsonObject1 =new JSONObject();
                                            try {

                                                jsonObject1.put("madonhang",madonhang);
                                                jsonObject1.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                                jsonObject1.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                                jsonObject1.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                                jsonObject1.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluongsp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject1);
                                        }
                                        HashMap<String,String>hashMap=new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }

                                };
                                queue.add(request);
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