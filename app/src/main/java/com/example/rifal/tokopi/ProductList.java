package com.example.rifal.tokopi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import inter.ItemClickListener;
import model.Product;
import viewHolder.ProductViewHolder;

public class ProductList extends AppCompatActivity {
    private final static String CATEGORY_ID="CATEGORYID";

    FirebaseDatabase database;
    DatabaseReference product;
    FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter;

    RecyclerView recycler_product_list;
    RecyclerView.LayoutManager layoutManager;

    MaterialSearchBar search_item;
    FirebaseRecyclerAdapter<Product, ProductViewHolder> searchAdapter;
    List<String> suggestion=new ArrayList<>();

    private String categoryId;//id menu yang berisi semua NAMA PRODUK di menu ini


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        // init firebase
        database=FirebaseDatabase.getInstance();
        product=database.getReference("Product");
        // inti recycler view
        recycler_product_list=findViewById(R.id.recycler_product_list);
        recycler_product_list.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_product_list.setLayoutManager(layoutManager);
        //get categoryId from Home Activity
        if (getIntent()!=null){
            categoryId=getIntent().getStringExtra(CATEGORY_ID);
            if (!categoryId.isEmpty() && categoryId!=null){
                // load product list item
                loadproductList(categoryId);
            }
        }

        search_item=findViewById(R.id.search_item);
        search_item.setHint("Masukan Nama Produk");
        loadSuggest(); // load suggestion from firebase
        search_item.setLastSuggestions(suggestion);
        search_item.setCardViewElevation(10);
        search_item.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                List<String> suggest=new ArrayList<>();
                for (String value:suggestion){
                    if (value.toLowerCase().contains(search_item.getText().toString().toLowerCase())){
                        suggest.add(value);
                    }
                }
                search_item.setLastSuggestions(suggest);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        search_item.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                // when search bar is closed restore original adapter
                if(!enabled){
                    recycler_product_list.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

    }

    private void startSearch(String text) {
        // use new adapter
        searchAdapter=new FirebaseRecyclerAdapter<Product, ProductViewHolder>(Product.class,
                R.layout.product_item, ProductViewHolder.class,
                product.orderByChild("name").equalTo(text)) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {
                viewHolder.name_product_item.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.image_product_item);
                final Product localProduct=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // move to productDetails and sent product id
                        startActivity(ProductDetails.newIntent(ProductList.this,searchAdapter.getRef(position).getKey()));
                        //Toast.makeText(ProductList.this, ""+localProduct.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        recycler_product_list.setAdapter(searchAdapter);// set adapter for search result
    }

    private void loadSuggest() {

        Query query=product.orderByChild("menuId").equalTo(categoryId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Product item =data.getValue(Product.class);
                    suggestion.add(item.getName());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadproductList(String categoryId) {
        adapter=new FirebaseRecyclerAdapter<Product, ProductViewHolder>(
                Product.class,R.layout.product_item, ProductViewHolder.class,
                product.orderByChild("menuId").equalTo(categoryId)
        ) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Product model, int position) {
                viewHolder.name_product_item.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.image_product_item);
                final Product localProduct=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // move to productDetails and sent product id
                        startActivity(ProductDetails.newIntent(ProductList.this,adapter.getRef(position).getKey()));
                        //Toast.makeText(ProductList.this, ""+localProductgetName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        // set up adapter
        recycler_product_list.setAdapter(adapter);
    }

    public static Intent newIntent(Context context,String categoryId){
        Intent intent=new Intent(context,ProductList.class);
        intent.putExtra(CATEGORY_ID,categoryId);
        return intent;
    }
}
