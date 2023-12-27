package com.example.shopmiphamapp.Carousel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopmiphamapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
//        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(carouselItem.getResId())
                .placeholder(R.drawable.layout_none) // Ảnh placeholder hiển thị trong quá trình tải
                .error(R.drawable.layout_none) // Ảnh hiển thị khi có lỗi xảy ra
                .into(holder.imgCarousel, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Quá trình tải ảnh thành công, ẩn ProgressBar và hiển thị ImageView
                        holder.progressBar.setVisibility(View.GONE);
                        holder.imgCarousel.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
                        holder.progressBar.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                });
//        holder.imgCarousel.setImageResource(carouselItem.getResId());
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
