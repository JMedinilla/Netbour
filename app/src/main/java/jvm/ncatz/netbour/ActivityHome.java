package jvm.ncatz.netbour;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
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
        FrgMeeting.ListMeeting, FrgIncidence.ListIncidence, FrgEntry.ListEntry,
        FrgDocument.ListDocument, FrgCommunity.ListCommunity {

    public static final int FRAGMENT_HOME = 100;
    public static final int FRAGMENT_HELP = 101;
    public static final int FRAGMENT_PROFILE = 102;
    public static final int FRAGMENT_SETTINGS = 103;
    public static final int FRAGMENT_ABOUT = 104;
    public static final int FRAGMENT_LIST_COMMUNITY = 110;
    public static final int FRAGMENT_LIST_DOCUMENT = 111;
    public static final int FRAGMENT_LIST_ENTRYF = 112;
    public static final int FRAGMENT_LIST_ENTRYS = 113;
    public static final int FRAGMENT_LIST_INCIDENCE = 114;
    public static final int FRAGMENT_LIST_MEETING = 115;
    public static final int FRAGMENT_LIST_USER = 116;
    public static final int DURATION_SHORT = 1;
    public static final int DURATION_LONG = 2;

    @BindView(R.id.activity_main_drawer)
    DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_navigation)
    NavigationView navigationView;
    @BindView(R.id.activity_main_coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.activity_main_toolbar_text)
    TextView toolbarText;
    @BindView(R.id.activity_main_action)
    FloatingActionButton actionButton;

    @OnClick(R.id.activity_main_action)
    public void actionClick(View view) {
        switch (fragment_opened) {
            case FRAGMENT_LIST_COMMUNITY:
                openFormCommunity(null);
                break;
            case FRAGMENT_LIST_DOCUMENT:
                openFormDocument(null);
                break;
            case FRAGMENT_LIST_ENTRYF:
            case FRAGMENT_LIST_ENTRYS:
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

    CircleImageView profile_image;
    TextView profile_name;

    private int fragment_opened;
    private String actual_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        actual_code = "default";

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean transaction = false;

                switch (item.getItemId()) {
                    case R.id.groupOptions_Incidences:
                        if (!actual_code.equals("default")) {
                            clearFragmentStack();
                            showIncidents();
                            transaction = true;
                        } else {
                            showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                        }
                        break;
                    case R.id.groupOptions_Board:
                        if (!actual_code.equals("default")) {
                            clearFragmentStack();
                            showEntryFirst();
                            transaction = true;
                        } else {
                            showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                        }
                        break;
                    case R.id.groupOptions_ComBoard:
                        if (!actual_code.equals("default")) {
                            clearFragmentStack();
                            showEntrySecond();
                            transaction = true;
                        } else {
                            showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                        }
                        break;
                    case R.id.groupOptions_Documents:
                        if (!actual_code.equals("default")) {
                            clearFragmentStack();
                            showDocuments();
                            transaction = true;
                        } else {
                            showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                        }
                        break;
                    case R.id.groupOptions_Meetings:
                        if (!actual_code.equals("default")) {
                            clearFragmentStack();
                            showMeetings();
                            transaction = true;
                        } else {
                            showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                        }
                        break;
                    case R.id.groupOptions_Users:
                        if (!actual_code.equals("default")) {
                            clearFragmentStack();
                            showUsers();
                            transaction = true;
                        } else {
                            showSnackbar(getString(R.string.default_community_code), DURATION_SHORT);
                        }
                        break;
                    case R.id.groupOptions_Communities:
                        clearFragmentStack();
                        showCommunities();
                        transaction = true;
                        break;
                    case R.id.groupOptions_Profile:
                        clearFragmentStack();
                        showProfile();
                        transaction = true;
                        break;
                    case R.id.groupOthers_Settings:
                        clearFragmentStack();
                        showSettings();
                        transaction = true;
                        break;
                    case R.id.groupOthers_Help:
                        clearFragmentStack();
                        showHelp();
                        transaction = true;
                        break;
                    case R.id.groupOthers_About:
                        clearFragmentStack();
                        showAbout();
                        transaction = true;
                        break;
                    case R.id.groupClose_Close:
                        clearFragmentStack();
                        closeSesion();
                        transaction = true;
                        break;
                }

                if (transaction) {
                    item.setChecked(true);
                    changeActionTitle(item.getTitle());
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
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("communityForm", community);

        FrgFormCommunity frgFormCommunity = new FrgFormCommunity();
        frgFormCommunity.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormCommunity, "frgFormCommunity");
        transaction.addToBackStack("frgFormCommunity");
        transaction.commit();
    }

    private void openFormUser(PoUser user) {
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("userForm", user);
        bundle.putString("comcode", actual_code);

        FrgFormUser frgFormUser = new FrgFormUser();
        frgFormUser.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormUser, "frgFormUser");
        transaction.addToBackStack("frgFormUser");
        transaction.commit();
    }

    private void openFormMeeting(PoMeeting meeting) {
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("meetingForm", meeting);
        bundle.putString("comcode", actual_code);

        FrgFormMeeting frgFormMeeting = new FrgFormMeeting();
        frgFormMeeting.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormMeeting, "frgFormMeeting");
        transaction.addToBackStack("frgFormMeeting");
        transaction.commit();
    }

    private void openFormDocument(PoDocument document) {
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("documentForm", document);
        bundle.putString("comcode", actual_code);

        FrgFormDocument frgFormDocument = new FrgFormDocument();
        frgFormDocument.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormDocument, "frgFormDocument");
        transaction.addToBackStack("frgFormDocument");
        transaction.commit();
    }

    private void openFormEntry(PoEntry entry) {
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("entryForm", entry);
        bundle.putString("comcode", actual_code);

        FrgFormEntry frgFormEntry = new FrgFormEntry();
        frgFormEntry.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormEntry, "frgFormEntry");
        transaction.addToBackStack("frgFormEntry");
        transaction.commit();
    }

    private void openFormIncidence(PoIncidence incidence) {
        actionButton.setVisibility(View.INVISIBLE);

        Bundle bundle = new Bundle();
        bundle.putParcelable("incidenceForm", incidence);
        bundle.putString("comcode", actual_code);

        FrgFormIncidence frgFormIncidence = new FrgFormIncidence();
        frgFormIncidence.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgFormIncidence, "frgFormIncidence");
        transaction.addToBackStack("frgFormIncidence");
        transaction.commit();
    }

    private void showAbout() {
        if (fragment_opened != FRAGMENT_ABOUT) {
            actionButton.setVisibility(View.INVISIBLE);

            //

            fragment_opened = FRAGMENT_ABOUT;
        }
    }

    private void showHelp() {
        if (fragment_opened != FRAGMENT_HELP) {
            actionButton.setVisibility(View.INVISIBLE);

            FrgHelp frgHelp = new FrgHelp();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgHelp, "frgHelp");
            transaction.commit();

            fragment_opened = FRAGMENT_HELP;
        }
    }

    private void showSettings() {
        if (fragment_opened != FRAGMENT_SETTINGS) {
            actionButton.setVisibility(View.INVISIBLE);

            FrgSettings frgSettings = new FrgSettings();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgSettings, "frgSettings");
            transaction.commit();

            fragment_opened = FRAGMENT_SETTINGS;
        }
    }

    private void showProfile() {
        if (fragment_opened != FRAGMENT_PROFILE) {
            actionButton.setVisibility(View.INVISIBLE);

            FrgProfile frgProfile = new FrgProfile();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgProfile, "frgProfile");
            transaction.commit();

            fragment_opened = FRAGMENT_PROFILE;
        }
    }

    private void showCommunities() {
        if (fragment_opened != FRAGMENT_LIST_COMMUNITY) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setImageResource(R.drawable.ic_plus_white_48dp);

            FrgCommunity frgCommunity = new FrgCommunity();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgCommunity, "frgCommunity");
            transaction.commit();

            fragment_opened = FRAGMENT_LIST_COMMUNITY;
        }
    }

    private void showUsers() {
        if (fragment_opened != FRAGMENT_LIST_USER) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setImageResource(R.drawable.ic_plus_white_48dp);

            Bundle bundle = new Bundle();
            bundle.putString("comcode", actual_code);

            FrgUser frgUser = new FrgUser();
            frgUser.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgUser, "frgUser");
            transaction.commit();

            fragment_opened = FRAGMENT_LIST_USER;
        }
    }

    private void showMeetings() {
        if (fragment_opened != FRAGMENT_LIST_MEETING) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setImageResource(R.drawable.ic_plus_white_48dp);

            Bundle bundle = new Bundle();
            bundle.putString("comcode", actual_code);

            FrgMeeting frgMeeting = new FrgMeeting();
            frgMeeting.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgMeeting, "frgMeeting");
            transaction.commit();

            fragment_opened = FRAGMENT_LIST_MEETING;
        }
    }

    private void showDocuments() {
        if (fragment_opened != FRAGMENT_LIST_DOCUMENT) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setImageResource(R.drawable.ic_plus_white_48dp);

            Bundle bundle = new Bundle();
            bundle.putString("comcode", actual_code);

            FrgDocument frgDocument = new FrgDocument();
            frgDocument.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgDocument, "frgDocument");
            transaction.commit();

            fragment_opened = FRAGMENT_LIST_DOCUMENT;
        }
    }

    private void showEntryFirst() {
        if (fragment_opened != FRAGMENT_LIST_ENTRYF) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setImageResource(R.drawable.ic_plus_white_48dp);

            Bundle bundle = new Bundle();
            bundle.putInt("category", PoEntry.CATEGORY_FIRST);
            bundle.putString("comcode", actual_code);

            FrgEntry frgEntry = new FrgEntry();
            frgEntry.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgEntry, "frgEntry");
            transaction.commit();

            fragment_opened = FRAGMENT_LIST_ENTRYF;
        }
    }

    private void showEntrySecond() {
        if (fragment_opened != FRAGMENT_LIST_ENTRYS) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setImageResource(R.drawable.ic_plus_white_48dp);

            Bundle bundle = new Bundle();
            bundle.putInt("category", PoEntry.CATEGORY_SECOND);
            bundle.putString("comcode", actual_code);

            FrgEntry frgEntry = new FrgEntry();
            frgEntry.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgEntry, "frgEntry");
            transaction.commit();

            fragment_opened = FRAGMENT_LIST_ENTRYS;
        }
    }

    private void showIncidents() {
        if (fragment_opened != FRAGMENT_LIST_INCIDENCE) {
            actionButton.setVisibility(View.VISIBLE);
            actionButton.setImageResource(R.drawable.ic_plus_white_48dp);

            Bundle bundle = new Bundle();
            bundle.putString("comcode", actual_code);

            FrgIncidence frgIncidence = new FrgIncidence();
            frgIncidence.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, frgIncidence, "frgIncidence");
            transaction.commit();

            fragment_opened = FRAGMENT_LIST_INCIDENCE;
        }
    }

    private void closeSesion() {
        actionButton.setVisibility(View.INVISIBLE);
    }

    private void showHome() {
        actionButton.setVisibility(View.INVISIBLE);

        FrgHome frgHome = new FrgHome();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame, frgHome, "frgHome");
        transaction.commit();

        fragment_opened = FRAGMENT_HOME;
        changeActionTitle(getString(R.string.app_name));
    }

    private void clearFragmentStack() {
        FragmentManager manager = getSupportFragmentManager();
        for (int i = 0; i < manager.getBackStackEntryCount(); i++) {
            manager.popBackStack();
        }
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

    private void changeActionTitle(CharSequence title) {
        toolbarText.setText(title);
    }

    @Override
    public void changeCode(String code) {
        actual_code = code;
        showSnackbar(getString(R.string.changed_code) + actual_code, DURATION_LONG);
    }

    @Override
    public void sendCommunity(PoCommunity item) {
        openFormCommunity(item);
    }

    @Override
    public void deletedCommunity() {
        showSnackbar(getString(R.string.community_deleted), DURATION_SHORT);
    }

    @Override
    public void sendUser(PoUser item) {
        openFormUser(item);
    }

    @Override
    public void deletedUser() {
        showSnackbar(getString(R.string.user_deleted), DURATION_SHORT);
    }

    @Override
    public void sendEntry(PoEntry item) {
        openFormEntry(item);
    }

    @Override
    public void deletedEntry() {
        showSnackbar(getString(R.string.entry_deleted), DURATION_SHORT);
    }

    @Override
    public void sendMeeting(PoMeeting item) {
        openFormMeeting(item);
    }

    @Override
    public void deletedMeeting() {
        showSnackbar(getString(R.string.meeting_deleted), DURATION_SHORT);
    }

    @Override
    public void sendDocument(PoDocument item) {
        openFormDocument(item);
    }

    @Override
    public void deletedDocument() {
        showSnackbar(getString(R.string.document_deleted), DURATION_SHORT);
    }

    @Override
    public void sendIncidence(PoIncidence item) {
        openFormIncidence(item);
    }

    @Override
    public void deletedIncidence() {
        showSnackbar(getString(R.string.incidence_deleted), DURATION_SHORT);
    }
}
