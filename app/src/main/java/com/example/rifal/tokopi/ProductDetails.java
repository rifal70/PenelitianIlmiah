package com.example.rifal.tokopi;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import database.Database;
import model.Product;
import model.Order;

public class ProductDetails extends AppCompatActivity implements View.OnClickListener{

    private final static String product_ID="MENU_ID";

    TextView product_name,product_price,product_description,discount;
    ImageView product_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton fab_card;
    ElegantNumberButton number_button;



    FirebaseDatabase database;
    DatabaseReference product;

    String productId;
    Product model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        // init firebase
        database=FirebaseDatabase.getInstance();
        product=database.getReference("Product");
        // get Views
        product_name=findViewById(R.id.product_name_details);
        discount=findViewById(R.id.discount);
        product_price=findViewById(R.id.product_price);
        product_description=findViewById(R.id.product_description);
        product_image=findViewById(R.id.image_product_details);
        fab_card=findViewById(R.id.fab_card);
        fab_card.setOnClickListener(this);
        number_button=findViewById(R.id.number_button);
        collapsingToolbarLayout=findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedAppBar);



        // get menu id from Intent
        if (getIntent()!=null){
            productId=getIntent().getStringExtra(product_ID);
            if (!productId.isEmpty()&&productId!=null){
                getDetailsproduct(productId);

            }
        }
    }

    private void getDetailsproduct(String productId) {
        product.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 model=dataSnapshot.getValue(Product.class);
                Picasso.with(getBaseContext()).load(model.getImage()).into(product_image);
                collapsingToolbarLayout.setTitle(model.getName());
                product_price.setText(model.getPrice());
                product_description.setText(model.getDescription());
                product_name.setText(model.getName());
                discount.setText(model.getDiscount());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static Intent newIntent(Context context, String menuId){
        Intent intent=new Intent(context,ProductDetails.class);
        intent.putExtra(product_ID,menuId);
        return intent;
    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        if (id==R.id.fab_card){
            Order order =new Order(productId,model.getName(),number_button.getNumber(),model.getPrice(),model.getDiscount());
            new Database(ProductDetails.this).addToCart(order);
            Toast.makeText(this, "Ditambahkan ke keranjang", Toast.LENGTH_SHORT).show();
        }
    }
}
