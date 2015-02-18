package com.amartinez.androidtest.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amartinez.androidtest.R;
import com.amartinez.androidtest.model.ContactContent;
import com.amartinez.androidtest.model.Contact;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDetailsFragment extends Fragment {

    public static String CONTACT_ID = "contact_id";

    public ContactDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_details, container, false);

        String contactID = (String) getArguments().get(CONTACT_ID);
        Contact contact = ContactContent.CONTACTS_MAP.get(contactID);

        if (contact != null) {

            ImageView avatarImageView = (ImageView) v.findViewById(R.id.avatar_description);
            String url = "http://api.adorable.io/avatars/128/" +  contact.getEmail() + ".png";
            Picasso.with(getActivity())
                    .load(url)
                    .into(avatarImageView);

            placeText(v, R.id.first_name_details, contact.getFirst_name());
            placeText(v, R.id.surname_details, contact.getSurname());
            placeText(v, R.id.address_details, contact.getAddress());
            placeText(v, R.id.email_details, contact.getEmail());
            placeText(v, R.id.phone_details, contact.getPhone_number());

            SimpleDateFormat dateFormatter, stringFormatter;
            stringFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

            try {
                Date date = stringFormatter.parse(contact.getUpdatedAt());
                placeText(v, R.id.updated_details, dateFormatter.format(date));
            }
            catch (Exception e) {}

            try {
                Date date = stringFormatter.parse(contact.getCreatedAt());
                placeText(v, R.id.added_details, dateFormatter.format(date));
            }
            catch (Exception e) {}
        }

        return v;
    }

    /**
     * Place the text in the text view
     * @param v View where the textview is contained
     * @param textViewID ID of the textView
     * @param text Text to place
     */
    private void placeText(View v, int textViewID, String text) {
        TextView tv = (TextView)v.findViewById(textViewID);

        if (tv != null) {
            tv.setText(text);
        }
    }


}
