package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class AddSanPham extends AppCompatActivity {
    EditText edttensanpham,edthinhanh,edtgiasanpham,edtidsanpham,edtmotasanpham;
    Button btnthem,btnhuy;
    String urlinsert= Server.themsanpham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_san_pham);
        Anhxa();
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten=edttensanpham.getText().toString().trim();
                String hinh=edthinhanh.getText().toString().trim();
                String gia=edtgiasanpham.getText().toString().trim();
                String idsp=edtidsanpham.getText().toString().trim();
                String motasp=edtmotasanpham.getText().toString().trim();

                if(ten.isEmpty()||hinh.isEmpty()||gia.isEmpty()||idsp.isEmpty()||motasp.isEmpty()){
                    Toast.makeText(AddSanPham.this,"Vui lòng nhập đủ",Toast.LENGTH_SHORT).show();
                }
                else {
                    Themsanpham(urlinsert);
                }

            }
        });
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void Themsanpham(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("1")){
                    Toast.makeText(AddSanPham.this,"Thêm thành công sản phẩm",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddSanPham.this,Quanlysanpham.class));
                }
                else {
                    Toast.makeText(AddSanPham.this,"Thêm sản phẩm thất bại",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddSanPham.this,"Xảy ra lỗi",Toast.LENGTH_SHORT).show();
                Log.d("AAA","Lỗi!\n"+error.toString());

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String > params= new HashMap<>();
                params.put("tensanpham",edttensanpham.getText().toString().trim());
                params.put("hinhanh",edthinhanh.getText().toString().trim());
                params.put("giasanpham",edtgiasanpham.getText().toString().trim());
                params.put("idsanpham",edtidsanpham.getText().toString().trim());
                params.put("motasanpham",edtmotasanpham.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Anhxa() {
        btnthem=(Button)findViewById(R.id.buttonthem);
        btnhuy=(Button)findViewById(R.id.buttonhuy);
        edttensanpham=(EditText) findViewById(R.id.editTensanpham);
        edthinhanh=(EditText) findViewById(R.id.edithinhanh);
        edtgiasanpham=(EditText) findViewById(R.id.editgiasanpham);
        edtidsanpham=(EditText) findViewById(R.id.editidsanpham);
        edtmotasanpham=(EditText) findViewById(R.id.editmota);

    }

}