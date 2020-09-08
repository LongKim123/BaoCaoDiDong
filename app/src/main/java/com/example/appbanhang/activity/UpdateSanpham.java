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
import com.example.appbanhang.model.Sanpham;

import java.util.HashMap;
import java.util.Map;

public class UpdateSanpham extends AppCompatActivity {
    EditText suaten,suahinhanh,suagia,suaidsanpham,suamota;
    Button btnsuamon,btnback;
    int idss=0;
    String urlupdate="http://192.168.1.215/server/update.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sanpham);
        Intent intent= getIntent();
        Sanpham sanpham=(Sanpham) intent.getSerializableExtra("sanpham");
        Anhxa();
        idss=sanpham.getID();
        suaten.setText(sanpham.getTensanpham());
        suahinhanh.setText(sanpham.getHinhanhsanpham());
        suagia.setText(sanpham.getGiasanpham()+"");
        suaidsanpham.setText(sanpham.getIDsanpham()+"");
        suamota.setText(sanpham.getMotasanpham());
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnsuamon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten=suaten.getText().toString().trim();
                String hinh=suahinhanh.getText().toString().trim();
                String gia=suagia.getText().toString().trim();
                String idsp=suaidsanpham.getText().toString().trim();
                String motasp=suamota.getText().toString().trim();
                if(ten.matches("")||hinh.matches("")||gia.equals("")||idsp.equals("")||motasp.matches("")){
                    Toast.makeText(UpdateSanpham.this,"Vui lòng nhập cho đầy đủ",Toast.LENGTH_SHORT).show();
                }
                else {
                    Capnhatsanpham(urlupdate);
                }

            }
        });
    }
    private void Capnhatsanpham(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("1")){
                    Toast.makeText(UpdateSanpham.this,"Sửa thành công sản phẩm",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateSanpham.this,Quanlysanpham.class));
                }
                else {
                    Toast.makeText(UpdateSanpham.this,"Sửa sản phẩm thất bại",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("id",String.valueOf(idss));
                params.put("tensanpham",suaten.getText().toString().trim());
                params.put("hinhanh",suahinhanh.getText().toString().trim());
                params.put("giasanpham",suagia.getText().toString().trim());
                params.put("idsanpham",suaidsanpham.getText().toString().trim());
                params.put("motasanpham",suamota.getText().toString().trim());
                return params;

            }
        };
        requestQueue.add(stringRequest);
    }
    private void Anhxa() {
        btnsuamon=(Button)findViewById(R.id.btnsuamon);
        btnback=(Button) findViewById(R.id.buttonback);
        suaten=(EditText) findViewById(R.id.suaTensanpham);
        suahinhanh=(EditText) findViewById(R.id.suahinhanh);
        suagia=(EditText) findViewById(R.id.suagiasanpham);
        suaidsanpham=(EditText) findViewById(R.id.suaidsanpham);
        suamota=(EditText) findViewById(R.id.suamota);







    }
}