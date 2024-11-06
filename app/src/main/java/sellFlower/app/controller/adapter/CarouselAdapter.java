package sellFlower.app.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import sellFlower.app.R;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {
    private Context context;
    private List<Integer> carouselItems;

    public CarouselAdapter(Context context, List<Integer> carouselItems) {
        this.context = context;
        this.carouselItems = carouselItems;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_carousel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        holder.imageView.setImageResource(carouselItems.get(position));
    }

    @Override
    public int getItemCount() {
        return carouselItems.size();
    }

    static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.carousel_image);
        }
    }
}