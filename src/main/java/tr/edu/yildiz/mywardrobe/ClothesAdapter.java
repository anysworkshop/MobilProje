package tr.edu.yildiz.mywardrobe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tr.edu.yildiz.mywardrobe.Clothes;
import tr.edu.yildiz.mywardrobe.R;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ClothesViewHolder> {

    private ArrayList<Clothes> mClothesArrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemDelete(int position);
        void onItemUpdate(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ClothesViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewClothes;
        public TextView textTypeClothes;
        public TextView textColorClothes;
        public TextView textPatternClothes;
        public TextView textDateClothes;
        public TextView textPriceClothes;
        public ImageView buttonDeleteClothes;
        public ImageView buttonUpdateClothes;

        public ClothesViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            imageViewClothes = itemView.findViewById(R.id.imageViewCardClothes);
            textTypeClothes = itemView.findViewById(R.id.type_clothes);
            textColorClothes = itemView.findViewById(R.id.color_clothes);
            textPatternClothes = itemView.findViewById(R.id.pattern_clothes);
            textDateClothes = itemView.findViewById(R.id.date_clothes);
            textPriceClothes = itemView.findViewById(R.id.price_clothes);
            buttonDeleteClothes = itemView.findViewById(R.id.clothes_delete);
            buttonUpdateClothes = itemView.findViewById(R.id.clothes_edit);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            buttonDeleteClothes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemDelete(position);
                        }
                    }
                }
            });

            buttonUpdateClothes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemUpdate(position);
                        }
                    }
                }
            });

        }
    }

    public ClothesAdapter(ArrayList<Clothes> ClothesArrayList){
        mClothesArrayList = ClothesArrayList;
    }

    @NonNull
    @Override
    public ClothesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothes_item,parent,false);
        ClothesViewHolder evh = new ClothesViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClothesViewHolder holder, int position) {
        Clothes currentItem = mClothesArrayList.get(position);

        Bitmap bitmap = BitmapFactory.decodeByteArray(currentItem.getByteArrays(),0,currentItem.getByteArrays().length);
        holder.imageViewClothes.setImageBitmap(bitmap);
        holder.textTypeClothes.setText(currentItem.getType());
        holder.textColorClothes.setText(currentItem.getColor());
        holder.textPatternClothes.setText(currentItem.getPattern());
        holder.textDateClothes.setText(currentItem.getDate());
        holder.textPriceClothes.setText( "$"+ String.valueOf(currentItem.getPrice()));
    }

    @Override
    public int getItemCount() {
        return mClothesArrayList.size();
    }



}
