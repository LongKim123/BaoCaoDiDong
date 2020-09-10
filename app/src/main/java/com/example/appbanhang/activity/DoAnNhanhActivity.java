package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import com.example.appbanhang.adapter.DoAnNhanhAdapter;
import com.example.appbanhang.model.Sanpham;
import com.example.appbanhang.ultil.CheckConnection;
import com.example.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoAnNhanhActivity extends AppCompatActivity {

    Toolbar toolbarDAN;
    ListView lvDAN;
    DoAnNhanhAdapter doAnNhanhAdapter;
    ArrayList<Sanpham> mangDAN;
    int idDAN=0;
    int page=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doannhanh);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            addViews();
            GetIdloaisp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();

        }
        else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent= new Intent(getApplicationContext(), com.example.appbanhang.activity.Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void LoadMoreData() {
        lvDAN.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangDAN.get(i));
                startActivity(intent);
            }
        });
    }
    private void GetData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Duongdansanpham+String.valueOf(Page);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int ID=0;
                String TenDAN="";
                Integer GiaDAN=0;
                String imgDAN="";
                String MotaDAN="";
                int IDspDAN=0;
                if(response !=null && response.length() !=2){
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            TenDAN = jsonObject.getString("tensanpham");
                            imgDAN = jsonObject.getString("hinhanh");
                            GiaDAN = jsonObject.getInt("giasanpham");
                            IDspDAN = jsonObject.getInt("idsanpham");
                            MotaDAN = jsonObject.getString("motasanpham");

                            mangDAN.add(new Sanpham(ID, TenDAN, imgDAN, GiaDAN, IDspDAN, MotaDAN));
                            doAnNhanhAdapter.notifyDataSetChanged();
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
                HashMap<String,String> param=new HashMap<>();
                param.put("idsanpham", String.valueOf(idDAN));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }


    private void addViews() {
        toolbarDAN=(Toolbar) findViewById(R.id.toolbarlaptop);
        lvDAN= (ListView) findViewById(R.id.lvDAN);
        mangDAN=new ArrayList<>();
        doAnNhanhAdapter =new DoAnNhanhAdapter(getApplicationContext(),mangDAN);
        lvDAN.setAdapter(doAnNhanhAdapter);
    }
    private void GetIdloaisp() {
        idDAN=getIntent().getIntExtra("idloaisanpham",-1);

    }
    private void ActionToolbar() {
        setSupportActionBar(toolbarDAN);
        //getSupportActionBar().setTitle("Điện Thoại");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDAN.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}