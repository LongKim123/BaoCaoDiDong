package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.QuanlysanphamAdapter;
import com.example.appbanhang.model.Sanpham;
import com.example.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Quanlysanpham extends AppCompatActivity {
    ArrayList<Sanpham> mangquanly;
    QuanlysanphamAdapter quanlysanphamAdapter;
    Toolbar toolbarquanly ;
    ListView lvquanly;
    //215
    String urldelete=Server.xoasanpham;
    int iddt=0;
    int page=1;
    int idssp=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlysanpham);

        addViews();
        GetIdloaisp();
        ActionToolbar();
        GetQuanLySanPham(page);
    }
    private void GetIdloaisp() {
        iddt=getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",iddt+"");
    }
    public void DeleteSanPham(int idsp){
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, urldelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("1")){
                    Toast.makeText(Quanlysanpham.this,"Xóa thành công",Toast.LENGTH_SHORT).show();
                    GetQuanLySanPham(page);
                }
                else {
                    Toast.makeText(Quanlysanpham.this,"Xóa thất bại",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Quanlysanpham.this,"lỗi kết nối",Toast.LENGTH_SHORT).show();


            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("id",String.valueOf(idsp));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void GetQuanLySanPham(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Duongdanquanly+String.valueOf(Page);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID=0;
                String Tenlaptop="";
                Integer Gialaptop=0;
                String Hinhanhlaptop="";
                String Motaslaptop="";
                int IDsplaptop=0;
                if(response !=null && response.length() !=2){
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        mangquanly.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tenlaptop = jsonObject.getString("tensanpham");
                            Hinhanhlaptop = jsonObject.getString("hinhanh");
                            Gialaptop = jsonObject.getInt("giasanpham");
                            IDsplaptop = jsonObject.getInt("idsanpham");
                            Motaslaptop = jsonObject.getString("motasanpham");

                            mangquanly.add(new Sanpham(ID, Tenlaptop, Hinhanhlaptop, Gialaptop, IDsplaptop, Motaslaptop));
                            quanlysanphamAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // mangloaisp.add(3,new Loaisp(0, "Thông tin", "http://kinhtevadubao.vn/uploads/images/news/1515687283_news_10383.jpg"));

                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }


    private void ActionToolbar() {
        setSupportActionBar(toolbarquanly);
        //getSupportActionBar().setTitle("Điện Thoại");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarquanly.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addViews() {
        toolbarquanly=(Toolbar) findViewById(R.id.toolbarquanly);
        lvquanly= (ListView) findViewById(R.id.listviewquanly);
        mangquanly=new ArrayList<>();
        quanlysanphamAdapter=new QuanlysanphamAdapter(Quanlysanpham.this,mangquanly);
        lvquanly.setAdapter(quanlysanphamAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_sanpham,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menuAddSanpham);
        {
            startActivity(new Intent(Quanlysanpham.this,AddSanPham.class));
        }
        return super.onOptionsItemSelected(item);
    }
}