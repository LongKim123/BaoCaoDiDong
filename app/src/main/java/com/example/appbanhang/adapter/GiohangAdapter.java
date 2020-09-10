package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.MainActivity;
import com.example.appbanhang.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    com.example.appbanhang.activity.Giohang context;
    ArrayList<Giohang> arraygiohang;
    ArrayAdapter<Giohang> adaptergiohang;


    public GiohangAdapter(com.example.appbanhang.activity.Giohang context, ArrayList<Giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }


    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHoler{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button  btnvalues,btnxoamon;
        public ImageButton btnminus, btnplus;
        public ListView lvgiohang;
    }


    @Override
    public View getView(final int  i, View view, ViewGroup viewGroup) {
        ViewHoler viewHoler=null;
        if(view==null){
            viewHoler= new ViewHoler();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_giohang,null);
            viewHoler.txttengiohang=(TextView)view.findViewById(R.id.tv_tengiohang);
            viewHoler.txtgiagiohang=(TextView) view.findViewById(R.id.tv_giagiohang);
            viewHoler.imggiohang=(ImageView) view.findViewById(R.id.img_giohang);
            viewHoler.btnminus=(ImageButton) view.findViewById(R.id.btn_minus);
            viewHoler.btnplus= (ImageButton) view.findViewById(R.id.btn_plus);
            viewHoler.btnvalues=(Button) view.findViewById(R.id.btn_values);
            viewHoler.btnxoamon=(Button) view.findViewById(R.id.btn_xoamon);
            viewHoler.lvgiohang=(ListView) view.findViewById(R.id.listviewgiohang) ;


            view.setTag(viewHoler);

        }
        else{
            viewHoler= (ViewHoler) view.getTag();
        }

        Giohang giohang=(Giohang) getItem(i);
        viewHoler.txttengiohang.setText(giohang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHoler.txtgiagiohang.setText(decimalFormat.format(giohang.getGiasp())+" VNĐ");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHoler.imggiohang);
        viewHoler.btnvalues.setText(giohang.getSoluongsp()+ "");
        int sl=Integer.parseInt(viewHoler.btnvalues.getText().toString());
        if(sl>=10){
            viewHoler. btnminus.setVisibility(View.VISIBLE);
            viewHoler.btnplus.setVisibility(View.INVISIBLE);
        }
        else if(sl<1){
            viewHoler.btnminus.setVisibility(View.INVISIBLE);
            viewHoler.btnplus.setVisibility(View.INVISIBLE);
        }
        else if(sl==1){
            viewHoler. btnminus.setVisibility(View.INVISIBLE);
            viewHoler.btnplus.setVisibility(View.VISIBLE);
        }
        else if(sl>=2&&sl<10){
            viewHoler. btnminus.setVisibility(View.VISIBLE);
            viewHoler.btnplus.setVisibility(View.VISIBLE);
        }
        ViewHoler finalViewHoler = viewHoler;
        viewHoler.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int sl1=Integer.parseInt(finalViewHoler.btnvalues.toString());
                int slmoinhat=Integer.parseInt(finalViewHoler.btnvalues.getText().toString())+1;
                int slht= MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht=MainActivity.manggiohang.get(i).getGiasp();

               // finalViewHoler.setText(slmoi+"");
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat=(giaht*slmoinhat)/slht;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
                finalViewHoler.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+" VNĐ");
                com.example.appbanhang.activity.Giohang.EventUltil();

                if(slmoinhat>9){
                    finalViewHoler.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHoler.btnminus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnvalues.setText(String.valueOf(slmoinhat));
                }

                else{
                    finalViewHoler.btnplus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnminus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });
        viewHoler.btnxoamon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoinhat=Integer.parseInt(finalViewHoler.btnvalues.getText().toString())-1;
                int slht= MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht=MainActivity.manggiohang.get(i).getGiasp();

                // finalViewHoler.setText(slmoi+"");
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat=0;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
                finalViewHoler.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+" Đ");
                com.example.appbanhang.activity.Giohang.EventUltil();
                MainActivity.manggiohang.remove(i);
                context.CheckData();





            }
        });
        viewHoler.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int slmoinhat=Integer.parseInt(finalViewHoler.btnvalues.getText().toString())-1;
                int slht= MainActivity.manggiohang.get(i).getSoluongsp();
                long giaht=MainActivity.manggiohang.get(i).getGiasp();

                // finalViewHoler.setText(slmoi+"");
                MainActivity.manggiohang.get(i).setSoluongsp(slmoinhat);
                long giamoinhat=(giaht*slmoinhat)/slht;
                MainActivity.manggiohang.get(i).setGiasp(giamoinhat);
                DecimalFormat decimalFormat =new DecimalFormat("###,###,###");
                finalViewHoler.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+" Đ");
                com.example.appbanhang.activity.Giohang.EventUltil();




                if(slmoinhat<2){
                    finalViewHoler.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHoler.btnplus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnvalues.setText(String.valueOf(slmoinhat));
                }
                else{
                    finalViewHoler.btnplus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnminus.setVisibility(View.VISIBLE);
                    finalViewHoler.btnvalues.setText(String.valueOf(slmoinhat));
                }
            }
        });

        return view;
    }
}
