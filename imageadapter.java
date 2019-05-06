package com.example.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class imageadapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<image> imageList;

    public imageadapter(Context context, int layout, ArrayList<image> imageList) {
        this.context = context;
        this.layout = layout;
        this.imageList = imageList;
    }

    public class ViewHolder{
        ImageView imageView;

    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);


            holder.imageView = (ImageView) row.findViewById(R.id.img);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        image yo = imageList.get(position);


        byte[] Image = yo.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }

}
