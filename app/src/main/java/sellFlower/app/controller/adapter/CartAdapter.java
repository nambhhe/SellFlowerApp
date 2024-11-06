package sellFlower.app.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import sellFlower.app.R;
import sellFlower.app.model.CartItem;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItems;
    private Context context;
    private OnCartItemUpdateListener onCartItemUpdateListener;
    private OnCartItemRemoveListener onCartItemRemoveListener;

    public interface OnCartItemUpdateListener {
        void onCartItemUpdate(CartItem cartItem);
    }

    public interface OnCartItemRemoveListener {
        void onCartItemRemove(CartItem cartItem);
    }

    public CartAdapter(List<CartItem> cartItems, OnCartItemUpdateListener updateListener, OnCartItemRemoveListener removeListener) {
        this.cartItems = cartItems;
        this.onCartItemUpdateListener = updateListener;
        this.onCartItemRemoveListener = removeListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        holder.flowerNameTextView.setText(cartItem.getFlowerName());
        holder.priceTextView.setText(String.format("$%.2f", cartItem.getPrice()));
        holder.quantityTextView.setText(String.valueOf(cartItem.getQuantity()));

        // Load image using Glide
        Glide.with(context)
                .load(cartItem.getImageUrl())
                .placeholder(R.drawable.loading_placeholder) // Placeholder image
                .error(R.drawable.error_placeholder) // Error image
                .into(holder.flowerImageView);

        holder.increaseButton.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            holder.quantityTextView.setText(String.valueOf(cartItem.getQuantity()));
            onCartItemUpdateListener.onCartItemUpdate(cartItem);
        });

        holder.decreaseButton.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                holder.quantityTextView.setText(String.valueOf(cartItem.getQuantity()));
                onCartItemUpdateListener.onCartItemUpdate(cartItem);
            }
        });

        holder.removeButton.setOnClickListener(v -> {
            onCartItemRemoveListener.onCartItemRemove(cartItem);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateItems(List<CartItem> newCartItems) {
        this.cartItems = newCartItems;
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView flowerNameTextView;
        TextView priceTextView;
        TextView quantityTextView;
        ImageView flowerImageView;
        Button increaseButton;
        Button decreaseButton;
        Button removeButton;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            flowerNameTextView = itemView.findViewById(R.id.flowerNameTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            quantityTextView = itemView.findViewById(R.id.quantityTextView);
            flowerImageView = itemView.findViewById(R.id.flowerImageView);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}