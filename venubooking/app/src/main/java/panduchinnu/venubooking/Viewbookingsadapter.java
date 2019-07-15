package panduchinnu.venubooking;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Nikhil on 6/28/2017.
 */

class Viewbookingsadapter extends BaseAdapter {
    Context c;
    LayoutInflater inflater;
    ArrayList<String> names,dates,status,bookedfors;

    public Viewbookingsadapter(Context context, ArrayList<String> sname, ArrayList<String> date, ArrayList<String> status, ArrayList<String> bookedfor) {

        this.c=context;
        this.names=sname;
        this.dates=date;
        this.bookedfors=bookedfor;
        this.status=status;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        Bundle args=new Bundle();
        args.putString("bookedfors",bookedfors.get(position));
        args.putString("hallname",names.get(position));

        return args;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        inflater = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        rowView = inflater.inflate(R.layout.bookings, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView15);
        TextView txtStatus = (TextView) rowView.findViewById(R.id.textView17);
        TextView txtBookedname = (TextView) rowView.findViewById(R.id.textView18);
        TextView txtDate = (TextView) rowView.findViewById(R.id.textView19);



        txtTitle.setText((String) names.get(position));
        txtStatus.setText((String)status.get(position));
        txtBookedname.setText((String)bookedfors.get(position));
        txtDate.setText((String)dates.get(position));



        return rowView;
    }
}
