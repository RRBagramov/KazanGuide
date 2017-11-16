package ru.kpfu.kazanguide.util;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.kpfu.kazanguide.model.Guide;
import ru.kpfu.kazanguide.R;


public class CaptionedImageAdapter extends RecyclerView.Adapter<CaptionedImageAdapter.ViewHolder> {

    private List<Guide> guides;
    private Listener listener;
    private Context context;

    public static interface Listener{
        public void onClick(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;

    }
    public CaptionedImageAdapter(List<Guide> guides, Context context) {
        this.guides = guides;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView view) {
            super(view);
            cardView = view;
        }
    }

    @Override
    public CaptionedImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);

        Picasso.with(context)
                .load(guides.get(position).getPhotoLink())
                .into(imageView);

        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
        textView.setText(guides.get(position).getName());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (guides == null)
            return 0;
        return guides.size();
    }


}
