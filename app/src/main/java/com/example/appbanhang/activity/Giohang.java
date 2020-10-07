package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.GiohangAdapter;

import java.text.DecimalFormat;

public class Giohang extends AppCompatActivity {
    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhToan,btntieptucmua,btnxoamon;
    Toolbar toolbargiohang;
     GiohangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        addViews();
        ActionToolbar();
        CheckData();
        EventUltil();
       //CactChOnItemListView();
        //chonxoa();
        eventButton();
    }



    private void eventButton() {

        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Giohang.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnthanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.manggiohang.size()>0){
                    Intent intent=new Intent(Giohang.this,xacnhanthongtin.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Giohang.this, "Giỏ Hàng Của Bạn Đang Trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CactChOnItemListView() {
        btnxoamon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                gioHangAdapter.notifyDataSetChanged();

                return false;
            }
        });

    }



    public static void EventUltil() {
        long tongtien=0;
        for(int i=0;i<MainActivity.manggiohang.size();i++){
            tongtien+=MainActivity.manggiohang.get(i).getGiasp();

        }
        DecimalFormat decimalFomat=new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFomat.format(tongtien)+" Đ");


    }

    public void CheckData() {
        if(MainActivity.manggiohang.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }
        else{
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
      //  getSupportActionBar().setTitle("Giỏ Hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addViews() {
        lvgiohang= (ListView) findViewById(R.id.listviewgiohang);
        txtthongbao= (TextView) findViewById(R.id.textviewthongbao);
        txttongtien= (TextView) findViewById(R.id.textviewtongtien);
        btnthanhToan= (Button) findViewById(R.id.buttonthanhtoangiohang );
        btnxoamon=(Button) findViewById(R.id.btn_xoamon);
        btntieptucmua= (Button) findViewById(R.id.buttontieptucmuahang);
        toolbargiohang= (Toolbar) findViewById(R.id.toolbargiohang);
        gioHangAdapter=new GiohangAdapter(Giohang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(gioHangAdapter);

    }
}