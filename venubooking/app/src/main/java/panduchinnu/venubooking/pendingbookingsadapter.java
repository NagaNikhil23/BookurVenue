package panduchinnu.venubooking;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nikhil on 6/28/2017.
 */

class pendingbookingsadapter extends BaseAdapter{
    Context c;
    LayoutInflater inflater;
    ArrayList<String> names,dates,bookedfors;

    public pendingbookingsadapter(Context context, ArrayList<String> names, ArrayList<String> dates, ArrayList<String> bookedfors) {
        this.c=context;
        this.names=names;
        this.dates=dates;
        this.bookedfors=bookedfors;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Bundle getItem(int position)
    {
        Bundle args=new Bundle();
        args.putString("hallname",names.get(position));
        args.putString("customer",bookedfors.get(position));
        args.putString("date",dates.get(position));
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
        rowView = inflater.inflate(R.layout.pendinglayout, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView23);
        TextView txtBookedname = (TextView) rowView.findViewById(R.id.textView24);
        TextView txtDate = (TextView) rowView.findViewById(R.id.textView25);

        txtTitle.setText((String) names.get(position));
        txtBookedname.setText((String)bookedfors.get(position));
        txtDate.setText((String)dates.get(position));

        return rowView;
    }
}
