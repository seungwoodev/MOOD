package com.example.project1;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {
    //creating variables for our array list, recycler view progress bar and adapter.
    private ArrayList<PhoneBook> contactsModalArrayList;
    private RecyclerView contactRV;
    private ContactRVAdapter contactRVAdapter;
    private ProgressBar loadingPB;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //on below line we are initializing our variables.
        contactsModalArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_1, container, false);
        contactRV = view.findViewById(R.id.idRVContacts);
        //on below line we are setting layout mnager.
        contactRV.setLayoutManager(new LinearLayoutManager(getContext()));
        loadingPB = view.findViewById(R.id.idPBLoading);

        //calling a method to request permissions.
        //requestPermissions();
        getContacts();
        //adding on click listener for our fab.
        FloatingActionButton addNewContactFAB = view.findViewById(R.id.idFABadd);
        addNewContactFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //opening a new fragment on below line.
                Intent intent = new Intent(getActivity(), Fragment1_add.class);
                startActivity(intent);

            }
        });
        contactRVAdapter = new ContactRVAdapter(contactsModalArrayList);
        //on below line we are setting adapter to our recycler view.
        contactRV.setAdapter(contactRVAdapter);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void onCreateOptionsMenu(Menu menu) {
        //in this on create options menu we are calling a menu inflater and inflating our menu file.
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        //on below line we are getting our menu item as search view item
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        //on below line we are creating a variable for our search view.
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        //on below line we are setting on query text listner for our search view.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //on query submit we are clearing the focus forour search view.
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //on changing the text in our search view we are calling a filter method to filter our array list.
                filter(newText.toLowerCase());
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void filter(String text) {
        //in this method we are filtering our array list.
        //on below line we are creating a new filtered array list.
        ArrayList<PhoneBook> filteredlist = new ArrayList<>();
        //on below line we are running a loop for checking if the item is present in array list.
        for (PhoneBook item : contactsModalArrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                //on below line we are adding item to our filtered array list.
                filteredlist.add(item);
            }
        }
        //on below line we are checking if the filtered list is empty or not.
        if (filteredlist.isEmpty()) {
            Toast.makeText(getActivity(), "No Contact Found", Toast.LENGTH_SHORT).show();
        } else {
            //passing this filtered list to our adapter with filter list method.
            contactRVAdapter.filterList(filteredlist);
        }
    }

    private void requestPermissions() {
        // below line is use to request
        // permission in the current activity.
        Dexter.withContext(getContext())
                // below line is use to request the number of
                // permissions which are required in our app.
                .withPermissions(Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS, Manifest.permission.WRITE_CONTACTS)
                // after adding permissions we are
                // calling an with listener method.
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        // this method is called when all permissions are granted
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            Log.d("Permission ","넹");
                            // do you work now
                            getContacts();
                            Toast.makeText(getActivity(), "All the permissions are granted..", Toast.LENGTH_SHORT).show();
                        }
                        // check for permanent denial of any permission
//                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
//                            // permission is denied permanently,
//                            // we will show user a dialog message.
//                            showSettingsDialog();
//                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        // this method is called when user grants some
                        // permission and denies some of them.
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
            // this method is use to handle error
            // in runtime permissions
            @Override
            public void onError(DexterError error) {
                // we are displaying a toast message for error message.
                Toast.makeText(getActivity().getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
            }
        })
                // below line is use to run the permissions
                // on same thread and to check the permissions
                .onSameThread().check();
    }

    // below is the shoe setting dialog
    // method which is use to display a
    // dialogue message.
//    private void showSettingsDialog() {
//        // we are displaying an alert dialog for permissions
//        // we are displaying an alert dialog for permissions
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//        // below line is the title
//        // for our alert dialog.
//        builder.setTitle("Need Permissions");
//
//        // below line is our message for our dialog
//        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
//        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // this method is called on click on positive
//                // button and on clicking shit button we
//                // are redirecting our user from our app to the
//                // settings page of our app.
//                dialog.cancel();
//                // below is the intent from which we
//                // are redirecting our user.
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
//                intent.setData(uri);
//                startActivityForResult(intent, 101);
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // this method is called when
//                // user click on negative button.
//                dialog.cancel();
//            }
//        });
//        // below line is used
//        // to display our dialog
//        builder.show();
//    }

    private void getContacts() {
        //this method is use to read contact from users device.
        //on below line we are creating a string variables for our contact id and display name.
        String contactId = "";
        String displayName = "";
        //on below line we are calling our content resolver for getting contacts
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        //on blow line we are checking the count for our cursor.
        if (cursor.getCount() > 0) {
            //if the count is greater thatn 0 then we are running a loop to move our cursor to next.
            while (cursor.moveToNext()) {
                //on below line we are getting the phone number.
                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    //we are checking if the has phine number is >0
                    //on below line we are getting our contact id and user name for that contact
                    contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    //on below line we are calling a content resolver and making a query
                    Cursor phoneCursor = getActivity().getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{contactId},
                            null);
                    //on below line we are moving our cursor to next position.
                    if (phoneCursor.moveToNext()) {
                        //on below line we are getting the phone number for our users and then adding the name along with phone number in array list.
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if(phoneNumber.startsWith("01")) {
                            contactsModalArrayList.add(new PhoneBook(displayName, phoneNumber));
                        }
                    }
                    //on below line we are closing our phone cursor.
                    phoneCursor.close();
                }
            }
        }
        //on below line we are closing our cursor.
        cursor.close();
        //on below line we are hiding our progress bar and notifying our adapter class.
        loadingPB.setVisibility(View.GONE);
        //SimpleTextAdapter.notifyDataSetChanged();
    }

}