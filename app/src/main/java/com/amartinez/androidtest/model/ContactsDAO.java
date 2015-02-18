package com.amartinez.androidtest.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amartinez on 17/02/15.
 */
public class ContactsDAO extends SQLiteOpenHelper {

    private Context mContext;
    private static ContactsDAO storageHelper;
    private static String DATABASE_NAME = "contacts_db";
    private static int DATABASE_VERSION = 1;
    private static String CONTACTS_TABLE_NAME = "actions_table";
    private static String CONTACT_FIRST_NAME = "contacts_first_name";
    private static String CONTACT_SURNAME = "contacts_surname";
    private static String CONTACT_ADDRESS = "contacts_address";
    private static String CONTACT_EMAIL = "contacts_email";
    private static String CONTACT_PHONE_NUMBER = "contacts_id";
    private static String CONTACT_ID = "phone_number";
    private static String CONTACT_CREATED = "contacts_created";
    private static String CONTACT_UPDATED = "contacts_updated";



    public ContactsDAO(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    /**
     * Get the singleton instance of this class.
     * No need to double-check lock or synchronize as no different threads will access it.
     * @param context
     * @return
     */
    public static ContactsDAO getInstance(Context context) {
        if (storageHelper == null) {
            storageHelper = new ContactsDAO(context.getApplicationContext());
        }

        return storageHelper;
    }

    public void storeContacts(List<Contact> pContacts) {

        for (Contact contact : pContacts) {
            ContentValues cv = new ContentValues();
            cv.put(CONTACT_FIRST_NAME, contact.getFirst_name());
            cv.put(CONTACT_SURNAME, contact.getSurname());
            cv.put(CONTACT_ADDRESS, contact.getAddress());
            cv.put(CONTACT_EMAIL, contact.getEmail());
            cv.put(CONTACT_ID, contact.getId());
            cv.put(CONTACT_CREATED, contact.getCreatedAt());
            cv.put(CONTACT_UPDATED, contact.getUpdatedAt());
            cv.put(CONTACT_PHONE_NUMBER, contact.getPhone_number());

            //Using replace instead of insert we assure that if old info is updated, we update the entry
            getWritableDatabase().replace(CONTACTS_TABLE_NAME, null, cv);
        }
    }

    public List<Contact> getStoredContacts() {
        List<Contact> contactList = new ArrayList<>();

        String[] columns = null; //null for all columns
        String selection = null; //null for all entries

        Cursor c = getWritableDatabase().query(CONTACTS_TABLE_NAME, columns, selection, null, null, null, null, null);

        if (c.getCount() != 0) {

            while(c.moveToNext()) {
                Contact contactToAdd = new Contact();

                contactToAdd.setFirst_name(c.getString(c.getColumnIndex(CONTACT_FIRST_NAME)));
                contactToAdd.setSurname(c.getString(c.getColumnIndex(CONTACT_SURNAME)));
                contactToAdd.setAddress(c.getString(c.getColumnIndex(CONTACT_ADDRESS)));
                contactToAdd.setEmail(c.getString(c.getColumnIndex(CONTACT_EMAIL)));
                contactToAdd.setId(c.getLong(c.getColumnIndex(CONTACT_ID)));
                contactToAdd.setPhone_number(c.getString(c.getColumnIndex(CONTACT_PHONE_NUMBER)));
                contactToAdd.setUpdatedAt(c.getString(c.getColumnIndex(CONTACT_UPDATED)));
                contactToAdd.setCreatedAt(c.getString(c.getColumnIndex(CONTACT_CREATED)));

                contactList.add(contactToAdd);
            }
            c.close();
        }
        return contactList;
    }

    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CONTACTS_TABLE_NAME + " (" + CONTACT_FIRST_NAME + " TEXT, " +
                CONTACT_SURNAME + " TEXT, " +
                CONTACT_ADDRESS + " TEXT, " +
                CONTACT_EMAIL + " TEXT, " +
                CONTACT_PHONE_NUMBER + " TEXT, " +
                CONTACT_ID + " TEXT PRIMARY KEY, " +
                CONTACT_CREATED + " DATE, " +
                CONTACT_UPDATED + " DATE);");
    }

    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.delete(CONTACTS_TABLE_NAME, null, null);
    }
}
