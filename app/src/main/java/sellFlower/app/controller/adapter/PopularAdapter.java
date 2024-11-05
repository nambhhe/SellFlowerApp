package sellFlower.app.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.ArrayList;

import sellFlower.app.R;
import sellFlower.app.model.Flower;
import sellFlower.app.databinding.ViewholderPopularListBinding;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.Viewholder> {

    ArrayList<Flower> itemList;
    Context context;
    ViewholderPopularListBinding binding;

    public PopularAdapter(ArrayList<Flower> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public PopularAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = ViewholderPopularListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.Viewholder holder, int position) {
        binding.titleTextView.setText(itemList.get(position).getName());
        binding.feeTextView.setText("$" + itemList.get(position).getPrice());
        binding.ratingTextView.setText("" + itemList.get(position).getRating());

        // Load image using Glide
        Glide.with(context)
                .load(itemList.get(position).getImageUrl())
                .placeholder(R.drawable.loading_placeholder) // Add a placeholder image
                .error(R.drawable.error_placeholder) // Add an error image
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(new GranularRoundedCorners(30, 30, 30, 30))
                .into(binding.productImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle click
            }
        });
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        public Viewholder(ViewholderPopularListBinding binding) {
            super(binding.getRoot());
        }
    }
}
