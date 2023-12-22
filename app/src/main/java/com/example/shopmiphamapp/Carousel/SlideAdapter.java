package com.example.shopmiphamapp.Carousel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopmiphamapp.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SlideViewHodel> {
    private List<SlideImage> slideItems;
    private ViewPager2 viewPager2;

    public SlideAdapter(List<SlideImage> slideItems, ViewPager2 viewPager2) {
        this.slideItems = slideItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SlideViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideViewHodel(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHodel holder, int position) {
        holder.setImage(slideItems.get(position));
        if (position == slideItems.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return slideItems.size();
    }

    class SlideViewHodel extends RecyclerView.ViewHolder {
        private RoundedImageView imgeView;

        SlideViewHodel(@NonNull View itemView) {
            super(itemView);
            imgeView = itemView.findViewById(R.id.imageSlide);
        }

        void setImage(SlideImage slideItem) {

            imgeView.setImageResource(slideItem.getImage());
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            slideItems.addAll(slideItems);
            notifyDataSetChanged();
        }
    };
}
