package com.example.appbanhang.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanhang.R;
import com.example.appbanhang.adapter.GiohangAdapter;

import java.text.DecimalFormat;

public class Giohang extends AppCompatActivity {
    ListView lvgiohang;
    TextView txtthongbao;
    static TextView txttongtien;
    Button btnthanhToan,btntieptucmua;
    Toolbar toolbargiohang;
     GiohangAdapter gioHangAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionToolbar();
        CheckData();
        EventUltil();
        CactChOnItemListView();
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
                    Intent intent=new Intent(Giohang.this,Thongtinkhachhang.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Giohang.this, "Giỏ Hàng Của Bạn Đang Trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CactChOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int position, long l) {

                final AlertDialog.Builder buidlder=new AlertDialog.Builder(Giohang.this);
                buidlder.setTitle("Xác nhận xóa");
                buidlder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không ?");
                buidlder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if(MainActivity.manggiohang.size()<=0){
                            txtthongbao.setVisibility(View.VISIBLE);

                        }else{
                            MainActivity.manggiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if(MainActivity.manggiohang.size()<=0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }
                            else{
                                txtthongbao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                buidlder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                AlertDialog alertDialog=buidlder.create();
                alertDialog.show();

                return true;
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

    private void CheckData() {
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

    private void Anhxa() {
        lvgiohang= (ListView) findViewById(R.id.listviewgiohang);
        txtthongbao= (TextView) findViewById(R.id.textviewthongbao);
        txttongtien= (TextView) findViewById(R.id.textviewtongtien);
        btnthanhToan= (Button) findViewById(R.id.buttonthanhtoangiohang );
        btntieptucmua= (Button) findViewById(R.id.buttontieptucmuahang);
        toolbargiohang= (Toolbar) findViewById(R.id.toolbargiohang);
        gioHangAdapter=new GiohangAdapter(Giohang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(gioHangAdapter);

    }
}