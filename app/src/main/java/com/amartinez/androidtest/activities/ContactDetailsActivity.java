package com.amartinez.androidtest.activities;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.amartinez.androidtest.R;
import com.amartinez.androidtest.fragments.ContactDetailsFragment;


public class ContactDetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        if (savedInstanceState == null) {
            String id = getIntent().getStringExtra(ContactDetailsFragment.CONTACT_ID);

            ContactDetailsFragment fragment = new ContactDetailsFragment();
            Bundle arguments = new Bundle();
            arguments.putString(ContactDetailsFragment.CONTACT_ID, id);
            fragment.setArguments(arguments);

            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }
}
