package com.example.rifal.tokopi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import common.Common;
import model.Request;
import viewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Init Database
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView)findViewById(R.id.recycler_order);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadOrders(Common.currentUser.getPhone());
    }

    private void loadOrders(String phone) {

        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class, R.layout.order_layout, OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {


            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txt_id.setText(adapter.getRef(position).getKey());
                viewHolder.txt_status.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txt_address.setText(model.getAddress());
                viewHolder.txt_phone.setText(model.getPhone());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {

        if(status.equals("0"))
            return "Sedang di proses";
        else if (status.equals("1"))
            return "Dibatalkan (jarak Pengiriman diluar jangkauan)";
        else if (status.equals("2"))
            return "Dibatalkan (Stok habis)";
        else if (status.equals("3"))
            return "Dibatalkan (Permintaan pembeli)";
        else if (status.equals("4"))
            return "(Biaya Ongkir Rp.3500)";
        else if (status.equals("5"))
            return "(Biaya Ongkir Rp.6000)";
        else if (status.equals("6"))
            return "(Biaya Ongkir Rp.9000)";
        else if (status.equals("7"))
            return "(Sedang di kirim";
        else if (status.equals("8"))
            return "Sudah tiba";
        else if (status.equals("9"))
            return "Transaksi Selesai";
        else
            return "";
    }

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,OrderStatus.class);
        return intent;
    }

}
