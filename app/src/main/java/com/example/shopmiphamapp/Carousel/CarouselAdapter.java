package com.example.shopmiphamapp.Carousel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmiphamapp.R;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {
    private List<CarouselItem> mListCarousel;

    public CarouselAdapter(List<CarouselItem> mListCarousel) {
        this.mListCarousel = mListCarousel;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cacousel, parent, false);
        return new CarouselViewHolder(view);
    }

//    Xet du lieu len anh
    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        CarouselItem carouselItem = mListCarousel.get(position);
        if (carouselItem == null) {
            return;
        }
        holder.imgCarousel.setImageResource(carouselItem.getResId());
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

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCarousel = itemView.findViewById(R.id.img_carousel);
        }
    }
}
