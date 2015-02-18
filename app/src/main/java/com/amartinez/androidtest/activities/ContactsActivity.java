package com.amartinez.androidtest.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.amartinez.androidtest.ApiService;
import com.amartinez.androidtest.fragments.ContactDetailsFragment;
import com.amartinez.androidtest.model.ContactContent;
import com.amartinez.androidtest.model.ContactsDAO;
import com.amartinez.androidtest.R;
import com.amartinez.androidtest.fragments.ContactsListFragment;
import com.amartinez.androidtest.model.Contact;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class ContactsActivity extends ActionBarActivity implements ContactsListFragment.OnFragmentInteractionListener{

    private boolean twoPanes = false;
    private static String CONTACTS_URL = "http://fast-gorge.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        if (findViewById(R.id.details_container) != null) {
            twoPanes = true;
        }

        if (savedInstanceState == null) {
            final ContactsListFragment contactsListFragment = new ContactsListFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.main_container, contactsListFragment)
                    .commit();

            //Only download if new activity. If recreated activity, the contacts will be loaded already
            loadContactList(contactsListFragment);
        }
    }

    /**
     * Load the contact list and populate the fragment with it. The database is always updated when loading. In case of failure, the database will be read.
     * @param pContactsListFragment
     */
    private void loadContactList(final ContactsListFragment pContactsListFragment) {

        final Context activityContext = this;
        Gson gson = new GsonBuilder().create();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(CONTACTS_URL).setConverter(new GsonConverter(gson)).build();
        ApiService myApiService = restAdapter.create(ApiService.class);
        myApiService.getContacts(new Callback<List<Contact>>() {
            @Override
            public void success(final List<Contact> t, final Response response) {
                //Save in database
                ContactsDAO.getInstance(activityContext).storeContacts(t);
                loadContactsInFragment(t);
            }

            @Override
            public void failure(final RetrofitError error) {

                Toast.makeText(activityContext, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                //Try to reload from database in case is offline
                List<Contact> contacts = ContactsDAO.getInstance(activityContext).getStoredContacts();
                if (contacts != null && contacts.size() > 0)
                    loadContactsInFragment(contacts);
            }

            private void loadContactsInFragment(final List<Contact> pContacts) {
                //Add them to the map
                for (Contact c : pContacts) {
                    ContactContent.CONTACTS_MAP.put(Long.toString(c.getId()), c);
                }
                pContactsListFragment.reloadData();
            }
        });
    }

    @Override
    public void onContactClicked(long id) {

        if (twoPanes) {
            ContactDetailsFragment fragment = new ContactDetailsFragment();
            Bundle arguments = new Bundle();
            arguments.putString(ContactDetailsFragment.CONTACT_ID, Long.toString(id));
            fragment.setArguments(arguments);

            getFragmentManager().beginTransaction()
                    .replace(R.id.details_container, fragment)
                    .commit();
        }
        else {
            Intent i = new Intent(this, ContactDetailsActivity.class);
            i.putExtra(ContactDetailsFragment.CONTACT_ID, Long.toString(id));
            startActivity(i);
        }
    }
}
