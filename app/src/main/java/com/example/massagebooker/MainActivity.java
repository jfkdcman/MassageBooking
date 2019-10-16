package com.example.massagebooker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //massagetypes
    String[] MASSAGETYPELIST = {"Aromatherapy", "Swedish", "Deep Tissue", "Sports", "Shiatsu", "Thai", "Prenatal", "Couples"};

    //Booking Values
    public static BookingDetails bookingDetails;

    BottomNavigationView bottomNavigationView;
    static Button switchButtonId;

    private List<BottomBarFragment> fragments = new ArrayList<>(4);
    public static final String TAG_FRAGMENT_HOME = "tag_frag_home";
    public static final String TAG_FRAGMENT_HISTORY = "tag_frag_history";
    public static final String TAG_FRAGMENT_ANNOUNCEMENTS = "tag_frag_announcements";
    public static final String TAG_FRAGMENT_ACCOUNT = "tag_frag_account";
    public static final String TAG_FRAGMENT_BOOKING = "tag_frag_booking";
    public static final String TAG_FRAGMENT_BOOKING_LOADING = "tag_frag_booking_loading";
    public static final String TAG_FRAGMENT_MASSAGETYPE = "tag_frag_massagetype";

    public static boolean BOOKING_STATUS = false;
    public static String MASSAGE_TYPE = "";
    public static String DATE = "";
    public static String TIME = "";
    public static String LOCATION = "";
    public static int CALL_SWITCH_FROM_FRAG_VALUE = 0;

    private void buildFragmentsList() {
        BottomBarFragment homeFragment = buildFragment("Home", 0);
        BottomBarFragment historyFragment = buildFragment("History", 1);
        BottomBarFragment announcementsFragment = buildFragment("Announcements", 2);
        BottomBarFragment accountFragment = buildFragment("Account", 3);
        BottomBarFragment bookingFragment = buildFragment("Booking", 4);
        BottomBarFragment bookingLoadingFragment = buildFragment("BookingLoading", 5);
        BottomBarFragment massageTypeFragment = buildFragment("MassageType", 6);

        fragments.add(homeFragment);
        fragments.add(historyFragment);
        fragments.add(announcementsFragment);
        fragments.add(accountFragment);
        fragments.add(bookingFragment);
        fragments.add(bookingLoadingFragment);
        fragments.add(massageTypeFragment);
    }
    public void switchFragment(int pos, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_fragmentholder, fragments.get(pos), tag)
                .commit();
    }

    private BottomBarFragment buildFragment(String title, int position) {
        BottomBarFragment fragment = new BottomBarFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BottomBarFragment.ARG_TITLE, title);
        bundle.putInt(BottomBarFragment.ARG_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //switchimg fragments in BottomBarFragment
        switchButtonId = (Button) findViewById(R.id.switchButtonId);
        switchButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (CALL_SWITCH_FROM_FRAG_VALUE) {
                    case 0:
                        switchFragment(CALL_SWITCH_FROM_FRAG_VALUE, TAG_FRAGMENT_HOME);
                        break;
                    case 4:
                        switchFragment(CALL_SWITCH_FROM_FRAG_VALUE, TAG_FRAGMENT_BOOKING);
                        break;
                    case 5:
                        switchFragment(CALL_SWITCH_FROM_FRAG_VALUE, TAG_FRAGMENT_BOOKING_LOADING);
                        break;
                    case 6:
                        switchFragment(CALL_SWITCH_FROM_FRAG_VALUE, TAG_FRAGMENT_MASSAGETYPE);
                        break;

                }
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FrameLayout myRoot;
                View itemView;
                switch (item.getItemId()) {
                    case R.id.bottomNavigationHomeMenuId:

                        switchFragment(0, TAG_FRAGMENT_HOME);
                        //myRoot = findViewById(R.id.frame_screenholder);
                        //itemView = getLayoutInflater().inflate(R.layout.home_fragment, myRoot);
                        //Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.bottomNavigationHistoryMenuId:
                        switchFragment(1, TAG_FRAGMENT_HISTORY);
                        //myRoot = findViewById(R.id.frame_screenholder);
                        //itemView = getLayoutInflater().inflate(R.layout.account_fragment, myRoot);
                        //Toast.makeText(MainActivity.this, "History", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.bottomNavigationAnnouncementMenuId:
                        switchFragment(2, TAG_FRAGMENT_ANNOUNCEMENTS);
                        //myRoot = findViewById(R.id.frame_screenholder);
                        //itemView = getLayoutInflater().inflate(R.layout.announcement_fragment, myRoot);
                        //Toast.makeText(MainActivity.this, "Announcemment", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.bottomNavigationAccountMenuId:
                        switchFragment(3, TAG_FRAGMENT_ACCOUNT);
                        //myRoot = findViewById(R.id.frame_screenholder);
                        //itemView = getLayoutInflater().inflate(R.layout.account_fragment, myRoot);
                        //Toast.makeText(MainActivity.this, "Account", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });


        buildFragmentsList();
        // Set the 0th Fragment to be displayed by default.
        switchFragment(0, TAG_FRAGMENT_HOME);

    }
}
