package jvm.ncatz.netbour;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
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
import jvm.ncatz.netbour.pck_fragment.FrgHelp;
import jvm.ncatz.netbour.pck_fragment.FrgHome;
import jvm.ncatz.netbour.pck_fragment.FrgProfile;
import jvm.ncatz.netbour.pck_fragment.FrgQR;
import jvm.ncatz.netbour.pck_fragment.FrgSettings;
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
import jvm.ncatz.netbour.pck_interface.FrgBack;
import jvm.ncatz.netbour.pck_pojo.PoCommunity;
import jvm.ncatz.netbour.pck_pojo.PoDocument;
import jvm.ncatz.netbour.pck_pojo.PoEntry;
import jvm.ncatz.netbour.pck_pojo.PoIncidence;
import jvm.ncatz.netbour.pck_pojo.PoMeeting;
import jvm.ncatz.netbour.pck_pojo.PoUser;

public class ActivityHome extends AppCompatActivity implements FrgQR.IQR, FrgUser.ListUser, FrgBack,
        FrgMeeting.ListMeeting, FrgIncidence.ListIncidence, FrgEntry.ListEntry, FrgDocument.ListDocument,
        FrgCommunity.ListCommunity, FrgFormUser.FormUser, FrgFormMeeting.FormMeeting, FrgFormIncidence.FormIncidence,
        FrgFormEntry.FormEntry, FrgFormDocument.FormDocument, FrgFormCommunity.FormCommunity {

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
                openFormCommunity(null);
                break;
            case FRAGMENT_LIST_DOCUMENT:
                openFormDocument(null);
                break;
            case FRAGMENT_LIST_ENTRY:
                openFormEntry(null);
                break;
            case FRAGMENT_LIST_INCIDENCE:
                openFormIncidence(null);
                break;
            case FRAGMENT_LIST_MEETING:
                openFormMeeting(null);
                break;
            case FRAGMENT_LIST_USER:
                openFormUser(null);
                break;
        }
    }

    private FrgCommunity frgCommunity;
    private FrgDocument frgDocument;
    private FrgEntry frgEntry;
    private FrgIncidence frgIncidence;
    private FrgMeeting frgMeeting;
    private FrgUser frgUser;
    private FrgHome frgHome;
    private FrgProfile frgProfile;
    private FrgSettings frgSettings;
    private FrgHelp frgHelp;

    private FragmentTransaction transaction;

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

        frgHome = new FrgHome();
        frgProfile = new FrgProfile();
        frgSettings = new FrgSettings();
        frgHelp = new FrgHelp();
        frgCommunity = new FrgCommunity();
        frgDocument = new FrgDocument();
        frgIncidence = new FrgIncidence();
        frgMeeting = new FrgMeeting();
        frgUser = new FrgUser();

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

    @Override
    public void backFromForm() {
        actionButton.setVisibility(View.VISIBLE);
    }

    private void openFormCommunity(PoCommunity community) {
        FrgFormCommunity frgFormCommunity = new FrgFormCommunity();
        actionButton.setVisibility(View.INVISIBLE);
        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("communityForm", community);
        frgFormCommunity.setArguments(bundle);
        transaction.replace(R.id.activity_main_frame, frgFormCommunity, "frgFormCommunity");
        transaction.addToBackStack("frgFormCommunity");
        transaction.commit();
    }

    private void openFormUser(PoUser user) {
        FrgFormUser frgFormUser = new FrgFormUser();
        actionButton.setVisibility(View.INVISIBLE);
        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("userForm", user);
        frgFormUser.setArguments(bundle);
        transaction.replace(R.id.activity_main_frame, frgFormUser, "frgFormUser");
        transaction.addToBackStack("frgFormUser");
        transaction.commit();
    }

    private void openFormMeeting(PoMeeting meeting) {
        FrgFormMeeting frgFormMeeting = new FrgFormMeeting();
        actionButton.setVisibility(View.INVISIBLE);
        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("meetingForm", meeting);
        frgFormMeeting.setArguments(bundle);
        transaction.replace(R.id.activity_main_frame, frgFormMeeting, "frgFormMeeting");
        transaction.addToBackStack("frgFormMeeting");
        transaction.commit();
    }

    private void openFormDocument(PoDocument document) {
        FrgFormDocument frgFormDocument = new FrgFormDocument();
        actionButton.setVisibility(View.INVISIBLE);
        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("documentForm", document);
        frgFormDocument.setArguments(bundle);
        transaction.replace(R.id.activity_main_frame, frgFormDocument, "frgFormDocument");
        transaction.addToBackStack("frgFormDocument");
        transaction.commit();
    }

    private void openFormEntry(PoEntry entry) {
        FrgFormEntry frgFormEntry = new FrgFormEntry();
        actionButton.setVisibility(View.INVISIBLE);
        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("entryForm", entry);
        frgFormEntry.setArguments(bundle);
        transaction.replace(R.id.activity_main_frame, frgFormEntry, "frgFormEntry");
        transaction.addToBackStack("frgFormEntry");
        transaction.commit();
    }

    private void openFormIncidence(PoIncidence incidence) {
        FrgFormIncidence frgFormIncidence = new FrgFormIncidence();
        actionButton.setVisibility(View.INVISIBLE);
        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("incidenceForm", incidence);
        frgFormIncidence.setArguments(bundle);
        transaction.replace(R.id.activity_main_frame, frgFormIncidence, "frgFormIncidence");
        transaction.addToBackStack("frgFormIncidence");
        transaction.commit();
    }

    private void showAbout() {
        actionButton.setVisibility(View.INVISIBLE);
        //
    }

    private void showHelp() {
        actionButton.setVisibility(View.INVISIBLE);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgHelp, "frgHelp");
        transaction.commit();
    }

    private void showSettings() {
        actionButton.setVisibility(View.INVISIBLE);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgSettings, "frgSettings");
        transaction.commit();
    }

    private void showProfile() {
        actionButton.setVisibility(View.INVISIBLE);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgProfile, "frgProfile");
        transaction.commit();
    }

    private void showCommunities() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgCommunity, "frgCommunity");
        transaction.commit();
    }

    private void showUsers() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgUser, "frgUser");
        transaction.commit();
    }

    private void showMeetings() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgMeeting, "frgMeeting");
        transaction.commit();
    }

    private void showDocuments() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgDocument, "frgDocument");
        transaction.commit();
    }

    private void showEntryFirst() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
        frgEntry = new FrgEntry();
        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("categoryFirst", PoEntry.CATEGORY_FIRST);
        frgEntry.setArguments(bundle);
        transaction.replace(R.id.activity_main_frame, frgEntry, "frgEntry");
        transaction.commit();
    }

    private void showEntrySecond() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
        frgEntry = new FrgEntry();
        transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("categorySecond", PoEntry.CATEGORY_SECOND);
        frgEntry.setArguments(bundle);
        transaction.replace(R.id.activity_main_frame, frgEntry, "frgEntry");
        transaction.commit();
    }

    private void showIncidents() {
        actionButton.setVisibility(View.VISIBLE);
        actionButton.setImageResource(R.drawable.ic_plus_white_48dp);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgIncidence, "frgIncidence");
        transaction.commit();
    }

    private void closeSesion() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void showHome() {
        actionButton.setVisibility(View.INVISIBLE);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgHome, "frgHome");
        transaction.commit();
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
}
