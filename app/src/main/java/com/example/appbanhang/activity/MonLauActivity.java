package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.appbanhang.adapter.MonLauAdapter;
import com.example.appbanhang.model.Sanpham;
import com.example.appbanhang.ultil.CheckConnection;
import com.example.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonLauActivity extends AppCompatActivity {
        Toolbar toolbarLau;
        ListView lvLau;
        MonLauAdapter monLauAdapter;
        ArrayList<Sanpham> mangLau;
        int idLau=0;
        int page=1;
        int ID=0;
        String Tensanpham="";
        Integer Giasanpham=0;
        String Hinhanhsanpham="";
        String Motasanpham="";
        int IDsanpham=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monlau);
        addViews();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
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
        lvLau.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangLau.get(i));
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
                if(response !=null && response.length() !=2){
                    try {
                        JSONArray jsonArray= new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensanpham");
                            Hinhanhsanpham = jsonObject.getString("hinhanh");
                            Giasanpham = jsonObject.getInt("giasanpham");
                            IDsanpham = jsonObject.getInt("idsanpham");
                            Motasanpham = jsonObject.getString("motasanpham");

                            mangLau.add(new Sanpham(ID, Tensanpham, Hinhanhsanpham, Giasanpham, IDsanpham, Motasanpham));
                            monLauAdapter.notifyDataSetChanged();
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
                param.put("idsanpham", String.valueOf(idLau));
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarLau);
        //getSupportActionBar().setTitle("Điện Thoại");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLau.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void GetIdloaisp() {
        idLau=getIntent().getIntExtra("idloaisanpham",-1);
        Log.d("giatriloaisanpham",idLau+"");
    }

    private void addViews() {
        toolbarLau=(Toolbar) findViewById(R.id.toolbardienthoai);
        lvLau= (ListView) findViewById(R.id.lvLau);
        mangLau=new ArrayList<>();
        monLauAdapter =new MonLauAdapter(MonLauActivity.this,mangLau);
        lvLau.setAdapter(monLauAdapter);
        //mangdt.add(0,new Sanpham(5,"http://kinhtevadubao.vn/uploads/images/news/1515687283_news_10383.jpg","Iphone 6",20000000,2,"san pham rat la tot"));

    }
}