package com.example.appbanhang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appbanhang.R;
import com.example.appbanhang.model.Giohang;
import com.example.appbanhang.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarChitiet;
    ImageView imgChitiet;
    TextView txtten,txtgia,txtmota;
    Spinner spinner;
    Button btndatmua;
    int id=0;
    String TenChitiet="";
    int GiaChitiet=0;
    String HinhanhChitiet="";
    String MotaChitiet="";
    int Idsanpham=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        addViews();
        ActionToolBar();
        GetInformation();
        CatchEventSpiner();
        EventButton();
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.manggiohang.size()>0){
                    int sl=Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exist=false;
                    for(int i=0;i<MainActivity.manggiohang.size();i++){
                        if(MainActivity.manggiohang.get(i).getIdsp()==id){
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp()+sl);
                            if(MainActivity.manggiohang.get(i).getSoluongsp()>=10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(GiaChitiet*MainActivity.manggiohang.get(i).getSoluongsp());
                            exist=true;
                        }
                        }
                    if(exist==false){
                        int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi= soluong*GiaChitiet;
                        MainActivity.manggiohang.add(new Giohang(id,TenChitiet,Giamoi,HinhanhChitiet,soluong));
                    }
                }
                else{
                    int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi= soluong*GiaChitiet;
                    MainActivity.manggiohang.add(new Giohang(id,TenChitiet,Giamoi,HinhanhChitiet,soluong));
                }
                Intent intent =new Intent(getApplicationContext(), com.example.appbanhang.activity.Giohang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpiner() {
       Integer[] soluong= new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        Sanpham sanpham= (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id=sanpham.getID();
        TenChitiet=sanpham.getTensanpham();
        GiaChitiet=sanpham.getGiasanpham();
        MotaChitiet=sanpham.getMotasanpham();
        HinhanhChitiet=sanpham.getHinhanhsanpham();
        Idsanpham=sanpham.getIDsanpham();
        txtten.setText(TenChitiet);
        txtmota.setText(MotaChitiet);
        DecimalFormat deci=new DecimalFormat("###,###,###");
        txtgia.setText("Giá "+deci.format(GiaChitiet)+" Đ");

        Picasso.with(getApplicationContext()).load(HinhanhChitiet)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(imgChitiet);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChitiet);
        //getSupportActionBar().setTitle("Điện Thoại");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addViews() {
        toolbarChitiet=(Toolbar) findViewById(R.id.toolbarchitietsanpham);
        imgChitiet= (ImageView) findViewById(R.id.imageviewchitietsanpham);
        txtten= (TextView) findViewById(R.id.textviewtenchitietsanpham);
        txtgia= (TextView) findViewById(R.id.textviewgiachitietsanpham);
        txtmota= (TextView) findViewById(R.id.textviewmotachitietsanpham);
        spinner= (Spinner) findViewById(R.id.spinner);
        btndatmua= (Button) findViewById(R.id.buttondatmua);
    }
}