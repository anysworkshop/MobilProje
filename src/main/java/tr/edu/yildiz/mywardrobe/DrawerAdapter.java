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

import tr.edu.yildiz.mywardrobe.Drawer;
import tr.edu.yildiz.mywardrobe.R;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private ArrayList<Drawer> mDrawerArrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemDelete(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class DrawerViewHolder extends RecyclerView.ViewHolder{
        //public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public ImageView buttonDeleteDrawer;

        public DrawerViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            //imageView = itemView.findViewById(R.id.imageViewCard);
            textView1 = itemView.findViewById(R.id.textViewCard1);
            textView2 = itemView.findViewById(R.id.textViewCard2);
            buttonDeleteDrawer = itemView.findViewById(R.id.drawer_delete);

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

            buttonDeleteDrawer.setOnClickListener(new View.OnClickListener() {
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

        }
    }

    public DrawerAdapter(ArrayList<Drawer> DrawerArrayList){
        mDrawerArrayList = DrawerArrayList;
    }

    @NonNull
    @Override
    public DrawerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item,parent,false);
        DrawerViewHolder evh = new DrawerViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerViewHolder holder, int position) {
        Drawer currentItem = mDrawerArrayList.get(position);

        //Bitmap bitmap = BitmapFactory.decodeByteArray(currentItem.getByteArrays(),0,currentItem.getByteArrays().length);
        //holder.imageView.setImageBitmap(bitmap);
        holder.textView1.setText(currentItem.getName());
        holder.textView2.setText(currentItem.getName());
    }

    @Override
    public int getItemCount() {
        return mDrawerArrayList.size();
    }



}
