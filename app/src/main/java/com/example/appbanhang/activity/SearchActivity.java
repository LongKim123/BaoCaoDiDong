package com.example.appbanhang.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhang.R;
import com.example.appbanhang.adapter.SanphamAdapter;
import com.example.appbanhang.model.Sanpham;
import com.example.appbanhang.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerViewmanhinhchinh;
    ListView listViewmanhinhchinh;
    ImageButton btnsearch;
    EditText edtsearch;
    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Anhxa();
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=edtsearch.getText().toString();
                SearchSP();
            }
        });


    }
    private void SearchSP() {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, Server.timkiemsanpham, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {




                int ID=0;
                String TenDAN="";
                Integer GiaDAN=0;
                String imgDAN="";
                String MotaDAN="";
                int IDspDAN=0;
                if(response !=null && response.length() !=2) {
                    try {
                        mangsanpham.clear();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            TenDAN = jsonObject.getString("tensanpham");
                            imgDAN = jsonObject.getString("hinhanh");
                            GiaDAN = jsonObject.getInt("giasanpham");
                            IDspDAN = jsonObject.getInt("idsanpham");
                            MotaDAN = jsonObject.getString("motasanpham");

                            mangsanpham.add(new Sanpham(ID, TenDAN, imgDAN, GiaDAN, IDspDAN, MotaDAN));
                            sanphamAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(SearchActivity.this,"Không có thông tin như yêu cầu",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(SearchActivity.this,"Không có thông tin như yêu cầu",Toast.LENGTH_SHORT).show();


                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this,"lỗi kết nối",Toast.LENGTH_SHORT).show();


            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("tensanpham",edtsearch.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void Anhxa() {
        recyclerViewmanhinhchinh=(RecyclerView) findViewById(R.id.recyclerviewsearch);
        listViewmanhinhchinh=(ListView) findViewById(R.id.listviewmanhinhchinhsearch);
        btnsearch=findViewById(R.id.buttontimkiem);
        edtsearch=findViewById(R.id.edittimkiem);
        mangsanpham= new ArrayList<>();
        sanphamAdapter= new SanphamAdapter(mangsanpham,getApplicationContext());
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),1));
        recyclerViewmanhinhchinh.setAdapter(sanphamAdapter);

    }
}