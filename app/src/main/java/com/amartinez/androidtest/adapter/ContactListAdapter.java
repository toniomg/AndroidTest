package com.amartinez.androidtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amartinez.androidtest.R;
import com.amartinez.androidtest.model.Contact;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amartinez on 17/02/15.
 * ContactList Adapter for the main list of contacts.
 */
public class ContactListAdapter extends BaseAdapter {

    List<Contact> demo = new ArrayList<>();
    Context mContext;

    static class ViewHolder {
        public TextView contactName;
        public TextView contactSurname;
        public ImageView avatar;
    }

    public ContactListAdapter(Context pContext, final List<Contact> pDemo) {
        demo = pDemo;
        mContext = pContext;
    }

    @Override
    public int getCount() {
        return demo.size();
    }

    @Override
    public Object getItem(final int position) {
        return demo.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return ((Contact)(getItem(position))).getId();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.contact_list_item, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.contactName = (TextView)rowView.findViewById(R.id.contact_item_name);
            viewHolder.contactSurname = (TextView)rowView.findViewById(R.id.contact_item_surname);
            viewHolder.avatar = (ImageView)rowView.findViewById(R.id.imageView);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder)rowView.getTag();
        holder.contactName.setText(demo.get(position).getFirst_name());
        holder.contactSurname.setText(demo.get(position).getSurname());

        //Load the image asynchronously
        String url = "http://api.adorable.io/avatars/64/" +  demo.get(position).getEmail() + ".png";
        Picasso.with(mContext)
                .load(url)
                .into(holder.avatar);

        return rowView;
    }
}
