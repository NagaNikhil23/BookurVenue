package panduchinnu.venubooking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.*;

/**
 * Created by Nikhil on 6/25/2017.
 */

class ImageAdapter extends ArrayAdapter {
    public Context c;
    ArrayList names;
    LayoutInflater inflater;
    ArrayList<byte[]> images;

    public ImageAdapter(Context context, ArrayList arrlis, ArrayList<byte[]> image) {
        super(context,R.layout.listlayout,arrlis);
        this.c=context;
        this.names=arrlis;
        this.images=image;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.listlayout, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView10);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView3);


        txtTitle.setText((String) names.get(position));
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(images.get(position),0,images.get(position).length));

        return rowView;
    }

}
