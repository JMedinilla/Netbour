package jvm.ncatz.netbour;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import jvm.ncatz.netbour.pck_fragment.FrgQR;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormCommunity;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormDocument;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormEntry;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormIncidence;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormMeeting;
import jvm.ncatz.netbour.pck_fragment.form.FrgFormUser;
import jvm.ncatz.netbour.pck_fragment.list.FrgCommunity;
import jvm.ncatz.netbour.pck_fragment.list.FrgDocument;
import jvm.ncatz.netbour.pck_fragment.list.FrgEntry;
import jvm.ncatz.netbour.pck_fragment.list.FrgIncidence;
import jvm.ncatz.netbour.pck_fragment.list.FrgMeeting;
import jvm.ncatz.netbour.pck_fragment.list.FrgUser;

public class ActivityHome extends AppCompatActivity
        implements FrgQR.IQR, FrgUser.ListUser,
        FrgMeeting.ListMeeting, FrgIncidence.ListIncidence, FrgEntry.ListEntry, FrgDocument.ListDocument,
        FrgCommunity.ListCommunity, FrgFormUser.FormUser, FrgFormMeeting.FormMeeting, FrgFormIncidence.FormIncidence,
        FrgFormEntry.FormEntry, FrgFormDocument.FormDocument, FrgFormCommunity.FormCommunity {

    public static final int FRAGMENT_FORM = 99;
    public static final int FRAGMENT_HOME = 100;
    public static final int FRAGMENT_HELP = 101;
    public static final int FRAGMENT_PROFILE = 102;
    public static final int FRAGMENT_SETTINGS = 103;
    public static final int FRAGMENT_ABOUT = 104;
    public static final int FRAGMENT_LIST_COMMUNITY = 110;
    public static final int FRAGMENT_LIST_DOCUMENT = 111;
    public static final int FRAGMENT_LIST_ENTRY = 112;
    public static final int FRAGMENT_LIST_INCIDENCE = 113;
    public static final int FRAGMENT_LIST_MEETING = 114;
    public static final int FRAGMENT_LIST_USER = 115;
    public static final int DURATION_SHORT = 1;
    public static final int DURATION_LONG = 2;

    private int fragment_opened;

    @BindView(R.id.activity_main_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_navigation)
    NavigationView navigationView;
    @BindView(R.id.activity_main_coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main_action)
    FloatingActionButton actionButton;

    CircleImageView profile_image;
    TextView profile_name;

    @OnClick(R.id.activity_main_action)
    public void actionClick(View view) {
        switch (fragment_opened) {
            case FRAGMENT_LIST_COMMUNITY:
                openFormCommunity();
                fragment_opened = FRAGMENT_FORM;
                break;
            case FRAGMENT_LIST_DOCUMENT:
                openFormDocument();
                fragment_opened = FRAGMENT_FORM;
                break;
            case FRAGMENT_LIST_ENTRY:
                openFormEntry();
                fragment_opened = FRAGMENT_FORM;
                break;
            case FRAGMENT_LIST_INCIDENCE:
                openFormIncidence();
                fragment_opened = FRAGMENT_FORM;
                break;
            case FRAGMENT_LIST_MEETING:
                openFormMeeting();
                fragment_opened = FRAGMENT_FORM;
                break;
            case FRAGMENT_LIST_USER:
                openFormUser();
                fragment_opened = FRAGMENT_FORM;
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean transaction;

                switch (item.getItemId()) {
                    case R.id.groupOptions_Incidences:
                        fragment_opened = FRAGMENT_LIST_INCIDENCE;
                        showIncidents();
                        transaction = true;
                        break;
                    case R.id.groupOptions_Board:
                        fragment_opened = FRAGMENT_LIST_ENTRY;
                        showEntryFirst();
                        transaction = true;
                        break;
                    case R.id.groupOptions_ComBoard:
                        fragment_opened = FRAGMENT_LIST_ENTRY;
                        showEntrySecond();
                        transaction = true;
                        break;
                    case R.id.groupOptions_Documents:
                        fragment_opened = FRAGMENT_LIST_DOCUMENT;
                        showDocuments();
                        transaction = true;
                        break;
                    case R.id.groupOptions_Meetings:
                        fragment_opened = FRAGMENT_LIST_MEETING;
                        showMeetings();
                        transaction = true;
                        break;
                    case R.id.groupOptions_Users:
                        fragment_opened = FRAGMENT_LIST_USER;
                        showUsers();
                        transaction = true;
                        break;
                    case R.id.groupOptions_Communities:
                        fragment_opened = FRAGMENT_LIST_COMMUNITY;
                        showCommunities();
                        transaction = true;
                        break;
                    case R.id.groupOptions_Profile:
                        fragment_opened = FRAGMENT_PROFILE;
                        showProfile();
                        transaction = true;
                        break;
                    case R.id.groupOthers_Settings:
                        fragment_opened = FRAGMENT_SETTINGS;
                        showSettings();
                        transaction = true;
                        break;
                    case R.id.groupOthers_Help:
                        fragment_opened = FRAGMENT_HELP;
                        showHelp();
                        transaction = true;
                        break;
                    case R.id.groupOthers_About:
                        fragment_opened = FRAGMENT_ABOUT;
                        showAbout();
                        transaction = true;
                        break;
                    case R.id.groupClose_Close:
                        closeSesion();
                        transaction = true;
                        break;
                    default:
                        transaction = false;
                        break;
                }
                if (transaction) {
                    item.setChecked(true);
                    ActionBar actionBar = getSupportActionBar();
                    if (actionBar != null) {
                        actionBar.setTitle(item.getTitle());
                    }
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        View header = navigationView.getHeaderView(0);
        if (header != null) {
            profile_image = (CircleImageView) header.findViewById(R.id.header_circle_image);
            profile_name = (TextView) header.findViewById(R.id.header_txtName);
        }

        showHome();
        fragment_opened = FRAGMENT_HOME;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSnackbar(String message, int duration) {
        Snackbar snackbar;
        switch (duration) {
            case DURATION_SHORT:
                snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT);
                break;
            case DURATION_LONG:
                snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
                break;
            default:
                snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE);
                break;
        }
        snackbar.show();
    }

    private void showToast(String message, int duration) {
        Toast toast;
        switch (duration) {
            case DURATION_SHORT:
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                break;
            case DURATION_LONG:
                toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
                break;
            default:
                toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
                break;
        }
        toast.show();
    }

    private void openFormUser() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void openFormMeeting() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void openFormIncidence() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void openFormEntry() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void openFormDocument() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void openFormCommunity() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void showAbout() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void showHelp() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void showSettings() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void showProfile() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void showCommunities() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
    }

    private void showUsers() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
    }

    private void showMeetings() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
    }

    private void showDocuments() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
    }

    private void showEntrySecond() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
    }

    private void showEntryFirst() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
    }

    private void showIncidents() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
    }

    private void closeSesion() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void showHome() {
        actionButton.setVisibility(View.INVISIBLE);
    }
}
