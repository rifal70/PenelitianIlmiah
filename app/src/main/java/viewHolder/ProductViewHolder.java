package viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import inter.ItemClickListener;
import com.example.rifal.tokopi.R;

/**
 * Created by mohamed on 11/22/17.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView image_product_item;
    public TextView name_product_item;
    private ItemClickListener itemClickListener;

    public ProductViewHolder(View itemView) {
        super(itemView);
        image_product_item=itemView.findViewById(R.id.image_product_item);
        name_product_item=itemView.findViewById(R.id.name_product_item);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
