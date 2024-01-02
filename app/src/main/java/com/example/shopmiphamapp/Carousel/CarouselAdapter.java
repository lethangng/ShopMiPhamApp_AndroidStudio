package com.example.shopmiphamapp.Carousel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmiphamapp.Database.Carousel.Carousel;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {
    private Context mContext;
    private List<CarouselItem> mListCarousel;

    public CarouselAdapter(Context context) {
        this.mContext = context;
    }
    public void setData(List<CarouselItem> carousels) {
        this.mListCarousel = carousels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cacousel, parent, false);
        return new CarouselViewHolder(view);
    }

    // Xet du lieu len anh
    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        CarouselItem carouselItem = mListCarousel.get(position);
        if (carouselItem == null) {
            return;
        }

        Helper.loadImage(carouselItem.getResId(), holder.imgCarousel, holder.progressBar);

    }

    @Override
    public int getItemCount() {
        if (mListCarousel != null) {
            return mListCarousel.size();
        }
        return 0;
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCarousel;
        private ProgressBar progressBar;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCarousel = itemView.findViewById(R.id.img_carousel);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
